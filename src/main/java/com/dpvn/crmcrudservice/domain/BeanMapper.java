package com.dpvn.crmcrudservice.domain;

import com.dpvn.crmcrudservice.domain.dto.UserDto;
import com.dpvn.crmcrudservice.domain.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

public class BeanMapper {
  private static BeanMapper instance;
  private final ModelMapper modelMapper;

  private BeanMapper() {
    modelMapper = new ModelMapper();
    modelMapper
        .getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT)
        .setFieldMatchingEnabled(true)
        .setSkipNullEnabled(true);
    // Example of custom property mapping
    //      // Customize mappings for specific complex transformations, for example:
    //      instance.typeMap(SourceClass.class, DestinationClass.class)
    //          .addMappings(mapper -> {
    //            mapper.map(src -> src.getNestedObject().getInnerProperty(),
    //                DestinationClass::setSimpleProperty);
    //            // Handle cases where properties do not match automatically
    //            mapper.map(SourceClass::getOldName, DestinationClass::getNewName);
    //          });
    // Custom mappings
    modelMapper.addMappings(new PropertyMap<User, UserDto>() {
      @Override
      protected void configure() {
        map().setRoleId(source.getRole().getId());
        map().setDepartmentId(source.getDepartment().getId());
        map().setAddressId(source.getAddress().getId());
      }
    });
  }

  public static BeanMapper instance() {
    if (instance == null) {
      instance = new BeanMapper();
    }
    return instance;
  }

  public <T> T map(Object source, Class<T> destinationType) {
    return modelMapper.map(source, destinationType);
  }
}
