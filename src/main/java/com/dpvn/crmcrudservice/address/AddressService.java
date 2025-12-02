package com.dpvn.crmcrudservice.address;

import org.springframework.stereotype.Service;

@Service
public class AddressService {
  //  public Address findAddressByLocationNames(
  //      String wardNameInput, String districtNameInput, String provinceNameInput) {
  //    if (StringUtil.isEmpty(wardNameInput)
  //        || StringUtil.isEmpty(districtNameInput)
  //        || StringUtil.isEmpty(provinceNameInput)) {
  //      return null;
  //    }
  //    String wardName = wardNameInput.trim().toLowerCase().replaceAll("phường|xã", "").trim();
  //    String districtName =
  //        districtNameInput.trim().toLowerCase().replaceAll("quận|huyện|thị trấn", "").trim();
  //    String provinceName =
  //        provinceNameInput.trim().toLowerCase().replaceAll("tỉnh|thành phố", "").trim();
  //
  //    List<Address> addresses = cacheEntityService.getAddresses();
  //    List<Address> results =
  //        addresses.stream()
  //            .filter(
  //                address ->
  //                    address.getWardName().toLowerCase().contains(wardName)
  //                        && address.getDistrictName().toLowerCase().contains(districtName)
  //                        && address.getProvinceName().toLowerCase().contains(provinceName))
  //            .toList();
  //    if (ListUtil.isEmpty(results)) {
  //      return null;
  //    }
  //    return results.get(0);
  //  }
  //
  //  public Address findAddressById(Long id) {
  //    if (id == null) {
  //      return null;
  //    }
  //    List<Address> addresses = cacheEntityService.getAddresses();
  //    return addresses.stream()
  //        .filter(address -> id.equals(address.getId()))
  //        .findFirst()
  //        .orElse(null);
  //  }
}
