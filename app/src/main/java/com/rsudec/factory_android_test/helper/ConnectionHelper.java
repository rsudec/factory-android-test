package com.rsudec.factory_android_test.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.util.Log;

public class ConnectionHelper {

    static private ConnectionHelper INSTANCE;
    static private boolean isConnected = false;

    private ConnectionHelper(){}

    public static ConnectionHelper getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ConnectionHelper();
        }
        return INSTANCE;
    }
    public void registerNetworkCallback(Context ctx)
    {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback(){
                                                                   @Override
                                                                   public void onAvailable(Network network) {

                                                                       isConnected = true;
                                                                       Log.d("CONNECTION", "Uređaj je spojen.");
                                                                   }
                                                                   @Override
                                                                   public void onLost(Network network) {
                                                                       isConnected = false; // Global Static Variable
                                                                       Log.d("CONNECTION", "Veza prekinuta.");
                                                                   }
                                                               }

            );
            isConnected = false;
        }catch (Exception e){
            isConnected = false;
        }
    }
    public  boolean isConnected() {
        return isConnected;
    }


}
