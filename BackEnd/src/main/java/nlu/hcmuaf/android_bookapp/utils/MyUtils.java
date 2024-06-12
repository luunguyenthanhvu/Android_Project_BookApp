package nlu.hcmuaf.android_bookapp.utils;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class MyUtils {

  public String generateOtp() {
    Random random = new Random();
    return String.format("%06d", random.nextInt(999999));
  }

  public String generateRandomPassword(int length) {
    String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder password = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      int index = random.nextInt(charset.length());
      password.append(charset.charAt(index));
    }
    return password.toString();
  }
}
