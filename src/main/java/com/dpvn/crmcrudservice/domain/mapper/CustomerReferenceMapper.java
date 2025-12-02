package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.CustomerReferenceDto;
import com.dpvn.crmcrudservice.domain.entity.CustomerReference;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface CustomerReferenceMapper
    extends BaseMapper<CustomerReference, CustomerReferenceDto> {}
