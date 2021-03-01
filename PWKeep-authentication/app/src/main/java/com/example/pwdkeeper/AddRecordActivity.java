package com.example.pwdkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AddRecordActivity extends AppCompatActivity {

    EditText text_subject, text_context,text_user;
    Button btnSave, btnCancel;
    Encryption enc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        text_context = findViewById(R.id.editContext);
        text_user = findViewById(R.id.editUser);
        text_subject = findViewById(R.id.editSubject);
        btnCancel = findViewById(R.id.btncancel);
        btnSave =  findViewById(R.id.btnSave);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dh = new DatabaseHelper(AddRecordActivity.this);
                dh.addRecord(text_subject.getText().toString().trim(),text_user.getText().toString().trim(),encodeData(text_context.getText().toString().trim()));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
        startActivity(intent);
        finish();
    }
    public String encodeData(String data) {
        byte [] byte_data = data.getBytes();
        String encoded = Base64.encodeToString(byte_data,Base64.DEFAULT);
        return encoded;
    }
}
