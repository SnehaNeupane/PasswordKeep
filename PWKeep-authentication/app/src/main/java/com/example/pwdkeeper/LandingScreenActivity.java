package com.example.pwdkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingScreenActivity extends AppCompatActivity {

    Button newRecord, listRecord, generatepwd, btnSearch;
    public static final int ALL_RECORDS = 0;
    public static final int SEARCH_RECORDS = 1;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_landing_screen);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

//          btnSearch = findViewById(R.id.btnSearch);
            newRecord = findViewById(R.id.newRecord);
            listRecord = findViewById(R.id.listRecord);
            generatepwd = findViewById(R.id.generateBtn);

            newRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AddRecordActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            listRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("type", ALL_RECORDS);
                    startActivity(intent);
                    finish();
                }
            });

//            btnSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
//                    intent.putExtra("type", SEARCH_RECORDS);
//                    startActivity(intent);
//                    finish();
//                }
//            });

            generatepwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), GeneratePwdActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

            @Override
            public void onBackPressed () {

                finishAffinity();
            }
        }