package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.client.ReportCrudServiceClient;
import com.dpvn.crmcrudservice.domain.constant.Customers;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.dto.CustomerDto;
import com.dpvn.crmcrudservice.domain.dto.GoldCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.GoldMineCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.TreasureCustomerDto;
import com.dpvn.crmcrudservice.domain.dto.ViewCustomerDetailDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.crmcrudservice.domain.entity.CustomerStatus;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.crmcrudservice.domain.mapper.CustomerMapper;
import com.dpvn.crmcrudservice.domain.mapper.CustomerStatusMapper;
import com.dpvn.crmcrudservice.domain.mapper.SaleCustomerStateMapper;
import com.dpvn.crmcrudservice.repository.CustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerRepository;
import com.dpvn.crmcrudservice.repository.SaleCustomerStateRepository;
import com.dpvn.reportcrudservice.domain.dto.UserDto;
import com.dpvn.sharedcore.domain.dto.PagingResponse;
import com.dpvn.sharedcore.util.ListUtil;
import com.dpvn.sharedcore.util.ObjectUtil;
import com.dpvn.sharedcore.util.StringUtil;
import com.dpvn.sharedjpa.service.AbstractCrudService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractCrudService<Customer> {

  private final SaleCustomerRepository saleCustomerRepository;
  private final CustomerCustomRepository customerCustomRepository;
  private final ReportCrudServiceClient reportCrudServiceClient;
  private final SaleCustomerStateRepository saleCustomerStateRepository;
  private final CustomerMapper customerMapper;
  private final CustomerStatusMapper customerStatusMapper;
  private final SaleCustomerStateMapper saleCustomerStateMapper;

  public CustomerService(
      CustomerRepository repository,
      SaleCustomerRepository saleCustomerRepository,
      CustomerCustomRepository customerCustomRepository,
      ReportCrudServiceClient reportCrudServiceClient,
      SaleCustomerStateRepository saleCustomerStateRepository,
      CustomerMapper customerMapper,
      CustomerStatusMapper customerStatusMapper,
      SaleCustomerStateMapper saleCustomerStateMapper) {
    super(repository);
    this.saleCustomerRepository = saleCustomerRepository;
    this.customerCustomRepository = customerCustomRepository;
    this.reportCrudServiceClient = reportCrudServiceClient;
    this.saleCustomerStateRepository = saleCustomerStateRepository;
    this.customerMapper = customerMapper;
    this.customerStatusMapper = customerStatusMapper;
    this.saleCustomerStateMapper = saleCustomerStateMapper;
  }

  @Override
  public void sync(List<Customer> entities) {
    entities.forEach(this::standardizeMobilePhone);

    List<Long> idfs = entities.stream().map(Customer::getIdf).filter(Objects::nonNull).toList();
    List<String> phones =
        entities.stream().map(Customer::getMobilePhone).filter(StringUtil::isNotEmpty).toList();

    Map<Long, Customer> dbCustomersByIdf =
        repository.findByIdfIn(idfs).stream()
            .collect(Collectors.toMap(Customer::getIdf, Function.identity()));
    Map<String, Customer> dbCustomersByPhone =
        ((CustomerRepository) repository)
            .findAllCustomersByMobilePhoneIn(phones).stream()
                .collect(
                    Collectors.toMap(
                        Customer::getMobilePhone, Function.identity(), (c1, c2) -> c1));

    List<Customer> customers = new ArrayList<>();
    // unique input data
    Set<String> mobiles = new HashSet<>();
    List<Customer> uniqueCustomers =
        entities.stream()
            .filter(
                customer ->
                    StringUtil.isEmpty(customer.getMobilePhone())
                        || mobiles.add(customer.getMobilePhone()))
            .toList();

    uniqueCustomers.forEach(
        entity -> {
          // fix for case Customer update phone number in Kiotviet system
          // phone number changed, but idf still existed =)))
          Customer dbCustomer =
              dbCustomersByIdf.get(entity.getIdf()) != null
                  ? dbCustomersByIdf.get(entity.getIdf())
                  : dbCustomersByPhone.get(entity.getMobilePhone());
          if (dbCustomer == null) {
            entity.getReferences().forEach(ref -> ref.setCustomer(entity));
            customers.add(entity);
          } else {
            ObjectUtil.assign(dbCustomer, entity, List.of("references"));

            if (ListUtil.isNotEmpty(entity.getReferences())) {
              List<CustomerReference> newReferences = entity.getReferences();
              newReferences.forEach(
                  newRef -> {
                    newRef.setCustomer(dbCustomer);
                    newRef.setActive(true);
                    newRef.setDeleted(false);
                  });
              dbCustomer
                  .getReferences()
                  .forEach(
                      oldRef -> {
                        if (newReferences.stream()
                            .noneMatch(nf -> StringUtil.equals(nf.getValue(), oldRef.getValue()))) {
                          CustomerReference newReference = new CustomerReference();
                          newReference.setCode(oldRef.getCode());
                          newReference.setValue(oldRef.getValue());
                          newReference.setActive(false);
                          newReference.setDeleted(false);
                          newReferences.add(newReference);
                        }
                      });
              dbCustomer.getReferences().addAll(newReferences);
            }

            customers.add(dbCustomer);
          }
        });
    saveAll(customers);
    LOGGER.info(
        "CustomerService.sync: {} records updated: {}",
        customers.size(),
        StringUtil.join(customers.stream().map(c -> c.getId().toString()).toList()));
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

  public CustomerStatus getCustomerStatus(Long customerId) {
    List<SaleCustomer> saleCustomers = saleCustomerRepository.findAllActiveByCustomerId(customerId);
    Customer customer = findById(customerId).orElseThrow();

    // khách đang lên đơn bởi Ngà, chăm dùm -> chuyển sng cho Trâm (có 2 owner tại 1 thời điểm
    SaleCustomer owner =
        saleCustomers.stream()
            .filter(sc -> SaleCustomers.Status.TREASURE.equals(sc.getStatus()))
            .max(Comparator.comparing(SaleCustomer::getAvailableTo))
            .orElse(null);
    List<SaleCustomer> relatedOwners =
        saleCustomers.stream()
            .filter(
                sc ->
                    (owner == null || sc.getId().equals(owner.getId()))
                        && !SaleCustomers.Status.TREASURE.equals(sc.getStatus()))
            .collect(Collectors.toList());
    SaleCustomer occupier =
        relatedOwners.stream()
            .filter(sc -> SaleCustomers.Reason.ORDER.equals(sc.getReasonCode()))
            .findFirst()
            .orElse(null);
    if (occupier != null) {
      relatedOwners.remove(occupier);
    }

    CustomerStatus customerStatus = new CustomerStatus();
    customerStatus.setOwner(owner);
    customerStatus.setRelatedOwners(relatedOwners);
    customerStatus.setStatus(customer.getStatus());
    customerStatus.setOccupier(occupier);
    if (occupier != null) {
      customerStatus.setAvailableTo(occupier.getAvailableTo());
    }
    if (owner != null) {
      customerStatus.setAvailableTo(owner.getAvailableTo());
    }
    return customerStatus;
  }

  public PagingResponse<TreasureCustomerDto> findAllMyTreasureCustomers(
      Long saleId, Long customerTypeId, String filterText, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.findAllMyTreasureCustomers(
        saleId, filterText, customerTypeId, pageable);
  }

  public PagingResponse<GoldCustomerDto> findAllMyGoldCustomers(
      Long saleId,
      Long customerTypeId,
      Long saleCustomerCategoryId,
      List<String> tags,
      String filterText,
      int page,
      int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.findAllMyGoldCustomers(
        saleId, filterText, customerTypeId, saleCustomerCategoryId, tags, pageable);
  }

  public ViewCustomerDetailDto getCustomerDetailView(Long userId, Long customerId) {
    ViewCustomerDetailDto result = new ViewCustomerDetailDto();

    Customer customer = findById(customerId).orElse(null);
    if (customer == null) {
      result.addError("Customer with ID " + customerId + " not found");
      return result;
    }
    result.setCustomer(customerMapper.toDto(customer));

    UserDto userDto = reportCrudServiceClient.getUserById(userId);
    CustomerStatus customerStatus = getCustomerStatus(customerId);
    result.setStatus(customerStatusMapper.toDto(customerStatus));

    if (!userDto.isGod()) {
      // nếu không phải owner
      if (customerStatus.getOwner() != null
          && !customerStatus.getOwner().getSaleId().equals(userId)) {
        return new ViewCustomerDetailDto("You don't have permission to view this customer");
      }

      // nếu đang là GOLD đến từ ORDER của ai đó ko phải mình
      List<Long> goldFromOrderOwners =
          customerStatus.getRelatedOwners().stream()
              .filter(sc -> SaleCustomers.Reason.ORDER.equals(sc.getStatus()))
              .map(SaleCustomer::getSaleId)
              .toList();
      if (ListUtil.isNotEmpty(goldFromOrderOwners) && !goldFromOrderOwners.contains(userId)) {
        return new ViewCustomerDetailDto("You don't have permission to view this customer");
      }

      SaleCustomerState saleCustomerState =
          saleCustomerStateRepository.findBySaleIdAndCustomerId(userId, customerId);
      if (saleCustomerState != null) {
        result.setState(saleCustomerStateMapper.toDto(saleCustomerState));
      }
    }

    return result;
  }

  public PagingResponse<GoldMineCustomerDto> findAllInGoldMineCustomers(
      Long saleId,
      Long sourceId,
      Long customerTypeId,
      List<String> tags,
      String filterText,
      int page,
      int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.findAllInGoldMineCustomers(
        saleId, filterText, sourceId, customerTypeId, tags, pageable);
  }

  public PagingResponse<CustomerDto> findAllInSandBankCustomers(
      Long sourceId, Long customerTypeId, String filterText, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return customerCustomRepository.findAllInSandBankCustomers(
        filterText, sourceId, customerTypeId, pageable);
  }
}
