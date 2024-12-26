package com.dpvn.crmcrudservice.domain.constant;

public class Customers {
  private Customers() {}

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

  public static class Type {
    private Type() {}

    public static final int PHARMACY = 1;
    public static final int PHARMACIST = 2;
    public static final int PATIENT = 3;
    public static final int HOSPITAL = 4;
    public static final int HEALTHCARE_CENTER = 5; // BEAUTY_SALON
    public static final int DRUGSTORE = 6;
    public static final int CLINIC = 7;
    public static final int DENTISTRY = 8;
    public static final int COMPANY = 9;
    public static final int OTHER = 99;
  }

  public static class Category {
    private Category() {}

    public static final int WHOLE_SALE = 1;
    public static final int RETAIL_SALE = 2;
    public static final int COMPANY = 3;
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
    public static final String OTHER = "OTHER";
  }

  /**
   * Chia khách hàng thành 1 số phân loại cố định theo business như sau: - SOLD: khách hàng đã từng
   * bán - TAKING_CARE: khách đang chăm sóc, dựa trên lượt interaction/task có trong 1 tuần (có thể
   * cầu hình sau) - cont...
   */
  public static class Tag {
    private Tag() {}

    public static final String SOLD = "SOLD";
    public static final String TAKING_CARE = "TAKING_CARE";

    public static final String TASK_NOW = "TASK_NOW";
    public static final String TASK_TODAY = "TASK_TODAY";
    public static final String TASK_OVERDUE = "TASK_OVERDUE";
    public static final String TASK_NO_DEADLINE = "TASK_NO_DEADLINE";
  }

  public static class Status {
    private Status() {}

    public static final String ASSIGNED = "ASSIGNED";
  }
}
