package com.kh.androidqr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kh.androidqr.util.Camera_Manager;
import com.kh.androidqr.util.Camera_Preview;
import com.kh.androidqr.util.Display_Manager;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int PERMISSION_REQUEST_CODE = 1000;
    private Display_Manager dm;
    private Camera_Manager cm;
    private Camera_Preview cp;
    private boolean cf = false;
    private ArrayList<Integer> preview_size;
    private long time= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디스플레이 매니저
        dm = new Display_Manager(MainActivity.this);
        // 카메라 매니저
        cm = new Camera_Manager(MainActivity.this);

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

        // 퍼미션 체크
        permissionCheck();
        Log.d("체크완료", "가나다라");


        preview_size = cm.getCamera_Preview_Max_Size();

        // 카메라 열기
        //cm.getCameraInstance(cf);
        cp = new Camera_Preview(MainActivity.this, preview_size.get(0), preview_size.get(1));

        //휴대폰 카메라 사진 지원 사이즈
        cm.getCamera_Picture_Size();

        //휴대폰 카메라 프리뷰 지원 사이즈
        cm.getCamera_Preview_Size();



        FrameLayout preview = findViewById(R.id.camera_view_main);
        preview.addView(cp);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Result.class);
                if(Camera_Preview.barcodeContents != null) {
                    intent.putExtra("Data", Camera_Preview.barcodeContents);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "검색된 QR코드가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void permissionCheck(){
        if(Build.VERSION.SDK_INT >= 23)
        {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            ArrayList<String> arrayPermission = new ArrayList<>();

            if(permissionCheck != PackageManager.PERMISSION_GRANTED)
            {
                arrayPermission.add(Manifest.permission.CAMERA);
            }

            if(arrayPermission.size() > 0)
            {
                String strArray[] = new String[arrayPermission.size()];
                strArray = arrayPermission.toArray(strArray);
                ActivityCompat.requestPermissions(this, strArray, PERMISSION_REQUEST_CODE);
            }
            else {
                // Initialize 코드
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length < 1) {
                    Toast.makeText(this, "Failed get permission", Toast.LENGTH_SHORT).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    return;
                }

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission is denied : " + permissions[i], Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }

                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                // Initialize 코드
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        System.out.println("the code is catch");
    }

    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.",Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
        }
    }
}
