package com.kh.androidqr;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
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
    private ArrayList<Integer> preview_size;
    private long time = 0;

    private FrameLayout preview;
    private TextView notice_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        // 디스플레이 매니저
        dm = new Display_Manager(this);

        // 카메라 매니저
        cm = new Camera_Manager(this);

        // 퍼미션 매니저
        pm = new Permission_Manager(this);


        // 디스플레이 사이즈
        dm.getScreenSize();

        // 디스플레이 DPI
        dm.getScreenDpi();

        // 배율
        dm.getDisplayMagnification();

        // 카메라 존재 여부
        cf = cm.checkCameraExist();

        // 카메라 갯수
        cm.checkCameraHardwareCount();

        cm.getCamera_Preview_Max_Size();

        //휴대폰 카메라 사진 지원 사이즈
        cm.getCamera_Picture_Size();

        //휴대폰 카메라 프리뷰 지원 사이즈
        cm.getCamera_Preview_Size();

        // 퍼미션 체크

        preview_size = cm.getCamera_Preview_Max_Size();

        // 카메라 열기
        //cm.getCameraInstance(cf);
        cm.stop_Camera();
        cp = new Camera_Preview(this, 1920, 1080);




        preview = findViewById(R.id.camera_view_main);
        notice_view = findViewById(R.id.notice_view);
        notice_view.setMovementMethod(new ScrollingMovementMethod());
        preview.addView(cp);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                if (Camera_Preview.barcodeContents != null) {
                    intent.putExtra("Data", Camera_Preview.barcodeContents);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "검색된 QR코드가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
 */
    }
    private void init() {
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
