package nlu.hcmuaf.android_bookapp.service.templates;

import nlu.hcmuaf.android_bookapp.entities.PublishCompany;

public interface IPublishCompanyService extends IDataInitializer{

  PublishCompany getPublishCompanyByCompanyName(String companyName);
}
