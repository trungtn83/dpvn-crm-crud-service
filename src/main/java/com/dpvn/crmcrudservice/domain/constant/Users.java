package com.dpvn.crmcrudservice.domain.constant;

public class Users {
  private Users() {}

  public static class Department {
    private Department() {}

    public static final String BOM = "BOM";
    public static final String ACCOUNT = "ACCOUNT";
    public static final String MKT = "MKT";
    public static final String ADMIN = "ADMIN";
    public static final String SALE = "SALE";
  }

  public static class Role {
    private Role() {}

    public static final String HUMAN = "HUMAN";
    public static final String SUPERMAN = "SUPERMAN";
    public static final String DEMIGOD = "DEMIGOD";
    public static final String GOD = "GOD";
  }

  public static class Property {
    private Property() {}

    public static final String VOIP24H = "VOIP24H";
    public static final String PHONE = "PHONE";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String COMPUTER = "COMPUTER";
  }
}
