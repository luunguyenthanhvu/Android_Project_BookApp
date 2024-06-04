package nlu.hcmuaf.android_bookapp.service.templates;

import nlu.hcmuaf.android_bookapp.entities.PublishCompany;

public interface IPublishCompanyService {

  void loadDefaultPublishCompany();

  PublishCompany getPublishCompanyByCompanyName(String companyName);
}
