package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.dto.GoldCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.GoldMineCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.TreasureCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.mapper.CustomerMapper;
import com.dpvn.crmcrudservice.domain.mapper.GoldCustomerMapper;
import com.dpvn.crmcrudservice.domain.mapper.GoldMineCustomerMapper;
import com.dpvn.crmcrudservice.domain.mapper.TreasureCustomerMapper;
import com.dpvn.sharedcore.domain.dto.PagingResponse;
import com.dpvn.sharedcore.util.FastMap;
import com.dpvn.sharedcore.util.ListUtil;
import com.dpvn.sharedcore.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CustomerCustomRepository {
  private final TreasureCustomerMapper treasureCustomerMapper;
  private final GoldCustomerMapper goldCustomerMapper;
  private final Map<String, String> sqlTemplates;
  private final CustomerMapper customerMapper;
  private final GoldMineCustomerMapper goldMineCustomerMapper;
  @PersistenceContext private EntityManager entityManager;
  private final CustomerRepository customerRepository;

  public CustomerCustomRepository(
      CustomerRepository customerRepository,
      TreasureCustomerMapper treasureCustomerMapper,
      GoldCustomerMapper goldCustomerMapper,
      Map<String, String> sqlTemplates,
      CustomerMapper customerMapper,
      GoldMineCustomerMapper goldMineCustomerMapper) {
    this.customerRepository = customerRepository;
    this.treasureCustomerMapper = treasureCustomerMapper;
    this.goldCustomerMapper = goldCustomerMapper;
    this.sqlTemplates = sqlTemplates;
    this.customerMapper = customerMapper;
    this.goldMineCustomerMapper = goldMineCustomerMapper;
  }

  public PagingResponse<TreasureCustomerDto> findAllMyTreasureCustomers(
      Long userId, String filterText, Long customerTypeId, Pageable pageable) {
    String TEMPLATE_SELECT = sqlTemplates.get("treasure_customers_select.sql");
    String TEMPLATE_COUNT = sqlTemplates.get("treasure_customers_count.sql");

    StringBuilder filterBuilder = new StringBuilder();
    if (userId != null) {
      filterBuilder.append(" AND sc.sale_id = :saleId");
    }
    if (StringUtil.isNotEmpty(filterText)) {
      filterBuilder.append(
          " AND (c.customer_name ILIKE :filterText OR CAST(c.addresses as text) ILIKE :filterText OR c.mobile_phone LIKE :filterText OR c.tax_code ILIKE :filterText)");
    }
    if (customerTypeId != null) {
      filterBuilder.append(" AND c.customer_type_id = :customerTypeId");
    }

    String sql = TEMPLATE_SELECT.replace("{FILTER}", filterBuilder);
    String countSql = TEMPLATE_COUNT.replace("{FILTER}", filterBuilder);

    Query query = entityManager.createNativeQuery(sql);
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    Query countQuery = entityManager.createNativeQuery(countSql);

    if (userId != null) {
      query.setParameter("saleId", userId);
      countQuery.setParameter("saleId", userId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      String filterTextForSql = "%" + filterText + "%";
      query.setParameter("filterText", filterTextForSql);
      countQuery.setParameter("filterText", filterTextForSql);
    }
    if (customerTypeId != null) {
      query.setParameter("customerTypeId", customerTypeId);
      countQuery.setParameter("customerTypeId", customerTypeId);
    }

    List<Object[]> os = query.getResultList();
    List<FastMap> rawCustomers =
        os.stream()
            .map(
                o ->
                    FastMap.create().add("id", o[0]).add("available_to", o[1]).add("sale_id", o[2]))
            .toList();

    List<Customer> customers =
        customerRepository.findAllById(rawCustomers.stream().map(o -> o.getLong("id")).toList());
    Map<Long, Customer> customersMap =
        customers.stream().collect(Collectors.toMap(Customer::getId, c -> c));

    List<TreasureCustomerDto> treasureCustomerDtos =
        rawCustomers.stream()
            .map(
                rc -> {
                  Customer customer = customersMap.get(rc.getLong("id"));
                  TreasureCustomerDto dto = treasureCustomerMapper.toDto(customer);
                  dto.setSoldById(rc.getLong("sale_id"));
                  dto.setAvailableTo(rc.getInstant("available_to"));
                  return dto;
                })
            .toList();

    return new PagingResponse<>(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        ((Number) countQuery.getSingleResult()).longValue(),
        treasureCustomerDtos);
  }

  public PagingResponse<GoldCustomerDto> findAllMyGoldCustomers(
      Long userId,
      String filterText,
      Long customerTypeId,
      Long saleCustomerCategoryId,
      List<String> tags,
      Pageable pageable) {
    String TEMPLATE_SELECT = sqlTemplates.get("gold_customers_select.sql");
    String TEMPLATE_COUNT = sqlTemplates.get("gold_customers_count.sql");

    StringBuilder goldFilterBuilder = new StringBuilder();
    if (userId != null) {
      goldFilterBuilder.append(" AND sc.sale_id = :saleId");
    }
    if (ListUtil.isNotEmpty(tags)) {
      goldFilterBuilder.append(" AND sc.reason_code IN :tags");
    }

    StringBuilder baseFilterBuilder = new StringBuilder();
    if (StringUtil.isNotEmpty(filterText)) {
      baseFilterBuilder.append(
          " AND (c.customer_name ILIKE :filterText OR CAST(c.addresses as text) ILIKE :filterText OR c.mobile_phone LIKE :filterText OR c.tax_code LIKE :filterText)");
    }
    if (customerTypeId != null) {
      baseFilterBuilder.append(" AND c.customer_type_id = :customerTypeId");
    }
    if (saleCustomerCategoryId != null) {
      baseFilterBuilder.append(" AND scs.customer_category_id = :customerCategoryId");
    }

    String sql =
        TEMPLATE_SELECT
            .replace("{GOLD_FILTER}", goldFilterBuilder)
            .replace("{BASE_FILTER}", baseFilterBuilder);
    String countSql =
        TEMPLATE_COUNT
            .replace("{GOLD_FILTER}", goldFilterBuilder)
            .replace("{BASE_FILTER}", baseFilterBuilder);

    Query query = entityManager.createNativeQuery(sql);
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    Query countQuery = entityManager.createNativeQuery(countSql);

    if (userId != null) {
      query.setParameter("saleId", userId);
      countQuery.setParameter("saleId", userId);
    }
    if (StringUtil.isNotEmpty(filterText)) {
      String filterTextForSql = "%" + filterText + "%";
      query.setParameter("filterText", filterTextForSql);
      countQuery.setParameter("filterText", filterTextForSql);
    }
    if (customerTypeId != null) {
      query.setParameter("customerTypeId", customerTypeId);
      countQuery.setParameter("customerTypeId", customerTypeId);
    }
    if (saleCustomerCategoryId != null) {
      query.setParameter("customerCategoryId", saleCustomerCategoryId);
      countQuery.setParameter("customerCategoryId", saleCustomerCategoryId);
    }
    if (ListUtil.isNotEmpty(tags)) {
      query.setParameter("tags", tags);
      countQuery.setParameter("tags", tags);
    }

    List<Object[]> os = query.getResultList();
    List<FastMap> rawCustomers =
        os.stream()
            .map(
                o ->
                    FastMap.create()
                        .add("id", o[0])
                        .add("sale_ids", o[1])
                        .add("reason_codes", o[2])
                        .add("customer_category_ids", o[3])
                        .add("available_tos", o[4]))
            .toList();

    List<Customer> customers =
        customerRepository.findAllById(rawCustomers.stream().map(o -> o.getLong("id")).toList());
    Map<Long, Customer> customerMap =
        customers.stream().collect(Collectors.toMap(Customer::getId, c -> c));

    List<GoldCustomerDto> goldCustomerDtos =
        rawCustomers.stream()
            .map(
                rc -> {
                  Customer customer = customerMap.get(rc.getLong("id"));
                  GoldCustomerDto dto = goldCustomerMapper.toDto(customer);
                  dto.setSaleIds(
                      StringUtil.split(rc.getString("sale_ids")).stream()
                          .map(Long::parseLong)
                          .toList());
                  if (dto.getSaleIds().size() == 1) {
                    dto.setReasonCode(rc.getString("reason_codes"));
                    dto.setSaleCustomerCategoryId(rc.getLong("customer_category_ids"));
                    dto.setAvailableTo(rc.getInstant("available_tos"));
                  }
                  return dto;
                })
            .toList();
    return new PagingResponse<>(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        ((Number) countQuery.getSingleResult()).longValue(),
        goldCustomerDtos);
  }

  public PagingResponse<GoldMineCustomerDto> findAllInGoldMineCustomers(
      Long saleId,
      String filterText,
      Long sourceId,
      Long customerTypeId,
      List<String> tags,
      Pageable pageable) {
    String TEMPLATE_SELECT = sqlTemplates.get("in_goldmine_customers_select.sql");
    String TEMPLATE_COUNT = sqlTemplates.get("in_goldmine_customers_count.sql");

    StringBuilder filterBuilder = new StringBuilder();
    if (StringUtil.isNotEmpty(filterText)) {
      filterBuilder.append(
          " AND (c.customer_name ILIKE :filterText OR CAST(c.addresses as text) ILIKE :filterText OR c.mobile_phone LIKE :filterText OR c.tax_code ILIKE :filterText)");
    }
    if (sourceId != null) {
      filterBuilder.append(" AND c.source_id = :sourceId");
    }
    if (customerTypeId != null) {
      filterBuilder.append(" AND c.customer_type_id = :customerTypeId");
    }
    if (ListUtil.isNotEmpty(tags)) {
      if (tags.contains("SOLD")) {
        filterBuilder.append(
            " AND (EXISTS (SELECT 1 FROM sale_customer_reference scr WHERE scr.customer_id = c.id AND sc.status = 'SOLD' AND sc.active = TRUE AND sc.deleted = false))");
      }
    }

    String sql = TEMPLATE_SELECT.replace("{FILTER}", filterBuilder);
    String countSql = TEMPLATE_COUNT.replace("{FILTER}", filterBuilder);

    Query query = entityManager.createNativeQuery(sql);
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    Query countQuery = entityManager.createNativeQuery(countSql);

    if (StringUtil.isNotEmpty(filterText)) {
      String filterTextForSql = "%" + filterText + "%";
      query.setParameter("filterText", filterTextForSql);
      countQuery.setParameter("filterText", filterTextForSql);
    }
    if (sourceId != null) {
      query.setParameter("sourceId", sourceId);
      countQuery.setParameter("sourceId", sourceId);
    }
    if (customerTypeId != null) {
      query.setParameter("customerTypeId", customerTypeId);
      countQuery.setParameter("customerTypeId", customerTypeId);
    }
    if (ListUtil.isNotEmpty(tags)) {
      query.setParameter("tags", tags);
      countQuery.setParameter("tags", tags);
    }
    query.setParameter("saleId", saleId);
    countQuery.setParameter("saleId", saleId);

    List<Object[]> os = query.getResultList();
    List<FastMap> rawCustomers =
        os.stream()
            .map(o -> FastMap.create().add("id", o[0]).add("favorite", o[1]).add("boom", o[2]))
            .toList();
    List<Long> rawCustomerIds = rawCustomers.stream().map(o -> o.getLong("id")).toList();
    List<Customer> customers = customerRepository.findAllById(rawCustomerIds);
    Map<Long, Customer> customerMap =
        customers.stream().collect(Collectors.toMap(Customer::getId, c -> c));

    List<GoldMineCustomerDto> goldMineCustomerDtos =
        rawCustomers.stream()
            .map(
                rc -> {
                  Customer customer = customerMap.get(rc.getLong("id"));
                  GoldMineCustomerDto dto = goldMineCustomerMapper.toDto(customer);
                  dto.setBoom(rc.getBoolean("boom"));
                  dto.setFavorite(rc.getBoolean("favorite"));
                  return dto;
                })
            .toList();

    return new PagingResponse<>(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        ((Number) countQuery.getSingleResult()).longValue(),
        goldMineCustomerDtos);
  }

  public PagingResponse<CustomerDto> findAllInSandBankCustomers(
      String filterText, Long sourceId, Long customerTypeId, Pageable pageable) {
    String TEMPLATE_SELECT = sqlTemplates.get("in_sandbank_customers_select.sql");
    String TEMPLATE_COUNT = sqlTemplates.get("in_sandbank_customers_count.sql");

    StringBuilder filterBuilder = new StringBuilder();
    if (StringUtil.isNotEmpty(filterText)) {
      filterBuilder.append(
          " AND (c.customer_name ILIKE :filterText OR CAST(c.addresses as text) ILIKE :filterText OR c.mobile_phone LIKE :filterText OR c.tax_code ILIKE :filterText)");
    }
    if (sourceId != null) {
      filterBuilder.append(" AND c.source_id = :sourceId");
    }
    if (customerTypeId != null) {
      filterBuilder.append(" AND c.customer_type_id = :customerTypeId");
    }

    String sql = TEMPLATE_SELECT.replace("{FILTER}", filterBuilder);
    String countSql = TEMPLATE_COUNT.replace("{FILTER}", filterBuilder);

    Query query = entityManager.createNativeQuery(sql);
    query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    query.setMaxResults(pageable.getPageSize());
    Query countQuery = entityManager.createNativeQuery(countSql);

    if (StringUtil.isNotEmpty(filterText)) {
      String filterTextForSql = "%" + filterText + "%";
      query.setParameter("filterText", filterTextForSql);
      countQuery.setParameter("filterText", filterTextForSql);
    }
    if (sourceId != null) {
      query.setParameter("sourceId", sourceId);
      countQuery.setParameter("sourceId", sourceId);
    }
    if (customerTypeId != null) {
      query.setParameter("customerTypeId", customerTypeId);
      countQuery.setParameter("customerTypeId", customerTypeId);
    }

    List<Long> rawCustomerIds = query.getResultList();
    List<Customer> customers = customerRepository.findAllById(rawCustomerIds);

    return new PagingResponse<>(
        pageable.getPageNumber(),
        pageable.getPageSize(),
        ((Number) countQuery.getSingleResult()).longValue(),
        customerMapper.toDtoList(customers));
  }
}
