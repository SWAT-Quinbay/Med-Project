package com.example.adminService.utils;

public class Constants {
    public static final String ADMIN_ROLE ="ADMIN";
    public static final String DEALER_ROLE ="DEALER";
    public static final String RETAILER_ROLE ="RETAILER";


    public static final String APPROVED_STATUS="APPROVED";
    public static final String PENDING_STATUS="PENDING";
    public static final String DENIED_STATUS="DENIED";

    public static final String DEALER_KAFKA_TOPIC = "dealerStockUpdate";
    public static final String RETAILER_KAFKA_TOPIC = "retailerStockUpdate";


    // Auth Controller
    public static final String AUTH_BASE_URL = "/auth";
    public static final String AUTH_VALIDATE_TOKEN = "/validateToken";

    //Inventory
    public static final String INVENTORY_BASE_URL = "/inventory";
    public static final String INVENTORY_GET_BY_ID = "/id/{id}";
    public static final String INVENTORY_GET_ALL = "/all";
    public static final String INVENTORY_SEARCH = "/search";
    public static final String INVENTORY_ADD = "/add";
    public static final String INVENTORY_ADD_ALL = "/add/all";
    public static final String INVENTORY_UPDATE = "/update";
    public static final String INVENTORY_UPDATE_ACTIVE = "/update/active/{id}";
    public static final String INVENTORY_DETAIL = "/detail";
    public static final String INVENTORY_DETAIL_SEARCH = "/detail/search";
}
