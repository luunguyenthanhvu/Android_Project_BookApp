package nlu.hcmuaf.android_bookapp.config;

import lombok.RequiredArgsConstructor;
import nlu.hcmuaf.android_bookapp.enums.ERole;
import nlu.hcmuaf.android_bookapp.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth // ủy quyền
            .requestMatchers("/api/books/**").permitAll() // ai cũng có thể truy cập
            .requestMatchers("/api/v1/auth/**").permitAll() // ai cũng có thể truy cập
            .requestMatchers("/api/v1/key/**").permitAll()
            .requestMatchers("/api/v1/user/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/user/cart/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/user/cart/update/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/user/cart/delete/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/user/address/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/user/orders/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/user/bills/**")
            .hasAnyAuthority(ERole.ADMIN.name(), ERole.MANAGER.name(), ERole.USER.name())
            .requestMatchers("/api/v1/product/fish")
            .hasAnyAuthority(ERole.ADMIN.name())// Chỉ ADMIN
            .requestMatchers("/api/v1/product/sion")
            .hasAnyAuthority(ERole.MANAGER.name())// Chỉ MANAGER
            .anyRequest()
            .authenticated())
        .sessionManagement(
            sessionManager -> sessionManager.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS)) // quản lý các phiên
        .authenticationProvider(daoAuthenticationProvider())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationProvider(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

}
