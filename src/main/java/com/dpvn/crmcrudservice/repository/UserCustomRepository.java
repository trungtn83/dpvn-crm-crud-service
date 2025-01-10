package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.crmcrudservice.domain.entity.User;
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
  private final UserRepository userRepository;
  @PersistenceContext private EntityManager entityManager;

  public UserCustomRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<User> searchUsers(
      String filterText, List<String> departments, List<String> roles, Pageable pageable) {
    String SELECT = generateSelect();
    String SELECT_COUNT = "SELECT count(*)";
    String FROM = generateFrom();
    String WHERE = generateWhere(filterText, departments, roles);
    String ORDER_BY = generateOrder();
    List<User> results =
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
    return "SELECT u.id";
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
        new StringBuilder(" WHERE (u.active = TRUE AND u.deleted is not true)");
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(" AND (u.username ILIKE '%' || :filterText || '%' OR u.full_name ILIKE '%' || :filterText || '%' OR u.email ILIKE '%' || :filterText || '%' OR u.address_detail ILIKE '%' || :filterText || '%' OR u.description ILIKE '%' || :filterText || '%')");
    }
    if (ListUtil.isNotEmpty(departments)) {
      WHERE.append(
          " AND (d.department_name IN :departments)");
    }
    if (ListUtil.isNotEmpty(roles)) {
      WHERE.append(" AND (r.role_name IN :roles)");
    }
    return WHERE.toString();
  }

  private String generateOrder() {
    return " ORDER BY u.modified_date DESC NULLS FIRST";
  }

  private List<User> getResults(
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

    List<Long> os = query.getResultList();
    return userRepository.findByIdIn(os);
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
