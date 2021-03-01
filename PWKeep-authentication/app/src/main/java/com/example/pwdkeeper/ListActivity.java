package com.example.pwdkeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.SecureRandom;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper dh;
    ArrayList<String> diary_id, diary_subject,diary_user, diary_context;
    CustomAdapter customAdapter;
    EditText action_search;
    FloatingActionButton floatingActionButton, floatingAddButton, floatingGenerateButton;
    ImageView empty_imageview;
    TextView nodata, tv_generate, tv_add;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

//        int type=getIntent().getIntExtra("type",0);

        recyclerView = findViewById(R.id.recyclerView);
        action_search = findViewById(R.id.action_search);
        floatingActionButton =findViewById(R.id.floatingButton);
        floatingAddButton = findViewById(R.id.floatingAddButton);
        floatingGenerateButton = findViewById(R.id.floatingGenerateButton);
        empty_imageview = findViewById(R.id.empty_imageview);
        nodata = findViewById(R.id.nodata);
        tv_generate = findViewById(R.id.tv_generate);
        tv_add = findViewById(R.id.tv_add);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), AddRecordActivity.class);
//                startActivity(intent);
//                finish();
                animateFab();
            }
        });

        floatingAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRecordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        floatingGenerateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GeneratePwdActivity.class);
                startActivity(intent);
                finish();
            }
        });


//        if (type==LandingScreenActivity.ALL_RECORDS){
//            action_search.setVisibility(View.GONE);
//        }else{
//            action_search.setVisibility(View.VISIBLE);
//        }

        dh = new DatabaseHelper(ListActivity.this);
        diary_id = new ArrayList<>();
        diary_subject = new ArrayList<>();
        diary_context = new ArrayList<>();
        diary_user = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(ListActivity.this, this, diary_id, diary_subject,diary_user, diary_context);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));

        action_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                storeSearchDataInArrays(s.toString());
            }
        });

    }

    private void animateFab()
    {
        if(isOpen){
            floatingActionButton.startAnimation(rotateBackward);
            floatingGenerateButton.startAnimation(fabClose);
            floatingAddButton.startAnimation(fabClose);
            floatingGenerateButton.setClickable(false);
            floatingAddButton.setClickable(false);
            tv_generate.setVisibility(View.GONE);
            tv_add.setVisibility(View.GONE);
            isOpen=false;
        } else {
            floatingActionButton.startAnimation(rotateForward);
            floatingGenerateButton.startAnimation(fabOpen);
            floatingAddButton.startAnimation(fabOpen);
            floatingGenerateButton.setClickable(true);
            floatingAddButton.setClickable(true);
            tv_generate.setVisibility(View.VISIBLE);
            tv_add.setVisibility(View.VISIBLE);
            isOpen=true;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }

    void storeDataInArrays(){
        Cursor cursor  = dh.readAllData();
        if (cursor.getCount() == 0){
//            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
            empty_imageview.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()){
                diary_id.add(cursor.getString(0));
                diary_subject.add(cursor.getString(1));
                diary_context.add(cursor.getString(3));
                diary_user.add(cursor.getString(2));
            }
            empty_imageview.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }


    void storeSearchDataInArrays(String search){
        Cursor cursor  = dh.getSearchData(search);
        diary_id.clear();
        diary_subject.clear();
        diary_context.clear();
        if (cursor.getCount() != 0){
           /* Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {*/
            while (cursor.moveToNext()){
                diary_id.add(cursor.getString(0));
                diary_subject.add(cursor.getString(1));
                diary_context.add(cursor.getString(2));
            }
        }
        customAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), LandingScreenActivity.class);
        startActivity(intent);
        finish();
    }
}
