package com.bingerdranch.android.hadithread;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText book_text;
    private EditText title_text;
    private EditText word_text;

    private ListView listView;
    private Button buttonGo;

    private ArrayList <String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.listViewResultSearch);
        list = new ArrayList<>();
        for (int i = 0; i<20;i++){
            list.add("Search result # " + i+1);
        }
        adapter = new ArrayAdapter<String>(SearchActivity.this,R.layout.book_item,list);
        listView.setAdapter(adapter);
    }
}
