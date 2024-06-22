package nlu.hcmuaf.android_bookapp.service.templates;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookService extends IDataInitializer {

  Page<ListBookResponseDTO> getNewBookList(Pageable pageable);

  Page<ListBookResponseDTO> getDiscountBookList(Pageable pageable);

  BookDetailResponseDTO getBookDetailsByBookId(long bookId);

  List<ListBookResponseDTO> findBooks(String coverType, String publisher, Pageable pageable);
}
