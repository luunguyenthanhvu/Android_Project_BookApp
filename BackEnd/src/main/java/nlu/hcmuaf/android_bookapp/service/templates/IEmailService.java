package nlu.hcmuaf.android_bookapp.service.templates;

public interface IEmailService {

  void sendVerificationCode(String email, String verificationCode);

  void sendThankYou(String email);
}
