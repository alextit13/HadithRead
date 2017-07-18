package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends Activity {

    private final static String FILE_NAME = "content.txt";
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
        downloadBooksNames();
        arrayAdapterBooks = new ArrayAdapter<>(MainActivity.this,R.layout.book_item,arrayListBooks);
        listViewBooks.setAdapter(arrayAdapterBooks);
        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"Click on book # " + arrayListBooks.get(position),Toast.LENGTH_SHORT).show();
                textsRead();
                Intent intent = new Intent(MainActivity.this, ParthActivity.class);
                startActivity(intent);
            }
        });
    }
    public void downloadBooksNames(){
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.one));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.two));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.three));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.four));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.five));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.six));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.sewen));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.eigth));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.nine));
        arrayListBooks.add(getResources().getResourceEntryName(R.raw.ten));
    }

    public String textsRead(){
        String textOnFile = "";
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.one);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            textOnFile = new String(b);
            Log.d(LOG_TAG,textOnFile);
            //txtHelp.setText(new String(b));
        } catch (Exception e) {
            // e.printStackTrace();
            Log.d(LOG_TAG, "Жопа блять");
        }
        return textOnFile;
    }

    public void generateTitles(String textOnFile){
        textOnFile = textsRead();

    }
}
