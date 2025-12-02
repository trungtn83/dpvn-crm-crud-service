package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.CustomerStatusDto;
import com.dpvn.crmcrudservice.domain.entity.CustomerStatus;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface CustomerStatusMapper extends BaseMapper<CustomerStatus, CustomerStatusDto> {
  default Set<Long> mapRelatedOwnersToIds(List<SaleCustomer> saleCustomers) {
    return saleCustomers.stream().map(SaleCustomer::getSaleId).collect(Collectors.toSet());
  }

  default Long mapOwnerToId(SaleCustomer saleCustomer) {
    if (saleCustomer != null) {
      return saleCustomer.getSaleId();
    }
    return null;
  }

  default List<SaleCustomer> mapRelatedOwners(Set<Long> saleIds) {
    return saleIds.stream()
        .map(
            id -> {
              SaleCustomer saleCustomer = new SaleCustomer();
              saleCustomer.setSaleId(id);
              return saleCustomer;
            })
        .toList();
  }

  default SaleCustomer mapOwner(Long id) {
    if (id != null) {
      SaleCustomer saleCustomer = new SaleCustomer();
      saleCustomer.setSaleId(id);
      return saleCustomer;
    }
    return null;
  }
}
