package com.example.pwdkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class EnterPasscodeActivity extends AppCompatActivity {

    EditText editText;
    Button btnEnter;
    String password;
    DatabaseHelper db;
    TextView createpwd, authenticate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_passcode);
        final EditText editText;
        Button btnEnter;
        final String password;

        SharedPreferences settings = getSharedPreferences("password", MODE_PRIVATE);
        password = settings.getString("password", "");

        editText = findViewById(R.id.editText);
        btnEnter = findViewById(R.id.btnEnter);
        createpwd = findViewById(R.id.createpassword);
        authenticate = findViewById(R.id.authenticate);


        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                try {
                    if (text.equals(decodeData(password))) {
                        //Enter the application
                        Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(EnterPasscodeActivity.this, "Wrong Password!", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        createpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatePasscodeActivity.class);
                startActivity(intent);
                finish();
            }
        });

         authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BiometricActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public String decodeData(String data) throws UnsupportedEncodingException {
        byte[] result = Base64.decode(data,Base64.DEFAULT);
        String str_result = new String(result, "UTF-8");
        return str_result;
    }

}
