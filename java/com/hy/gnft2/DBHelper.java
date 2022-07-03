package com.hy.gnft2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="GNFT.db";
    public static final String GNFT_TABLE_NAME = "sleeptime";
    public static final String GNFT_COLUMN_ID = "id";
    public static final String GNFT_COLUMN_TIME = "time";

    public DBHelper(Context context) { super(context, DATABASE_NAME,null,1);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists sleeptime" + "(id integer primary key, time text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS sleeptime");
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("time",time);

        db.insert("sleeptime",null,contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from sleeptime where id =" + id + "", null);
        return cursor;
    }

    public ArrayList getAllData(){
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from sleeptime",null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()==false){
            arrayList.add(cursor.getString(cursor.getColumnIndex(GNFT_COLUMN_TIME)));
            cursor.moveToNext();
        }
        return arrayList;
    }
}
