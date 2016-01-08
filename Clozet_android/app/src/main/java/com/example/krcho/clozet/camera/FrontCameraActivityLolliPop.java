package com.example.krcho.clozet.camera;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.krcho.clozet.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


// this project
// http://jylee-world.blogspot.kr/2014/12/a-tutorial-of-androidhardwarecamera2.html

// example more
// https://github.com/googlesamples/android-Camera2Basic/blob/master/Application/src/main/java/com/example/android/camera2basic/Camera2BasicFragment.java

public class FrontCameraActivityLolliPop extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "CAMERA";
    private boolean isFront = true;
    private boolean isTimer3 = true;
    private int time = 3;

    private Context mContext = this;
    private Camera frontCamera, backCamera;
    private CameraPreview frontPreview, backPreview;
    private Button timer, transCamera, cancel, photo;
    private ImageView count;
    private FrameLayout preview;
    private CountDownTimer countDown3, countDown5;
    private SoundPool sound;
    private int soundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //same as set-up android:screenOrientation="portrait" in <activity>, AndroidManifest.xml
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_front_camera);

        mContext = this;

        // 카메라 사용여부 체크
        if(!checkCameraHardware(getApplicationContext())){
            finish();
        }

        // 카메라 인스턴스 생성
        frontCamera = getFrontCameraInstance();
        backCamera = getBackCameraInstance();


        // 프리뷰창을 생성하고 액티비티의 레아이웃으로 지정
        frontPreview = new CameraPreview(this, frontCamera);
        backPreview = new CameraPreview(this, backCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(frontPreview);

        // 효과음 인스턴스
        sound = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
        soundId = sound.load(this, R.raw.camera, 1);

        // 타이머 관련 인스턴스 생성
        countDown3 = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                switch (time) {
                    case 1:
                        count.setBackground(getDrawable(R.drawable.count_1));
                        break;
                    case 2:
                        count.setBackground(getDrawable(R.drawable.count_2));
                        break;
                    case 3:
                        count.setBackground(getDrawable(R.drawable.count_3));
                        break;
                }

                time--;
            }

            @Override
            public void onFinish() {
                count.setVisibility(View.GONE);
                frontCamera.takePicture(null, null, mPicture);
                sound.play(soundId, 10.0F, 1.0F,  0,  0,  1.0F);
                time = 3;
            }
        };

        countDown5 = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                switch (time) {
                    case 1:
                        count.setBackground(getDrawable(R.drawable.count_1));
                        break;
                    case 2:
                        count.setBackground(getDrawable(R.drawable.count_2));
                        break;
                    case 3:
                        count.setBackground(getDrawable(R.drawable.count_3));
                        break;
                    case 4:
                        count.setBackground(getDrawable(R.drawable.count_4));
                        break;
                    case 5:
                        count.setBackground(getDrawable(R.drawable.count_5));
                        break;
                }

                time--;
            }

            @Override
            public void onFinish() {
                count.setVisibility(View.GONE);
                frontCamera.takePicture(null, null, mPicture);
                sound.play(soundId, 10.0F, 1.0F,  0,  0,  1.0F);
                time = 5;
            }
        };

        timer = (Button) findViewById(R.id.btn_timer);
        transCamera = (Button) findViewById(R.id.btn_transCamera);
        cancel = (Button) findViewById(R.id.btn_cancel);
        photo = (Button) findViewById(R.id.btn_photo);
        count = (ImageView) findViewById(R.id.camera_count);

        timer.setOnClickListener(this);
        transCamera.setOnClickListener(this);
        cancel.setOnClickListener(this);
        photo.setOnClickListener(this);
    }


    /**
     * 카메라 사용여부 가능 체크
     * @param context
     * @return
     */

    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Log.i(TAG, "Number of available camera : " + Camera.getNumberOfCameras());
            return true;
        } else {
            Toast.makeText(context, "No camera found!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    /**
     * 카메라 인스턴스 호출
     * @return
     */

    public Camera getFrontCameraInstance(){
        try{
            // open() 의 매개변수로 int 값을 받을 수 도 있는데, 일반적으로 0이 후면 카메라, 1이 전면 카메라를 의미합니다.
            frontCamera = Camera.open(1);

        } catch(Exception e){
            Log.i(TAG,"Error : Using Camera");
            e.printStackTrace();
        }
        return frontCamera;
    }

    public Camera getBackCameraInstance(){
        try{
            // open() 의 매개변수로 int 값을 받을 수 도 있는데, 일반적으로 0이 후면 카메라, 1이 전면 카메라를 의미합니다.
            backCamera = Camera.open(0);


        } catch(Exception e){
            Log.i(TAG,"Error : Using Camera");
            e.printStackTrace();
        }
        return backCamera;
    }


    /** 이미지를 저장할 파일 객체를 생성
     * 저장되면 Picture 폴더에 MyCameraApp 폴더안에 저장된다. (MyCameraApp 폴더명은 변경가능)
     */

    private static File getOutputMediaFile(){
        //SD 카드가 마운트 되어있는지 먼저 확인
        // Environment.getExternalStorageState() 로 마운트 상태 확인 가능합니다
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");

        // 없는 경로라면 따로 생성®
        if(!mediaStorageDir.exists()){
            if(! mediaStorageDir.mkdirs()){
                Log.d("MyCamera", "failed to create directory");
                return null;
            }
        }

        // 파일명을 적당히 생성, 여기선 시간으로 파일명 중복을 피한다
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timestamp + ".jpg");
        Log.i("MyCamera", "Saved at"+Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES));
        System.out.println(mediaFile.getPath());
        return mediaFile;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // JPEG 이미지가 byte[] 형태로 들어옵니다.
            File pictureFile = getOutputMediaFile();
            if(pictureFile == null){
                Toast.makeText(mContext, "Error camera image saving", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                //Thread.sleep(500);
                if (isFront) {
                    frontCamera.startPreview();
                } else {
                    backCamera.startPreview();
                }
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_timer:
                isTimer3 = !isTimer3;
                if (isTimer3) {
                    timer.setBackground(getDrawable(R.drawable.btn_timer_3sc));
                    time = 3;
                } else {
                    timer.setBackground(getDrawable(R.drawable.btn_timer_5sc));
                    time = 5;
                }
                break;

            case R.id.btn_transCamera:
                isFront = !isFront;

                preview.removeAllViews();

                if (isFront) {
                    backCamera.release();
                    frontCamera.release();
                    frontCamera.open(1);
//                    getFrontCameraInstance();
                    preview.removeAllViews();
                    preview.addView(frontPreview);
                } else {
                    frontCamera.release();
                    backCamera.release();
                    backCamera.open(0);
//                    getBackCameraInstance();
                    preview.removeAllViews();
                    preview.addView(backPreview);
                }
                break;

            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_photo:
                count.setVisibility(View.VISIBLE);

                if (isTimer3) {
                    countDown3.start();
                } else {
                    countDown5.start();
                }

                break;
        }
    }

}
