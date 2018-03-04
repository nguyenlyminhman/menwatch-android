package com.greenwich.utils;

/**
 * Created by nguye on 2/26/2018.
 */

public class MenwatchServer {
    //nhà: 192.168.0.102
    //trường: 192.168.247.2
    public static String host = "192.168.247.2:3000";
//    public static String host ="menwatch.herokuapp.com";
    public static String linkImage = "http://" + host + "/images/";
    public static String linkBrand = "http://" + host + "/api/brand";
    public static String linkStyle = "http://" + host + "/api/style";
    public static String linkLatestProduct = "http://" + host + "/api/product/all";
    public static String linkProductByBrand = "http://" + host + "/api/product/brand/";
    public static String linkProductByStyle = "http://" + host + "/api/product/style/";

}
