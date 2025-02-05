package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.ObjectUtil;
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

  public Page<Customer> searchInOceanCustomers(
      String filterText,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds,
      Pageable pageable) {
    String SELECT = "SELECT DISTINCT ON (c.id) c.id, c.status, c.modified_date";
    String FROM = generateInOceanFrom(filterText, locationCodes);
    String WHERE = generateInOceanWhere(filterText, typeIds, locationCodes, sourceIds);
    String ORDER_BY =
        " ORDER BY (CASE WHEN status = 'VERIFIED' THEN 1 ELSE 0 END) ASC, modified_date DESC";

    List<Customer> results =
        getInOceanResults(
            String.format(
                "%s %s %s ORDER BY c.id, c.modified_date DESC",
                SELECT, FROM, WHERE), // improve performance by ORDER BY c.id
            ORDER_BY,
            filterText,
            typeIds,
            locationCodes,
            sourceIds,
            pageable);
    Long total =
        getInOceanTotal(
            String.format("%s %s %s", SELECT, FROM, WHERE),
            filterText,
            typeIds,
            locationCodes,
            sourceIds);

    return new PageImpl<>(results, pageable, total);
  }

  private String generateInOceanFrom(String filterText, List<String> locationCodes) {
    StringBuilder FROM =
        new StringBuilder(
            """
            FROM customer c
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      FROM.append(
          """
          LEFT JOIN customer_reference cr ON c.id = cr.customer_id
        """);
    }
    if (ListUtil.isNotEmpty(locationCodes) && locationCodes.size() == 3) {
      FROM.append(
          """
          LEFT JOIN customer_address ca ON c.id = ca.customer_id
        """);
    }
    return FROM.toString();
  }

  private String generateInOceanWhere(
      String filterText, List<Long> typeIds, List<String> locationCodes, List<Integer> sourceIds) {
    StringBuilder WHERE = new StringBuilder(" WHERE c.active = false AND c.deleted IS NOT TRUE");

    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%')
                  OR (ca.address ILIKE '%' || :filterText || '%' OR ca.ward_name ILIKE '%' || :filterText || '%' OR ca.district_name ILIKE '%' || :filterText || '%' OR ca.province_name ILIKE '%' || :filterText || '%')
                  OR ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
               )
          """);
    }
    if (ListUtil.isNotEmpty(typeIds)) {
      WHERE.append(" AND c.customer_type_id IN (:typeIds)");
    }
    if (ListUtil.isNotEmpty(sourceIds)) {
      WHERE.append(" AND c.source_id IN (:sourceIds)");
    }

    if (ListUtil.isNotEmpty(locationCodes) && locationCodes.size() == 3) {
      String provinceCode = locationCodes.get(0);
      if (StringUtil.isNotEmpty(provinceCode)) {
        WHERE.append(" AND ca.province_code = :provinceCode");
      }
      String districtCode = locationCodes.get(1);
      if (StringUtil.isNotEmpty(districtCode)) {
        WHERE.append(" AND ca.district_code = :districtCode");
      }
      String wardCode = locationCodes.get(2);
      if (StringUtil.isNotEmpty(wardCode)) {
        WHERE.append(" AND ca.ward_code = :wardCode");
      }
    }

    return WHERE.toString();
  }

  private List<Customer> getInOceanResults(
      String sql,
      String ORDER_BY,
      String filterText,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds,
      Pageable pageable) {
    Query query =
        entityManager.createNativeQuery(
            String.format("SELECT id from (%s) temp %s", sql, ORDER_BY), Object.class);

    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (ListUtil.isNotEmpty(typeIds)) {
      query.setParameter("typeIds", typeIds);
    }
    if (ListUtil.isNotEmpty(sourceIds)) {
      query.setParameter("sourceIds", sourceIds);
    }
    if (ListUtil.isNotEmpty(locationCodes) && locationCodes.size() == 3) {
      String provinceCode = locationCodes.get(0);
      if (StringUtil.isNotEmpty(provinceCode)) {
        query.setParameter("provinceCode", provinceCode);
      }
      String districtCode = locationCodes.get(1);
      if (StringUtil.isNotEmpty(districtCode)) {
        query.setParameter("districtCode", districtCode);
      }
      String wardCode = locationCodes.get(2);
      if (StringUtil.isNotEmpty(wardCode)) {
        query.setParameter("wardCode", wardCode);
      }
    }

    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    // return only id, so, List<Object[]> -> List<Long>
    List<Long> os = query.getResultList();

    // inject CustomerDto here to have references, addresses automatically
    return customerRepository.findByIdIn(os);
  }

  private Long getInOceanTotal(
      String sql,
      String filterText,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds) {
    Query query =
        entityManager.createNativeQuery(String.format("SELECT count(*) from (%s) temp", sql));
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (ListUtil.isNotEmpty(typeIds)) {
      query.setParameter("typeIds", typeIds);
    }
    if (ListUtil.isNotEmpty(sourceIds)) {
      query.setParameter("sourceIds", sourceIds);
    }
    if (ListUtil.isNotEmpty(locationCodes)) {
      String provinceCode = locationCodes.get(0);
      if (StringUtil.isNotEmpty(provinceCode)) {
        query.setParameter("provinceCode", provinceCode);
      }
      if (locationCodes.size() > 1) {
        String districtCode = locationCodes.get(1);
        if (StringUtil.isNotEmpty(districtCode)) {
          query.setParameter("districtCode", districtCode);
        }
        if (locationCodes.size() > 2) {
          String wardCode = locationCodes.get(2);
          if (StringUtil.isNotEmpty(wardCode)) {
            query.setParameter("wardCode", wardCode);
          }
        }
      }
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  public Page<FastMap> searchInPoolCustomers(
      Long saleId,
      String filterText,
      List<String> tags,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds,
      Pageable pageable) {
    String SELECT =
        """
            SELECT DISTINCT ON (is_star, c.modified_date, c.id)
                c.id,
                CASE WHEN sc_star.id IS NOT NULL THEN true ELSE false END AS is_star,
                CASE WHEN boom_interaction.id IS NOT NULL THEN true ELSE false END AS is_boom
      """;
    String SELECT_COUNT = "SELECT count(*)";
    String FROM = generateInPoolFrom(filterText, locationCodes);
    String WHERE = generateInPoolWhere(filterText, tags, typeIds, locationCodes, sourceIds);
    String ORDER_BY = " ORDER BY is_star DESC, c.modified_date DESC, c.id";

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

  private String generateInPoolFrom(String filterText, List<String> locationCodes) {
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
      FROM.append(
          """
          LEFT JOIN customer_reference cr ON c.id = cr.customer_id
          LEFT JOIN customer_address ca ON c.id = ca.customer_id
        """);
    }
    if (StringUtil.isEmpty(filterText)
        && ListUtil.isNotEmpty(locationCodes)
        && locationCodes.size() == 3) {
      FROM.append(
          """
          LEFT JOIN customer_address ca ON c.id = ca.customer_id
        """);
    }
    return FROM.toString();
  }

  private String generateInPoolWhere(
      String filterText,
      List<String> tags,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds) {
    StringBuilder WHERE =
        new StringBuilder(
            "WHERE (c.active = TRUE AND c.deleted IS NOT TRUE AND c.status = '"
                + Customers.Status.VERIFIED
                + "')");

    // sure that customer is not in old and assgined list now
    // nếu là dạng tự tìm thì cho phép nhiều người cùng thấy, fix cho case reason = 7
    // nếu tự tạo thì ko thấy trong kho vàng, nhưng người khác vẫn tạo mới vói số đt đó được
    WHERE.append(
        """
        AND NOT EXISTS (
                SELECT 1
                FROM sale_customer sc
                WHERE sc.customer_id = c.id
                  AND sc.active = TRUE
                  AND sc.deleted IS NOT TRUE
                  AND sc.relationship_type = 1
                  AND sc.reason_id IN (1, 2, 3, 4, 70, 71, 72)
                  AND ((sc.available_from IS NULL OR sc.available_from <= CURRENT_TIMESTAMP AND (sc.available_to IS NULL OR sc.available_to >= CURRENT_TIMESTAMP)))
            )
        """);
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%')
                  OR (ca.address ILIKE '%' || :filterText || '%' OR ca.ward_name ILIKE '%' || :filterText || '%' OR ca.district_name ILIKE '%' || :filterText || '%' OR ca.province_name ILIKE '%' || :filterText || '%')
                  OR ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
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
    if (ListUtil.isNotEmpty(typeIds)) {
      WHERE.append(" AND c.customer_type_id IN (:typeIds)");
    }
    if (ListUtil.isNotEmpty(sourceIds)) {
      WHERE.append(" AND c.source_id IN (:sourceIds)");
    }
    if (ListUtil.isNotEmpty(locationCodes) && locationCodes.size() == 3) {
      String provinceCode = locationCodes.get(0);
      if (StringUtil.isNotEmpty(provinceCode)) {
        WHERE.append(" AND ca.province_code = :provinceCode");
      }
      String districtCode = locationCodes.get(1);
      if (StringUtil.isNotEmpty(districtCode)) {
        WHERE.append(" AND ca.district_code = :districtCode");
      }
      String wardCode = locationCodes.get(2);
      if (StringUtil.isNotEmpty(wardCode)) {
        WHERE.append(" AND ca.ward_code = :wardCode");
      }
    }
    return WHERE.toString();
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

    // inject CustomerDto here to have references, addresses automatically
    List<Long> customerIds = os.stream().map(o -> Long.parseLong(o[0].toString())).toList();
    Map<Long, Customer> customers =
        customerRepository.findByIdIn(customerIds).stream()
            .collect(Collectors.toMap(Customer::getId, o -> o));

    return os.stream()
        .map(
            o -> {
              FastMap ro = toFastMapFromInPoolCustomerObjects(o);
              ro.putAll(
                  ObjectUtil.readValue(
                      customers.get(Long.parseLong(o[0].toString())).toDto(), FastMap.class));
              return ro;
            })
        .toList();
  }

  private FastMap toFastMapFromInPoolCustomerObjects(Object[] o) {
    return FastMap.create()
        .add("id", o[0])
        .add("action", FastMap.create().add("star", o[1]).add("boom", o[2]));
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
      Long customerTypeId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds,
      Pageable pageable) {
    assert pageable != null;

    String SELECT =
        """
            SELECT DISTINCT ON (latest_sc.available_to, c.id)
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
    String FROM = generateMyCustomerFrom(saleId, customerCategoryId, filterText);
    String WHERE = generateMyCustomersWhere(filterText, customerTypeId, reasonIds);
    String ORDER = " ORDER BY latest_sc.available_to ASC, c.id";
    List<FastMap> results =
        getMyCustomerResults(
            String.format("%s %s %s %s", SELECT, FROM, WHERE, ORDER),
            saleId,
            customerTypeId,
            customerCategoryId,
            filterText,
            reasonIds,
            pageable);
    Long total =
        getMyCustomerTotal(
            String.format("%s %s %s", SELECT_COUNT, FROM, WHERE),
            saleId,
            customerTypeId,
            customerCategoryId,
            filterText,
            reasonIds);
    return new PageImpl<>(results, pageable, total);
  }

  private List<FastMap> getMyCustomerResults(
      String sql,
      Long saleId,
      Long customerTypeId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds,
      Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);
    if (saleId != null) {
      query.setParameter("saleId", saleId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (customerTypeId != null) {
      query.setParameter("customerTypeId", customerTypeId);
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

    // inject CustomerDto here to have references, addresses automatically
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
      Long customerTypeId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds) {
    Query query = entityManager.createNativeQuery(sql, Object.class);
    if (saleId != null) {
      query.setParameter("saleId", saleId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    if (customerTypeId != null) {
      query.setParameter("customerTypeId", customerTypeId);
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

  private String generateMyCustomerFrom(Long saleId, Long customerCategoryId, String filterText) {
    String FROM =
        String.format(
            """
        FROM customer c
        JOIN (
            SELECT DISTINCT ON (sc.customer_id)
                   sc.*
            FROM sale_customer sc
            WHERE sc.active = true
              AND sc.deleted is not true
              %s
              AND sc.relationship_type = 1
              AND ((sc.available_from IS NULL OR sc.available_from <= CURRENT_TIMESTAMP AND (sc.available_to IS NULL OR sc.available_to >= CURRENT_TIMESTAMP)))
            ORDER BY sc.customer_id, sc.available_to DESC NULLS FIRST
        ) latest_sc ON latest_sc.customer_id = c.id
        """,
            saleId == null ? "" : "AND sc.sale_id = :saleId");
    if (customerCategoryId != null) {
      FROM +=
          String.format(
              """
          JOIN (
              SELECT DISTINCT ON (scs_inner.customer_id, scs_inner.sale_id)
                     scs_inner.id, scs_inner.customer_id, scs_inner.sale_id, scs_inner.created_date, scs_inner.customer_category_id
              FROM sale_customer_state scs_inner
              WHERE %s
              ORDER BY scs_inner.customer_id, scs_inner.sale_id, scs_inner.created_date DESC
          ) AS scs ON scs.customer_id = c.id AND scs.customer_category_id = :customerCategoryId
          """,
              saleId == null ? "1 = 1" : "AND scs_inner.sale_id = :saleId");
    }
    if (StringUtil.isNotEmpty(filterText)) {
      FROM +=
          """
          LEFT JOIN customer_reference cr ON c.id = cr.customer_id
          LEFT JOIN customer_address ca ON c.id = ca.customer_id
        """;
    }
    FROM +=
        String.format(
            """
        LEFT JOIN (
            SELECT DISTINCT ON (scs.customer_id)
                   scs.*
            FROM sale_customer_state scs
            ORDER BY scs.customer_id, scs.modified_date DESC
        ) latest_scs ON latest_scs.customer_id = c.id %s
        LEFT JOIN (
            SELECT DISTINCT ON (i.customer_id)
                   i.*
            FROM interaction i
            WHERE i.visibility = 0 %s
            ORDER BY i.customer_id, i.created_date DESC
        ) latest_i ON latest_i.customer_id = c.id
        """,
            saleId == null ? "" : "AND latest_scs.sale_id = :saleId",
            saleId == null ? "" : "OR i.created_by = :saleId");
    return FROM;
  }

  private String generateMyCustomersWhere(
      String filterText, Long customerTypeId, List<Integer> reasonIds) {
    StringBuilder WHERE = new StringBuilder("WHERE (c.active = true AND c.deleted IS NOT TRUE)");
    if (customerTypeId != null) {
      WHERE.append(" AND c.customer_type_id = :customerTypeId");
    }
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE.append(
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%')
                  OR (ca.address ILIKE '%' || :filterText || '%' OR ca.ward_name ILIKE '%' || :filterText || '%' OR ca.district_name ILIKE '%' || :filterText || '%' OR ca.province_name ILIKE '%' || :filterText || '%')
                  OR ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
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
        String.format(
            """
        WITH LastModifiedTasks AS (
            SELECT DISTINCT ON (customer_id) customer_id, id AS last_modified_task_id, "name" AS last_modified_task_name, modified_date AS last_modified_task_date
            FROM task
            WHERE progress <> 100 %s
            ORDER BY customer_id, modified_date DESC
        ),
        OldestCreatedTasks AS (
            SELECT DISTINCT ON (customer_id) customer_id, id AS oldest_modified_task_id, "name" AS oldest_modified_task_name, modified_date AS oldest_modified_task_date
            FROM task
            WHERE progress <> 100 %s
            ORDER BY customer_id, modified_date ASC
        )
        """,
            saleId == null ? "" : "AND user_id = :saleId",
            saleId == null ? "" : "AND user_id = :saleId");
    String SELECT =
        """
        SELECT
           c.id,
           XXX.sale_id,
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
    String FROM = generateTaskBasedFrom(saleId, filterText);
    String WHERE = generateTaskBasedWhere(filterText, tags);
    String GROUP_BY =
        """
        GROUP BY
            c.id,
            XXX.sale_id,
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
            pageable);
    Long total =
        getTaskBasedTotal(
            String.format("%s%s %s %s", WITH, SELECT_COUNT, FROM, WHERE), saleId, filterText, tags);

    return new PageImpl<>(results, pageable, total);
  }

  private List<FastMap> getTaskBasedResult(
      String sql, Long saleId, String filterText, Pageable pageable) {
    Query query = entityManager.createNativeQuery(sql, Object.class);

    if (saleId != null) {
      query.setParameter("saleId", saleId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }

    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    List<Object[]> os = query.getResultList();

    // inject CustomerDto here to have references, addresses automatically
    List<Long> customerIds = os.stream().map(o -> Long.parseLong(o[0].toString())).toList();
    Map<Long, Customer> customers =
        customerRepository.findByIdIn(customerIds).stream()
            .collect(Collectors.toMap(Customer::getId, o -> o));

    return os.stream()
        .map(
            o ->
                toFastMapFromTaskBasedCustomerObjects(o)
                    .add("customer", customers.get(Long.parseLong(o[0].toString())).toDto())
                    .add("saleId", o[1]))
        .toList();
  }

  private FastMap toFastMapFromTaskBasedCustomerObjects(Object[] o) {
    return FastMap.create()
        .add("id", o[0])
        .add("saleId", o[1])
        .add("tags", FastMap.create().add("now", o[2]).add("today", o[3]).add("overdue", o[4]))
        .add("lastTaskId", o[5])
        .add("lastTaskName", o[6])
        .add("lastTaskDate", o[7])
        .add("oldestTaskId", o[8])
        .add("oldestTaskName", o[9])
        .add("oldestTaskDate", o[10]);
  }

  private Long getTaskBasedTotal(String sql, Long saleId, String filterText, List<String> tags) {
    Query query = entityManager.createNativeQuery(sql);
    if (saleId != null) {
      query.setParameter("saleId", saleId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      query.setParameter("filterText", filterText);
    }
    return ((Number) query.getSingleResult()).longValue();
  }

  private String generateTaskBasedFrom(Long saleId, String filterText) {
    String FROM =
        String.format(
            """
            FROM customer c
            INNER JOIN (
              SELECT sale_id, customer_id
              FROM sale_customer sc
              WHERE sc.active = TRUE %s
                AND sc.deleted IS NOT TRUE
                AND sc.relationship_type = 1
                AND (sc.available_from IS NULL OR sc.available_from <= CURRENT_TIMESTAMP AND (sc.available_to IS NULL OR sc.available_to >= CURRENT_TIMESTAMP))
            ) AS XXX on XXX.customer_id = c.id
            JOIN task t ON c.id = t.customer_id AND t.progress <> 100 %s
            LEFT JOIN LastModifiedTasks lmt ON c.id = lmt.customer_id
            LEFT JOIN OldestCreatedTasks oct ON c.id = oct.customer_id
        """,
            saleId == null ? "" : "AND sc.sale_id = :saleId",
            saleId == null ? "" : "AND t.user_id = :saleId");

    if (StringUtil.isNotEmpty(filterText)) {
      FROM +=
          """
          LEFT JOIN customer_reference cr ON c.id = cr.customer_id
          LEFT JOIN customer_address ca ON c.id = ca.customer_id
        """;
    }
    return FROM;
  }

  private String generateTaskBasedWhere(String filterText, List<String> tags) {
    String WHERE =
        "WHERE (c.active = true AND (c.status = '"
            + Customers.Status.VERIFIED
            + "' OR c.status = '"
            + Customers.Status.VERIFYING
            + "'))";
    if (StringUtil.isNotEmpty(filterText)) {
      WHERE +=
          """
               AND (
                  (c.customer_code ILIKE '%' || :filterText || '%' OR c.customer_name ILIKE '%' || :filterText || '%' OR c.mobile_phone ILIKE '%' || :filterText || '%')
                  OR (ca.address ILIKE '%' || :filterText || '%' OR ca.ward_name ILIKE '%' || :filterText || '%' OR ca.district_name ILIKE '%' || :filterText || '%' OR ca.province_name ILIKE '%' || :filterText || '%')
                  OR ((cr.code = 'ZALO' OR cr.code = 'MOBILE_PHONE') AND cr.value ILIKE '%' || :filterText || '%')
                  OR (t.title ILIKE '%' || :filterText || '%' OR t.name ILIKE '%' || :filterText || '%' OR t.content ILIKE '%' || :filterText || '%')
               )
          """;
    }
    if (ListUtil.isNotEmpty(tags)) {
      if (tags.contains(Customers.Tag.TASK_NOW)) {
        WHERE += " AND t.priority = 90";
      }
      if (tags.contains(Customers.Tag.TASK_TODAY)) {
        WHERE += " AND DATE(t.to_date) = CURRENT_DATE";
      }
      if (tags.contains(Customers.Tag.TASK_OVERDUE)) {
        WHERE += " AND DATE(t.to_date) < CURRENT_DATE";
      }
      if (tags.contains(Customers.Tag.TASK_NO_DEADLINE)) {
        WHERE += " AND t.to_date IS NULL";
      }
    }
    return WHERE;
  }
}
