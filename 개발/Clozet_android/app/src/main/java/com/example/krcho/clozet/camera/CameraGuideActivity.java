package com.example.krcho.clozet.camera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krcho.clozet.R;
import com.hojung.nfc.HojungNFCReadLibrary;
import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


// this project
// http://jylee-world.blogspot.kr/2014/12/a-tutorial-of-androidhardwarecamera2.html

// example more
// https://github.com/googlesamples/android-Camera2Basic/blob/master/Application/src/main/java/com/example/android/camera2basic/Camera2BasicFragment.java

public class CameraGuideActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "CAMERA";
    private int mCameraFacing;
    private boolean isTimer3 = true;
    private int time = 3;

    private Handler mHandler;
    private Runnable mRunnable;
    private Context mContext = this;
    private CameraView mCamView;
    private Button timer, transCamera, cancel, photo;
    private ImageView count, guide;
    private CountDownTimer countDown3, countDown5;

    //NFC
    HojungNFCReadLibrary hojungNFCReadLibrary;

    private void initNFC() {
        try {
            Log.d("NFC", "intent : " + getIntent().getAction());
            Intent intent = getIntent();
            hojungNFCReadLibrary.onResume(intent);
        } catch (Exception e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        initNFC();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("NFC", "onPause");
        hojungNFCReadLibrary.onPause();

    }


    @Override
    public void onNewIntent(Intent intent) {
        Log.d("NFC", "onNewIntent");
        hojungNFCReadLibrary.onNewIntent(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //same as set-up android:screenOrientation="portrait" in <activity>, AndroidManifest.xml
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mContext = this;
        mCameraFacing = Camera.CameraInfo.CAMERA_FACING_FRONT;

        init();

        //NFC is use?
        android.nfc.NfcAdapter mNfcAdapter = android.nfc.NfcAdapter.getDefaultAdapter(mContext);
        hojungNFCReadLibrary = new HojungNFCReadLibrary(getIntent(), mContext, new OnHojungNFCListener() {

            @Override
            public void onReceiveMessage(NfcModel[] models) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(String arg0) {
                // TODO Auto-generated method stub

            }
        });

        // 타이머 관련 인스턴스 생성
        countDown3 = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                switch (time) {
                    case 1:
                        count.setBackgroundResource(R.drawable.count_1);
                        break;
                    case 2:
                        count.setBackgroundResource(R.drawable.count_2);
                        break;
                    case 3:
                        count.setBackgroundResource(R.drawable.count_3);
                        break;
                }

                time--;
            }

            @Override
            public void onFinish() {
                count.setVisibility(View.GONE);
                mCamView.capture();
                time = 3;
            }
        };

        countDown5 = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                switch (time) {
                    case 1:
                        count.setBackgroundResource(R.drawable.count_1);
                        break;
                    case 2:
                        count.setBackgroundResource(R.drawable.count_2);
                        break;
                    case 3:
                        count.setBackgroundResource(R.drawable.count_3);
                        break;
                    case 4:
                        count.setBackgroundResource(R.drawable.count_4);
                        break;
                    case 5:
                        count.setBackgroundResource(R.drawable.count_5);
                        break;
                }

                time--;
            }

            @Override
            public void onFinish() {
                count.setVisibility(View.GONE);
                mCamView.capture();
                time = 5;
            }
        };
    }

    private void init() {
        mCamView = new CameraView(getApplicationContext(), mCameraFacing);
        setContentView(mCamView);
        addContentView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.activity_camera_guide, null),
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        guide = (ImageView) findViewById(R.id.background_guide);
        timer = (Button) findViewById(R.id.btn_timer);
        transCamera = (Button) findViewById(R.id.btn_transCamera);
        cancel = (Button) findViewById(R.id.btn_cancel);
        photo = (Button) findViewById(R.id.btn_photo);
        count = (ImageView) findViewById(R.id.camera_count);

        guide.setOnClickListener(this);
        timer.setOnClickListener(this);
        transCamera.setOnClickListener(this);
        cancel.setOnClickListener(this);
        photo.setOnClickListener(this);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                guide.setVisibility(View.GONE);
            }
        };

        mHandler = new Handler();
        mHandler.postDelayed(mRunnable, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.background_guide:
                guide.setVisibility(View.GONE);
                break;

            case R.id.btn_timer:
                guide.setVisibility(View.GONE);

                isTimer3 = !isTimer3;
                if (isTimer3) {
                    timer.setBackgroundResource(R.drawable.btn_timer_3sc);
                    time = 3;
                } else {
                    timer.setBackgroundResource(R.drawable.btn_timer_5sc);
                    time = 5;
                }
                break;

            case R.id.btn_transCamera:
                guide.setVisibility(View.GONE);

                // 전면 -> 후면 or 후면 -> 전면으로 카메라 상태 전환
                mCameraFacing = (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_BACK) ?
                        Camera.CameraInfo.CAMERA_FACING_FRONT
                        : Camera.CameraInfo.CAMERA_FACING_BACK;

                // 변경된 방향으로 새로운 카메라 View 생성
                mCamView = new CameraView(getApplicationContext(), mCameraFacing);

                // ContentView, Listener 재설정
                init();

                break;

            case R.id.btn_cancel:
                guide.setVisibility(View.GONE);

                finish();
                break;

            case R.id.btn_photo:
                guide.setVisibility(View.GONE);
                count.setVisibility(View.VISIBLE);

                mCamView.mCamera.autoFocus(mAutoFocus);

                if (isTimer3) {
                    countDown3.start();
                } else {
                    countDown5.start();
                }

                break;
        }
    }

    Camera.AutoFocusCallback mAutoFocus = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            if (success) {
            } else {
                Toast.makeText(getApplicationContext(), "초점을 잡을 수 없습니다.", Toast.LENGTH_LONG).show();
            }
        }
    };

}

class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private static SurfaceHolder mHolder;
    static Camera mCamera;
    private static int mCameraFacing;
    private Context context;

    public CameraView(Context context, int cameraFacing) {
        super(context);

        this.context = context;

        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mCameraFacing = cameraFacing;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        mCamera.startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        try {
            mCamera = Camera.open(mCameraFacing);
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.set("orientation", "portrait");
//            parameters.set("jpeg-quality", 70);
//            parameters.setPictureFormat(PixelFormat.JPEG);
//            parameters.setPictureSize(1920, 1080);
            mCamera.setDisplayOrientation(90);
            mCamera.setParameters(parameters);

            mCamera.setPreviewDisplay(mHolder);

        } catch (IOException e) {
            mCamera.release();
            mCamera = null;

            e.printStackTrace(System.out);
        }
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
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            // JPEG 이미지가 byte[] 형태로 들어옵니다.
            File[] pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                Toast.makeText(context, "Error camera image saving", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // 이미지 회전을 위한 Matrix 생성
                Matrix frontMatrix = new Matrix();
                frontMatrix.postRotate(-90);

                Matrix backMatrix = new Matrix();
                backMatrix.postRotate(90);

                Bitmap bigBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                //전면, 후면 여부에 따른 이미지 회전 (원본 이미지)
                if (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    bigBitmap = rotate(bigBitmap, -90);
                } else {
                    bigBitmap = rotate(bigBitmap, 90);
                }

                pictureFile[0].createNewFile();
                FileOutputStream fos = new FileOutputStream(pictureFile[0]);
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;

                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);

                //전면, 후면 여부에 따른 이미지 회전 (셈플 이미지)
                if (mCameraFacing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    bitmap = rotate(bitmap, -90);
                } else {
                    bitmap = rotate(bitmap, 90);
                }

                pictureFile[1].createNewFile();
                FileOutputStream fos_sample = new FileOutputStream(pictureFile[1]);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos_sample);

                //Thread.sleep(500);
                camera.startPreview();

                Intent intent = new Intent(context, CameraPreviewActivity.class);
                intent.putExtra("file", pictureFile[0].getPath());
                intent.putExtra("sampleFile", pictureFile[1].getPath());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (FileNotFoundException e) {
                Log.d("Camera", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("Camera", "Error accessing file: " + e.getMessage());
            }
        }
    };

    public Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if(degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)
            {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }

    private static File[] getOutputMediaFile() {
        //SD 카드가 마운트 되어있는지 먼저 확인
        // Environment.getExternalStorageState() 로 마운트 상태 확인 가능합니다
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Clozet");
        File sampleStorageDir = new File(mediaStorageDir.getPath() + "/Sample");

        // 없는 경로라면 따로 생성®
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Clozet", "failed to create directory");
                return null;
            }
        }

        // 없는 경로라면 따로 생성®
        if (!sampleStorageDir.exists()) {
            if (!sampleStorageDir.mkdirs()) {
                Log.d("Clozet", "failed to create directory");
                return null;
            }
        }

        // 파일명을 적당히 생성, 여기선 시간으로 파일명 중복을 피한다
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        File sampleFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "Clozet_" + timestamp + "$$$.jpg");
        sampleFile = new File(mediaStorageDir.getPath() + File.separator + "Sample" + File.separator + "Clozet_" + timestamp + "$$$.jpg");
        Log.i("Clozet", "Saved at" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        System.out.println(mediaFile.getPath());
        return new File[]{mediaFile, sampleFile};
    }
}