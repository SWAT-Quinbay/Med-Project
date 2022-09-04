package com.example.salesService.CustomException;

public class SalesIDNotFound extends Exception{
    public SalesIDNotFound(String str){
        super(str);
    }
}
