package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Comparator;
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
      String filterText,
      Boolean active,
      List<Long> departments,
      List<Long> roles,
      Pageable pageable) {
    String SELECT = generateSelect();
    String SELECT_COUNT = "SELECT count(*)";
    String FROM = generateFrom();
    String WHERE = generateWhere(filterText, active, departments, roles);
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
        """;
  }

  private String generateWhere(
      String filterText, Boolean active, List<Long> departments, List<Long> roles) {
    String WHERE =
        "WHERE "
            + (active == null
                ? "(1=1)"
                : (active
                    ? "(u.active = TRUE and u.deleted IS NOT TRUE)"
                    : "(u.active IS NOT TRUE OR u.deleted IS TRUE)"));
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE +=
          " AND (u.username ILIKE '%' || :filterText || '%' OR u.full_name ILIKE '%' || :filterText || '%' OR u.email ILIKE '%' || :filterText || '%' OR u.address_detail ILIKE '%' || :filterText || '%' OR u.description ILIKE '%' || :filterText || '%')";
    }
    if (ListUtil.isNotEmpty(departments)) {
      WHERE += " AND (u.department_id IN :departments)";
    }
    if (ListUtil.isNotEmpty(roles)) {
      WHERE += " AND (u.role_id IN :roles)";
    }
    return WHERE;
  }

  private String generateOrder() {
    return " ORDER BY u.created_date DESC NULLS FIRST";
  }

  private List<User> getResults(
      String sql, String filterText, List<Long> departments, List<Long> roles, Pageable pageable) {
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
    return userRepository.findByIdIn(os).stream()
        .sorted(Comparator.comparing((u -> os.indexOf(u.getId()))))
        .toList();
  }

  private Long getTotal(String sql, String filterText, List<Long> departments, List<Long> roles) {
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
