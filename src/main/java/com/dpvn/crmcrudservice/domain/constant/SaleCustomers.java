package com.dpvn.crmcrudservice.domain.constant;

import java.util.Map;

public class SaleCustomers {
  private SaleCustomers() {}

  public static class Reason {
    private Reason() {}

    /** TYPE = PIC Có đơn hàng phát sinh nên khách này là của sale trong vòng 3 tháng */
    public static final int INVOICE = 1;

    /**
     * TYPE = PIC Có đơn đặt hàng, tạm thời là temp owner cho đến khi đơn hàng hoàn thành chỉ chuyển
     * thành INVOICE
     */
    public static final int ORDER = 2;

    /**
     * TYPE = PIC Được gán với chiến dịch bán hàng của công ty, tạm thời là temp owner cho đến khi
     * hết thời gian hoặc có đơn
     */
    public static final int CAMPAIGN = 3;

    /**
     * TYPE = PIC Được gán bởi người quản lý, tạm thời là temp owner cho đến khi hết thời gian hoặc
     * có đơn
     */
    public static final int LEADER = 4;

    /**
     * TYPE = EVOLVE Tự gán/ được gán vào khách hàng để chú ý chăm sóc, có thể là khách tiềm năng
     * nên tag để tập trung hơn
     */
    public static final int STAR = 5;

    /**
     * TYPE = EVOLVE Đang request admin để gán customer này thành khách đang chăm sóc, vì nhìn thấy có tiềm năng hoặc đang chăm dở
     */
    public static final int REQUESTING = 6;

    /** TYPE = PIC or EVOLVE */
    public static final int OTHER = 99;
  }

  public static class State {
    private State() {}

    public static final int NEW_CONTACT = 1;
    public static final int INTERESTED = 2;
    public static final int BEING_CARED = 3;
    public static final int AWAITING_RESPONSE = 4;
    public static final int REJECTED = 5;
    public static final int CLOSED = 6;
    public static final int FOLLOW_UP = 7;
    public static final int INACTIVE = 8;
    public static final int OLD = 9;
    public static final int OTHER = 99;

    public static final Map<Integer, String> STATUS_MAP =
        Map.of(
            NEW_CONTACT, "Khách mới tiếp cận",
            INTERESTED, "Khách quan tâm",
            BEING_CARED, "Khách đang chăm sóc",
            AWAITING_RESPONSE, "Khách đang chờ phản hồi",
            REJECTED, "Khách từ chối",
            CLOSED, "Khách đã chốt",
            FOLLOW_UP, "Khách cần theo dõi",
            INACTIVE, "Khách không hoạt động",
            OLD, "Khách đã ra đơn",
            OTHER, "Khách khác");
  }
}
