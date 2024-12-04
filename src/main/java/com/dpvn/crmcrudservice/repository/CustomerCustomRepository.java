package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CustomerCustomRepository {
  @PersistenceContext private EntityManager entityManager;
  private final CustomerRepository customerRepository;

  public CustomerCustomRepository(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> searchCustomers(
      String name,
      String code,
      Integer gender,
      String mobilePhone,
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
      sql.append(" AND c.customer_name ILIKE '%' || :name || '%'");
    }
    if (StringUtil.isNotEmpty(code)) {
      sql.append(" AND c.customer_code = :code");
    }
    if (gender != null) {
      sql.append(" AND c.gender = :gender ");
    }
    if (StringUtil.isNotEmpty(mobilePhone)) {
      sql.append(" AND c.mobile_phone = :mobilePhone");
    }
    if (StringUtil.isNotEmpty(email)) {
      sql.append(" AND c.email = :email");
    }
    if (StringUtil.isNotEmpty(address)) {
      sql.append(" AND c.address ILIKE '%' || :address || '%'");
    }
    if (StringUtil.isNotEmpty(taxCode)) {
      sql.append(" AND c.tax_code = :taxCode");
    }
    if (customerType != null) {
      sql.append(" AND c.customer_type = :customerType");
    }
    if (status != null) {
      sql.append(" AND c.status = :status");
    }
    if (availability != null) {
      sql.append(" AND c.availability = :availability");
    }
    if (StringUtil.isNotEmpty(source)) {
      sql.append(" AND c.source = :source");
    }
    if (StringUtil.isNotEmpty(note)) {
      sql.append(" AND c.source_note ILIKE '%' || :note || '%'");
    }

    if (StringUtil.isNotEmpty(paginator.getSortBy())
        && StringUtil.isNotEmpty(paginator.getSortDirection())) {
      sql.append(" ORDER BY c.")
          .append(paginator.getSortBy())
          .append(" ")
          .append(paginator.getSortDirection());
    }

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
    if (StringUtil.isNotEmpty(mobilePhone)) {
      query.setParameter("mobilePhone", mobilePhone);
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

    if (paginator.getPage() != null && paginator.getSize() != null) {
      query.setFirstResult((paginator.getPage() - 1) * paginator.getSize());
      query.setMaxResults(paginator.getSize());
    }

    return query.getResultList();
  }

  public Page<FastMap> searchInPoolCustomers(
      Long saleId, String filterText, List<String> tags, Pageable pageable) {
    String SELECT =
        """
            SELECT DISTINCT ON (is_star, c.id)
                c.id,
                c.created_by,
                c.created_date,
                c.modified_by,
                c.modified_date,
                c.address,
                c.address_code,
                c.customer_category_id,
                c.customer_code,
                c.customer_name,
                c.customer_type_id,
                c.email,
                c.gender,
                c.level_point,
                c.mobile_phone,
                c.notes,
                c.pin_code,
                c.relationships,
                c.source_id,
                c.source_note,
                c.special_events,
                c.status,
                c.tax_code,
                c.customer_id,
                c.birthday,
                CASE WHEN sc_star.id IS NOT NULL THEN true ELSE false END AS is_star,
                CASE WHEN boom_interaction.id IS NOT NULL THEN true ELSE false END AS is_boom
      """;
    String SELECT_COUNT = "SELECT count(*)";
    StringBuilder FROM =
        new StringBuilder(
            """
            FROM customer c
            LEFT JOIN (
                SELECT sc.customer_id, MAX(sc.created_date) AS id
                FROM sale_customer sc
                WHERE sc.sale_id = :saleId
                  AND sc.relationship_type = 2
                  AND sc.reason_id = 5
                GROUP BY sc.customer_id
            ) AS sc_star
                ON sc_star.customer_id = c.id
            LEFT JOIN (
                SELECT i.customer_id, MAX(i.id) AS id
                FROM interaction i
                WHERE i.type_id = 1
                GROUP BY i.customer_id
            ) AS boom_interaction
                ON boom_interaction.customer_id = c.id
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      FROM.append(" LEFT JOIN customer_reference cr ON c.id = cr.customer_id ");
    }

    StringBuilder WHERE =
        new StringBuilder(
            """
        WHERE NOT EXISTS (
                SELECT 1
                FROM sale_customer sc
                WHERE sc.customer_id = c.id
                  AND sc.status = 1
                  AND sc.relationship_type = 1
                  AND (sc.available_to IS NULL OR sc.available_to >= CURRENT_TIMESTAMP)
            )
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%' OR c.address ILIKE '%' || :filterText || '%')
                  OR
                  ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
               )
          """);
    }
    if (ListUtil.isNotEmpty(tags)) {
      if (tags.contains(Customers.Tag.SOLD)) {
        WHERE.append(
            " AND (EXISTS (SELECT 1 FROM sale_customer sc WHERE sc.customer_id = c.id AND sc.relationship_type = 1 AND sc.reason_id = 5))");
      }
      if (tags.contains(Customers.Tag.TAKING_CARE)) {
        WHERE.append(
            " AND (EXISTS (SELECT 1 FROM interaction i WHERE i.customer_id = c.id AND i.created_date >= CURRENT_DATE - INTERVAL '7 days'))");
      }
    }

    String ORDER_BY =
        """
        ORDER BY
                is_star DESC,
                c.id ASC
        """;

    List<FastMap> results =
        getInPoolResults(
            String.format("%s %s %s %s", SELECT, FROM, WHERE, ORDER_BY),
            saleId,
            filterText,
            pageable);
    Long total =
        getInPoolTotal(String.format("%s %s %s", SELECT_COUNT, FROM, WHERE), saleId, filterText);

    return new PageImpl<>(results, pageable, total);
  }

  private List<FastMap> getInPoolResults(
      String sql, Long saleId, String filterText, Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);

    query.setParameter("saleId", saleId);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }

    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    List<Object[]> os = query.getResultList();
    return os.stream().map(this::toFastMapFromInPoolCustomerObjects).toList();
  }

  private FastMap toFastMapFromInPoolCustomerObjects(Object[] o) {
    return FastMap.create()
        .add("id", o[0])
        .add("createdBy", o[1])
        .add("createdDate", o[2])
        .add("updatedBy", o[3])
        .add("updatedDate", o[4])
        .add("address", o[5])
        .add("addressCode", o[6])
        .add("customerCategoryId", o[7])
        .add("customerCode", o[8])
        .add("customerName", o[9])
        .add("customerTypeId", o[10])
        .add("email", o[11])
        .add("gender", o[12])
        .add("levelPoint", o[13])
        .add("mobilePhone", o[14])
        .add("notes", o[15])
        .add("pinCode", o[16])
        .add("relationships", o[17])
        .add("sourceId", o[18])
        .add("sourceNote", o[19])
        .add("specialEvents", o[20])
        .add("status", o[21])
        .add("taxCode", o[22])
        .add("customerId", o[23])
        .add("birthday", o[24])
        .add("action", FastMap.create().add("star", o[25]).add("boom", o[26]));
  }

  private Long getInPoolTotal(String sql, Long saleId, String filterText) {
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("saleId", saleId);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  public Page<FastMap> searchMyCustomers(
      Long saleId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds,
      Pageable pageable) {
    assert pageable != null;

    String SELECT =
        """
            SELECT DISTINCT ON (c.id, latest_sc.modified_date, latest_i.modified_date)
                c.id AS customer_id,
                latest_sc.id AS sale_customer_id,
                latest_sc.created_by AS sale_customer_created_by,
                latest_sc.created_date AS sale_customer_created_date,
                latest_sc.modified_by AS sale_customer_modified_by,
                latest_sc.modified_date AS sale_customer_modified_date,
                latest_sc.available_from AS sale_customer_available_from,
                latest_sc.available_to AS sale_customer_available_to,
                latest_sc.note AS sale_customer_note,
                latest_sc.reason_id AS sale_customer_reason_id,
                latest_sc.reason_note AS sale_customer_reason_note,
                latest_sc.relationship_type AS sale_customer_relationship_type,
                latest_sc.status AS sale_customer_status,
                latest_sc.customer_id AS sale_customer_customer_id,
                latest_sc.sale_id AS sale_customer_sale_id,
                latest_sc.reason_ref AS sale_customer_reason_ref,
                latest_scs.id AS sale_customer_state_id,
                latest_scs.created_by AS sale_customer_state_created_by,
                latest_scs.created_date AS sale_customer_state_created_date,
                latest_scs.modified_by AS sale_customer_state_modified_by,
                latest_scs.modified_date AS sale_customer_state_modified_date,
                latest_scs.note AS sale_customer_state_note,
                latest_scs.status AS sale_customer_state_status,
                latest_scs.characteristic AS sale_customer_state_characteristic,
                latest_scs.fee_ship AS sale_customer_state_fee_ship,
                latest_scs.other AS sale_customer_state_other,
                latest_scs.prefer_products AS sale_customer_state_prefer_products,
                latest_scs.prefer_products_price AS sale_customer_state_prefer_products_price,
                latest_scs.price_make_up AS sale_customer_state_price_make_up,
                latest_scs.sale_id AS sale_customer_state_sale_id,
                latest_scs.customer_category_id AS sale_customer_state_customer_category_id,
                latest_i.id AS interaction_id,
                latest_i.created_by AS interaction_created_by,
                latest_i.created_date AS interaction_created_date,
                latest_i.modified_by AS interaction_modified_by,
                latest_i.modified_date AS interaction_modified_date,
                latest_i.audios AS interaction_audios,
                latest_i.campaign_id AS interaction_campaign_id,
                latest_i.content AS interaction_content,
                latest_i.files AS interaction_files,
                latest_i.images AS interaction_images,
                latest_i.interact_by AS interaction_interact_by,
                latest_i.title AS interaction_title,
                latest_i.type AS interaction_type,
                latest_i.type_id AS interaction_type_id,
                latest_i.urls AS interaction_urls,
                latest_i.videos AS interaction_videos,
                latest_i.visibility AS interaction_visibility
      """;
    String SELECT_COUNT = "SELECT COUNT(*)";
    String FROM = generateMyCustomerFrom(customerCategoryId, filterText);
    String WHERE = generateMyCustomersWhere(filterText, reasonIds);
    String ORDER =
        " ORDER BY latest_i.modified_date NULLS FIRST,latest_sc.modified_date NULLS FIRST,c.id";
    List<FastMap> results =
        getMyCustomerResults(
            String.format("%s %s %s %s", SELECT, FROM, WHERE, ORDER),
            saleId,
            customerCategoryId,
            filterText,
            reasonIds,
            pageable);
    Long total =
        getMyCustomerTotal(
            String.format("%s %s %s", SELECT_COUNT, FROM, WHERE),
            saleId,
            customerCategoryId,
            filterText,
            reasonIds);
    return new PageImpl<>(results, pageable, total);
  }

  private List<FastMap> getMyCustomerResults(
      String sql,
      Long saleId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds,
      Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);
    query.setParameter("saleId", saleId);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (customerCategoryId != null) {
      query.setParameter("customerCategoryId", customerCategoryId);
    }
    if (ListUtil.isNotEmpty(reasonIds)) {
      query.setParameter("reasonIds", reasonIds);
    }
    if (pageable.getPageSize() > 0) {
      query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
      query.setMaxResults(pageable.getPageSize());
    }
    List<Object[]> os = query.getResultList();

    // inject CustomerDto here to have references automatically
    List<Long> customerIds = os.stream().map(o -> Long.parseLong(o[0].toString())).toList();
    Map<Long, Customer> customers =
        customerRepository.findByIdIn(customerIds).stream()
            .collect(Collectors.toMap(Customer::getId, o -> o));

    return os.stream()
        .map(
            o ->
                toFastMapFromMyCustomerObjects(o)
                    .add("customer", customers.get(Long.parseLong(o[0].toString())).toDto()))
        .toList();
  }

  private Long getMyCustomerTotal(
      String sql,
      Long saleId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds) {
    Query query = entityManager.createNativeQuery(sql, Object.class);
    query.setParameter("saleId", saleId);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (customerCategoryId != null) {
      query.setParameter("customerCategoryId", customerCategoryId);
    }
    if (ListUtil.isNotEmpty(reasonIds)) {
      query.setParameter("reasonIds", reasonIds);
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  private FastMap toFastMapFromMyCustomerObjects(Object[] o) {
    return FastMap.create()
        .add(
            "saleCustomer",
            FastMap.create()
                .add("id", o[1])
                .add("createdBy", o[2])
                .add("createdDate", o[3])
                .add("modifiedBy", o[4])
                .add("modifiedDate", o[5])
                .add("availableFrom", o[6])
                .add("availableTo", o[7])
                .add("note", o[8])
                .add("reasonId", o[9])
                .add("reasonNote", o[10])
                .add("relationshipType", o[11])
                .add("status", o[12])
                .add("customerId", o[13])
                .add("saleId", o[14])
                .add("reasonRef", o[15]))
        .add(
            "state",
            FastMap.create()
                .add("id", o[16])
                .add("createdBy", o[17])
                .add("createdDate", o[18])
                .add("modifiedBy", o[19])
                .add("modifiedDate", o[20])
                .add("note", o[21])
                .add("status", o[22])
                .add("characteristic", o[23])
                .add("feeShip", o[24])
                .add("other", o[25])
                .add("preferProducts", o[26])
                .add("preferProductsPrice", o[27])
                .add("priceMakeUp", o[28])
                .add("saleId", o[29])
                .add("customerCategoryId", o[30]))
        .add(
            "interaction",
            FastMap.create()
                .add("id", o[31])
                .add("createdBy", o[32])
                .add("createdDate", o[33])
                .add("modifiedBy", o[34])
                .add("modifiedDate", o[35])
                .add("audios", o[36])
                .add("campaignId", o[37])
                .add("content", o[38])
                .add("files", o[39])
                .add("images", o[40])
                .add("interactBy", o[41])
                .add("title", o[42])
                .add("type", o[43])
                .add("typeId", o[44])
                .add("urls", o[45])
                .add("videos", o[46])
                .add("visibility", o[47]));
  }

  private String generateMyCustomerFrom(Long customerCategoryId, String filterText) {
    StringBuilder FROM =
        new StringBuilder(
            """
        FROM customer c
        JOIN (
            SELECT DISTINCT ON (sc.customer_id)
                   sc.*
            FROM sale_customer sc
            WHERE sc.sale_id = :saleId AND sc.relationship_type = 1 AND sc.available_to >= NOW()
            ORDER BY sc.customer_id, sc.created_date DESC
        ) latest_sc ON latest_sc.customer_id = c.id
        """);

    if (customerCategoryId != null) {
      FROM.append(
          """
          JOIN (
              SELECT DISTINCT ON (scs_inner.customer_id, scs_inner.sale_id)
                     scs_inner.id, scs_inner.customer_id, scs_inner.sale_id, scs_inner.created_date, scs_inner.customer_category_id
              FROM sale_customer_state scs_inner
              where scs_inner.sale_id = :saleId
              ORDER BY scs_inner.customer_id, scs_inner.sale_id, scs_inner.created_date DESC
          ) AS scs ON scs.customer_id = c.id AND scs.sale_id = :saleId and scs.customer_category_id = :customerCategoryId
          """);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      FROM.append(" LEFT JOIN customer_reference cr ON c.id = cr.customer_id ");
    }
    FROM.append(
        """
        LEFT JOIN (
            SELECT DISTINCT ON (scs.customer_id)
                   scs.*
            FROM sale_customer_state scs
            ORDER BY scs.customer_id, scs.modified_date DESC
        ) latest_scs ON latest_scs.customer_id = c.id and latest_scs.sale_id = :saleId
        LEFT JOIN (
            SELECT DISTINCT ON (i.customer_id)
                   i.*
            FROM interaction i
            WHERE i.created_by = :saleId OR i.visibility = 0
            ORDER BY i.customer_id, i.created_date DESC
        ) latest_i ON latest_i.customer_id = c.id
        """);
    return FROM.toString();
  }

  private String generateMyCustomersWhere(String filterText, List<Integer> reasonIds) {
    StringBuilder WHERE = new StringBuilder("WHERE 1=1");
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%' OR c.address ILIKE '%' || :filterText || '%')
                  OR
                  ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
               )
          """);
    }
    if (ListUtil.isNotEmpty(reasonIds)) {
      WHERE.append(" AND latest_sc.reason_id IN (:reasonIds)");
    }
    return WHERE.toString();
  }

  public Page<FastMap> searchTaskBasedCustomers(
      Long saleId, String filterText, List<String> tags, Pageable pageable) {
    String WITH =
        """
        WITH LastModifiedTasks AS (
            SELECT DISTINCT ON (customer_id) customer_id, id AS last_modified_task_id, "name" AS last_modified_task_name, modified_date AS last_modified_task_date
            FROM task
            WHERE progress <> 100 AND user_id = :saleId
            ORDER BY customer_id, modified_date DESC
        ),
        OldestCreatedTasks AS (
            SELECT DISTINCT ON (customer_id) customer_id, id AS oldest_modified_task_id, "name" AS oldest_modified_task_name, modified_date AS oldest_modified_task_date
            FROM task
            WHERE progress <> 100 AND user_id = :saleId
            ORDER BY customer_id, modified_date ASC
        )
        """;
    String SELECT =
        """
        SELECT
           c.id,
           c.created_by,
           c.created_date,
           c.modified_by,
           c.modified_date,
           c.address,
           c.address_code,
           c.customer_category_id,
           c.customer_code,
           c.customer_name,
           c.customer_type_id,
           c.email,
           c.gender,
           c.level_point,
           c.mobile_phone,
           c.notes,
           c.pin_code,
           c.relationships,
           c.source_id,
           c.source_note,
           c.special_events,
           c.status,
           c.tax_code,
           c.customer_id,
           c.birthday,
           COUNT(CASE WHEN t.priority = 90 THEN 1 END) AS now_task_count,
           COUNT(CASE WHEN DATE(t.to_date) = CURRENT_DATE THEN 1 END) AS today_task_count,
           COUNT(CASE WHEN DATE(t.to_date) < CURRENT_DATE THEN 1 END) AS overdue_task_count,
           lmt.last_modified_task_id,
           lmt.last_modified_task_name,
           lmt.last_modified_task_date,
           oct.oldest_modified_task_id,
           oct.oldest_modified_task_name,
           oct.oldest_modified_task_date
        """;
    String SELECT_COUNT = "SELECT COUNT(DISTINCT c.id)";
    String FROM = generateTaskBasedFrom(filterText);
    String WHERE = generateTaskBasedWhere(filterText, tags);
    String GROUP_BY =
        """
        GROUP BY
            c.id,
            c.created_by,
            c.created_date,
            c.modified_by,
            c.modified_date,
            c.address,
            c.address_code,
            c.customer_category_id,
            c.customer_code,
            c.customer_name,
            c.customer_type_id,
            c.email,
            c.gender,
            c.level_point,
            c.mobile_phone,
            c.notes,
            c.pin_code,
            c.relationships,
            c.source_id,
            c.source_note,
            c.special_events,
            c.status,
            c.tax_code,
            c.customer_id,
            c.birthday,
            lmt.last_modified_task_id,
            lmt.last_modified_task_name,
            lmt.last_modified_task_date,
            oct.oldest_modified_task_id,
            oct.oldest_modified_task_name,
            oct.oldest_modified_task_date
        """;
    String ORDER_BY = " ORDER BY oct.oldest_modified_task_date ASC";

    List<FastMap> results =
        getTaskBasedResult(
            String.format("%s%s %s %s %s %s", WITH, SELECT, FROM, WHERE, GROUP_BY, ORDER_BY),
            saleId,
            filterText,
            tags,
            pageable);
    Long total =
        getTaskBasedTotal(
            String.format("%s%s %s %s", WITH, SELECT_COUNT, FROM, WHERE), saleId, filterText, tags);

    return new PageImpl<>(results, pageable, total);
  }

  private List<FastMap> getTaskBasedResult(
      String sql, Long saleId, String filterText, List<String> tags, Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);

    query.setParameter("saleId", saleId);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }

    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    List<Object[]> os = query.getResultList();
    return os.stream().map(this::toFastMapFromTaskBasedCustomerObjects).toList();
  }

  private FastMap toFastMapFromTaskBasedCustomerObjects(Object[] o) {
    return FastMap.create()
        .add("id", o[0])
        .add("createdBy", o[1])
        .add("createdDate", o[2])
        .add("updatedBy", o[3])
        .add("updatedDate", o[4])
        .add("address", o[5])
        .add("addressCode", o[6])
        .add("customerCategoryId", o[7])
        .add("customerCode", o[8])
        .add("customerName", o[9])
        .add("customerTypeId", o[10])
        .add("email", o[11])
        .add("gender", o[12])
        .add("levelPoint", o[13])
        .add("mobilePhone", o[14])
        .add("notes", o[15])
        .add("pinCode", o[16])
        .add("relationships", o[17])
        .add("sourceId", o[18])
        .add("sourceNote", o[19])
        .add("specialEvents", o[20])
        .add("status", o[21])
        .add("taxCode", o[22])
        .add("customerId", o[23])
        .add("birthday", o[24])
        .add("tags", FastMap.create().add("now", o[25]).add("today", o[26]).add("overdue", o[27]))
        .add("lastTaskId", o[28])
        .add("lastTaskName", o[29])
        .add("lastTaskDate", o[30])
        .add("oldestTaskId", o[31])
        .add("oldestTaskName", o[32])
        .add("oldestTaskDate", o[33]);
  }

  private Long getTaskBasedTotal(String sql, Long saleId, String filterText, List<String> tags) {
    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("saleId", saleId);
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  private String generateTaskBasedFrom(String filterText) {
    StringBuilder FROM =
        new StringBuilder(
            """
        FROM customer c
        JOIN task t ON c.id = t.customer_id  AND t.user_id = :saleId AND t.progress <> 100
        LEFT JOIN LastModifiedTasks lmt ON c.id = lmt.customer_id
        LEFT JOIN OldestCreatedTasks oct ON c.id = oct.customer_id
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      FROM.append(" LEFT JOIN customer_reference cr ON c.id = cr.customer_id ");
    }
    return FROM.toString();
  }

  private String generateTaskBasedWhere(String filterText, List<String> tags) {
    StringBuilder WHERE = new StringBuilder("WHERE 1=1");
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%' OR c.address ILIKE '%' || :filterText || '%')
                  OR
                  ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
                  OR
                  (t.title ILIKE '%' || :filterText || '%' OR t.name ILIKE '%' || :filterText || '%' OR t.content ILIKE '%' || :filterText || '%')
               )
          """);
    }
    if (ListUtil.isNotEmpty(tags)) {
      if (tags.contains(Customers.Tag.TASK_NOW)) {
        WHERE.append(" AND t.priority = 90");
      }
      if (tags.contains(Customers.Tag.TASK_TODAY)) {
        WHERE.append(" AND DATE(t.to_date) = CURRENT_DATE");
      }
      if (tags.contains(Customers.Tag.TASK_OVERDUE)) {
        WHERE.append(" AND DATE(t.to_date) < CURRENT_DATE");
      }
      if (tags.contains(Customers.Tag.TASK_NO_DEADLINE)) {
        WHERE.append(" AND t.to_date IS NULL");
      }
    }
    return WHERE.toString();
  }
}
