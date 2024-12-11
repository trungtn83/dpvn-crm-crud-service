package com.dpvn.crmcrudservice.repository;

import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class UserCustomRepository {
  @PersistenceContext private EntityManager entityManager;

  public Page<FastMap> searchUsers(
      String filterText, List<String> departments, List<String> roles, Pageable pageable) {
    String SELECT = generateSelect();
    String SELECT_COUNT = "SELECT count(*)";
    String FROM = generateFrom();
    String WHERE = generateWhere(filterText, departments, roles);
    String ORDER_BY = generateOrder();
    List<FastMap> results =
        getResults(
            String.format("%s %s %s %s", SELECT, FROM, WHERE, ORDER_BY),
            filterText,
            departments,
            roles,
            pageable);
    Long total =
        getTotal(
            String.format("%s %s %s", SELECT_COUNT, FROM, WHERE), filterText, departments, roles);
    return new PageImpl<>(results, pageable, total);
  }

  private String generateSelect() {
    return """
        SELECT
            u.id,
            u.created_by,
            u.created_date,
            u.modified_by,
            u.modified_date,
            u.address_detail,
            u.email,
            u.full_name,
            u.mobile_phone,
            u.status,
            u.username,
            u.address_id,
            u.description,
            u.dob,
            d.id AS department_id,
            d.department_name,
            d.description as department_description,
            r.id AS role_id,
            r.role_name,
            r.description as role_description
        """;
  }

  private String generateFrom() {
    return """
        FROM "user" u
          LEFT JOIN department d ON u.department_id = d.id
          LEFT JOIN role r ON u.role_id = r.id
        """;
  }

  private String generateWhere(String filterText, List<String> departments, List<String> roles) {
    StringBuilder WHERE =
        new StringBuilder(
            """
        WHERE 1=1
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
          AND (u.username ILIKE '%' || :filterText || '%' OR u.full_name ILIKE '%' || :filterText || '%' OR u.email ILIKE '%' || :filterText || '%' OR u.address_detail ILIKE '%' || :filterText || '%' OR u.description ILIKE '%' || :filterText || '%')
          """);
    }
    if (ListUtil.isNotEmpty(departments)) {
      WHERE.append(
          """
          AND (d.department_name IN :departments)
          """);
    }
    if (ListUtil.isNotEmpty(roles)) {
      WHERE.append(
          """
          AND (r.role_name IN :roles)
          """);
    }
    return WHERE.toString();
  }

  private String generateOrder() {
    return """
        ORDER BY u.modified_date DESC NULLS FIRST
        """;
  }

  private List<FastMap> getResults(
      String sql,
      String filterText,
      List<String> departments,
      List<String> roles,
      Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (ListUtil.isNotEmpty(departments)) {
      query.setParameter("departments", departments);
    }
    if (ListUtil.isNotEmpty(roles)) {
      query.setParameter("roles", roles);
    }
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());

    List<Object[]> os = query.getResultList();
    return os.stream().map(this::toFastMapFromUserObjects).toList();
  }

  private FastMap toFastMapFromUserObjects(Object[] o) {
    return FastMap.create()
        .add("id", o[0])
        .add("createdBy", o[1])
        .add("createdDate", o[2])
        .add("modifiedBy", o[3])
        .add("modifiedDate", o[4])
        .add("addressDetail", o[5])
        .add("email", o[6])
        .add("fullName", o[7])
        .add("mobilePhone", o[8])
        .add("status", o[9])
        .add("username", o[10])
        .add("addressId", o[11])
        .add("description", o[12])
        .add("dob", o[13])
        .add("departmentId", o[14])
        .add("departmentName", o[15])
        .add("departmentDescription", o[16])
        .add("roleId", o[17])
        .add("roleName", o[18])
        .add("roleDescription", o[19]);
  }

  private Long getTotal(
      String sql, String filterText, List<String> departments, List<String> roles) {
    Query query = entityManager.createNativeQuery(sql);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (ListUtil.isNotEmpty(departments)) {
      query.setParameter("departments", departments);
    }
    if (ListUtil.isNotEmpty(roles)) {
      query.setParameter("roles", roles);
    }
    return ((Number) query.getSingleResult()).longValue();
  }
}
