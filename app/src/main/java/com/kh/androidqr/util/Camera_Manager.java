package com.kh.androidqr.util;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class Camera_Manager {


    private Camera c = null;
    private AppCompatActivity main_activity;


    public Camera_Manager(AppCompatActivity main_activity)
    {
        this.main_activity = main_activity;

        if(checkCameraExist()) {
            setCameraInstance();
        }
    }

    // 카메라 열기
    public void setCameraInstance()
    {
            try {
                c = Camera.open();
                Log.d("camera가 오픈되었는가?", c.toString());
            } catch (Exception e) {
                Log.d("카메라 여부", "카메라가 존재하지 않습니다.");
                Log.d("에러 사유",e.toString());
            }
    }

    public Camera getCameraInstance()
    {
        Log.d("camera", c.toString());
        return c;
    }

    public void stop_Camera()
    {
        c.release();
    }

    // 카메라 하드웨어 존재 여부
    public boolean checkCameraExist()
    {
        if(main_activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Log.d("카메라 지원 여부", String.valueOf(true));
            //getCameraInstance(true);
            return true;
        } else
        {
            Log.d("카메라 지원 여부", String.valueOf(false));
            return false;
        }
    }

    // 카메라 갯수
    public int checkCameraHardwareCount()
    {
        int cc = Camera.getNumberOfCameras();
        Log.d("카메라의 갯수", String.valueOf(cc));
        return cc;
    }

    // 지원되는 카메라 사진 크기
    public List getCamera_Picture_Size(){
        if(c != null)
        {
            Camera.Parameters parameters = c.getParameters();
            if(parameters != null)
            {
                List<Camera.Size> pictureSizeList = parameters.getSupportedPictureSizes();

                // 지원되는 사진 사이즈
                for(Camera.Size size : pictureSizeList)
                {
                    Log.d("PictureSize Width : ","width: "+size.width+"(width)"+
                            "height :"+size.height+"(height)");
                }
                return pictureSizeList;
            }
        }
        return null;
    }

    // 지원되는 카메라 프리뷰 크기
    public List getCamera_Preview_Size(){
        if(c != null)
        {
            Camera.Parameters parameters = c.getParameters();
            if(parameters != null)
            {
                List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();
                // 지원되는 프리뷰 사이즈
                for(Camera.Size size : previewSizeList)
                {
                    Log.d("PreviewSize Width : ","width: "+size.width+"(width)"+
                            "height :"+size.height+"(height)");
                }
                return previewSizeList;
            }
        }
        return null;
    }

    public ArrayList<Integer> getCamera_Preview_Max_Size(){
        if(c != null)
        {
            Camera.Parameters parameters = c.getParameters();
            ArrayList<Integer> previewMaxSize = new ArrayList<Integer>();
            if(parameters != null)
            {
                List<Camera.Size> previewSizeList = parameters.getSupportedPreviewSizes();

                for(Camera.Size size : previewSizeList)
                {
                    if(previewMaxSize.isEmpty())
                    {
                        previewMaxSize.add(0,size.width);
                        previewMaxSize.add(1,size.height);
                    } else{
                        if((size.width >= previewMaxSize.get(0)) && (size.height >= previewMaxSize.get(1)))
                        {
                            previewMaxSize.add(0,size.width);
                            previewMaxSize.add(1,size.height);
                        }
                    }
                }
                Log.d("width.......",previewMaxSize.get(0) + "height : " + previewMaxSize.get(1));
                return previewMaxSize;
            }
        }
        return null;
    }
}
