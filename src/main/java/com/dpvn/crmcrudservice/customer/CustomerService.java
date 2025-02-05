package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.address.AddressService;
import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.CustomerAddress;
import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.interaction.InteractionService;
import com.dpvn.crmcrudservice.interaction.InteractionUtil;
import com.dpvn.crmcrudservice.repository.CacheEntityService;
import com.dpvn.crmcrudservice.repository.CustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.crmcrudservice.user.UserService;
import com.dpvn.shared.domain.entity.Address;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.ObjectUtil;
import com.dpvn.shared.util.StringUtil;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService extends AbstractCrudService<Customer> {
  private final CustomerCustomRepository customerCustomRepository;
  private final SaleCustomerRepository saleCustomerRepository;
  private final UserService userService;
  private final SaleCustomerService saleCustomerService;
  private final AddressService addressService;
  private final CacheEntityService cacheEntityService;
  private final InteractionService interactionService;

  public CustomerService(
      CustomerRepository repository,
      CustomerCustomRepository customerCustomRepository,
      SaleCustomerRepository saleCustomerRepository,
      UserService userService,
      SaleCustomerService saleCustomerService,
      AddressService addressService,
      CacheEntityService cacheEntityService,
      InteractionService interactionService) {
    super(repository);
    this.customerCustomRepository = customerCustomRepository;
    this.saleCustomerRepository = saleCustomerRepository;
    this.userService = userService;
    this.saleCustomerService = saleCustomerService;
    this.addressService = addressService;
    this.cacheEntityService = cacheEntityService;
    this.interactionService = interactionService;
  }

  @Transactional
  @Override
  public void sync(List<Customer> entities) {
    List<Customer> customers = new ArrayList<>();
    AtomicInteger inserted = new AtomicInteger(0);
    AtomicInteger updated = new AtomicInteger(0);

    try {
      entities.forEach(
          entity -> {
            injectAddressId(entity);
            injectCustomerType(entity);
            standardizeMobilePhone(entity);
          });

      // unique input data
      Set<String> mobiles = new HashSet<>();
      List<Customer> uniqueCustomers =
          entities.stream()
              .filter(customer -> StringUtil.isNotEmpty(customer.getMobilePhone()))
              .filter(customer -> mobiles.add(customer.getMobilePhone()))
              .toList();

      uniqueCustomers.stream()
          .filter(entity -> StringUtil.isNotEmpty(entity.getMobilePhone()))
          .forEach(
              entity -> {
                Customer dbCustomer =
                    ((CustomerRepository) repository)
                        .findByMobilePhone(entity.getMobilePhone())
                        .orElse(null);
                if (dbCustomer == null) {
                  entity.getReferences().forEach(ref -> ref.setCustomer(entity));
                  entity.getAddresses().forEach(address -> address.setCustomer(entity));
                  customers.add(entity);
                  inserted.getAndIncrement();
                } else {
                  ObjectUtil.assign(dbCustomer, entity, List.of("references", "addresses"));

                  if (ListUtil.isNotEmpty(entity.getReferences())) {
                    dbCustomer.getReferences().clear();
                    List<CustomerReference> references = entity.getReferences();
                    references.forEach(ref -> ref.setCustomer(dbCustomer));
                    dbCustomer.getReferences().addAll(references);
                  }
                  if (ListUtil.isNotEmpty(entity.getAddresses())) {
                    dbCustomer.getAddresses().clear();
                    List<CustomerAddress> addresses = entity.getAddresses();
                    addresses.forEach(address -> address.setCustomer(dbCustomer));
                    dbCustomer.getAddresses().addAll(addresses);
                  }
                  customers.add(dbCustomer);
                  updated.getAndIncrement();
                }
              });
      saveAll(customers);
      LOGGER.info(
          ListUtil.toString(customers.stream().map(Customer::getMobilePhone).toList()),
          String.format(
              "Received %d customers, stored=%d, inserted=%d, updated=%d",
              entities.size(), customers.size(), inserted.get(), updated.get()));
    } catch (Exception e) {
      LOGGER.error(
          ListUtil.toString(Arrays.asList(e.getStackTrace())), "Failed to sync customers: %s", e);
    }
  }

  private void standardizeMobilePhone(Customer customer) {
    String customerMobilePhone = customer.getMobilePhone();
    if (StringUtil.isNotEmpty(customerMobilePhone)) {
      List<String> phones = StringUtil.split(customerMobilePhone, "[/-]");
      String mobilePhone = phones.get(0);
      customer.setMobilePhone(StringUtil.standardizePhoneNumber(mobilePhone));
      for (int i = 1; i < phones.size(); i++) {
        String referencePhone = phones.get(i);
        CustomerReference reference = new CustomerReference();
        reference.setCustomer(customer);
        reference.setCode(Customers.References.MOBILE_PHONE);
        reference.setValue(StringUtil.standardizePhoneNumber(referencePhone));
        customer.getReferences().add(reference);
      }
    }
  }

  public List<Customer> findByStatusForInitRelationship(Integer page, Integer pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id"));
    return ((CustomerRepository) repository)
        .findByStatusForInitRelationship(pageable).stream().toList();
  }

  private void injectAddressId(Customer customer) {
    customer
        .getAddresses()
        .forEach(
            customerAddress -> {
              Address address =
                  addressService.findAddressByLocationNames(
                      customerAddress.getWardName(),
                      customerAddress.getDistrictName(),
                      customerAddress.getProvinceName());
              if (address != null) {
                customerAddress.setIdf(address.getId());
                customerAddress.setWardName(address.getWardName());
                customerAddress.setWardCode(address.getWardCode());
                customerAddress.setDistrictName(address.getDistrictName());
                customerAddress.setDistrictCode(address.getDistrictCode());
                customerAddress.setProvinceName(address.getProvinceName());
                customerAddress.setProvinceCode(address.getProvinceCode());
                customerAddress.setRegionName(address.getRegionName());
                customerAddress.setRegionCode(address.getRegionCode());
              }
            });
  }

  private void injectCustomerType(Customer customer) {
    if (customer.getCustomerTypeId() == null) {
      List<CustomerType> customerTypes = cacheEntityService.getCustomerTypes();
      CustomerType customerType =
          customerTypes.stream()
              .filter(
                  ct -> {
                    List<String> types = StringUtil.split(ct.getTypeReferences().toLowerCase());
                    types.add(ct.getTypeCode().toLowerCase());
                    String type =
                        customer.getCustomerType() == null
                            ? ""
                            : customer.getCustomerType().toLowerCase();
                    return types.contains(type);
                  })
              .findFirst()
              .orElse(
                  customerTypes.stream()
                      .filter(ct -> ct.getTypeCode().equals("OTHER"))
                      .findFirst()
                      .orElseThrow(
                          () -> {
                            LOGGER.error(
                                String.format(
                                    "Customer type %s not found", customer.getCustomerType()),
                                customer.getCustomerType());
                            return new BadRequestException(
                                String.format(
                                    "Customer type %s not found", customer.getCustomerType()));
                          }));
      customer.setCustomerTypeId(customerType.getId());
    }
  }

  public List<Customer> findByIds(List<Long> ids) {
    return ((CustomerRepository) repository).findByIdIn(ids);
  }

  public List<Customer> findCustomersByMobilePhone(String mobilePhone) {
    return ((CustomerRepository) repository).findCustomersByMobilePhone(mobilePhone);
  }

  /**
   * @return null when ok, error message when can not assign based on business rule
   */
  @Transactional
  public String assign(User sale, Customer customer, SaleCustomer info) {
    info.setSaleId(sale.getId());
    info.setCustomer(customer);

    String revoke = revoke(sale, customer);
    if (revoke == null) {
      saleCustomerService.create(info);
    }

    return revoke;
  }

  @Transactional
  public String revoke(User sale, Customer customer) {
    SaleCustomer dbActiveSaleCustomer =
        saleCustomerRepository.findBySaleIdAndCustomerIdAndActiveAndDeleted(
            sale.getId(), customer.getId(), true, false);
    if (dbActiveSaleCustomer != null) {
      if (dbActiveSaleCustomer.getRelationshipType() == RelationshipType.PIC) {
        String errorMessage =
            String.format(
                "Customer %s is belong to other sale: %d",
                customer.getId(), dbActiveSaleCustomer.getSaleId());
        LOGGER.error(errorMessage);
        return errorMessage;
      }

      saleCustomerService.update(
          dbActiveSaleCustomer.getId(), FastMap.create().add("active", false).add("deleted", true));
    }

    return null;
  }

  @Transactional
  public void assign(Long saleId, Long customerId, SaleCustomer info) {
    User sale =
        userService
            .findById(saleId)
            .orElseThrow(
                () -> new BadRequestException(String.format("Sale with id %s not found", saleId)));
    Customer customer =
        findById(customerId)
            .orElseThrow(
                () ->
                    new BadRequestException(
                        String.format("Customer with id %s not found", customerId)));

    String result = assign(sale, customer, info);
    if (StringUtil.isNotEmpty(result)) {
      throw new BadRequestException(result);
    }
  }

  @Transactional
  public void revoke(Long saleId, Long customerId) {
    User sale =
        userService
            .findById(saleId)
            .orElseThrow(
                () -> new BadRequestException(String.format("Sale with id %s not found", saleId)));
    Customer customer =
        findById(customerId)
            .orElseThrow(
                () ->
                    new BadRequestException(
                        String.format("Customer with id %s not found", customerId)));
    String result = revoke(sale, customer);
    if (StringUtil.isNotEmpty(result)) {
      throw new BadRequestException(result);
    }
  }

  /**
   * @return list of failed customers that can not be assigned to sale
   */
  @Transactional
  public List<Long> assigns(Long saleId, List<Long> customerIds, SaleCustomer info) {
    User sale =
        userService
            .findById(saleId)
            .orElseThrow(
                () -> new BadRequestException(String.format("Sale with id %s not found", saleId)));
    List<Customer> customers = findByIds(customerIds);
    String infoStr = ObjectUtil.writeValueAsString(info);
    return customers.stream()
        .map(
            customer -> {
              // to avoid reference when saving info into database
              String result =
                  assign(sale, customer, ObjectUtil.readValue(infoStr, SaleCustomer.class));
              if (StringUtil.isNotEmpty(result)) {
                return customer.getId();
              }
              return null;
            })
        .filter(Objects::nonNull)
        .toList();
  }

  @Transactional
  public List<Long> revokes(Long saleId, List<Long> customerIds) {
    User sale =
        userService
            .findById(saleId)
            .orElseThrow(
                () -> new BadRequestException(String.format("Sale with id %s not found", saleId)));
    List<Customer> customers = findByIds(customerIds);
    return customers.stream()
        .map(
            customer -> {
              String result = revoke(sale, customer);
              if (StringUtil.isNotEmpty(result)) {
                return customer.getId();
              }
              return null;
            })
        .filter(Objects::nonNull)
        .toList();
  }

  public Page<FastMap> findTaskBasedCustomers(
      Long userId, String filterText, List<String> tags, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.searchTaskBasedCustomers(userId, filterText, tags, pageable);
  }

  public Page<FastMap> findInPoolCustomers(
      Long userId,
      String filterText,
      List<String> tags,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds,
      int page,
      int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.searchInPoolCustomers(
        userId, filterText, tags, typeIds, locationCodes, sourceIds, pageable);
  }

  public Page<Customer> findInOceanCustomers(
      String filterText,
      List<Long> typeIds,
      List<String> locationCodes,
      List<Integer> sourceIds,
      int page,
      int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.searchInOceanCustomers(
        filterText, typeIds, locationCodes, sourceIds, pageable);
  }

  public Page<FastMap> findMyCustomers(
      Long saleId,
      Long customerTypeId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds,
      int page,
      int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.searchMyCustomers(
        saleId, customerTypeId, customerCategoryId, filterText, reasonIds, pageable);
  }

  @Transactional
  public void updateLastTransaction(
      Long customerId, Instant lastTransaction, boolean isSuccessful) {
    Customer customer =
        findById(customerId)
            .orElseThrow(
                () ->
                    new BadRequestException(
                        String.format("Customer with id %s not found", customerId)));

    if (isSuccessful) {
      customer
          .getReferences()
          .removeIf(r -> Customers.References.LAST_SUCCESSFUL_TRANSACTION_DATE.equals(r.getCode()));
      CustomerReference lastSuccessfulTransactionReference = new CustomerReference();
      lastSuccessfulTransactionReference.setCustomer(customer);
      lastSuccessfulTransactionReference.setCode(
          Customers.References.LAST_SUCCESSFUL_TRANSACTION_DATE);
      lastSuccessfulTransactionReference.setValue(lastTransaction.toString());
      lastSuccessfulTransactionReference.setName("Giao dịch thành công cuối cùng");
      customer.getReferences().add(lastSuccessfulTransactionReference);
    }

    customer
        .getReferences()
        .removeIf(r -> Customers.References.LAST_TRANSACTION_DATE.equals(r.getCode()));
    CustomerReference lastTransactionReference = new CustomerReference();
    lastTransactionReference.setCustomer(customer);
    lastTransactionReference.setCode(Customers.References.LAST_TRANSACTION_DATE);
    lastTransactionReference.setValue(lastTransaction.toString());
    lastTransactionReference.setName("Giao dịch cuối cùng");
    customer.getReferences().add(lastTransactionReference);

    save(customer);
  }

  public Customer findLastCreatedCustomerBySource(Integer sourceId) {
    return ((CustomerRepository) repository).findFirstBySourceIdOrderByCreatedDateDesc(sourceId);
  }

  @Override
  @Transactional
  public Customer create(Customer entity) {
    injectAddress(entity);
    injectCustomerType(entity);
    entity.getAddresses().forEach(ca -> ca.setCustomer(entity));
    entity.getReferences().forEach(ref -> ref.setCustomer(entity));
    return super.create(entity);
  }

  @Override
  @Transactional
  public Customer update(Long id, FastMap data) {
    Customer dbEntity = findById(id).orElseThrow();
    ObjectUtil.assign(dbEntity, data);

    List<CustomerReference> references = data.getListClass("references", CustomerReference.class);
    if (ListUtil.isNotEmpty(references)) {
      dbEntity.getReferences().clear();
      references.forEach(ref -> ref.setCustomer(dbEntity));
      dbEntity.getReferences().addAll(references);
    }

    List<CustomerAddress> addresses = data.getListClass("addresses", CustomerAddress.class);
    if (ListUtil.isNotEmpty(addresses)) {
      dbEntity.getAddresses().clear();
      addresses.forEach(ca -> ca.setCustomer(dbEntity));
      dbEntity.getAddresses().addAll(addresses);
    }

    injectAddress(dbEntity);
    injectCustomerType(dbEntity);
    return save(dbEntity);
  }

  private void injectAddress(Customer customer) {
    customer
        .getAddresses()
        .forEach(
            customerAddress -> {
              Address address = addressService.findAddressById(customerAddress.getIdf());
              if (address != null) {
                customerAddress.setWardCode(address.getWardCode());
                customerAddress.setWardName(address.getWardName());
                customerAddress.setDistrictCode(address.getDistrictCode());
                customerAddress.setDistrictName(address.getDistrictName());
                customerAddress.setProvinceCode(address.getProvinceCode());
                customerAddress.setProvinceName(address.getProvinceName());
                customerAddress.setRegionCode(address.getRegionCode());
                customerAddress.setRegionName(address.getRegionName());
              }
            });
  }

  @Transactional
  public void approveCustomerFromSandToGold(
      Long userId, Long customerId, Boolean approved, Integer dispatchTypeId, Long saleId) {
    Customer customer =
        update(
            customerId,
            FastMap.create().add("status", Customers.Status.VERIFIED).add("active", approved));
    String title = "Duyệt khách hàng";
    if (approved) {
      approveAndAssignToPool(customerId);
      if (saleId != null) {
        User sale = userService.findById(saleId).orElseThrow();
        approveAndAssignToSale(customer, sale);
        String saleName = userId.equals(saleId) ? "bản thân" : sale.getFullName();
        String content = String.format("Tìm ra vàng, phân công cho %s", saleName);
        interactionService.create(
            InteractionUtil.generateSystemInteraction(userId, customerId, null, title, content));
      } else if (dispatchTypeId != null) {
        LOGGER.info("approveAndAutoAssign is not implemented yet");
      } else {
        interactionService.create(
            InteractionUtil.generateSystemInteraction(
                userId,
                customerId,
                null,
                title,
                "Tìm ra vàng, không phân công cho ai và đưa về Kho vàng"));
      }
    } else {
      interactionService.create(
          InteractionUtil.generateSystemInteraction(
              userId,
              customerId,
              null,
              title,
              "Khách hàng không đồng ý chuyển từ cát lên vàng, đưa về trạng thái bị đóng băng"));
    }
  }

  private void approveAndAssignToPool(Long customerId) {
    List<SaleCustomer> saleCustomers =
        saleCustomerService.findSaleCustomersByOptions(
            null,
            List.of(customerId),
            RelationshipType.PIC,
            List.of(SaleCustomers.Reason.CAMPAIGN),
            null);
    saleCustomers.forEach(sc -> saleCustomerService.delete(sc.getId()));
  }

  private void approveAndAssignToSale(Customer customer, User sale) {
    approveAndAssignToPool(customer.getId());
    SaleCustomer saleCustomer = SaleCustomerUtil.generateSaleCustomer(customer, sale);
    saleCustomerService.create(saleCustomer);
  }
}
