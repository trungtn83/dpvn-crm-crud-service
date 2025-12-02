package com.dpvn.crmcrudservice.domain.constant;

public class SaleCustomers {
  private SaleCustomers() {}

  public static class Status {
    private Status() {}

    public static final String TREASURE = "TREASURE";
    public static final String GOLD = "GOLD";
  }

  public static class Reason {
    private Reason() {}

    public static final String INVOICE = "INVOICE";
    public static final String ORDER = "ORDER";
    public static final String DISCOVERY = "DISCOVERY";
    public static final String GOLDMINE = "GOLDMINE";
    public static final String SANDBANK = "SANDBANK";
    public static final String CAMPAIGN = "CAMPAIGN";
    public static final String LEADER = "LEADER";
    public static final String OTHER = "OTHER";
  }

  public static class Reference {
    private Reference() {}

    public static final String FAVORITE = "FAVORITE";
    public static final String BOOM = "BOOM";
    public static final String SOLD = "SOLD";
  }
}
