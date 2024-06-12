package nlu.hcmuaf.android_bookapp.service.impl;

import nlu.hcmuaf.android_bookapp.entities.Payments;
import nlu.hcmuaf.android_bookapp.enums.EPaymentMethod;
import nlu.hcmuaf.android_bookapp.repositories.PaymentRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public void loadDefaultData() {
    if (paymentRepository.getAllBy().isEmpty()) {
      for (EPaymentMethod method : EPaymentMethod.values()) {
        Payments payments = new Payments(method);
        paymentRepository.save(payments);
      }
    }
  }
}
