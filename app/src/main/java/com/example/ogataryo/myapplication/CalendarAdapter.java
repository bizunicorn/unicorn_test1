package com.example.ogataryo.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    public List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;

    //aaaaaaa
    //カスタムセルを拡張したらここでWigetを定義
    private static class ViewHolder {
        public TextView dateText;
    }

    public CalendarAdapter(Context context) {
        //レイアウトXMLファイルからViewを作る
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        //DateManagerクラス
        mDateManager = new DateManager();

        //日付の情報をdateArrayに格納
        dateArray = mDateManager.getDays();
    }

    @Override
    public int getCount() {
        //dateArray.size()== 35
        return dateArray.size();
    }

    @Override
    //viewの表示
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            //拡張
            convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null);
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById(R.id.dateText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //セルのサイズを指定
            float dp = mContext.getResources().getDisplayMetrics().density;
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeeks() ) / mDateManager.getWeeks());
            convertView.setLayoutParams(params);

        //メモの日付(比較用)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.US);

        //日付のみ表示させる
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("d", Locale.US);

        //表示
        for (int i = 0; i < getCount(); i++) {
            if (dateFormat.format(dateArray.get(position)).equals("2018.04.19")) {
                holder.dateText.setText(dateFormat2.format(dateArray.get(position))  + "\n" +  "\n"+"test");
            } else {
                holder.dateText.setText(dateFormat2.format(dateArray.get(position)));
            }
            i++;
        }

        //当月以外のセルをグレーアウト
        if (mDateManager.isCurrentMonth(dateArray.get(position))) {
            //当月
            convertView.setBackgroundColor(Color.WHITE);

        } else {
            //別月
            holder.dateText.setText("");
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

    /*    //当日の背景
        if (mDateManager.isToday(dateArray.get(position))) {
            convertView.setBackgroundColor(Color.CYAN);
        }*/

        //日曜日を赤、土曜日を青に
        int colorId;

        switch (mDateManager.getDayOfWeek(dateArray.get(position))) {

            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;

            default:
                colorId = Color.BLACK;
                break;
        }

        holder.dateText.setTextColor(colorId);
        return convertView;
    }

    @Override
    public long getItemId(int position) {
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyyMMdd", Locale.US);
        return Long.valueOf(String.valueOf(dateFormat3.format(dateArray.get(position))));
    }


    @Override
    public Object getItem(int position) {
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyyMMdd", Locale.US);
            return Long.valueOf(String.valueOf(dateFormat3.format(dateArray.get(position))));
    }

    //表示月を取得
    public String getTitle() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.US);
        return format.format(mDateManager.mCalendar.getTime());
    }


    //翌月表示
    public void nextMonth() {
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    //前月表示
    public void prevMonth() {
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

}