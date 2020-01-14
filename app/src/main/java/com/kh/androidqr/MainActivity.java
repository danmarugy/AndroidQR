package com.kh.androidqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kh.androidqr.util.Camera_Manager;
import com.kh.androidqr.util.Camera_Preview;
import com.kh.androidqr.util.Display_Manager;
import com.kh.androidqr.util.Permission_Manager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Display_Manager dm;
    private Camera_Manager cm;
    private Camera_Preview cp;
    private Permission_Manager pm;
    private boolean cf = false;
    private long time = 0;

    private FrameLayout preview;
    private TextView notice_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        // Permission Manager
        pm = new Permission_Manager(MainActivity.this);
        // Display Manager
        dm = new Display_Manager(this);

        Point p = dm.getScreenSize();
        // Camera Manager
        //cm = new Camera_Manager(this);

        // Camera Preview
        cp = new Camera_Preview(this, p.x, p.y);

        //cameraView
        preview = findViewById(R.id.camera_view_main);
        preview.addView(cp);

        //noticeView
        notice_view = findViewById(R.id.notice_view);
        notice_view.setMovementMethod(new ScrollingMovementMethod());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onBackPressed() {
        /*
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
         */
    }


}
