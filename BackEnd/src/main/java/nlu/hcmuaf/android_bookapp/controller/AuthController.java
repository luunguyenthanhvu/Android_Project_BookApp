package nlu.hcmuaf.android_bookapp.controller;

import nlu.hcmuaf.android_bookapp.config.JwtService;
import nlu.hcmuaf.android_bookapp.dto.request.ForgotPasswordDTO;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.repositories.BookRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private IUserService userService;
  @Autowired
  private BookRepository bookRepository;

  @PostMapping("login")
  public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.login(requestDTO), HttpStatus.OK);
  }

  @PostMapping("register")
  public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.register(requestDTO), HttpStatus.OK);
  }

  @PostMapping("verify-account")
  public ResponseEntity<MessageResponseDTO> verifyAccount(
      @RequestBody VerifyRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.verifyAccount(requestDTO), HttpStatus.OK);
  }

  @PostMapping("forgot-pass")
  public ResponseEntity<MessageResponseDTO> forgotPassword(
      @RequestBody ForgotPasswordDTO requestDTO) {
    return new ResponseEntity<>(userService.forgotPassword(requestDTO), HttpStatus.OK);
  }

  @GetMapping("test/{bookId}")
  public ResponseEntity<Books> test(@PathVariable("bookId") long
      bookId) {
    return ResponseEntity.ok(bookRepository.getBooksByBookId(bookId).get());
  }
}
