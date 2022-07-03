package com.hy.gnft2;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Mainasmr extends AppCompatActivity {


    private Button ms1, ms2, ms3, ms4, ms5, ms6;
    private MediaPlayer m1, m2, m3, m4, m5, m6;
    private int pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainasmr);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        ms1 = (Button) findViewById(R.id.s1);
        ms2 = (Button) findViewById(R.id.s2);
        ms3 = (Button) findViewById(R.id.s3);
        ms4 = (Button) findViewById(R.id.s4);
        ms5 = (Button) findViewById(R.id.s5);
        ms6 = (Button) findViewById(R.id.s6);

        //버튼 클릭시 노래 재생
        ms1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m1 != null && m1.isPlaying())
                {//노래 재생 중일시 일시정지
                    m1.pause();

                }
                else if(m1 == null)
                {//노래 재생
                    m1 = m1.create(Mainasmr.this, R.raw.fire);
                    m1.start();
                    m1.setLooping(true);
                }
                else{//노래 재시작
                    m1.start();
                    m1.setLooping(true); //노래 무한 반복 재생
                }
            }
        });

        ms2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m2 != null && m2.isPlaying())
                {
                    m2.pause();

                }
                else if(m3 == null)
                {
                    m2 = m2.create(Mainasmr.this, R.raw.wind);
                    m2.start();
                    m2.setLooping(true);
                }
                else{
                    m2.start();
                    m2.setLooping(true);
                }
            }
        });

        ms3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m3 != null && m3.isPlaying())
                {
                    m3.pause();

                }
                else if(m3 == null)
                {
                    m3 = m3.create(Mainasmr.this, R.raw.song);
                    m3.start();
                    m3.setLooping(true);
                }
                else{
                    m3.start();
                    m3.setLooping(true);
                }
            }
        });
        ms4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m4 != null && m4.isPlaying())
                {
                    m4.pause();

                }
                else if(m4 == null)
                {
                    m4 = m4.create(Mainasmr.this, R.raw.rain);
                    m4.start();
                    m4.setLooping(true);
                }
                else{
                    m4.start();
                    m4.setLooping(true);
                }
            }
        });

        ms5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m5 != null && m5.isPlaying())
                {
                    m5.pause();

                }
                else if(m5 == null)
                {
                    m5 = m5.create(Mainasmr.this, R.raw.waves);
                    m5.start();
                    m5.setLooping(true);
                }
                else{
                    m5.start();
                    m5.setLooping(true);
                }
            }
        });

        ms6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m6 != null && m6.isPlaying())
                {
                    m6.pause();

                }
                else if(m6 == null)
                {
                    m6 = m6.create(Mainasmr.this, R.raw.river);
                    m6.start();
                    m6.setLooping(true);
                }
                else{
                    m6.start();
                    m6.setLooping(true);
                }
            }
        });





    }
}
