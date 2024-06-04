package nlu.hcmuaf.android_bookapp.repositories;

import java.util.List;
import java.util.Optional;
import nlu.hcmuaf.android_bookapp.entities.PublishCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishCompanyRepository extends JpaRepository<PublishCompany, Long> {

  @Query("SELECT P from Publish_Company P")
  List<PublishCompany> getAllBy();

  Optional<PublishCompany> getPublishCompanyByCompanyName(String companyName);
}
