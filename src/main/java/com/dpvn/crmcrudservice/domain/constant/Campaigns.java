package com.dpvn.crmcrudservice.domain.constant;

public class Campaigns {
  private Campaigns() {}
  public static class DispatchType {
    private DispatchType() {}
    public static final int ROUND_ROBIN = 1;
    public static final int REVENUE_CURRENT_LEAST = 20;
    public static final int REVENUE_LAST_MONTH_LEAST = 21;
    public static final int CUSTOMER_OWN_LEAST = 30;
    public static final int CUSTOMER_TAKING_CARE_LEAST = 31;
  }
}
