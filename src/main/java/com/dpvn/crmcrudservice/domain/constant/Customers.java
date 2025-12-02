package com.dpvn.crmcrudservice.domain.constant;

public class Customers {
  private Customers() {}

  public static class Status {
    private Status() {}

    public static final String ORE = "ORE";
    public static final String RUN_OF_MINE = "RUN_OF_MINE";
    public static final String GANGUE = "GANGUE";
  }

  public static class Type {
    private Type() {}

    public static final Long INDIVIDUAL = 0L;
    public static final Long COMPENSARY = 1000L;
    public static final Long PHARMACY = 2000L;
    public static final Long COMPANY = 3000L;
    public static final Long OTHER = 9000L;
  }

  public static class Source {
    private Source() {}

    public static final int KIOTVIET = 1;
    public static final int CRAFTONLINE = 2;
    public static final int SHOPEE = 3;
    public static final int FACEBOOK = 4;
    public static final int TELE = 5;
    public static final int GOOGLE = 6;
    public static final int TIKTOK = 7;
    public static final int ADV = 8;
    public static final int ZALO = 9;
    public static final int OTHER = 99;
  }

  public static class References {
    private References() {}

    public static final String MOBILE_PHONE = "MOBILE_PHONE";
    public static final String ZALO = "ZALO";
    public static final String FACEBOOK = "FACEBOOK";
    public static final String TIKTOK = "TIKTOK";
    public static final String SHOPEE = "SHOPEE";
    public static final String GOOGLE_MAP = "GOOGLE_MAP";
    public static final String LAST_SUCCESSFUL_TRANSACTION_DATE =
        "LAST_SUCCESSFUL_TRANSACTION_DATE";
    public static final String LAST_TRANSACTION_DATE = "LAST_TRANSACTION_DATE";

    public static final String PAPER_LICENSE = "PAPER_LICENSE";
    public static final String PAPER_PHARMACY_ELIGIBILITY_LICENSE =
        "PAPER_PHARMACY_ELIGIBILITY_LICENSE";
    public static final String PAPER_EXAMINATION_TREATMENT_LICENSE =
        "PAPER_EXAMINATION_TREATMENT_LICENSE";
    public static final String PAPER_GPP = "PAPER_GPP";
    public static final String PAPER_GDP = "PAPER_GDP";
    public static final String PAPER_GSP = "PAPER_GSP";

    public static final String OTHER = "OTHER";
  }
}
