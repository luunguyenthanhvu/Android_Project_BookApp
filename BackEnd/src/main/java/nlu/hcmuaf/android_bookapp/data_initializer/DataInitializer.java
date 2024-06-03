package nlu.hcmuaf.android_bookapp.data_initializer;

import nlu.hcmuaf.android_bookapp.service.templates.IPaymentService;
import nlu.hcmuaf.android_bookapp.service.templates.IPublishCompanyService;
import nlu.hcmuaf.android_bookapp.service.templates.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

  @Autowired
  private IPaymentService paymentService;
  @Autowired
  private IRoleService roleService;
  @Autowired
  private IPublishCompanyService publishCompanyService;

  @Override
  public void run(String... args) throws Exception {
    // Generate default role
    roleService.loadDefaultRole();

    // Generate default company
    publishCompanyService.loadDefaultPublishCompany();

    // Generate default payment
    paymentService.loadDefaultPayment();

  }
}