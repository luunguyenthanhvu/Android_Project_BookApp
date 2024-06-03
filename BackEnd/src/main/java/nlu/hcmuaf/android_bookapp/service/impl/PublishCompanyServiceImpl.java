package nlu.hcmuaf.android_bookapp.service.impl;

import java.util.Arrays;
import nlu.hcmuaf.android_bookapp.entities.PublishCompany;
import nlu.hcmuaf.android_bookapp.repositories.PublishCompanyRepository;
import nlu.hcmuaf.android_bookapp.service.templates.IPublishCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublishCompanyServiceImpl implements IPublishCompanyService {

  private final String[] defaultCompany = {"NXB Kim Đồng", "NXB Hà Nội"};
  @Autowired
  private PublishCompanyRepository publishCompanyRepository;

  @Override
  public void loadDefaultPublishCompany() {
    if (publishCompanyRepository.getAllBy().isEmpty()) {
      Arrays.stream(defaultCompany)
          .forEach(s -> publishCompanyRepository.save(new PublishCompany(s)));
    }
  }
}
