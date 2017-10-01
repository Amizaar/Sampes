package com.coprorated.amizaar.myarchitecturedtest.ui;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

/**
 * Created by amizaar on 02.08.2017.
 */

public class NetworkLiveData extends LiveData<Boolean> {
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private static NetworkLiveData instance;

    public static NetworkLiveData getInstance(Context context){
        if (instance == null){
            instance = new NetworkLiveData(context.getApplicationContext());
        }
        return instance;
    }

    private NetworkLiveData(Context context) {
        this.context = context;
    }

    private void prepareReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        filter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
//        filter.addAction("android.net.wifi.STATE_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager cm = (ConnectivityManager)
                        context.getApplicationContext().getSystemService (Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                setValue(ni != null && ni.isConnected());
            }
        };
        context.registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onActive() {
        prepareReceiver(context);
    } //todo produce leak memory o_0

    @Override
    protected void onInactive() {
        context.unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }

    public void update() {
        setValue(getValue());
    }
}
