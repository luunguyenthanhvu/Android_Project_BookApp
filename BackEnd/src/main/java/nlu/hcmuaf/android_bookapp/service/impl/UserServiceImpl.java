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
import nlu.hcmuaf.android_bookapp.enums.ERole;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserDetailRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IEmailService;
import nlu.hcmuaf.android_bookapp.service.templates.UserService;
import nlu.hcmuaf.android_bookapp.utils.MyUtils;
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
  @Autowired
  private IEmailService emailService;
  @Autowired
  private MyUtils myUtils;

  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public TokenResponseDTO login(LoginRequestDTO requestDTO) {
    try {
      Optional<Users> user = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (user.isPresent() && passwordEncoder.matches(requestDTO.getPassword(),
          user.get().getPassword())) {

        if (user.get().isVerified()) {
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(user.get().getUsername(),
                  requestDTO.getPassword())
          );

          String jwtToken = jwtService.generateToken(new CustomUserDetails(user.get()));
          return TokenResponseDTO
              .builder()
              .token(jwtToken)
              .role(user.get().getRoles().getRoleName().toString())
              .message("Login success!")
              .build();
        } else {
          return TokenResponseDTO
              .builder()
              .message("Please verified your account!")
              .build();
        }
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

      if (!userDetail.isPresent()) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Users users = modelMapper.map(requestDTO, Users.class);

        Iterable<Roles> roles = roleRepository.findAll();
        roles.forEach(role -> {
          if (role.getRoleName().equals(ERole.USER)) {
            users.setRoles(role);
          }
        });
        // set User verification code
        users.setVerified(false);
        users.setVerificationCode(myUtils.generateVerificationCode());

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

  @Override
  public MessageResponseDTO verifyAccount(String email, String verifyCode) {
    try {
      Optional<Users> users = userRepository.findAllInfoByEmail(email);
      if (users.isPresent()) {

        if (users.get().isVerified()) {
          return MessageResponseDTO
              .builder()
              .message("User already verified")
              .build();
        }

        if (users.get().getVerificationCode() != verifyCode) {
          return MessageResponseDTO
              .builder()
              .message("Please enter right verify code")
              .build();
        }

        userRepository.updateUserVerified(users.get().getUserId());
        emailService.sendThankYou(users.get().getUserDetails().getEmail());
        return MessageResponseDTO
            .builder()
            .message("Verified success")
            .build();
      }
    } catch (Exception e) {
      logger.error(e.getMessage());
      return MessageResponseDTO
          .builder()
          .message("500 Error")
          .build();
    }
    return MessageResponseDTO
        .builder()
        .message("User not exist")
        .build();
  }


}
