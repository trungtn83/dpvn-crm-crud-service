package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.repository.CustomerCustomRepository;
import com.dpvn.crmcrudservice.repository.CustomerRepository;
import com.dpvn.crmcrudservice.repository.Paginator;
import com.dpvn.shared.util.FastMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<Customer> {
  private final CustomerCustomRepository customerCustomRepository;

  public CustomerService(
      CustomerRepository repository, CustomerCustomRepository customerCustomRepository) {
    super(repository);
    this.customerCustomRepository = customerCustomRepository;
  }

  @Override
  public void sync(List<Customer> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
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
        condition.getString("phone"),
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
}
