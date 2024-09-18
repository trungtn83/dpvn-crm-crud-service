package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerCustomRepository {
  @PersistenceContext private EntityManager entityManager;

  public List<Customer> searchCustomers(
      String name,
      String code,
      Integer gender,
      String phone,
      String email,
      String address,
      String taxCode,
      Integer customerType,
      Integer status,
      Integer availability,
      String source,
      String note,
      Paginator paginator) {
    StringBuilder sql = new StringBuilder("SELECT * FROM customer c WHERE 1=1 ");

    if (StringUtil.isNotEmpty(name)) {
      sql.append("AND c.customer_name ILIKE '%' || :name || '%'");
    }
    if (StringUtil.isNotEmpty(code)) {
      sql.append("AND c.customer_code ILIKE '%' || :code || '%'");
    }
    if (gender != null) {
      sql.append("AND c.gender = :gender ");
    }
    if (StringUtil.isNotEmpty(phone)) {
      sql.append("AND c.mobile_phone ILIKE '%' || :phone || '%'");
    }
    if (StringUtil.isNotEmpty(email)) {
      sql.append("AND c.email ILIKE '%' || :email || '%'");
    }
    if (StringUtil.isNotEmpty(address)) {
      sql.append("AND c.address ILIKE '%' || :address || '%'");
    }
    if (StringUtil.isNotEmpty(taxCode)) {
      sql.append("AND c.tax_code ILIKE '%' || :taxCode || '%'");
    }
    if (customerType != null) {
      sql.append("AND c.customer_type = :customerType ");
    }
    if (status != null) {
      sql.append("AND c.status = :status ");
    }
    if (availability != null) {
      sql.append("AND c.availability = :availability ");
    }
    if (StringUtil.isNotEmpty(source)) {
      sql.append("AND c.source ILIKE '%' || :source || '%'");
    }
    if (StringUtil.isNotEmpty(note)) {
      sql.append("AND c.source_note ILIKE '%' || :note || '%'");
    }
    sql.append(" ORDER BY c.")
        .append(paginator.getSortBy())
        .append(" ")
        .append(paginator.getSortDirection());

    Query query = entityManager.createNativeQuery(sql.toString(), Customer.class);

    if (StringUtil.isNotEmpty(name)) {
      query.setParameter("name", name);
    }
    if (StringUtil.isNotEmpty(code)) {
      query.setParameter("code", code);
    }
    if (gender != null) {
      query.setParameter("gender", gender);
    }
    if (StringUtil.isNotEmpty(phone)) {
      query.setParameter("phone", phone);
    }
    if (StringUtil.isNotEmpty(email)) {
      query.setParameter("email", email);
    }
    if (StringUtil.isNotEmpty(address)) {
      query.setParameter("address", address);
    }
    if (StringUtil.isNotEmpty(taxCode)) {
      query.setParameter("tax_code", taxCode);
    }
    if (customerType != null) {
      query.setParameter("customer_type", customerType);
    }
    if (status != null) {
      query.setParameter("status", status);
    }
    if (availability != null) {
      query.setParameter("availability", availability);
    }
    if (StringUtil.isNotEmpty(source)) {
      query.setParameter("source", source);
    }
    if (StringUtil.isNotEmpty(note)) {
      query.setParameter("note", note);
    }
    query.setFirstResult((paginator.getPage() - 1) * paginator.getSize());
    query.setMaxResults(paginator.getSize());

    return query.getResultList();
  }
}
