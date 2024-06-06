package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.Shipments;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.repositories.ShipmentRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentServiceImpl implements IShipmentService {

  @Autowired
  private ShipmentRepository shipmentRepository;
  @Autowired
  private BookRepository bookRepository;

  @Override
  public void loadDefaultData() {
    try {
      List<Shipments> shipmentsList = shipmentRepository.getAllBy();
      if (shipmentsList.isEmpty()) {
        List<Books> booksList = bookRepository.getAllBy();
        int maxBook = booksList.size() - 1;
        for(int i = 0; i < maxBook; i++) {

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
