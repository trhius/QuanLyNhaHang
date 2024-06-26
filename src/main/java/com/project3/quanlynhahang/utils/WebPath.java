package com.project3.quanlynhahang.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WebPath {

    public interface CLIENT {
        String API_CLIENT_BASE_PATH = "/api/client";
        String API_CLIENT_AUTH_PATH = API_CLIENT_BASE_PATH + "/auth";
        String API_CLIENT_MENU_PATH = API_CLIENT_BASE_PATH + "/menu";
        String API_CLIENT_ORDER_PATH = API_CLIENT_BASE_PATH + "/order";
    }

    public interface ADMIN {
        String API_ADMIN_BASE_PATH = "/api/admin";
        String API_ADMIN_AUTH_PATH = API_ADMIN_BASE_PATH + "/auth";
        String API_ADMIN_MENU_PATH = API_ADMIN_BASE_PATH + "/menu";
        String API_ADMIN_EMPLOYEE_PATH = API_ADMIN_BASE_PATH + "/employee";
        String API_ADMIN_CUSTOMER_PATH = API_ADMIN_BASE_PATH + "/customer";
        String API_ADMIN_SUPPLIER_PATH = API_ADMIN_BASE_PATH + "/supplier";
        String API_ADMIN_REPORT_PATH = API_ADMIN_BASE_PATH + "/report";
    }

}
