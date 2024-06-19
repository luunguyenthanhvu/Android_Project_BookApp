package nlu.hcmuaf.android_bookapp.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nlu.hcmuaf.android_bookapp.entities.Addresses;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.ShipmentDetails;
import nlu.hcmuaf.android_bookapp.entities.ShipmentTransactions;
import nlu.hcmuaf.android_bookapp.entities.Shipments;
import nlu.hcmuaf.android_bookapp.entities.Users;
import nlu.hcmuaf.android_bookapp.repositories.AddressRepository;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.repositories.ShipmentRepository;
import nlu.hcmuaf.android_bookapp.repositories.ShipmentTransactionRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements IShipmentService {

  private static final Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);

  @Autowired
  private ShipmentRepository shipmentRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private ShipmentTransactionRepository shipmentTransactionRepository;

  @Override
  public void loadDefaultData() {
    try {
      List<Shipments> defaultData = shipmentRepository.getAllBy();
      Users admin = userRepository.findUsersByUsername("vuluu")
          .orElseThrow(() -> new IllegalArgumentException("User not found"));
      if (defaultData.isEmpty()) {
        // get the books in database
        List<Books> booksList = bookRepository.getAllBy();

        // to generate default data
        int maxNum = booksList.size();

        // get the default address
        Addresses addresses = addressRepository.findById(Long.valueOf(1))
            .orElseThrow(() -> new IllegalArgumentException("Address not found"));

        // List shipment
        List<Shipments> shipmentsList = new ArrayList<>();

        // List transaction
        List<ShipmentTransactions> transactionsList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
          // generate default shipments
          Shipments shipments = new Shipments();
          shipments.setAddress(addresses);
          shipments.setUser(admin);
          int day = Math.max(1, Math.min((i * 2) * 3, 28));
          shipments.setDateAdded(LocalDate.of(2024, i + 1, day));// Ensure the day is valid

          int quantity = 0;
          Set<ShipmentDetails> shipmentDetailsSet = new HashSet<>();

          int loopX = 46 * i;
          for (int x = loopX; x < Math.min(loopX + 46, maxNum); x++) {
            ShipmentDetails shipmentDetails = new ShipmentDetails();
            // set quantity for each book
            int randomQuantity = (int) (Math.random() * 50);
            quantity += randomQuantity < 20 ? randomQuantity += 20 : randomQuantity;
            shipmentDetails.setQuantity(randomQuantity);
            shipmentDetails.setShipment(shipments);
            shipmentDetails.setAvailable(true);
            shipmentDetails.setBook(booksList.get(x));

            // add to Set<shipmentDetails>
            shipmentDetailsSet.add(shipmentDetails);
          }

          // set shipment details
          shipments.setShipmentDetails(shipmentDetailsSet);

          // set quantity for shipments
          shipments.setTotalQuantity(quantity);

          ShipmentTransactions shipmentTransactions = new ShipmentTransactions();
          shipmentTransactions.setShipment(shipments);
          shipmentTransactions.setNote("Thêm lô hàng mới");
          shipmentTransactions.setTransactionDate(
              LocalDate.of(2024, i + 1, day)); // Ensure the day is valid
          shipmentTransactions.setStatus("Nhập kho sản phẩm");
          shipmentTransactions.setQuantityChange("+" + quantity);

          // add to list transaction
          transactionsList.add(shipmentTransactions);

          Set<ShipmentTransactions> shipmentTransactionsSet = new HashSet<>();
          shipmentTransactionsSet.add(shipmentTransactions);

          // set transaction to shipments
          shipments.setShipmentTransactions(shipmentTransactionsSet);

          // add to list shipments
          shipmentsList.add(shipments);
        }
        shipmentRepository.saveAll(shipmentsList);
        shipmentTransactionRepository.saveAll(transactionsList);
      }
    } catch (Exception e) {
      logger.error("Error loading default data", e);
    }
  }
}
