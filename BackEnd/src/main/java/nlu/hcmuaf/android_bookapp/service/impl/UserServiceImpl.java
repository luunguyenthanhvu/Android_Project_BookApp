package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.Optional;
import nlu.hcmuaf.android_bookapp.config.CustomUserDetails;
import nlu.hcmuaf.android_bookapp.config.JwtService;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Roles;
import nlu.hcmuaf.android_bookapp.entities.Users;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import nlu.hcmuaf.android_bookapp.service.templates.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private JwtService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public TokenResponseDTO login(LoginRequestDTO requestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestDTO.getUsername(),
            requestDTO.getPassword()));
    Optional<Users> user = userRepository.findUsersByUsername(requestDTO.getUsername());
    String jwtToken = jwtService.generateToken(String.valueOf(new CustomUserDetails(user.get())));
    return TokenResponseDTO
        .builder()
        .token(jwtToken)
        .role(String.valueOf(user.get().getRoles()))
        .build();
  }

  @Override
  public MessageResponseDTO register(RegisterRequestDTO requestDTO) {
//    Optional<Roles> roles = roleRepository.fin
    return null;
  }
}
