package com.dpvn.crmcrudservice.address;

import com.dpvn.shared.controller.AbstractCrudController;
import com.dpvn.shared.domain.dto.AddressDto;
import com.dpvn.shared.domain.entity.Address;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController extends AbstractCrudController<Address, AddressDto> {

  public AddressController(AddressService addressService) {
    super(addressService);
  }
}
