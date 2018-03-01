package com.greenwich.utils;

/**
 * Created by nguye on 2/26/2018.
 */

public class MenwatchServer {
    //nhà: 192.168.0.102
    //trường: 192.168.247.2
    public static String host = "192.168.247.2";
    public static String linkBrand = "http://" + host + ":3000/api/brand";
    public static String linkStyle = "http://" + host + ":3000/api/style";
    public static String linkLatestProduct = "http://" + host + ":3000/api/product/all";
}
