package com.dpvn.crmcrudservice.address;

import com.dpvn.crmcrudservice.repository.AddressRepository;
import com.dpvn.crmcrudservice.repository.CacheEntityService;
import com.dpvn.shared.domain.entity.Address;
import com.dpvn.shared.service.AbstractCrudService;
import com.dpvn.shared.util.ListUtil;
import com.dpvn.shared.util.StringUtil;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractCrudService<Address> {
  private final CacheEntityService cacheEntityService;

  public AddressService(AddressRepository repository, CacheEntityService cacheEntityService) {
    super(repository);
    this.cacheEntityService = cacheEntityService;
  }

  @Override
  public void sync(List<Address> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public Address findAddressByWardNameAndLocationName(String wardNameInput, String locationName) {
    if (StringUtil.isEmpty(wardNameInput) || StringUtil.isEmpty(locationName)) {
      return null;
    }
    List<String> locations = StringUtil.split(locationName, "-");
    if (ListUtil.isEmpty(locations)
        || locations.size() != 2
        || StringUtil.isEmpty(locations.get(0))
        || StringUtil.isEmpty(locations.get(1))) {
      return null;
    }
    String wardName = wardNameInput.trim().toLowerCase().replaceAll("phường|xã", "");
    String districtName =
        locations.get(0).trim().toLowerCase().replaceAll("quận|huyện|thị trấn", "");
    String provinceName = locations.get(1).trim().toLowerCase().replaceAll("tỉnh|thành phố", "");

    List<Address> addresses = cacheEntityService.getAddresses();
    List<Address> results =
        addresses.stream()
            .filter(
                address ->
                    address.getWardName().toLowerCase().contains(wardName)
                        || address.getDistrictName().toLowerCase().contains(districtName)
                        || address.getProvinceName().toLowerCase().contains(provinceName))
            .toList();
    if (ListUtil.isEmpty(results)) {
      return null;
    }
    return results.get(0);
  }
}
