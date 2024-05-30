package nlu.hcmuaf.android_bookapp;

import java.util.ArrayList;
import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Roles;
import nlu.hcmuaf.android_bookapp.enums.ERole;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.service.templates.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndApplication implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private BookService bookService;

  public static void main(String[] args) {
    SpringApplication.run(BackEndApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (roleRepository.findAll() != null) {
      List<Roles> rolesList = new ArrayList<>();
      rolesList.add(new Roles(ERole.ADMIN));
      rolesList.add(new Roles(ERole.MANAGER));
      rolesList.add(new Roles(ERole.USER));

      roleRepository.saveAll(rolesList);
    }

//    bookService.loadDefaultData();

  }
}
