package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.Optional;
import nlu.hcmuaf.android_bookapp.config.CustomUserDetails;
import nlu.hcmuaf.android_bookapp.entities.Users;
import nlu.hcmuaf.android_bookapp.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  private static final Logger logger = (Logger) LoggerFactory.getLogger(
      UserDetailsServiceImpl.class);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> user = userRepository.findUsersByUsername(username);
    if (user == null) {
      logger.error("Username not found: " + username);
      throw new UsernameNotFoundException("could not found user..!!");
    }
    logger.info("User Authenticated Successfully...!!!");
    return new CustomUserDetails(user.get());
  }
}
