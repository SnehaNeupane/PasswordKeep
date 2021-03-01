package com.example.pwdkeeper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText text_subject, text_user,text_context;
    Button btnUpdate, btnDelete;
    String id, subject, user, contexts;
    Encryption enc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        text_subject = findViewById(R.id.editSubject2);
        text_context = findViewById(R.id.editContext2);
        text_user = findViewById(R.id.editUser);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        //First call this function then only call dh.updateData function
        getAndSetIntentData();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              DatabaseHelper dh = new DatabaseHelper(getApplicationContext());
                DatabaseHelper dh = new DatabaseHelper(UpdateActivity.this);
                subject = text_subject.getText().toString().trim();
                user = text_user.getText().toString().trim();
                contexts = enc.encodeData(text_context.getText().toString().trim());
                dh.updateData(id, subject, user,contexts);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("subject") &&
                getIntent().hasExtra("contexts")){

            //Getting data from Intent
            id = getIntent().getStringExtra("id");
            subject = getIntent().getStringExtra("subject");
            user = getIntent().getStringExtra("user");
            contexts = getIntent().getStringExtra("contexts");

            //Setting Intent Data
            text_subject.setText(subject);
            text_context.setText(contexts);
            text_user.setText(user);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + subject + " ?");
        builder.setTitle("Are you sure you want to delete " + subject + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper dh = new DatabaseHelper(UpdateActivity.this);
                dh.deleteRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
