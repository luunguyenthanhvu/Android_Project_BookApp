package nlu.hcmuaf.android_bookapp.service.impl;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.Discounts;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.repositories.DiscountRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IDiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements IDiscountService {

  @Autowired
  private DiscountRepository discountRepository;

  @Autowired
  private BookRepository bookRepository;

  @Override
  @Transactional(rollbackOn = Exception.class)
  public void loadDefaultData() {
    try {
      List<Discounts> data = discountRepository.getAllBy();
      if (data.isEmpty()) {
        Discounts discounts = new Discounts();
        discounts.setPercent(0.25);
        discounts.setDescription("Giảm giá sách lô hàng cũ");
        discountRepository.save(discounts);

        List<String> listTitle = new ArrayList<>();
        listTitle.add("Thiên Sứ Nhà Bên");
        listTitle.add("Chúa Tể Bóng Tối");
        listTitle.add("Chuyện Tình Thanh Xuân Bi Hài Của Tôi Quả Nhiên Là Sai Lầm");
        listTitle.add("Arya Bàn Bên Thỉnh Thoảng Lại Trêu Ghẹo Tôi Bằng Tiếng Nga");
        for (String title : listTitle) {
          List<Books> listBookData = bookRepository.getBooksByTitle(title);
          listBookData.forEach(book -> book.setDiscounts(discounts));
          bookRepository.saveAll(listBookData);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
