package com.nataliajastrzebska.becheck;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nataliajastrzebska on 18/11/15.
 */


public class BeaconsView extends View {

    List <BeaconLocation> beaconLocationList;
    int RSSI;
    public BeaconsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        RSSI = 0;

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (beaconLocationList.size() > 0){
            drawPower(canvas, 300, 300, String.valueOf(RSSI), 70);
            for (int i = 0; i <beaconLocationList.size(); i++){
                if (beaconLocationList.get(i).getIsChecked()) {
                    drawCircle(canvas, beaconLocationList.get(i).getX(), beaconLocationList.get(i).getY(), Color.GREEN);
                    drawSquare(canvas, beaconLocationList.get(i).getX(), beaconLocationList.get(i).getY(), Color.argb(40, 0, 255, 0));
                }
                else {
                    drawCircle(canvas, beaconLocationList.get(i).getX(), beaconLocationList.get(i).getY(), Color.RED);
                    drawSquare(canvas, beaconLocationList.get(i).getX(), beaconLocationList.get(i).getY(), Color.argb(40, 255, 0, 0));
                    drawPower(canvas, beaconLocationList.get(i).getX(), beaconLocationList.get(i).getY(),
                            String.valueOf(beaconLocationList.get(i).getRssi()), 30);
                }
            }
        }
    }

    public void setPower(int rssi){
        this.RSSI = rssi;
    }

    public void setList(List<BeaconLocation> beaconLocationList){
        this.beaconLocationList = beaconLocationList;
    }


    void drawCircle(Canvas c,float x,float y, int color){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        c.drawCircle(x, y, 20, paint);
    }

    void drawPower(Canvas canvas, float x, float y, String rssi, int fontSize){
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        paint.setTextSize(fontSize);
        canvas.drawText(rssi, x-20, y+5, paint);
    }

    void drawSquare(Canvas canvas, float x, float y, int color){
        Paint paint= new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        canvas.drawRect(x-200,y-220, x+200, y+220,paint);
    }

}
