package com.example.ogataryo.myapplication;

import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateManager {
        Calendar mCalendar;
        Calendar mTodayCalendar;

        public DateManager(){
            TimeZone jst = TimeZone.getTimeZone("Asia/Tokyo");
            mCalendar = Calendar.getInstance();
            mTodayCalendar = Calendar.getInstance();
            mCalendar.setTimeZone(jst);
            mTodayCalendar.setTimeZone(jst);
        }
        //a

        //当月の要素を取得
        public List<Date> getDays(){

            //現在の状態を保持
            Date startDate = mCalendar.getTime();

            //GridViewに表示するマスの合計を計算(5*7=35マス)
            int count = getWeeks() * 7;

            //当月のカレンダーに表示される前月分の日数を計算
            mCalendar.set(Calendar.DATE, 1);
            int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;

            //add()はカレンダーに日付を増減する
            mCalendar.add(Calendar.DATE, -dayOfWeek);

            //日付(List)
            List<Date> days = new ArrayList<>();

            //count == 35
            for (int i = 0; i < count; i ++){

                //日付の情報を格納
                days.add(mCalendar.getTime());

                //日付を1日ずつ増幅
                mCalendar.add(Calendar.DATE, 1);
                //System.out.println(days.get(i));
            }

            //状態を復元
            mCalendar.setTime(startDate);
            return days;
        }

        //当月かどうか確認
        public boolean isCurrentMonth(Date date){

            //当月を格納
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM", Locale.JAPAN);
            format.setTimeZone(TimeZone.getTimeZone( "Asia/Tokyo" ));
            String currentMonth = format.format(mCalendar.getTime());

            //当月どうか比較
            if (currentMonth.equals(format.format(date))){
                //当月
                return true;
            }else {
                //別月
                return false;
            }
        }

        //週数を取得
        public int getWeeks(){
            return mCalendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
        }

        //曜日を取得
        public int getDayOfWeek(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            calendar.setTime(date);

            return calendar.get(Calendar.DAY_OF_WEEK);
        }

        //翌月へ
        public void nextMonth(){
            mCalendar.add(Calendar.MONTH, 1);
        }

        //前月へ
        public void prevMonth(){
            mCalendar.add(Calendar.MONTH, -1);
        }

        //当日
        public boolean isToday(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd", Locale.JAPAN);
            format.setTimeZone(TimeZone.getTimeZone( "Asia/Tokyo" ));
            String today = format.format(mTodayCalendar.getTime());

            if (today.equals(format.format(date))) {
                return true;
            }else {
                return false;
            }
        }

    }
