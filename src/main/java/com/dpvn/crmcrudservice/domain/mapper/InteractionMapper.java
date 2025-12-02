package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.InteractionDto;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface InteractionMapper extends BaseMapper<Interaction, InteractionDto> {}
