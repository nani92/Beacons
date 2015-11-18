package com.nataliajastrzebska.becheck;

/**
 * Created by nataliajastrzebska on 18/11/15.
 */
public class Beacon {
    String mac;
    int rssi;
    public Beacon (String mac, int rssi){
        this.mac = mac;
        this.rssi = rssi;
    }

    public int getRssi() {
        return rssi;
    }

    public String getMac() {
        return mac;
    }


}
