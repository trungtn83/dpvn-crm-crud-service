package com.dpvn.crmcrudservice.domain.mapper;

import com.dpvn.crmcrudservice.domain.dto.TaskDto;
import com.dpvn.crmcrudservice.domain.entity.Task;
import com.dpvn.sharedcore.domain.mapper.BaseMapper;
import com.dpvn.sharedcore.domain.mapper.BaseMapperConfig;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = BaseMapperConfig.class)
public interface TaskMapper extends BaseMapper<Task, TaskDto> {}
