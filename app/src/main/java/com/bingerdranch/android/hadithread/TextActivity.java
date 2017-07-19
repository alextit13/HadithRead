package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class TextActivity extends Activity {


    private TextView text_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.read_layout);

        text_view = (TextView) findViewById(R.id.text);
        text_view.setText("");
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        String text_itog = text.replace("<te>","");
        text_view.setText(text_itog);
    }
}
