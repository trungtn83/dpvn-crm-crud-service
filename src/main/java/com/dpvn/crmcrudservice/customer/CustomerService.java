package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.crmcrudservice.repository.CustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.repository.Paginator;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.crmcrudservice.user.UserService;
import com.dpvn.shared.exception.BadRequestException;
import com.dpvn.shared.log.Logger;
import com.dpvn.shared.service.AbstractService;
import com.dpvn.shared.util.FastMap;
import com.dpvn.shared.util.ObjectUtil;
import com.dpvn.shared.util.StringUtil;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer> {
  private final CustomerCustomRepository customerCustomRepository;
  private final SaleCustomerRepository saleCustomerRepository;
  private final UserService userService;
  private final SaleCustomerService saleCustomerService;

  public CustomerService(
      CustomerRepository repository,
      CustomerCustomRepository customerCustomRepository,
      SaleCustomerRepository saleCustomerRepository,
      UserService userService,
      SaleCustomerService saleCustomerService) {
    super(repository);
    this.customerCustomRepository = customerCustomRepository;
    this.saleCustomerRepository = saleCustomerRepository;
    this.userService = userService;
    this.saleCustomerService = saleCustomerService;
  }

  @Override
  public void sync(List<Customer> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Customer update(Long id, Customer entity) {
    Customer dbEntity = findById(id).orElseThrow();
    ObjectUtil.assign(dbEntity, entity, List.of("references"));
    dbEntity.getReferences().clear();
    List<CustomerReference> references = entity.getReferences();
    references.forEach(ref -> ref.setCustomer(dbEntity));
    dbEntity.getReferences().addAll(references);
    return save(dbEntity);
  }

  public List<Customer> findByIds(List<Long> ids) {
    return ((CustomerRepository) repository).findByIdIn(ids);
  }

  public List<Customer> search(FastMap condition) {
    Paginator paginator = Paginator.create().sortBy("created_date");
    // TODO: handle paging from condition here
    return customerCustomRepository.searchCustomers(
        condition.getString("name"),
        condition.getString("code"),
        condition.getInt("gender"),
        condition.getString("mobilePhone"),
        condition.getString("email"),
        condition.getString("address"),
        condition.getString("taxCode"),
        condition.getInt("customerType"),
        condition.getInt("status"),
        condition.getInt("availability"),
        condition.getString("source"),
        condition.getString("note"),
        paginator);
  }

  public List<Customer> findCustomersByMobilePhone(String mobilePhone) {
    return ((CustomerRepository) repository).findCustomersByMobilePhone(mobilePhone);
  }

  /**
   * @param sale
   * @param customer
   * @param info
   * @return null when ok, error message when can not assign based on business rule
   */
  private String assign(User sale, Customer customer, SaleCustomer info) {
    info.setSaleId(sale.getId());
    info.setCustomer(customer);

    String revoke = revoke(sale, customer);
    if (revoke == null) {
      saleCustomerService.save(info);
    }

    return revoke;
  }

  private String revoke(User sale, Customer customer) {
    SaleCustomer dbActiveSaleCustomer =
        saleCustomerRepository.findBySaleIdAndCustomerIdAndActive(
            sale.getId(), customer.getId(), Boolean.TRUE);
    if (dbActiveSaleCustomer != null) {
      if (dbActiveSaleCustomer.getRelationshipType() == RelationshipType.PIC) {
        String errorMessage =
            String.format(
                "Customer %s is belong to other sale: %d",
                customer.getId(), dbActiveSaleCustomer.getSaleId());
        Logger.error(errorMessage);
        return errorMessage;
      }

      dbActiveSaleCustomer.setActive(Boolean.FALSE);
      saleCustomerService.save(dbActiveSaleCustomer);
    }

    // update last modified field (and by in the future)
    save(customer);

    return null;
  }

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
   * @param saleId
   * @param customerIds, ignore invalid one
   * @param info
   * @return list of failed customers that can not be assigned to sale
   */
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
      Long userId, String filterText, List<String> tags, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.searchInPoolCustomers(userId, filterText, tags, pageable);
  }

  public Page<FastMap> findMyCustomers(
      Long saleId,
      Long customerCategoryId,
      String filterText,
      List<Integer> reasonIds,
      int page,
      int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.searchMyCustomers(
        saleId, customerCategoryId, filterText, reasonIds, pageable);
  }

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
}
