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

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    public static final String GOD = "GOD";
  }
}
