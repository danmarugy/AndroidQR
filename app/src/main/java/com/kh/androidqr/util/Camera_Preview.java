package com.kh.androidqr.util;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;
import android.hardware.camera2.*;

public class Camera_Preview extends SurfaceView implements  SurfaceHolder.Callback {

    private SurfaceHolder _holder;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;
    public static String barcodeContents = null;
    private String prebarcodeContents;

    public Camera_Preview(final Context context, int preview_width, int preview_height) //Camera camera)
    {
        super(context);
        //_camera = camera;
        barcodeDetector = new BarcodeDetector.Builder(context)
                .build();

        _holder = getHolder();

        cameraSource = new CameraSource
                .Builder(context, barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(29.8f)
                .setRequestedPreviewSize(preview_width,preview_height)
                .setAutoFocusEnabled(true)
                .build();

        _holder.addCallback(this);
        //_holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if(barcodes.size() != 0)
                {
                    barcodeContents  = barcodes.valueAt(0).displayValue;
                    if(barcodeContents != null && !barcodeContents.equals(prebarcodeContents))
                    {
                        prebarcodeContents = barcodeContents;
                        Log.d("Detection", barcodeContents);
                        Toast.makeText(context,"인식되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            Log.d("_holder", String.valueOf(_holder== null));
            //Log.d("_camera", String.valueOf(_camera== null));

            cameraSource.start(_holder);
            //_camera.setPreviewDisplay(holder);
            //_camera.startPreview();
        } catch (IOException e) {
            Log.d("e", String.valueOf(e));
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        /*
        _camera.stopPreview();
        _camera.setDisplayOrientation(90);
        Camera.Parameters params = _camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        _camera.setParameters(params);

        if(_holder.getSurface() == null)
        {
            return;
        }
        try {
            _camera.stopPreview();
        } catch (Exception e)
        {

        }

        try {
            _camera.setPreviewDisplay(_holder);
            _camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //_camera.release();
        cameraSource.stop();

    }

    public void mm(Context context)
    {
        CameraManager cm = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }

}
