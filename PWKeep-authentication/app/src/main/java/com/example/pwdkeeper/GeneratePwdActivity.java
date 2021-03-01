package com.example.pwdkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;

public class GeneratePwdActivity extends AppCompatActivity {

//    @BindView(R.id.pwd_tv)
//    TextView pwd_tv;
//    @BindView(R.id.pwdlength_tv)
//    TextView pwdlength_tv;
//    @BindView(R.id.seekBar)
//    SeekBar seekBar;
//    @BindView(R.id.btnPwdgenerate)
//    Button btnPwdgenerate;


    Button btnPwdgenerate;
    TextView pwd_tv, pwdlength_tv;;
    SeekBar seekBar;
    ImageView content_copy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_pwd);
//        ButterKnife.bind(this);

        btnPwdgenerate = findViewById(R.id.btnPwdgenerate);
        pwd_tv = findViewById(R.id.pwd_tv);
        pwdlength_tv = findViewById(R.id.pwdlength_tv);
        seekBar = findViewById(R.id.seekBar);
        content_copy = findViewById(R.id.content_copy);

        content_copy.setVisibility(View.INVISIBLE);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pwdlength_tv.setText("Length : " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//    }
//
//    @OnClick(R.id.btnPwdgenerate)
//    public void generate(View v){
//        String password = PasswordGenerator.process(seekBar.getProgress());
//        pwd_tv.setText(password);


        btnPwdgenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = generateString(seekBar.getProgress());
//                pwd_tv.setText(generateString(10));
                pwd_tv.setText(password);
                content_copy.setVisibility(View.VISIBLE);
            }
        });

        content_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Text View", pwd_tv.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(GeneratePwdActivity.this, "Copied!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String generateString(int length){
        char[] chars = "QWERTYUIOPASDFGHJKLZXCVBNMmnbvcxzlkjhgfdsapoiuytrewq1234567890!@#$%^&*()".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i=0; i<length; i++){
            char c = chars[random.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
