package com.kh.androidqr.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kh.androidqr.MainActivity;

import java.util.ArrayList;

public class Permission_Manager extends AppCompatActivity{

    final static int PERMISSION_REQUEST_CODE = 1000;
    private AppCompatActivity context;

    public Permission_Manager(AppCompatActivity context) {
        this.context = context;

        permissionCheck();
    }

    private void permissionCheck() {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
            ArrayList<String> arrayPermission = new ArrayList<>();

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                arrayPermission.add(Manifest.permission.CAMERA);
            }

            if (arrayPermission.size() > 0) {
                String strArray[] = new String[arrayPermission.size()];
                strArray = arrayPermission.toArray(strArray);
                ActivityCompat.requestPermissions(context,strArray, PERMISSION_REQUEST_CODE);
            } else {
                // Initialize 코드
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length < 1) {
                    Toast.makeText(context, "Failed get permission", Toast.LENGTH_SHORT).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    return;
                }

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(context, "Permission is denied : " + permissions[i], Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }

                Toast.makeText(context, "Permission is granted", Toast.LENGTH_SHORT).show();
                // Initialize 코드
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
