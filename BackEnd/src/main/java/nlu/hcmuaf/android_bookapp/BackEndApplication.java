package nlu.hcmuaf.android_bookapp;

import java.util.ArrayList;
import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Roles;
import nlu.hcmuaf.android_bookapp.enums.ERole;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndApplication {
  public static void main(String[] args) {
    SpringApplication.run(BackEndApplication.class, args);
  }

}
