package com.example.pwdkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals("")){
                    //If there is no passcode
                    Intent intent = new Intent(getApplicationContext(), EnterPasscodeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    //If passcode is present
                    Intent intent = new Intent(getApplicationContext(), CreatePasscodeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);

    }
}
