package nlu.hcmuaf.android_bookapp.controller;

import nlu.hcmuaf.android_bookapp.config.JwtService;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_bookapp.service.templates.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserService userService;

  @PostMapping("login")
  public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.login(requestDTO), HttpStatus.OK);
  }

  @PostMapping("register")
  public ResponseEntity<MessageResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) {
    return new ResponseEntity<>(userService.register(requestDTO), HttpStatus.OK);
  }

}
