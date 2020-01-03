package com.kh.androidqr.util;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;


public class Display_Manager {

    // dpi 배율
    private float dpi_magnification = 0.0f;
    private AppCompatActivity main_activity;
    private DisplayMetrics metrics;

    public Display_Manager() {}

    public Display_Manager(AppCompatActivity main_activity) {
            this.main_activity = main_activity;
    }

    // 디스플레이 사이즈
    public Point getScreenSize()
    {
        android.view.Display display = main_activity.getWindowManager().getDefaultDisplay();
        Point display_size = new Point();
        display.getSize(display_size);

        Log.d("display_width", String.valueOf(display_size.x));
        Log.d("display_height", String.valueOf(display_size.y));
        return display_size;
    }

    // 디스플레이 dpi
    public DisplayMetrics getScreenDpi()
    {
        metrics = new DisplayMetrics();
        WindowManager mgr = (WindowManager) main_activity.getSystemService(Context.WINDOW_SERVICE);
        mgr.getDefaultDisplay().getMetrics(metrics);
        setDisplayMagnification();
        Log.d("Display_width_dpi", String.valueOf(metrics.xdpi));
        Log.d("Display_height_dpi", String.valueOf(metrics.ydpi));
        Log.d("Display_DPI", String.valueOf(metrics.densityDpi));

        return  metrics;
    }

    public void setDisplayMagnification()
    {
        if(metrics != null) {
            dpi_magnification = metrics.densityDpi / 160;
        } else {
            getScreenDpi();
        }
    }

    public float getDisplayMagnification()
    {
        if(dpi_magnification != 0.0f) {
            Log.d("DPI_density", String.valueOf(dpi_magnification));
            return dpi_magnification;
        } else {
            setDisplayMagnification();
            return dpi_magnification;
        }
    }
}
