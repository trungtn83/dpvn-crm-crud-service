package com.dpvn.crmcrudservice.domain.constant;

public class Interactions {
  public static class Type {
    private Type() {}

    public static final int CUSTOMER_BLACK_LIST = 1;
    public static final int CUSTOMER_RESPONSE = 2;
    public static final int OTHER = 99;
  }

  public static class Visibility {
    private Visibility() {}

    public static final int PUBLIC = 0;
    public static final int PRIVATE = 1;
  }
}
