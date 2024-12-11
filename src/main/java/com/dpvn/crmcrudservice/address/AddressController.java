package com.dpvn.crmcrudservice.address;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.AddressDto;
import com.dpvn.crmcrudservice.domain.entity.Address;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
public class AddressController extends AbstractController<Address, AddressDto> {

  public AddressController(AddressService addressService) {
    super(addressService);
  }
}
