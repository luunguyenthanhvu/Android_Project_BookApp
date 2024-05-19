package nlu.hcmuaf.android_bookapp.controller;

import nlu.hcmuaf.android_bookapp.dto.request.AuthRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.JwtResponseDTO;
import nlu.hcmuaf.android_bookapp.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/")
public class LoginController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/login")
  public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO requestDTO) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestDTO.getUsername(),
            requestDTO.getPassword()));
    if (authentication.isAuthenticated()) {
      return JwtResponseDTO.builder()
          .accessToken(jwtService.generateToken(requestDTO.getUsername())).build();
    } else {
      throw new UsernameNotFoundException("invalid request user");
    }
  }
}
