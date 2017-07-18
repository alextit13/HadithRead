package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private ListView listViewBooks;
    private ArrayList <String> arrayListBooks;
    private ArrayAdapter<String> arrayAdapterBooks;
    final String FILENAME = "file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewBooks = (ListView) findViewById(R.id.list_view_books);
        arrayListBooks = new ArrayList<>();
        downloadBooksNames();
        arrayAdapterBooks = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,arrayListBooks);
        listViewBooks.setAdapter(arrayAdapterBooks);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Click on book # " + position,Toast.LENGTH_SHORT).show();
                //BooksReader(position);
            }
        });

    }

    public void downloadBooksNames(){

    }

    public String BooksReader(int position){
        //чтение из файлов

        String text = "";
        return text;
    }
}
