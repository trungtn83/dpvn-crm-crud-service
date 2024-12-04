package com.dpvn.crmcrudservice.domain.constant;

public class Tasks {
  public static class Type {
    private Type() {}

    public static final int PHONE = 10;
    public static final int MESSAGE = 20;
    public static final int EMAIL = 30;
    public static final int MEETING = 40;
    public static final int MEETING_ONLINE = 41;
    public static final int MEETING_OFFLINE = 42;
    public static final int PRICE_QUOTE = 500;
    public static final int SALE = 60;
    public static final int POST_SALE = 700;
    public static final int OTHER = 90;
  }

  public static class Priority {
    private Priority() {}

    public static final int LOW = 10;
    public static final int NORMAL = 20;
    public static final int HIGH = 30;
    public static final int URGENT = 40;
    public static final int RIGHT_NOW = 90;
  }
}
