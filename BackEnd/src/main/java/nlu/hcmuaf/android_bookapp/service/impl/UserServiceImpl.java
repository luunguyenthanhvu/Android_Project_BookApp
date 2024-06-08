package nlu.hcmuaf.android_bookapp.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import nlu.hcmuaf.android_bookapp.config.CustomUserDetails;
import nlu.hcmuaf.android_bookapp.config.JwtService;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Addresses;
import nlu.hcmuaf.android_bookapp.entities.Carts;
import nlu.hcmuaf.android_bookapp.entities.Roles;
import nlu.hcmuaf.android_bookapp.entities.UserAddresses;
import nlu.hcmuaf.android_bookapp.entities.UserDetails;
import nlu.hcmuaf.android_bookapp.entities.Users;
import nlu.hcmuaf.android_bookapp.enums.EGender;
import nlu.hcmuaf.android_bookapp.enums.ERole;
import nlu.hcmuaf.android_bookapp.repositories.AddressRepository;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserDetailRepository;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IEmailService;
import nlu.hcmuaf.android_bookapp.service.templates.IUserService;
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
public class UserServiceImpl implements IUserService {

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
  @Autowired
  private AddressRepository addressRepository;


  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  @Override
  public void createDefaultAccount() {
    try {
      if (!userRepository.findUsersByUsername("vuluu").isPresent()) {
        // basic info
        Users users = new Users();
        UserDetails userDetails = new UserDetails();
        userDetails.setUser(users);
        userDetails.setEmail("giaosukirito@gmail.com");
        userDetails.setOtp(myUtils.generateOtp());
        userDetails.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));
        userDetails.setImg(
            "https://res.cloudinary.com/dter3mlpl/image/upload/v1714235572/omj17wkqm1wyzmfdukcj.jpg");
        userDetails.setVerified(true);
        userDetails.setDob(LocalDate.of(2003, 11, 8));
        userDetails.setFirstname("Vũ");
        userDetails.setLastname("Lưu");
        userDetails.setGender(EGender.MALE);
        userDetails.setPhoneNum("0123456789");

        // Address user
        Addresses addresses = new Addresses();
        addresses.setCity("Hồ Chí Minh");
        addresses.setStreet("Khu Phố 6");
        addresses.setWard("Phường Linh Trung");
        addresses.setDistrict("Thủ Đức");

        UserAddresses userAddresses = new UserAddresses(userDetails, addresses, true);
        Set<UserAddresses> userAddressesSet = new HashSet<>();
        userAddressesSet.add(userAddresses);

        addresses.setUserAddresses(userAddressesSet);
        userDetails.setUserAddresses(userAddressesSet);
        users.setCreatedDate(LocalDate.of(2023, 1, 1));
        users.setRoles(roleRepository.getRolesByRoleName(ERole.ADMIN).get());
        users.setUserDetails(userDetails);
        // Create cart
        Carts cart = new Carts();
        cart.setUser(users);
        users.setCart(cart);
        users.setPassword(passwordEncoder.encode("vuluu123"));
        users.setUsername("vuluu");

        userRepository.save(users);
        addressRepository.save(addresses);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public TokenResponseDTO login(LoginRequestDTO requestDTO) {
    try {
      Optional<Users> user = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (user.isPresent()) {
        if (passwordEncoder.matches(requestDTO.getPassword(),
            user.get().getPassword())) {
          if (user.get().getUserDetails().isVerified()) {
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
        } else {
          return TokenResponseDTO
              .builder()
              .message("WRONG PASSWORD!")
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
      Optional<Users> checkUser = userRepository.findUsersByUsername(requestDTO.getUsername());

      if (checkUser.isPresent()) {
        return MessageResponseDTO
            .builder()
            .message("Username used")
            .build();
      } else if (!userDetail.isPresent()) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Users users = modelMapper.map(requestDTO, Users.class);

        Iterable<Roles> roles = roleRepository.findAll();
        roles.forEach(role -> {
          if (role.getRoleName().equals(ERole.USER)) {
            users.setRoles(role);
          }
        });

        UserDetails newUserDetail = new UserDetails();
        newUserDetail.setUser(users);
        newUserDetail.setEmail(requestDTO.getEmail());
        // set User verification code
        newUserDetail.setVerified(false);
        String otp = myUtils.generateOtp();
        newUserDetail.setOtp(otp);
        newUserDetail.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

        users.setCreatedDate(LocalDate.now());
        users.setUserDetails(newUserDetail);
        Carts cart = new Carts();
        cart.setUser(users);
        users.setCart(cart);
        // send email to User
        emailService.sendVerificationCode(requestDTO.getEmail(), otp);
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
  public MessageResponseDTO verifyAccount(@Validated VerifyRequestDTO requestDTO) {
    try {
      Optional<Users> users = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (users.isPresent()) {

        // 1. if user verify again
        if (users.get().getUserDetails().isVerified()) {
          return MessageResponseDTO
              .builder()
              .message("User already verified")
              .build();
        }

        // 2. if otp is expiry
        if (users.get().getUserDetails().getOtpExpiryTime().isBefore(LocalDateTime.now())) {
          String otp = myUtils.generateOtp();
          userDetailRepository.updateUserOtp(otp, LocalDateTime.now().plusMinutes(5),
              requestDTO.getEmail());
          emailService.sendVerificationCode(requestDTO.getEmail(), otp);
          return MessageResponseDTO
              .builder()
              .message("Your otp is expired! Please validate again")
              .build();
        }

        // 3. if user enter wrong otp
        if (!users.get().getUserDetails().getOtp().equals(requestDTO.getOtp())) {
          return MessageResponseDTO
              .builder()
              .message("Please enter right OTP")
              .build();
        }

        int rowsUpdated = userDetailRepository.updateUserVerified(requestDTO.getEmail());
        if (rowsUpdated != 0) {
          emailService.sendThankYou(users.get().getUserDetails().getEmail());
          return MessageResponseDTO
              .builder()
              .message("Verified success")
              .build();
        }
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
