package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SearchListActivity extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private ListView search_result;

    private String text;
    private ArrayList<Integer> indexes;
    ArrayAdapter <String> adapter;
    private int[]identificators;
    private String AllText = "";
    private HashMap<Integer,String>map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        indexes = new ArrayList<>();
        search_result = (ListView)findViewById(R.id.search_result);

        map = new HashMap<>();
        mainGeneration();
    }

    public void mainGeneration(){
        final Intent intent = getIntent();
        text = intent.getStringExtra("text");
        indexes = intent.getIntegerArrayListExtra("listIndex");
        ArrayList<String> list = new ArrayList<>();
        list.clear();
        for (int i=0;i<indexes.size();i++){
            list.add("..." + text.substring( indexes.get(i)-5,indexes.get(i)+5)+ "...");
        }
        adapter = new ArrayAdapter<String>(SearchListActivity.this,android.R.layout.simple_list_item_1,list);
        search_result.setAdapter(adapter);
        search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(SearchListActivity.this, ReadFromSearch.class);
                String textForRead = null;
                int point = indexes.get(position); // символ, который находится посередине текста, который мы должны отобразить
                textForRead = generateText(point, text);
                intent1.putExtra("text",textForRead);
                startActivity(intent1);
            }
        });
    }

    String generateText(int point, String text) {
        String textForRead = "";
        int start = point;
        int end = point;
        text = text.replaceAll("<hads>","*");
        text = text.replaceAll("<hade>","@");
        char[]arr = text.toCharArray();
        while (arr[start]!='*'){
            start--;
        }
        while (arr[end]!='@'){
            end++;
        }
        textForRead = text.substring(start,end);
        textForRead = textForRead.replaceAll("<arts>","");
        textForRead = textForRead.replaceAll("<arte>","");
        textForRead = textForRead.replaceAll("<urduts>","");
        textForRead = textForRead.replaceAll("<urdute>","");
        textForRead = textForRead.replaceAll("<ents>","");
        textForRead = textForRead.replaceAll("<ente>","");
        return textForRead;
    }
}
