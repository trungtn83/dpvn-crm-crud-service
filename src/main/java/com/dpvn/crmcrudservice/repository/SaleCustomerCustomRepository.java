package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
            WHERE sc.status = 1 AND sc.modified_date = (
              SELECT MAX(inner_sc.modified_date) FROM sale_customer inner_sc
              WHERE inner_sc.status = 1 AND inner_sc.sale_id = sc.sale_id AND inner_sc.customer_id = sc.customer_id and inner_sc.relationship_type = sc.relationship_type  and inner_sc.reason_id = sc.reason_id
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

  private Page<SaleCustomer> searchSaleCustomers(
      Long saleId,
      List<Long> customerIds,
      Integer relationshipType,
      List<Integer> reasonIds,
      String reasonRef,
      Long customerCategoryId,
      String filterText,
      Pageable pageable) {
    assert pageable != null;
    String SELECT =
        """
        SELECT
            sc.id AS sale_customer_id,
            sc.created_by AS sale_created_by,
            sc.created_date AS sale_created_date,
            sc.modified_by AS sale_modified_by,
            sc.modified_date AS sale_modified_date,
            sc.available_from AS sale_available_from,
            sc.available_to AS sale_available_to,
            sc.note AS sale_note,
            sc.reason_id AS sale_reason_id,
            sc.reason_note AS sale_reason_note,
            sc.relationship_type AS sale_relationship_type,
            sc.status AS sale_status,
            sc.customer_id AS sale_customer_id,
            sc.sale_id AS sale_sale_id,
            sc.reason_ref AS sale_reason_ref,
            c.id AS customer_id,
            c.created_by AS customer_created_by,
            c.created_date AS customer_created_date,
            c.modified_by AS customer_modified_by,
            c.modified_date AS customer_modified_date,
            c.address AS customer_address,
            c.address_id AS customer_address_id,
            c.customer_category_id AS customer_category_id,
            c.customer_code AS customer_code,
            c.customer_name AS customer_name,
            c.customer_type_id AS customer_type_id,
            c.email AS customer_email,
            c.gender AS customer_gender,
            c.level_point AS customer_level_point,
            c.mobile_phone AS customer_mobile_phone,
            c.notes AS customer_notes,
            c.pin_code AS customer_pin_code,
            c.relationships AS customer_relationships,
            c.source_id AS customer_source_id,
            c.source_note AS customer_source_note,
            c.special_events AS customer_special_events,
            c.status AS customer_status,
            c.tax_code AS customer_tax_code,
            c.customer_id AS customer_customer_id,
            c.birthday AS customer_birthday
        """;
    String SELECT_COUNT = "SELECT COUNT(*)";
    String FROM = generateFrom(customerCategoryId, filterText);
    String WHERE =
        generateWhere(
            saleId,
            customerIds,
            relationshipType,
            reasonIds,
            reasonRef,
            customerCategoryId,
            filterText);
    String ORDER =
        pageable == null || pageable.getSort().isEmpty()
            ? ""
            : pageable.getSort().stream()
                .map(order -> String.format("%s %s", order.getProperty(), order.getDirection()))
                .collect(Collectors.joining(",", " ORDER BY ", ""));

    List<SaleCustomer> results =
        getResults(
            String.format("%s %s %s %s", SELECT, FROM, WHERE, ORDER),
            saleId,
            customerIds,
            relationshipType,
            reasonIds,
            reasonRef,
            customerCategoryId,
            filterText,
            pageable);
    Long total =
        getTotal(
            String.format("%s %s %s", SELECT_COUNT, FROM, WHERE),
            saleId,
            customerIds,
            relationshipType,
            reasonIds,
            reasonRef,
            customerCategoryId,
            filterText);
    return new PageImpl<>(results, pageable, total);
  }

  private List<SaleCustomer> getResults(
      String sql,
      Long saleId,
      List<Long> customerIds,
      Integer relationshipType,
      List<Integer> reasonIds,
      String reasonRef,
      Long customerCategoryId,
      String filterText,
      Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);
    query.setParameter("saleId", saleId);
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
    if (customerCategoryId != null) {
      query.setParameter("customerCategoryId", customerCategoryId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }

    if (pageable != null && pageable.getPageNumber() > 0 && pageable.getPageSize() > 0) {
      query.setFirstResult((pageable.getPageNumber()) * pageable.getPageSize());
      query.setMaxResults(pageable.getPageSize());
    }
    List<Object[]> os = query.getResultList();

    return os.stream().map(this::toSaleCustomerFromObjects).toList();
  }

  private SaleCustomer toSaleCustomerFromObjects(Object[] o) {
    SaleCustomer saleCustomer = new SaleCustomer();
    Customer customer = new Customer();
    saleCustomer.setCustomer(customer);
    return saleCustomer;
  }

  private Long getTotal(
      String sql,
      Long saleId,
      List<Long> customerIds,
      Integer relationshipType,
      List<Integer> reasonIds,
      String reasonRef,
      Long customerCategoryId,
      String filterText) {
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("saleId", saleId);
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
    if (customerCategoryId != null) {
      query.setParameter("customerCategoryId", customerCategoryId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  private String generateFrom(Long customerCategoryId, String filterText) {
    StringBuilder FROM =
        new StringBuilder(
            """
        FROM sale_customer sc
        JOIN customer c ON sc.customer_id = c.id
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      FROM.append(
          " AND (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%' OR c.address ILIKE '%' || :filterText || '%')");
    }

    if (customerCategoryId != null) {
      FROM.append(
          """
          JOIN (
              SELECT DISTINCT ON (scs_inner.customer_id, scs_inner.sale_id)
                     scs_inner.id, scs_inner.customer_id, scs_inner.sale_id, scs_inner.created_date, scs_inner.customer_category_id
              FROM sale_customer_state scs_inner
              where scs_inner.sale_id = :saleId
              ORDER BY scs_inner.customer_id, scs_inner.sale_id, scs_inner.created_date DESC
          ) AS scs ON scs.customer_id = sc.customer_id AND scs.sale_id = sc.sale_id and scs.customer_category_id = :customerCategoryId
          """);
    }

    return FROM.toString();
  }

  private String generateWhere(
      Long saleId,
      List<Long> customerIds,
      Integer relationshipType,
      List<Integer> reasonIds,
      String reasonRef,
      Long customerCategoryId,
      String filterText) {
    StringBuilder WHERE =
        new StringBuilder(
            """
        WHERE sc.status = 1 AND sc.modified_date = (
          SELECT MAX(inner_sc.modified_date) FROM sale_customer inner_sc
          WHERE inner_sc.status = 1 AND inner_sc.sale_id = sc.sale_id AND inner_sc.customer_id = sc.customer_id and inner_sc.relationship_type = sc.relationship_type  and inner_sc.reason_id = sc.reason_id
        )
        """);
    if (saleId != null) {
      WHERE.append(" AND sc.sale_id = :saleId");
    }
    if (ListUtil.isNotEmpty(customerIds)) {
      WHERE.append(" AND sc.customer_id IN :customerIds");
    }
    if (relationshipType != null) {
      WHERE.append(" AND sc.relationship_type = :relationshipType");
    }
    if (ListUtil.isNotEmpty(reasonIds)) {
      WHERE.append(" AND sc.reason_id IN :reasonIds");
    }
    if (StringUtil.isNotEmpty(reasonRef)) {
      WHERE.append(" AND sc.reason_ref = :reasonRef");
    }
    return WHERE.toString();
  }
}
