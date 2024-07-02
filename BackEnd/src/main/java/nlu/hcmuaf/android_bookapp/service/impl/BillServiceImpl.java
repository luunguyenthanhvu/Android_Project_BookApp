package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import nlu.hcmuaf.android_bookapp.dto.request.BillRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.CartItemRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.CartItemResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListOrderResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Addresses;
import nlu.hcmuaf.android_bookapp.entities.BillDetails;
import nlu.hcmuaf.android_bookapp.entities.Bills;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.Payments;
import nlu.hcmuaf.android_bookapp.entities.Users;
import nlu.hcmuaf.android_bookapp.enums.EBillStatus;
import nlu.hcmuaf.android_bookapp.enums.EPaymentMethod;
import nlu.hcmuaf.android_bookapp.repositories.AddressRepository;
import nlu.hcmuaf.android_bookapp.repositories.BillRepository;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.repositories.PaymentRepository;
import nlu.hcmuaf.android_bookapp.repositories.ShipmentRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements IBillService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AddressRepository addressRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private BillRepository billRepository;
  @Autowired
  private PaymentRepository paymentRepository;
  @Autowired
  private ShipmentRepository shipmentRepository;

  @Override
  public void saveUserBills(Long userId, BillRequestDTO billRequestDTO) {
    try {
      Optional<Users> optionalUsers = userRepository.findUsersByUserId(userId);
      if (optionalUsers.isPresent()) {
        Users users = optionalUsers.get();
        Addresses addresses = addressRepository.findById(billRequestDTO.getAddressId()).get();
        Payments payments = paymentRepository.findAllByPaymentMethod(
            EPaymentMethod.valueOfLabel(billRequestDTO.getPaymentMethod())).get();
        List<Books> booksList = new ArrayList<>();
        Set<BillDetails> billDetailsSet = new HashSet<>();
        Bills bills = new Bills();
        bills.setAddress(addresses);
        bills.setPayments(payments);
        bills.setUser(users);
        bills.setStatus(EBillStatus.CONFIRMING);

        // count the total price
        List<BookDetailResponseDTO> bookDetailResponseDTO = new ArrayList<>();

        billRequestDTO.getCartItemRequestDTO().forEach(i -> {
          billDetailsSet.add(
              new BillDetails(bookRepository.findById(i.getBookId()).get(), bills,
                  i.getQuantity()));
          bookDetailResponseDTO.add(bookRepository.getBooksDetailsByBookId(i.getBookId()).get());
        });
        bills.setBillDetails(billDetailsSet);

        double totalPrice = 0.0;
        for (BookDetailResponseDTO item : bookDetailResponseDTO) {
          for (CartItemRequestDTO cartItem : billRequestDTO.getCartItemRequestDTO()) {
            if (item.getBookId() == cartItem.getBookId()) {
              double price = (item.getDiscountedPrice() != 0.0)
                  ? item.getDiscountedPrice()
                  : item.getOriginalPrice();
              totalPrice += price * cartItem.getQuantity();
            }
          }
        }
        bills.setTotalPrice(totalPrice + 30000);

        billRepository.save(bills);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<ListOrderResponseDTO> getUserOrder(long userId) {
    try {
      List<Bills> bills = billRepository.findAllByUserId(userId);
      List<ListOrderResponseDTO> result = new ArrayList<>();

      for (Bills b : bills) {
        Set<BillDetails> billDetails = b.getBillDetails();
        List<CartItemResponseDTO> cartItemResponseDTOList = new ArrayList<>(); // Khởi tạo lại cho mỗi đơn hàng
        double totalPrice = 0.0; // Đặt lại giá trị 0 cho mỗi đơn hàng

        for (BillDetails item : billDetails) {
          BookDetailResponseDTO bookDetailResponseDTO = bookRepository.getBooksDetailsByBookId(
              item.getBook().getBookId()).get();
          Books books = bookRepository.getBooksByBookId(item.getBook().getBookId()).get();

          CartItemResponseDTO cartItemResponseDTO = CartItemResponseDTO
              .builder()
              .bookId(item.getBook().getBookId())
              .originalPrice(bookDetailResponseDTO.getOriginalPrice())
              .discountedPrice(bookDetailResponseDTO.getDiscountedPrice())
              .thumbnail(books.getThumbnail())
              .title(bookDetailResponseDTO.getTitle())
              .quantity(item.getQuantity())
              .availableQuantity(bookDetailResponseDTO.getAvailableQuantity())
              .build();
          cartItemResponseDTOList.add(cartItemResponseDTO);
        }

        ListOrderResponseDTO listOrderResponseDTO = ListOrderResponseDTO
            .builder()
            .bookList(cartItemResponseDTOList)
            .orderId(b.getBillId())
            .totalPrice(b.getTotalPrice())
            .paymentMethod(b.getPayments().getPaymentMethod().toString())
            .status(b.getStatus().toString())
            .build();
        result.add(listOrderResponseDTO);
      }

      return result;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

}
