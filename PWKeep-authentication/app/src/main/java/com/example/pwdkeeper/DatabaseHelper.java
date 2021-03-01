package com.example.pwdkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Password.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "password";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SUBJECT = "Subject";
    private static final String COLUMN_USERNAME = "user_name" ;
    private static final String COLUMN_CONTEXT = "Content";
    private static final String COLUMN_TIMESTAMP = "Timestamp";
    private static final String COLUMN_UPDATEDTIMESTAMP = "Updated_at";
    Encryption encrypt;

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_SUBJECT + " TEXT, " +
                        COLUMN_USERNAME + " TEXT, " +
                        COLUMN_CONTEXT + " TEXT, " +
                        COLUMN_TIMESTAMP + " DEFAULT CURRENT_TIMESTAMP, " +
                        COLUMN_UPDATEDTIMESTAMP + " DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addRecord(String subject, String user_name, String contexts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/YYYY HH:mm:ss");
        String dateTime = simpleDateFormat.format(new Date());
        cv.put(COLUMN_SUBJECT, subject);
        cv.put(COLUMN_USERNAME,user_name);
        cv.put(COLUMN_CONTEXT, contexts);
        cv.put(COLUMN_TIMESTAMP, dateTime);
        cv.put(COLUMN_UPDATEDTIMESTAMP, dateTime);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
        }
    }


    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor =  db.rawQuery(query, null);
        }
        return cursor;
    }


    //Search DB code goes here...
    Cursor getSearchData(String search){
        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_SUBJECT+" like '%"+search+"%' or "+COLUMN_CONTEXT+" like '%"+search+"%'" ;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor =  db.rawQuery(query, null);
        }
        return cursor;
    }


    void updateData(String row_id, String subject,String user_name, String contexts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Date currentDate= Calendar.getInstance().getTime();;
        String datetime = currentDate.toString();
        cv.put(COLUMN_SUBJECT, subject);
        cv.put(COLUMN_USERNAME,user_name);
        cv.put(COLUMN_CONTEXT, contexts);
        cv.put(COLUMN_UPDATEDTIMESTAMP,datetime);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to update!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    void deleteRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
        }
    }
}
