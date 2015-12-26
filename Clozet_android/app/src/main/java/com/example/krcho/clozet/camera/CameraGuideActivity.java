package com.example.krcho.clozet.camera;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.krcho.clozet.R;

import java.util.Timer;
import java.util.TimerTask;

public class CameraGuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_guide2);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), FrontCameraActivityLolliPop.class));
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);
    }
}
