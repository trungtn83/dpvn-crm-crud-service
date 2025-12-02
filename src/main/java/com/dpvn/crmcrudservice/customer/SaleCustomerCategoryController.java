package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.dto.SaleCustomerCategoryDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import com.dpvn.crmcrudservice.domain.mapper.SaleCustomerCategoryMapper;
import com.dpvn.sharedjpa.controller.AbstractCrudController;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/sale-customer/category")
public class SaleCustomerCategoryController
    extends AbstractCrudController<SaleCustomerCategory, SaleCustomerCategoryDto> {

  private final SaleCustomerCategoryMapper saleCustomerCategoryMapper;

  public SaleCustomerCategoryController(
      SaleCustomerCategoryMapper mapper,
      SaleCustomerCategoryService service,
      SaleCustomerCategoryMapper saleCustomerCategoryMapper) {
    super(mapper, service);
    this.saleCustomerCategoryMapper = saleCustomerCategoryMapper;
  }

  @GetMapping("/find-by-sale")
  public List<SaleCustomerCategoryDto> findAllSaleCustomerCategoriesBySale(
      @RequestParam Long saleId) {
    List<SaleCustomerCategory> saleCustomerCategories =
        ((SaleCustomerCategoryService) service).findAllSaleCustomerCategoriesBySale(saleId);
    return saleCustomerCategoryMapper.toDtoList(saleCustomerCategories);
  }
}
