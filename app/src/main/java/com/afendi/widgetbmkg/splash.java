package com.afendi.widgetbmkg;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yoko on 07/08/2015.
 */
public class splash extends Activity {

    long Delay = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash);

        Timer runsplas = new Timer();

        TimerTask showspas = new TimerTask() {
            @Override
            public void run() {
                finish();

                Intent inten = new Intent(splash.this, MainActivity.class);
                startActivity(inten);
            }
        };
        runsplas.schedule(showspas, Delay);
    };

}