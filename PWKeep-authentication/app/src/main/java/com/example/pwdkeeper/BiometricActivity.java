package com.example.pwdkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BiometricActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric);
        TextView msg_txt =findViewById(R.id.text_msg);
        Button next = findViewById(R.id.btnNext);

        //Create BiometricManager to check if user can use fingerprint sensor or not
        BiometricManager biometricManager = BiometricManager.from(this);
        switch(biometricManager.canAuthenticate()){ //switch some contants to check different possibilites
            case BiometricManager.BIOMETRIC_SUCCESS:
                msg_txt.setText("Turn on Touch ID to unlock your vault with your fingerprint.");
                msg_txt.setTextColor(Color.parseColor("#Fafafa"));
                break;
//                If device doesn't have fingerprint sensor
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msg_txt.setText("The device doesn't have a fingerprint sensor");
                next.setVisibility(View.GONE);
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msg_txt.setText("The biometric sensor is currently unavailable");
                next.setVisibility(View.GONE);
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msg_txt.setText("Your device doesn't have any fingerprint saved, please check your security settings.");
                next.setVisibility(View.GONE);
        }

//        Create biometric dialogue box
        Executor executor = ContextCompat.getMainExecutor(this);
//        Create biometric prompt callback
        final BiometricPrompt biometricPrompt = new BiometricPrompt(BiometricActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override //this method is called when there is and error while authentication
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override //this method is called when authentication is a success
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

//        Create Biometric Dialogue
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setDescription("User your fingerprint to login to the app")
                .setNegativeButtonText("Cancel")
                .build();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                biometricPrompt.authenticate(promptInfo);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), EnterPasscodeActivity.class);
        startActivity(intent);
        finish();
    }
}
