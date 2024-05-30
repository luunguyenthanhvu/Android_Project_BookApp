package nlu.hcmuaf.android_bookapp.utils;

import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class MyUtils {

  public String generateVerificationCode() {
    Random random = new Random();
    return String.format("%06d", random.nextInt(999999));
  }
}
