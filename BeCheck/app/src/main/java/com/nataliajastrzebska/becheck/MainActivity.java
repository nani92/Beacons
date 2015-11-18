package com.nataliajastrzebska.becheck;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BluetoothLeScanner scanner;
    private ScanCallback scanCallback;
    List<Beacon> beacons;
    List<BeaconLocation> beaconLocationList;
    BeaconsView beaconsView;

    private static final ScanSettings SCAN_SETTINGS =
            new ScanSettings.Builder().
                    setScanMode(ScanSettings.MATCH_MODE_AGGRESSIVE)
                    .setReportDelay(0)
                    .build();


    private List<ScanFilter> buildScanFilters() {
        List<ScanFilter> scanFilters = new ArrayList<>();
        /*for (int i = 0; i < beaconLocationList.size(); i++){
            scanFilters.add(new ScanFilter.Builder()
                    .setDeviceAddress(beaconLocationList.get(i).getMac())
                    .build());
        }*/
        return scanFilters;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createScanner();
        beacons = new ArrayList<>();
        beaconLocationList = new ArrayList<>();
        beaconsView = (BeaconsView) findViewById(R.id.beacView);
        populateList();
        beaconsView.setList(beaconLocationList);
        beaconsView.invalidate();

        scanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {

                ScanRecord scanRecord = result.getScanRecord();
                if (scanRecord == null) {
                    //Log.w(TAG, "Null ScanRecord for device " + result.getDevice().getAddress());
                    return;
                }
                int i = getIndexOfDeviceInList(result.getDevice().getAddress());

                Log.d("natalia", "Beacon " + result.getDevice().getAddress() + " : " + result.getRssi());
                if ( i != -1){
                    beaconsView.setPower(result.getRssi());
                    beaconLocationList.get(i).setRssi(result.getRssi());
                }
                beaconsView.invalidate();

                beacons.add(new Beacon(result.getDevice().getAddress(), result.getRssi()));
                beaconsView.invalidate();
            }
        };
        scanner.startScan(buildScanFilters(), SCAN_SETTINGS, scanCallback);


    }

    int getIndexOfDeviceInList(String mac){
        int index = -1;
        for (int i = 0; i < beaconLocationList.size(); i++){
            if (mac.equals(beaconLocationList.get(i).getMac())){
                index = i;
            }
        }
        return index;
    }

    void populateList() {
        beaconLocationList.add(new BeaconLocation("C8:46:1E:46:4F:73", 210, 90));
        beaconLocationList.add(new BeaconLocation("CF:32:40:24:E0:91", 610, 90));
        beaconLocationList.add(new BeaconLocation("F3:F0:35:45:24:91", 210, 970));
        beaconLocationList.add(new BeaconLocation("F6:30:2D:B5:5E:DD", 610, 970));
        beaconLocationList.add(new BeaconLocation("E3:F9:70:57:F0:D3", 210, 530));
        beaconLocationList.add(new BeaconLocation("DB:E4:BF:32:B1:61", 610, 530));
    }

    private void createScanner() {
        BluetoothManager btManager =
                (BluetoothManager)this.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter btAdapter = btManager.getAdapter();
        if (btAdapter == null || !btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, Constants.REQUEST_CODE_ENABLE_BLE);
        }
        if (btAdapter == null || !btAdapter.isEnabled()) {
            Toast.makeText(this, "Can't enable Bluetooth", Toast.LENGTH_SHORT).show();
            return;
        }
        scanner = btAdapter.getBluetoothLeScanner();
    }
}
