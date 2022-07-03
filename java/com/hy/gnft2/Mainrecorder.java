package com.hy.gnft2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Mainrecorder extends AppCompatActivity {

    private ListView textView_time;

    private Cursor cursor;


    private SleepAdapter sleepAdapter;
    private DBHelper dbHelper;
    private Button mReset;
    private SQLiteDatabase sqlDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timerecorder);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        textView_time = (ListView) findViewById(R.id.sleepall);
        mReset = (Button) findViewById(R.id.reset);
        sleepAdapter = new SleepAdapter();
        dbHelper = new DBHelper(this);
        SQLiteDatabase GNFT = dbHelper.getReadableDatabase();
        cursor = GNFT.rawQuery("select * from sleeptime",null);

        while(cursor.moveToNext()){

            sleepAdapter.addItem(cursor.getInt(0),cursor.getString(1));

        }
        textView_time.setAdapter(sleepAdapter);
        textView_time.smoothScrollToPosition(0);
        mReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                sleepAdapter.notifyDataSetChanged();
                sqlDB = dbHelper.getWritableDatabase();
                dbHelper.onUpgrade(sqlDB, 1, 2);


                sqlDB.close();
                Toast.makeText(getApplicationContext(), "기록이 초기화되었습니다.",
                        Toast.LENGTH_SHORT).show();


            }
        });






    }


}
