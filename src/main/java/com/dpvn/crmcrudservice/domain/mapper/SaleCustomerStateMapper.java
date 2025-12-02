package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerStateDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerState;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface SaleCustomerStateMapper
    extends BaseMapper<SaleCustomerState, SaleCustomerStateDto> {}
