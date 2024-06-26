package nlu.hcmuaf.android_bookapp.controller;

import java.util.List;
import nlu.hcmuaf.android_bookapp.dto.response.BookDetailResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListBookResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.PageBookResponseDTO;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private IBookService bookService;
  @Autowired
  private BookRepository repository;

  @GetMapping("/new-book")
  public List<ListBookResponseDTO> getNewListBook(
      @PageableDefault(size = 30)
      Pageable pageable) {
    return bookService.getNewBookList(pageable).getContent();
  }

  @GetMapping("/discount-book")
  public List<ListBookResponseDTO> getDiscountListBook(
      @PageableDefault(size = 30)
      Pageable pageable) {
    return bookService.getDiscountBookList(pageable).getContent();
  }

  @GetMapping("/book-details/{bookId}")
  public ResponseEntity<BookDetailResponseDTO> getBookDetails(@PathVariable long bookId) {
    return ResponseEntity.ok(bookService.getBookDetailsByBookId(bookId));
  }

  @GetMapping("/get-books")
  public ResponseEntity<PageBookResponseDTO> findBooks(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String bookType,
      @RequestParam(required = false) String coverType,
      @RequestParam(required = false) String publisher,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "15") int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    return ResponseEntity.ok(
        bookService.findBooks(title, bookType, coverType, publisher, pageRequest));
  }
}
