package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.CustomerTypeDto;
import com.dpvn.crmcrudservice.domain.entity.CustomerType;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface CustomerTypeMapper extends BaseMapper<CustomerType, CustomerTypeDto> {}
