// package com.dpvn.crmcrudservice.customerold;
//
// import com.dpvn.crmcrudservice.domain.constant.SaleCustomers;
// import com.dpvn.crmcrudservice.domain.entity.Customer;
// import com.dpvn.crmcrudservice.domain.entity.SaleCustomer;
// import com.dpvn.sharedcore.domain.constant.Globals;
// import com.dpvn.sharedcore.util.DateUtil;
// import java.time.temporal.ChronoUnit;
//
// public class SaleCustomerUtil {
//  private SaleCustomerUtil() {}
//
//  public static SaleCustomer generateSaleCustomer(Customer customer, User sale, Campaign campaign)
// {
//    SaleCustomer saleCustomer = new SaleCustomer();
//    saleCustomer.setSaleId(sale.getId());
//    saleCustomer.setCustomer(customer);
//    saleCustomer.setRelationshipType(RelationshipType.PIC);
//    saleCustomer.setReasonId(SaleCustomers.Reason.CAMPAIGN);
//    saleCustomer.setReasonRef(campaign.getId().toString());
//    saleCustomer.setReasonNote("Tạo ra khi dispatch campaign " + campaign.getCampaignName());
//    saleCustomer.setAvailableFrom(campaign.getStartDate());
//    saleCustomer.setAvailableTo(campaign.getEndDate());
//    saleCustomer.setNote("");
//    saleCustomer.setActive(true);
//    saleCustomer.setDeleted(false);
//    return saleCustomer;
//  }
//
//  public static SaleCustomer generateSaleCustomer(Customer customer, User sale) {
//    SaleCustomer saleCustomer = new SaleCustomer();
//    saleCustomer.setSaleId(sale.getId());
//    saleCustomer.setCustomer(customer);
//    saleCustomer.setRelationshipType(RelationshipType.PIC);
//    saleCustomer.setReasonId(SaleCustomers.Reason.LEADER);
//    saleCustomer.setReasonRef(""); // should inject x-user-id here to know who assign this
//    saleCustomer.setReasonNote("Tạo ra khi reviewer approve customer");
//    saleCustomer.setAvailableFrom(DateUtil.now());
//    saleCustomer.setAvailableTo(
//        DateUtil.now().plus(Globals.Customer.LIFE_TIME_LEADER_ASSIGNED_IN_DAYS, ChronoUnit.DAYS));
//    saleCustomer.setNote("");
//    saleCustomer.setActive(true);
//    saleCustomer.setDeleted(false);
//    return saleCustomer;
//  }
// }
