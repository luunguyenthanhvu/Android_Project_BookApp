package nlu.hcmuaf.android_bookapp.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import nlu.hcmuaf.android_bookapp.config.CustomUserDetails;
import nlu.hcmuaf.android_bookapp.config.JwtService;
import nlu.hcmuaf.android_bookapp.dto.request.AddressRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.ForgotPasswordDTO;
import nlu.hcmuaf.android_bookapp.dto.request.LoginRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.RegisterRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.request.VerifyRequestDTO;
import nlu.hcmuaf.android_bookapp.dto.response.ListAddressResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_bookapp.dto.response.TokenResponseDTO;
import nlu.hcmuaf.android_bookapp.entities.Addresses;
import nlu.hcmuaf.android_bookapp.entities.Carts;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
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
  public void loadDefaultData() {
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
        userDetailRepository.save(userDetails);

        // Address user
        Addresses addresses1 = new Addresses();
        addresses1.setAddressDetails(
            "140 Đường Cầu Xây 2, Phường Tân Phú, Quận 9, Hồ Chí Minh, Việt Nam");

        Addresses addresses2 = new Addresses();
        addresses2.setAddressDetails("VQCR+GP6, Khu Phố 6, Thủ Đức, Hồ Chí Minh, Việt Nam");

        List<Addresses> addressesList = new ArrayList<>();
        addressesList.add(addresses1);
        addressesList.add(addresses2);

        // Save addresses first
        addressRepository.saveAll(addressesList);

        // Create UserAddresses
        UserAddresses userAddresses1 = new UserAddresses(userDetails, addresses1, true);
        UserAddresses userAddresses2 = new UserAddresses(userDetails, addresses2, false);

        Set<UserAddresses> userAddressesSet = new HashSet<>();
        userAddressesSet.add(userAddresses1);
        userAddressesSet.add(userAddresses2);

        addresses1.setUserAddresses(userAddressesSet);
        addresses2.setUserAddresses(userAddressesSet);

        userDetails.setUserAddresses(userAddressesSet);

        // Set user details
        users.setCreatedDate(LocalDate.of(2023, 1, 1));
        users.setRoles(roleRepository.getRolesByRoleName(ERole.ADMIN)
            .orElseThrow(() -> new RuntimeException("Role not found")));
        users.setUserDetails(userDetails);

        // Create cart
        Carts cart = new Carts();
        cart.setUser(users);
        users.setCart(cart);

        // Set password
        users.setPassword(passwordEncoder.encode("vuluu123"));
        users.setUsername("vuluu");

        userRepository.save(users);
      }
    } catch (Exception e) {
      logger.error("Failed to load default data: " + e.getMessage());
      e.printStackTrace();
    }
  }

  @Override
  public TokenResponseDTO login(LoginRequestDTO requestDTO) {
    try {
      Optional<Users> user = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (user.isPresent()) {
        if (passwordEncoder.matches(requestDTO.getPassword(), user.get().getPassword())) {
          if (user.get().getUserDetails().isVerified()) {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.get().getUsername(),
                    requestDTO.getPassword()));

            String jwtToken = jwtService.generateToken(new CustomUserDetails(user.get()));
            return TokenResponseDTO.builder()
                .token(jwtToken)
                .userId(user.get().getUserId())
                .username(user.get().getUsername())
                .role(user.get().getRoles().getRoleName().toString())
                .username(user.get().getUsername())
                .email(user.get().getUserDetails().getEmail())
                .img(user.get().getUserDetails().getImg())
                .message("Login success!")
                .build();
          } else {
            String otp = myUtils.generateOtp();
            userDetailRepository.updateUserOtp(otp, LocalDateTime.now().plusMinutes(5),
                requestDTO.getEmail());
            emailService.sendVerificationCode(requestDTO.getEmail(), otp);
            return TokenResponseDTO.builder()
                .message("Please verify your account!")
                .build();
          }
        } else {
          return TokenResponseDTO.builder()
              .message("WRONG PASSWORD!")
              .build();
        }
      }
    } catch (Exception e) {
      logger.error("Login failed: " + e.getMessage());
      e.printStackTrace();
      return TokenResponseDTO.builder()
          .message("Failed to login: " + e.getMessage())
          .build();
    }
    return TokenResponseDTO.builder()
        .message("User not found!")
        .build();
  }

  @Override
  public MessageResponseDTO register(@Validated RegisterRequestDTO requestDTO) {
    try {
      Optional<UserDetails> userDetail = userDetailRepository.findUserDetailsByEmail(
          requestDTO.getEmail());
      Optional<Users> checkUser = userRepository.findUsersByUsername(requestDTO.getUsername());

      if (checkUser.isPresent()) {
        return MessageResponseDTO.builder()
            .message("Username used!")
            .build();
      } else if (!userDetail.isPresent()) {
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        Users users = modelMapper.map(requestDTO, Users.class);

        roleRepository.getRolesByRoleName(ERole.USER).ifPresent(users::setRoles);

        UserDetails newUserDetail = new UserDetails();
        newUserDetail.setUser(users);
        newUserDetail.setEmail(requestDTO.getEmail());
        newUserDetail.setVerified(false);
        String otp = myUtils.generateOtp();
        newUserDetail.setOtp(otp);
        newUserDetail.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

        users.setCreatedDate(LocalDate.now());
        users.setUserDetails(newUserDetail);

        Carts cart = new Carts();
        cart.setUser(users);
        users.setCart(cart);

        emailService.sendVerificationCode(requestDTO.getEmail(), otp);
        userRepository.save(users);

        return MessageResponseDTO.builder()
            .message("Register success!")
            .build();
      }
    } catch (Exception e) {
      logger.error("Failed to register: " + e.getMessage());
      e.printStackTrace();
      return MessageResponseDTO.builder()
          .message("Failed to register: " + e.getMessage())
          .build();
    }
    return MessageResponseDTO.builder()
        .message("User already exists!")
        .build();
  }

  @Override
  public MessageResponseDTO verifyAccount(@Validated VerifyRequestDTO requestDTO) {
    try {
      Optional<Users> users = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (users.isPresent()) {
        if (users.get().getUserDetails().isVerified()) {
          return MessageResponseDTO.builder()
              .message("User already verified")
              .build();
        }

        if (users.get().getUserDetails().getOtpExpiryTime().isBefore(LocalDateTime.now())) {
          String otp = myUtils.generateOtp();
          userDetailRepository.updateUserOtp(otp, LocalDateTime.now().plusMinutes(5),
              requestDTO.getEmail());
          emailService.sendVerificationCode(requestDTO.getEmail(), otp);
          return MessageResponseDTO.builder()
              .message("Your otp has expired! Please validate again")
              .build();
        }

        if (!users.get().getUserDetails().getOtp().equals(requestDTO.getOtp())) {
          return MessageResponseDTO.builder()
              .message("Please enter the correct OTP")
              .build();
        }

        int rowsUpdated = userDetailRepository.updateUserVerified(requestDTO.getEmail());
        if (rowsUpdated != 0) {
          emailService.sendThankYou(users.get().getUserDetails().getEmail());
          return MessageResponseDTO.builder()
              .message("Verified successfully")
              .build();
        }
      }
    } catch (Exception e) {
      logger.error("Failed to verify account: " + e.getMessage());
      e.printStackTrace();
      return MessageResponseDTO.builder()
          .message("Failed to verify account: " + e.getMessage())
          .build();
    }
    return MessageResponseDTO.builder()
        .message("User does not exist")
        .build();
  }

  @Override
  public MessageResponseDTO forgotPassword(ForgotPasswordDTO requestDTO) {
    try {
      Optional<Users> data = userRepository.findAllInfoByEmail(requestDTO.getEmail());
      if (data.isPresent()) {
        Users users = data.get();
        String password = myUtils.generateRandomPassword(10);
        users.setPassword(passwordEncoder.encode(password));
        userRepository.save(users);
        emailService.sendNewPass(users.getUserDetails().getEmail(), password);
        return MessageResponseDTO.builder()
            .message("Update password success")
            .build();
      }
    } catch (Exception e) {
      logger.error("Failed to update password: " + e.getMessage());
      e.printStackTrace();
      return MessageResponseDTO.builder()
          .message("Failed to update password: " + e.getMessage())
          .build();
    }
    return MessageResponseDTO.builder()
        .message("User does not exist")
        .build();
  }

  @Override
  public List<ListAddressResponseDTO> addNewAddress(long userId, AddressRequestDTO requestDTO) {
    try {
      Optional<UserDetails> optional = userDetailRepository.findAllUserDetailsInfoByUserId(userId);
      if (optional.isPresent()) {
        UserDetails userDetails = optional.get();
        Set<UserAddresses> userAddressesSet = userDetails.getUserAddresses();

        // Handle mainAddress status
        if (requestDTO.isMainAddress()) {
          // If the new address is the main address, update other addresses accordingly
          userAddressesSet.forEach(data -> {
            if (data.isMainAddress()) {
              data.setMainAddress(false);
            }
          });
        }

        // Create a new Addresses object
        Addresses newAddress = Addresses.builder()
            .addressDetails(requestDTO.getAddressDetails())
            .build();
        addressRepository.save(newAddress);

        // Create a new UserAddresses and add it to the set
        UserAddresses newUserAddress = UserAddresses.builder()
            .address(newAddress)
            .userDetails(userDetails)
            .mainAddress(requestDTO.isMainAddress())
            .build();
        userAddressesSet.add(newUserAddress);

        // Save changes to the database
        userDetailRepository.save(userDetails);

        // Return the updated list of addresses
        Optional<UserDetails> optionalUserDetails = userDetailRepository.findAllUserDetailsInfoByUserId(
            userId);
        if (optionalUserDetails.isPresent()) {
          UserDetails userDetail = optional.get();
          Set<UserAddresses> userAddressesSets = userDetail.getUserAddresses();
          return userAddressesSets.stream()
              .map(i -> new ListAddressResponseDTO(i.getAddress().getAddressId(),
                  i.getAddress().getAddressDetails(),
                  i.isMainAddress()))
              .collect(Collectors.toList());
        }
      }
    } catch (Exception e) {
      logger.error("Failed to add address: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to add address: " + e.getMessage(), e);
    }
    return new ArrayList<>();
  }


  @Override
  public List<ListAddressResponseDTO> getListAddress(long userId) {
    try {
      Optional<UserDetails> optional = userDetailRepository.findAllUserDetailsInfoByUserId(userId);
      if (optional.isPresent()) {
        UserDetails userDetails = optional.get();
        Set<UserAddresses> userAddressesSet = userDetails.getUserAddresses();

        // Ensure userAddressesSet is initialized (if using Hibernate)
        if (userAddressesSet == null) {
          userAddressesSet = new HashSet<>();
        }

        return userAddressesSet.stream()
            .map(i -> new ListAddressResponseDTO(i.getAddress().getAddressId(),
                i.getAddress().getAddressDetails(),
                i.isMainAddress()))
            .collect(Collectors.toList());
      } else {
        logger.warn("No user details found for userId: " + userId);
      }
    } catch (Exception e) {
      logger.error("Failed to get list of addresses: " + e.getMessage());
      e.printStackTrace();
      // Log the exception with a logger instead of printStackTrace for production
    }
    return new ArrayList<>();
  }

  @Override
  public List<ListAddressResponseDTO> updateAddress(long userId, AddressRequestDTO requestDTO) {
    try {
      Optional<UserDetails> optional = userDetailRepository.findAllUserDetailsInfoByUserId(userId);
      if (optional.isPresent()) {
        UserDetails userDetails = optional.get();
        Set<UserAddresses> userAddressesSet = userDetails.getUserAddresses();

        // Find the current UserAddresses based on requestDTO.getAddressId()
        UserAddresses userAddresses = userAddressesSet.stream()
            .filter(address -> address.getAddress().getAddressId() == requestDTO.getAddressId())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Address not found"));

        // Remove the current UserAddresses from the set
        userAddressesSet.remove(userAddresses);

        // Create or update the current address information
        Addresses addressToUpdate = userAddresses.getAddress();
        addressToUpdate.setAddressDetails(requestDTO.getAddressDetails());
        addressRepository.save(addressToUpdate);

        // Handle mainAddress status
        if (requestDTO.isMainAddress()) {
          // If the new address is the main address, update other addresses accordingly
          userAddressesSet.forEach(data -> {
            if (data.isMainAddress()) {
              data.setMainAddress(false);
            }
          });
        }

        // Create a new UserAddresses and add it to the set
        UserAddresses updatedUserAddress = UserAddresses.builder()
            .address(addressToUpdate)
            .userDetails(userDetails)
            .mainAddress(requestDTO.isMainAddress())
            .build();
        userAddressesSet.add(updatedUserAddress);

        // Save changes to the database
        userDetailRepository.save(userDetails);

        // Return the updated list of addresses
        return userAddressesSet.stream()
            .map(address -> new ListAddressResponseDTO(
                address.getAddress().getAddressId(),
                address.getAddress().getAddressDetails(),
                address.isMainAddress()
            ))
            .collect(Collectors.toList());
      } else {
        throw new RuntimeException("User not found");
      }
    } catch (Exception e) {
      logger.error("Failed to update address: " + e.getMessage());
      e.printStackTrace();
      throw new RuntimeException("Failed to update address: " + e.getMessage(), e);
    }
  }

  @Override
  public List<ListAddressResponseDTO> deleteAddress(long userId, long addressId) {
    try {
      userDetailRepository.deleteUserAddressByUserIdAndAddressId(userId, addressId);
      Optional<UserDetails> optional = userDetailRepository.findAllUserDetailsInfoByUserId(
          userId);
      if (optional.isPresent()) {
        UserDetails userDetail = optional.get();
        Set<UserAddresses> userAddressesSet = userDetail.getUserAddresses();
        return userAddressesSet.stream()
            .map(i -> new ListAddressResponseDTO(i.getAddress().getAddressId(),
                i.getAddress().getAddressDetails(),
                i.isMainAddress()))
            .collect(Collectors.toList());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
