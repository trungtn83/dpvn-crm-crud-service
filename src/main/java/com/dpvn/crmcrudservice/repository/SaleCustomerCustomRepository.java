package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SaleCustomerCustomRepository {
  @PersistenceContext private EntityManager entityManager;

  public List<SaleCustomer> findSaleCustomersByOptions(
      Long saleId,
      List<Long> customerIds,
      Integer relationshipType,
      List<Integer> reasonIds,
      String reasonRef) {
    StringBuilder sql =
        new StringBuilder(
            """
            SELECT * FROM sale_customer sc
            WHERE sc.active = TRUE
              AND sc.deleted IS NOT TRUE
              AND ((sc.available_from IS NULL OR sc.available_from <= CURRENT_TIMESTAMP AND (sc.available_to IS NULL OR sc.available_to >= CURRENT_TIMESTAMP)))
              AND sc.modified_date = (
                                      SELECT MAX(inner_sc.modified_date)
                                      FROM sale_customer inner_sc
                                      WHERE inner_sc.active = true
                                          AND inner_sc.sale_id = sc.sale_id
                                          AND inner_sc.customer_id = sc.customer_id
                                          AND inner_sc.relationship_type = sc.relationship_type
                                          AND inner_sc.reason_id = sc.reason_id
                                     )
          """);

    if (saleId != null) {
      sql.append(" AND sc.sale_id = :saleId");
    }
    if (ListUtil.isNotEmpty(customerIds)) {
      sql.append(" AND sc.customer_id IN :customerIds");
    }
    if (relationshipType != null) {
      sql.append(" AND sc.relationship_type = :relationshipType");
    }
    if (ListUtil.isNotEmpty(reasonIds)) {
      sql.append(" AND sc.reason_id IN :reasonIds");
    }
    if (StringUtil.isNotEmpty(reasonRef)) {
      sql.append(" AND sc.reason_ref = :reasonRef");
    }

    Query query = entityManager.createNativeQuery(sql.toString(), SaleCustomer.class);

    if (saleId != null) {
      query.setParameter("saleId", saleId);
    }
    if (ListUtil.isNotEmpty(customerIds)) {
      query.setParameter("customerIds", customerIds);
    }
    if (relationshipType != null) {
      query.setParameter("relationshipType", relationshipType);
    }
    if (ListUtil.isNotEmpty(reasonIds)) {
      query.setParameter("reasonIds", reasonIds);
    }
    if (StringUtil.isNotEmpty(reasonRef)) {
      query.setParameter("reasonRef", reasonRef);
    }

    return query.getResultList();
  }
}
