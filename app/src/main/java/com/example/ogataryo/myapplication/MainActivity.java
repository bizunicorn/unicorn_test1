package com.example.ogataryo.myapplication;


import android.app.Dialog;
import android.app.ListActivity;
import android.net.Uri;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.View.OnClickListener;
import android.util.Log;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView varTextView;
    private Button prevButton, nextButton;
    private Button choiceDate;
    private CalendarAdapter mCalendarAdapter;
    private GridView calendarGridView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu.xmlの内容を読み込む
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // オプションメニューのアイテムが選択されたときに呼び出されるメソッド
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                // 画面遷移1
                return true;

            case R.id.item2:
                // 画面遷移2
                return true;

            case R.id.item3:
                /// 画面遷移3
                Uri uri = Uri.parse("http://v6391.vir.kagoya.net/axis/login/login/main");
                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(i);
                return true;

            case R.id.item4:
                // 画面遷移4
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleText = findViewById(R.id.titleText);

        //前月
        prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {

            @Override
            //クリック後
            public void onClick(View v) {
                mCalendarAdapter.prevMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });

        //翌月
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            //クリック後
            public void onClick(View v) {
                mCalendarAdapter.nextMonth();
                titleText.setText(mCalendarAdapter.getTitle());
            }
        });

        //コンストラクタ
        mCalendarAdapter = new CalendarAdapter(this);

        // 要素の取得
        calendarGridView = findViewById(R.id.calendarGridView);

        //リストに表示されるデータを登録
        calendarGridView.setAdapter(mCalendarAdapter);

        //年月を表示
        titleText.setText(mCalendarAdapter.getTitle());

        //カレンダーセルをクリックした時に呼び出されるメソッド
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str;
                str  = String.valueOf( mCalendarAdapter.getItemId( i ) );
                Toast.makeText(MainActivity.this, "クリックされました！" + str, Toast.LENGTH_LONG).show();
             }
        });

    }
}

