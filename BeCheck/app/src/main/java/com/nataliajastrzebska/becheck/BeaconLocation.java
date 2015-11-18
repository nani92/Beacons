package com.nataliajastrzebska.becheck;

import android.util.Log;

/**
 * Created by nataliajastrzebska on 18/11/15.
 */
public class BeaconLocation {
    private int x, y;
    private String mac;
    private int rssi;
    private boolean isChecked = false;

    public BeaconLocation(String mac, int x, int y){
        this.x = x;
        this.y = y;
        this.mac = mac;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
        shouldBeChecked();
    }

    public int getRssi(){
        return rssi;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getMac() {
        return  mac;
    }

    public boolean getIsChecked(){
        return isChecked;
    }

    void shouldBeChecked() {
        if (rssi > -30) {
            Log.d("natalia", "Beacon found");
            isChecked = true;
        }
    }
}
