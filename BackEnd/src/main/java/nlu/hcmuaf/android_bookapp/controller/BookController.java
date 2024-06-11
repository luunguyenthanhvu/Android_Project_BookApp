package nlu.hcmuaf.android_bookapp.controller;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hcmuaf.android_bookapp.service.templates.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private IBookService bookService;

  @GetMapping("/new-book")
  public List<ListBookResponseDTO> getNewListBook(
      @PageableDefault(size = 50)
      Pageable pageable) {
    return bookService.getNewBookList(pageable).getContent();
  }

  @GetMapping("/discount-book")
  public List<ListBookResponseDTO> getDiscountListBook(
      @PageableDefault(size = 50)
      Pageable pageable) {
    return bookService.getDiscountBookList(pageable).getContent();
  }

}
