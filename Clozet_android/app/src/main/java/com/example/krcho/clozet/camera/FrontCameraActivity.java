package com.example.krcho.clozet.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.example.krcho.clozet.R;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// http://examples.javacodegeeks.com/android/core/hardware/camera-hardware/android-camera-example/
// http://ifull.co.kr/xe/java/249061

public class FrontCameraActivity extends Activity {

    private CameraView mCamView;     // 카메라 프리뷰를 보여주는 SurfaceView
    //        private Button btnCapture;       // '촬영' 버튼
//        private Button btnCancel;        // '취소' 버튼 (현재 액티비티 종료)
//        private Button btnChangeFacing;  // '카메라 전환' 버튼
    private int mCameraFacing;       // 전면 or 후면 카메라 상태 저장
    private static FrontCameraActivity me; // CameraView에서 Activity.finish()로 액티비티를 종료시키기 위한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        me = this;
        mCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT; // 최초 카메라 상태는 전면카메라로 설정

        // '카메라 전환' 버튼을 선택하여 카메라를 새로 생성하면
        // contentView 설정과 Listener 설정 역시 다시 해주어야 하므로,
        // 해당 부분들을 init()메소드로 빼내어 onClick()에서 재호출하도록 함
        init();
    }

    private void init() {
        mCamView = new CameraView(me, mCameraFacing);
        setContentView(mCamView);
        addContentView(LayoutInflater.from(me).inflate(R.layout.activity_front_camera2, null),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        btnCapture = (Button) findViewById(R.id.button_capture);
//        btnCapture.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                mCamView.capture();
//            }
//        });
//
//        btnCancel = (Button) findViewById(R.id.button_cancel);
//        btnCancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                CameraActivity.exitCamera();
//            }
//        });
//
//        btnChangeFacing = (Button) findViewById(R.id.button_change_facing);
//        btnChangeFacing.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                // 전면 -> 후면 or 후면 -> 전면으로 카메라 상태 전환
//                mCameraFacing = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
//                        Camera.CameraInfo.CAMERA_FACING_FRONT
//                        : Camera.CameraInfo.CAMERA_FACING_BACK;
//
//                // 변경된 방향으로 새로운 카메라 View 생성
//                mCamView = new CameraView(me, mCameraFacing);
//
//
//                // ContentView, Listener 재설정
//                init();
//            }
//        });
    }

    public static void exitCamera() {
        me.finish();
    }
}

class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private static SurfaceHolder mHolder;
    private static Camera mCamera;
    private static int mCameraFacing;

    public CameraView(Context context, int cameraFacing) {
        super(context);

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCameraFacing = cameraFacing;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // 프리뷰를 회전시키거나 변경시 처리를 여기서 해준다.
        // 프리뷰 변경시에는 먼저 프리뷰를 멈춘다음 변경해야한다.

        if (mHolder.getSurface() == null) {
            // 프리뷰가 존재하지 않을때
            return;
        }

        // 우선 멈춘다
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // 프리뷰가 존재조차 하지 않는 경우다
        }


        // 프리뷰 변경, 처리 등을 여기서 해준다.
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = getBestPreviewSize(w, h);
        parameters.setPreviewSize(size.width, size.height);
        mCamera.setParameters(parameters);
        // 새로 변경된 설정으로 프리뷰를 재생성한다
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e) {
            Log.d("surfaceChanged", "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {
            mCamera = Camera.open(mCameraFacing);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.set("orientation", "portrait");
            mCamera.setDisplayOrientation(90);
            parameters.setRotation(90);
            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(mHolder);

        } catch (IOException e) {
            mCamera.release();
            mCamera = null;

            e.printStackTrace(System.out);
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height) {
        Camera.Size result = null;
        Camera.Parameters p = mCamera.getParameters();
        for (Camera.Size size : p.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }
        return result;

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    public void capture() {
        if (mCamera != null)
            mCamera.takePicture(shutterCallback, rawCallback, jpegCallback);
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(final byte[] data, Camera camera) {

            mCamera.stopPreview();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedOutputStream bos = null;
                    try {
                        bos = new BufferedOutputStream
                                (new FileOutputStream((String.format("capture.jpg",
                                        System.currentTimeMillis()))));
                        bos.write(data);
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            FrontCameraActivity.exitCamera();
        }
    };
}
