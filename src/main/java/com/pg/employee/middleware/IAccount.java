package com.pg.employee.middleware;

public interface IAccount {
    String getId();
    String getAccountEmail();
    String getAccountPassword();
    String getAccountType(); // 'restaurant' | 'employee'
    String getAccountRole();
    String getAccountRestaurantId();
    String getAccountEmployeeId();
}

