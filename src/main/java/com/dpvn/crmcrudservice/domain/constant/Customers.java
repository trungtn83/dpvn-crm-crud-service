package com.dpvn.crmcrudservice.domain.constant;

import java.util.Map;

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

    //  Đã chạy init xong cho khách hàng này
    public static final String ASSIGNED = "ASSIGNED";
    // Đã duyệt xong bởi leader/mkt
    public static final String VERIFIED = "VERIFIED";
    // Mới được GOD assign theo campaign để duyệt, chưa duyêt
    public static final String VERIFYING = "VERIFYING";
  }

  public static class Owner {
    private Owner() {}

    public static final String TREASURE = "TREASURE";
    public static final String GOLD = "GOLD";
    public static final String GOLDMINE = "GOLDMINE";
    public static final String SANDBANK = "SANDBANK";
  }

  public static Map<String, String> Owners =
      Map.of(
          Owner.TREASURE, "Trang sức",
          Owner.GOLD, "Vàng",
          Owner.GOLDMINE, "Kho vàng",
          Owner.SANDBANK, "Bãi cát");
}
