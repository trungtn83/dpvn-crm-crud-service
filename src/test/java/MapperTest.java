import com.dpvn.crmcrudservice.domain.dto.CustomerStatusDto;
import com.dpvn.crmcrudservice.domain.entity.CustomerStatus;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.mapper.CustomerStatusMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MapperTest {
  private final CustomerStatusMapper mapper = Mappers.getMapper(CustomerStatusMapper.class);

  @Test
  public void testMapper() {
    CustomerStatus customerStatus = new CustomerStatus();

    SaleCustomer owner = new SaleCustomer();
    owner.setId(0L);
    owner.setSaleId(1L);
    customerStatus.setOwner(owner);

    SaleCustomer owner1 = new SaleCustomer();
    owner1.setId(1L);
    owner1.setSaleId(2L);
    SaleCustomer owner2 = new SaleCustomer();
    owner2.setId(2L);
    owner2.setSaleId(3L);
    owner2.setReasonCode("ORDER");
    customerStatus.setRelatedOwners(List.of(owner1, owner2));

    SaleCustomer owner3 = new SaleCustomer();
    owner3.setId(3L);
    owner3.setSaleId(4L);
    customerStatus.setOccupier(owner3);

    CustomerStatusDto dto = mapper.toDto(customerStatus);
    System.out.println(dto);
  }
}
