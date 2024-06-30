package nlu.hcmuaf.android_bookapp.repositories;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.UserDetails;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailRepository extends CrudRepository<UserDetails, Long> {

  @Query("SELECT u FROM User_Details u WHERE u.email = :email")
  Optional<UserDetails> findUserDetailsByEmail(@Param("email") String email);

  @Modifying
  @Transactional
  @Query("UPDATE User_Details  u SET u.verified = true WHERE u.email = :email")
  int updateUserVerified(@Param("email") String email);

  @Modifying
  @Transactional
  @Query("UPDATE User_Details u SET u.otp = :otp, u.otpExpiryTime = :otpExpiryTime WHERE u.email = :email")
  int updateUserOtp(@Param("otp") String otp, @Param("otpExpiryTime") LocalDateTime otpExpiryTime,
      @Param("email") String email);

  @Query("SELECT ud FROM User_Details ud JOIN FETCH ud.userAddresses ua JOIN FETCH ua.address ad WHERE ud.user.userId = :userId")
  Optional<UserDetails> findAllUserDetailsInfoByUserId(@Param("userId") long userId);

  @Modifying
  @Transactional
  @Query("DELETE User_Addresses ua WHERE ua.userDetails.userId = :userId AND ua.address.addressId =:addressId")
  void deleteUserAddressByUserIdAndAddressId(@Param("userId") long userId,
      @Param("addressId") long addressId);
}
