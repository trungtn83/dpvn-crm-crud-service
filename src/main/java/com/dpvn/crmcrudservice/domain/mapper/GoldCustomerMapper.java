package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.GoldCustomerDto;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface GoldCustomerMapper extends BaseMapper<Customer, GoldCustomerDto> {}
