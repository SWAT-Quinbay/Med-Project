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




    public static final String INTERNAL_ERROR_MESSAGE ="Oops!. Something went wrong.we will rectify it as soon as possible.";
    public static final String INVALID_DATA_PROVIDED ="Invalid data provided in request params";

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



    // stock - request
    public static final String STOCK_BASE_URL = "/stock";
    public static final String STOCK_GET_BY_RECEIVER = "/receiver/{id}";
    public static final String STOCK_GET_BY_SENDER = "/history/{id}";
    public static final String STOCK_REQUEST_FOR_ADMIN = "/admin";
    public static final String STOCK_REQUEST_FOR_DEALER = "/dealer";
    public static final String STOCK_UPDATE_STATUS = "/update/status";


    // user controller
    public static final String USER_BASE_URL = "/users";
    public static final String USER_GET_ALL = "";
    public static final String USER_GET_BY_ID = "/id/{id}";
    public static final String USER_GET_BY_USERNAME = "/username/{username}";
    public static final String USER_SEARCH = "/search";
    public static final String USER_ADD = "/add";
    public static final String USER_UPDATE_NAME = "/update/name";
    public static final String USER_UPDATE_PASSWORD = "/update/password";
    public static final String USER_UPDATE_ACTIVE = "/update/active/{id}";


}
