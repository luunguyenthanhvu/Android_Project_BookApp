package nlu.hcmuaf.android_bookapp.config;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import nlu.hcmuaf.android_bookapp.entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class CustomUserDetails implements UserDetails {

  private String username;
  private String password;
  Collection<? extends GrantedAuthority> authorities;

  public CustomUserDetails(Users user) {
    this.username = user.getUsername();
    this.password = user.getPassword();
    List<GrantedAuthority> auths = List.of(
        new SimpleGrantedAuthority(user.getRoles().getRoleName().toString().toUpperCase()));
    this.authorities = auths;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
