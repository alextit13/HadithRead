package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private ListView listViewBooks;
    private ArrayList <String> arrayListBooks;
    private ArrayAdapter<String> arrayAdapterBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewBooks = (ListView) findViewById(R.id.list_view_books);
        arrayListBooks = new ArrayList<>();
        for (int i = 0; i<20; i++){
            String book = "book_" + i;
            arrayListBooks.add(i,book);
        }
        arrayAdapterBooks = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrayListBooks);
        listViewBooks.setAdapter(arrayAdapterBooks);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Click on book # " + position,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
