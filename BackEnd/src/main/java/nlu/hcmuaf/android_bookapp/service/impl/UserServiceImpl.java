package nlu.hcmuaf.android_bookapp.service.impl;

import java.time.LocalDate;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.config.CustomUserDetails;
import nlu.hcmuaf.android_bookapp.config.JwtService;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Roles;
import nlu.hcmuaf.android_bookapp.entities.UserDetails;
import nlu.hcmuaf.android_bookapp.entities.Users;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserDetailRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import nlu.hcmuaf.android_bookapp.service.templates.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
  @Autowired
  private UserDetailRepository userDetailRepository;
  @Autowired
  private ModelMapper modelMapper;

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public TokenResponseDTO login(LoginRequestDTO requestDTO) {
    try {
      Optional<UserDetails> userDetails = userDetailRepository.findUserDetailsByEmail(
          requestDTO.getEmail());
      if (userDetails.isPresent()) {
        Optional<Users> user = userRepository.findUsersByUserId(userDetails.get().getUserId());
        user.get().setRoles(new Roles());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.get().getUsername(),
                requestDTO.getPassword()));

        String jwtToken = jwtService.generateToken(
            String.valueOf(new CustomUserDetails(user.get())));
        return TokenResponseDTO
            .builder()
            .token(jwtToken)
            .role(String.valueOf(user.get().getRoles()))
            .message("Login success!")
            .build();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return TokenResponseDTO
          .builder()
          .message(e.getMessage())
          .build();
    }
    return TokenResponseDTO
        .builder()
        .message("User not found")
        .build();
  }

  @Override
  public MessageResponseDTO register(@Validated RegisterRequestDTO requestDTO) {
    try {
      Optional<UserDetails> userDetail = userDetailRepository.findUserDetailsByEmail(
          requestDTO.getEmail());
      System.out.println(userDetail);
      if (!userDetail.isPresent()) {
        Users users = modelMapper.map(requestDTO, Users.class);

        UserDetails newUserDetail = new UserDetails();
        newUserDetail.setUser(users);
        newUserDetail.setEmail(requestDTO.getEmail());

        users.setCreatedDate(LocalDate.now());
        users.setUserDetails(newUserDetail);

        userRepository.save(users);
        return MessageResponseDTO
            .builder()
            .message("Register success")
            .build();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return MessageResponseDTO
          .builder()
          .message(e.getMessage())
          .build();
    }
    return MessageResponseDTO
        .builder()
        .message("User already exist")
        .build();
  }

}
