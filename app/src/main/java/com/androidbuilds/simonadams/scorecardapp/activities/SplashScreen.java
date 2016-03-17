package com.androidbuilds.simonadams.scorecardapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.androidbuilds.simonadams.scorecardapp.R;

/**
 * Created by simonadams on 29/06/15.
 */
public class SplashScreen extends AppCompatActivity{

    private static final int SPLASH_SCREEN_DELAY = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toMainScreen = new Intent(SplashScreen.this, PickACourse.class);
                startActivity(toMainScreen);

                finish();
            }
        }, SPLASH_SCREEN_DELAY);




    }
}
