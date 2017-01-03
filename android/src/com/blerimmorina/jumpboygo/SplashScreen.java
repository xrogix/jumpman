package com.blerimmorina.jumpboygo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.blerimmorina.jumpboygo.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();

                Intent intent = new Intent();
                intent.setClass(SplashScreen.this, AndroidLauncher.class);
                startActivity(intent);
            }
        }, 6000);
    }

}
