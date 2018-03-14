package com.greenwich.utils;

/**
 * Created by nguye on 2/26/2018.
 */

public class MenwatchServer {
    //nhà: 192.168.0.102
    //trường: 192.168.247.2
    public static String host = "http://192.168.247.2:3000";
//    public static String host ="https://menwatch.herokuapp.com";
    public static String linkImage =  host + "/images/";
    public static String linkBrand =  host + "/api/brand";
    public static String linkStyle = host + "/api/style";
    public static String linkLatestProduct = host + "/api/product/all";
    public static String linkProductByBrand = host + "/api/product/brand/";
    public static String linkProductByStyle = host + "/api/product/style/";
    public static String linkLogin = host + "/api/customer/login";
    public static String linkCheckout = host + "/api/customer/checkout";
    public static String linkRegister = host + "/api/customer/register";
}
