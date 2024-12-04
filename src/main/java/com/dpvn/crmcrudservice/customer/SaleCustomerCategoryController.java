package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.AbstractController;
import com.dpvn.crmcrudservice.domain.dto.SaleCustomerCategoryDto;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomerCategory;
import com.dpvn.shared.util.StringUtil;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale-customer-category")
public class SaleCustomerCategoryController
    extends AbstractController<SaleCustomerCategory, SaleCustomerCategoryDto> {
  public SaleCustomerCategoryController(SaleCustomerCategoryService service) {
    super(service);
  }

  @GetMapping("/find-by-options")
  public List<SaleCustomerCategoryDto> findSaleCustomerCategoriesByOptions(
      @RequestParam Long saleId, @RequestParam(required = false) String code) {
    List<SaleCustomerCategory> saleCustomerCategories =
        StringUtil.isEmpty(code)
            ? ((SaleCustomerCategoryService) service).findBySaleId(saleId)
            : ((SaleCustomerCategoryService) service).findByCode(saleId, code);
    return saleCustomerCategories.stream().map(SaleCustomerCategory::toDto).toList();
  }
}
