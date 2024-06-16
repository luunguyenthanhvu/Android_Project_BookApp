package nlu.hcmuaf.android_bookapp.data_initializer;

import nlu.hcmuaf.android_bookapp.service.templates.IBookService;
import nlu.hcmuaf.android_bookapp.service.templates.IDiscountService;
import nlu.hcmuaf.android_bookapp.service.templates.IPaymentService;
import nlu.hcmuaf.android_bookapp.service.templates.IPublishCompanyService;
import nlu.hcmuaf.android_bookapp.service.templates.IRoleService;
import nlu.hcmuaf.android_bookapp.service.templates.IShipmentService;
import nlu.hcmuaf.android_bookapp.service.templates.IUserService;
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
  @Autowired
  private IBookService bookService;
  @Autowired
  private IUserService userService;
  @Autowired
  private IShipmentService shipmentService;
  @Autowired
  private IDiscountService discountService;

  @Override
  public void run(String... args) throws Exception {
    // Generate default role
    roleService.loadDefaultData();

    // Generate default company
    publishCompanyService.loadDefaultData();

    // Generate default payment
    paymentService.loadDefaultData();

    // Generate default books
    bookService.loadDefaultData();

    // Generate admin account
    userService.loadDefaultData();

    // Generate default shipment
    shipmentService.loadDefaultData();

    // Generate default discount
    discountService.loadDefaultData();
  }
}
