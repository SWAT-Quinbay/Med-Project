package com.example.dealerService.utils;

public class Constants {

    public static final String DEALER_ROLE ="DEALER";
    public static final String RETAILER_ROLE ="RETAILER";
    public static final String ADMIN_ROLE ="ADMIN";

    public static final String DEALER_KAFKA_TOPIC = "dealerStockUpdate";
    public static final String DEALER_KAFKA_GROUP_ID = "dealerGroup";


    public static final String DEALER_BASE_URL = "/dealer";
    public static final String DEALER_ADD = "/add";
    public static final String DEALER_SEARCH_ALL = "/search/all";
    public static final String DEALER_GET_BY_ID = "/id";
    public static final String DEALER_CHECK_QUANTITY = "/check";
    public static final String DEALER_REDUCE_QUANTITY = "/reduce";





    public static final String INTERNAL_ERROR_MESSAGE ="Oops!. Something went wrong.we will rectify it as soon as possible.";
    public static final String INVALID_DATA_PROVIDED ="Invalid data provided in request params";

}
