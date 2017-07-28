package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class ReadFromSearch extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private TextView textFromSearch;
    private ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_read_from_search);

        scroll = (ScrollView)findViewById(R.id.scroll);
        textFromSearch = (TextView) findViewById(R.id.textFromSearch);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        textFromSearch.setText(text);
    }
}
