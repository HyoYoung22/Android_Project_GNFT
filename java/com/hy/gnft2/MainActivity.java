package com.hy.gnft2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button mStartBtn, mRecordBtn, mPauseBtn, mbtnrec, mbtnasmr, mbtnfin, agebtn;
    private TextView mTimeTextView;
    private Thread timeThread = null;
    private Boolean isRunning = true;
    private DBHelper dbHelper;
    private EditText mage;


    View barView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowManager.LayoutParams params = getWindow().getAttributes();

        ControlView view = (ControlView) findViewById(R.id.plus);
        view.setKnobListener(new ControlView.KnobListener(){
            public void onChanged(double angle){
                if(angle > 0){

                    angle = angle + 1;

                }
                else {
                    angle = 360;
                }


                params.screenBrightness = (float) ((double) angle / 360);
                getWindow().setAttributes(params);

            }


        });




        ActionBar actionBar = getSupportActionBar();
        actionBar.hide(); //상단 바 제거

        mStartBtn = (Button) findViewById(R.id.btn_start);
        mRecordBtn = (Button) findViewById(R.id.btn_record);
        mPauseBtn = (Button) findViewById(R.id.btn_pause);
        mTimeTextView = (TextView) findViewById(R.id.timeView);
        mbtnrec = (Button) findViewById(R.id.btnrec);
        mbtnasmr = (Button) findViewById(R.id.btnasmr);
        mbtnfin = (Button) findViewById(R.id.btnfinish);
        mage = (EditText) findViewById(R.id.age);
        dbHelper = new DBHelper(this);
        agebtn = (Button) findViewById(R.id.agebtn);
        //연령별 권장 수면시간 추천해주는 리스너



        agebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mage.length() == 0) {
                    Toast.makeText(getApplicationContext(), "나이를 입력하세요",
                            Toast.LENGTH_SHORT).show();

                } else {
                    int i = Integer.parseInt(mage.getText().toString());
                    if (0 <= i && i <= 2) {
                        Toast.makeText(getApplicationContext(), "사용자의 권장 수면시간은 15시간 입니다.",
                                Toast.LENGTH_LONG).show();
                    } else if (2 < i && i <= 5) {
                        Toast.makeText(getApplicationContext(), "사용자의 권장 수면시간은 13시간 입니다.",
                                Toast.LENGTH_LONG).show();
                    } else if (5 < i && i <= 13) {
                        Toast.makeText(getApplicationContext(), "사용자의 권장 수면시간은 11시간 입니다.",
                                Toast.LENGTH_LONG).show();
                    } else if (13 < i && i <= 17) {
                        Toast.makeText(getApplicationContext(), "사용자의 권장 수면시간은 10시간 입니다.",
                                Toast.LENGTH_LONG).show();
                    } else if (17 < i && i <= 64) {
                        Toast.makeText(getApplicationContext(), "사용자의 권장 수면시간은 9시간 입니다.",
                                Toast.LENGTH_LONG).show();
                    } else if (64 < i) {
                        Toast.makeText(getApplicationContext(), "사용자의 권장 수면시간은 8시간 입니다.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        //기록 시작 버튼 클릭시
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                mRecordBtn.setVisibility(View.VISIBLE);
                mPauseBtn.setVisibility(View.VISIBLE);
                timeThread = new Thread(new timeThread()); //시간 측정 클래스
                timeThread.start(); //시간 측정 시작
            }
        });



        //기록 버튼 클릭시 데이터베이스 테이블에 측정 시간 기록
        mRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.btn_record: //메세지 창 생성
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
                        alt_bld.setMessage("수면 시간을 기록 하시겠습니까?").setIcon(R.drawable.check_dialog_64)
                                .setCancelable(false).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbHelper.insert(mTimeTextView.getText().toString());
                                        Toast.makeText(getApplicationContext(),"수면 시간이 기록되었습니다.",Toast.LENGTH_SHORT).show();
                                        v.setVisibility(View.GONE);
                                        mRecordBtn.setVisibility(View.GONE);
                                        mStartBtn.setVisibility(View.VISIBLE);
                                        mPauseBtn.setVisibility(View.GONE);
                                        timeThread.interrupt();
                                    }
                                }).setNegativeButton("아니오", null);
                        AlertDialog alert = alt_bld.create();
                        alert.show(); //메세지 창 출력


                }

            }
        });
        //측정 중지 버튼
        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    mPauseBtn.setText("일시정지");
                } else {
                    mPauseBtn.setText("시작");
                }
            }
        });
        //Mainasmr 액티비티로 인텐트
        mbtnasmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Mainasmr.class);
                startActivity(intent);
            }
        });
        //Mainrecorder 액티비티로 인텐트
        mbtnrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Mainrecorder.class);
                startActivity(intent);
            }
        });
        //종료 버튼 클릭시 메세지 창 생성 후 종료
        mbtnfin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnfinish:
                        AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
                        alt_bld.setTitle("편안한 밤 되셨습니까?").setMessage("앱을 종료하시겠습니까?").setIcon(R.drawable.check_dialog_64)
                                .setCancelable(false).setPositiveButton("네",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                }).setNegativeButton("아니오", null);
                        AlertDialog alert = alt_bld.create();
                        alert.show();


                }

            }
        });
    }
        //시간 측정
        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int mSec = msg.arg1 % 100;
                int sec = (msg.arg1 / 100) % 60;
                int min = (msg.arg1 / 100) / 60;
                int hour = (msg.arg1 / 100) / 3600;
                //100이 1초 100*60 은 1분 100*60*10은 10분 100*60*60은 한시간

                @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d:%02d", hour, min, sec, mSec);
                mTimeTextView.setText(result);
            }
        };



    public class timeThread implements Runnable {
                @Override
                public void run() {
                    int i = 0;

                    while (true) {
                        if(isRunning) { //일시정지를 누르면 멈춤
                            Message msg = new Message();
                            msg.arg1 = i++;
                            handler.sendMessage(msg);

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTimeTextView.setText("");
                                        mTimeTextView.setText("00:00:00:00");
                                    }
                                });
                                return; // 인터럽트 받을 경우 return
                            }
                        }
                        else if(isRunning == false)
                        {

                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mTimeTextView.setText("");
                                        mTimeTextView.setText("00:00:00:00");
                                    }
                                });
                                return; // 인터럽트 받을 경우 return
                            }

                        }
                    }
                }


            }


        //백버튼 누를 시 메세지창 생성 후 종료
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    AlertDialog.Builder alt_bld = new AlertDialog.Builder(MainActivity.this);
                    alt_bld.setTitle("편안한 밤 되셨습니까?").setMessage("앱을 종료하시겠습니까?").setIcon(R.drawable.check_dialog_64)
                        .setCancelable(false).setPositiveButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).setNegativeButton("아니오", null);
                AlertDialog alert = alt_bld.create();
                alert.show();

        }
        return true;
    }
}