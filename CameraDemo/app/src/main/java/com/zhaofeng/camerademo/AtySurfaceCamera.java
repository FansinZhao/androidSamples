package com.zhaofeng.camerademo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Size;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;


public class AtySurfaceCamera extends AppCompatActivity implements SurfaceHolder.Callback {

    private final static int REQUEST_CAMERA_CODE = 1;
    private SurfaceView surfaceView;
    private CameraManager manager;
    private String mCameraId;
    private android.util.Size mPreviewSize;
    private CaptureRequest.Builder builder;
    private Surface surface;
    private CameraCaptureSession.StateCallback mSessionCallBack = new CameraCaptureSession.StateCallback() {
        @Override
        public void onConfigured(CameraCaptureSession session) {
            try {
                session.setRepeatingRequest(builder.build(),null,mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {

        }
    };
    private CameraDevice.StateCallback mCallBack = new CameraDevice.StateCallback() {


        @Override
        public void onOpened(CameraDevice camera) {
            //创建会话
            try {
                builder = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                surface = surfaceView.getHolder().getSurface();
                builder.addTarget(surface);
                camera.createCaptureSession(Arrays.asList(surface),mSessionCallBack,mHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onDisconnected(CameraDevice camera) {

        }

        @Override
        public void onError(CameraDevice camera, int error) {

        }
    };
    private Handler mHandler;
    private HandlerThread mBackgroundThread;
    /**
     * Starts a background thread and its {@link Handler}.
     */
    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mHandler = new Handler(mBackgroundThread.getLooper());
    }

    /**
     * Stops the background thread and its {@link Handler}.
     */
    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Granted
                System.out.println("权限通过!");
            } else {
                // Permission Denied
                System.out.println("权限失败!");
            }
        }

    }

    @Override
    protected void onResume() {
        startBackgroundThread();
        super.onResume();
    }

    @Override
    protected void onPause() {
        stopBackgroundThread();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_camera);
        surfaceView = (SurfaceView) findViewById(R.id.sv);
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //获取manager
        manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //获取cameraId
        try {
            for (String cameraId : manager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = manager.getCameraCharacteristics(cameraId);
                Integer facing = cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null && facing == CameraCharacteristics.LENS_FACING_BACK) {
                    mCameraId = cameraId;

//                    //获取配置
//                    StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//                    //size
//                    mPreviewSize = map.getOutputSizes(SurfaceView.class)[0];
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_CODE);
                        return;
                    }
                    manager.openCamera(mCameraId, mCallBack, mHandler);
                    break;
                }
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        //
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
