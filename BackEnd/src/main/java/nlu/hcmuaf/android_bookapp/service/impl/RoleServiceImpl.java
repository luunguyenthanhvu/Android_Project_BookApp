package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.ArrayList;
import java.util.List;
import nlu.hcmuaf.android_bookapp.entities.Roles;
import nlu.hcmuaf.android_bookapp.enums.ERole;
import nlu.hcmuaf.android_bookapp.repositories.RoleRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public void loadDefaultData() {
    if (roleRepository.getAllBy().isEmpty()) {
      List<Roles> rolesList = new ArrayList<>();
      rolesList.add(new Roles(ERole.ADMIN));
      rolesList.add(new Roles(ERole.MANAGER));
      rolesList.add(new Roles(ERole.USER));

      roleRepository.saveAll(rolesList);
    }
  }
}
