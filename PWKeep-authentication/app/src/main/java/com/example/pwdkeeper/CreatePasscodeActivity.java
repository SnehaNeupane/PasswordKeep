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

public class CreatePasscodeActivity extends AppCompatActivity {

    EditText editText1, editText2;
    Button btnConfirm;
    TextView sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);


        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        btnConfirm = findViewById(R.id.btnConfirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();

                if (text1.equals("") || text2.equals("")) {
                    //No passcode present
                    Toast.makeText(CreatePasscodeActivity.this, "No Password entered!", Toast.LENGTH_SHORT).show();
                } else {
                    if (text1.equals(text2)) {
                        //Save the passcode
                        SharedPreferences.Editor editor = getSharedPreferences("password", MODE_PRIVATE).edit();
                        editor.putString("password", encodeData(text1));
                        editor.apply();


                        //Enter the app
                        Intent intent = new Intent(getApplicationContext(), EnterPasscodeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //No match on the passcode
                        Toast.makeText(CreatePasscodeActivity.this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
    public String encodeData(String data) {
        byte [] byte_data = data.getBytes();
        String encoded = Base64.encodeToString(byte_data,Base64.DEFAULT);
        return encoded;
    }
}
