package com.greenwich.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nguye on 2/26/2018.
 */

public class Connection {
    public static boolean checkNetworkConnection( Context context) {

        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo nInfo : networkInfo) {

            if (nInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (nInfo.isConnected())
                    haveConnectedWifi = true;

            if (nInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (nInfo.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
