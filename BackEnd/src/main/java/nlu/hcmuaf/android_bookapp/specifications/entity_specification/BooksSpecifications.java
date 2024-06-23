package nlu.hcmuaf.android_bookapp.specifications.entity_specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import nlu.hcmuaf.android_bookapp.entities.BookDetails;
import nlu.hcmuaf.android_bookapp.entities.Books;
import nlu.hcmuaf.android_bookapp.entities.Discounts;
import nlu.hcmuaf.android_bookapp.entities.PublishCompany;
import nlu.hcmuaf.android_bookapp.entities.ShipmentDetails;
import nlu.hcmuaf.android_bookapp.entities.Shipments;
import nlu.hcmuaf.android_bookapp.enums.EBookFormat;
import nlu.hcmuaf.android_bookapp.specifications.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

public class BooksSpecifications implements Specification<Books> {

  private SearchCriteria criteria;

  public BooksSpecifications(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate
      (Root<Books> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (criteria.getKey().equalsIgnoreCase("title")) {
      return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
    }

    if (criteria.getKey().equalsIgnoreCase("newBook")) {
      SetJoin<Books, ShipmentDetails> shipmentDetailsJoin = root.joinSet("shipmentDetails");
      // Join từ ShipmentDetails tới Shipments
      Join<ShipmentDetails, Shipments> shipmentJoin = shipmentDetailsJoin.join("shipment");
      query.orderBy(builder.desc(shipmentJoin.get("dateAdded")));
      return builder.isNotNull(shipmentJoin.get("dateAdded"));
    } else if (criteria.getKey().equalsIgnoreCase("discountBook")) {
      Join<Books, Discounts> discountsJoin = root.join("discounts");
      return builder.isNotNull(discountsJoin);
    }

    if (criteria.getKey().equalsIgnoreCase("coverType")) {
      Join<Books, BookDetails> bookDetailsJoin = root.join("bookDetails");
      if (criteria.getOperation().equalsIgnoreCase(":")) {
        return builder.equal(bookDetailsJoin.get("eBookFormat"),
            EBookFormat.valueOf(criteria.getValue().toString()));
      }
    } else if (criteria.getKey().equalsIgnoreCase("publisher")) {
      Join<Books, BookDetails> bookDetailsJoin = root.join("bookDetails");
      Join<BookDetails, PublishCompany> publishCompanyJoin = bookDetailsJoin.join("publishCompany");
      if (criteria.getOperation().equalsIgnoreCase(":")) {
        return builder.equal(publishCompanyJoin.get("companyName"), criteria.getValue().toString());
      }
    }

    if (criteria.getOperation().equalsIgnoreCase(">")) {
      return builder.greaterThanOrEqualTo(
          root.<String>get(criteria.getKey()), criteria.getValue().toString());
    } else if (criteria.getOperation().equalsIgnoreCase("<")) {
      return builder.lessThanOrEqualTo(
          root.<String>get(criteria.getKey()), criteria.getValue().toString());
    } else if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(
            root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
      } else {
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
      }
    }
    return null;
  }
}
