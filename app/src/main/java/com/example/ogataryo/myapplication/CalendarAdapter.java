package com.example.ogataryo.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Calendar.*;

//BaseAdapterを継承してカスタムでadapterを作成
public class CalendarAdapter extends BaseAdapter {
    public List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;
    private Calendar mCalendar;

    //カスタムセルを拡張したらここでWigetを定義
    private static class ViewHolder {
        public TextView dateText;
    }

    public CalendarAdapter(Context context) {
        //レイアウトXMLファイルからViewを作る
        mContext = context;
        mLayoutInflater = LayoutInflater.from( mContext );

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
            convertView = mLayoutInflater.inflate( R.layout.calendar_cell, null );
            holder = new ViewHolder();
            holder.dateText = convertView.findViewById( R.id.dateText );
            convertView.setTag( holder );

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //セルのサイズを指定
     /*   float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params = new AbsListView.LayoutParams( parent.getWidth() / 7 - (int) dp, (parent.getHeight() - (int) dp * mDateManager.getWeeks()) / mDateManager.getWeeks() );
        convertView.setLayoutParams( params );*/


        //日付のみ表示させる
        SimpleDateFormat dateFormat = new SimpleDateFormat( "d", Locale.JAPAN );
        dateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Tokyo" ) );


        //表示
        holder.dateText.setText( dateFormat.format( dateArray.get( position ) ) );


        //当月以外のセルをグレーアウト
        if (mDateManager.isCurrentMonth( dateArray.get( position ) )) {
            //当月
            convertView.setBackgroundColor( Color.WHITE );
        } else {
            //別月
            holder.dateText.setText( "" );
            convertView.setBackgroundColor( Color.TRANSPARENT );
        }


        int colorId;
        //日曜日を赤、土曜日を青に
        switch (mDateManager.getDayOfWeek( dateArray.get( position ) )) {
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

        if(mDateManager.isToday(dateArray.get(position ))){
            //当日の文字色、背景を指定
            convertView.setBackgroundColor( Color.GRAY );
            colorId = Color.WHITE;
        }

        holder.dateText.setTextColor( colorId );
        return convertView;
    }





    @Override
    public long getItemId(int position) {
        SimpleDateFormat toastDateFormat = new SimpleDateFormat( "yyyyMMdd", Locale.JAPAN );
        toastDateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Tokyo" ) );
        return Long.valueOf( String.valueOf( toastDateFormat.format( dateArray.get( position ) ) ) );
    }

    @Override
    public Object getItem(int position) {
        SimpleDateFormat toastDateFormat = new SimpleDateFormat( "yyyyMMdd", Locale.JAPAN );
        toastDateFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Tokyo" ) );
        return Long.valueOf( String.valueOf( toastDateFormat.format( dateArray.get( position ) ) ) );
    }

    //表示月を取得
    public String getTitle() {
        SimpleDateFormat format = new SimpleDateFormat( "yyyy.MM", Locale.JAPAN );
        return format.format( mDateManager.mCalendar.getTime() );
    }


    //翌月表示
    public void nextMonth() {
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        //データセットが変更されたことを、登録されているすべてのobserverに通知する。
        this.notifyDataSetChanged();
    }

    //前月表示
    public void prevMonth() {
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        //データセットが変更されたことを、登録されているすべてのobserverに通知する。
        this.notifyDataSetChanged();
    }

    }
/*        //選択月表示
        public void choiceMonth () {

        }*/


