package com.hy.gnft2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class SleepAdapter extends BaseAdapter {
    ArrayList<SleepTime> sleepTimeArrayList = new ArrayList<>();
    @Override
    public int getCount() {
        return sleepTimeArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return sleepTimeArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if(view ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.firstday,viewGroup,false);
        }

        TextView textView2 = (TextView) view.findViewById(R.id.tv1);
        TextView textView3 = (TextView) view.findViewById(R.id.tv2);
        SleepTime sleepTime = sleepTimeArrayList.get(i);
        textView2.setText(Integer.toString(sleepTime.getNum())+" 일차");
        textView3.setText("수면시간 :" + sleepTime.getTime());

        return view;
    }

    public void addItem(int id, String time){
        SleepTime sleepTime = new SleepTime();
        sleepTime.setNum(id);
        sleepTime.setTime(time);
        sleepTimeArrayList.add(sleepTime);

    }

}
