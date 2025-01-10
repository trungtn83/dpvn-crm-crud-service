package com.dpvn.crmcrudservice.customer;

import com.dpvn.crmcrudservice.domain.constant.RelationshipType;
import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.crmcrudservice.domain.entity.Customer;
import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
import com.dpvn.crmcrudservice.domain.entity.User;
import com.dpvn.shared.util.DateUtil;

import java.time.temporal.ChronoUnit;

public class SaleCustomerUtil {
  private SaleCustomerUtil() {}

  public static SaleCustomer generateSaleCustomer(Customer customer, User sale, Campaign campaign) {
    SaleCustomer saleCustomer = new SaleCustomer();
    saleCustomer.setSaleId(sale.getId());
    saleCustomer.setCustomer(customer);
    saleCustomer.setRelationshipType(RelationshipType.PIC);
      saleCustomer.setReasonId(SaleCustomers.Reason.CAMPAIGN);
      saleCustomer.setReasonRef(campaign.getId().toString());
      saleCustomer.setReasonNote("Tạo ra khi dispatch campaign " + campaign.getCampaignName());
      saleCustomer.setAvailableFrom(campaign.getStartDate());
      saleCustomer.setAvailableTo(campaign.getEndDate());
    saleCustomer.setNote("");
    saleCustomer.setActive(true);
    saleCustomer.setDeleted(false);
    return saleCustomer;
  }

  public static SaleCustomer generateSaleCustomer(Customer customer, User sale) {
    SaleCustomer saleCustomer = new SaleCustomer();
    saleCustomer.setSaleId(sale.getId());
    saleCustomer.setCustomer(customer);
    saleCustomer.setRelationshipType(RelationshipType.PIC);
    saleCustomer.setReasonId(SaleCustomers.Reason.LEADER);
    saleCustomer.setReasonRef(""); // should inject x-user-id here to know who assign this
    saleCustomer.setReasonNote("Tạo ra khi reviewer approve customer");
    saleCustomer.setAvailableFrom(DateUtil.now());
    saleCustomer.setAvailableTo(DateUtil.now().plus(7, ChronoUnit.DAYS));
    saleCustomer.setNote("");
    saleCustomer.setActive(true);
    saleCustomer.setDeleted(false);
    return saleCustomer;
  }
}
