package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static android.provider.Telephony.Mms.Part.FILENAME;

public class MainActivity extends Activity {

    private final static String FILE_NAME = "content.txt";
    private static final String LOG_TAG = "MyLogs";
    private ListView listViewBooks;
    private ArrayAdapter<String> arrayAdapterBooks;
    private ArrayList <String> listForBooks; // тут храняться названия книг
    private ArrayList <String> listForTitle; // тут храняться названия разделов
    private String textOnFile = "";
    private char [] arr;
    private ImageButton button_search;

    private String ATTRIBUTE_NAME_TEXT = "text";
    private String ATTRIBUTE_NAME_IMAGE = "image";
    private String ATTRIBUTE_NAME_TEXT2 = "text2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        listViewBooks = (ListView) findViewById(R.id.list_view_books);
        listForBooks = new ArrayList<>();
        textsRead();
        String [] text = {"Sunnah Ibn Majah","Comprehensive modification","Golden feminine", "Sunnah Abu Dawood",
                "Right Muslim","Sahih Bukhari","Sunni","Mushrikah Sharif","Motta Imam Boss","Shamal Tirmizi"};
        String [] text2 = {"مسند احمد", "سنن دارمی", "مشکوۃ شریف","موطا امام مالک","شمائل ترمذی","سنن ابن ماجہ",
                "جامع ترمذی","سنن نسائی","سنن ابوداؤد","صحیح مسلم"};
        int [] img = {R.drawable.book_red,R.drawable.book_mehroon,R.drawable.book_green,R.drawable.book_blue,
                        R.drawable.aqua,R.drawable.brown,R.drawable.orange, R.drawable.pink,R.drawable.green,R.drawable.book_mehroon};
        ArrayList<Map<String, Object>> data = new ArrayList<>(text.length);
        Map<String, Object> m;
        for (int i= 0; i<text.length;i++){
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT,text[i]);
            m.put(ATTRIBUTE_NAME_TEXT2,text2[i]);
            m.put(ATTRIBUTE_NAME_IMAGE,img[i]);
            data.add(m);
        }
        String [] from = {ATTRIBUTE_NAME_IMAGE, ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_TEXT2};
        int [] to = {R.id.image_book, R.id.text_book, R.id.text_book2};
        SimpleAdapter sAdapter = new SimpleAdapter(MainActivity.this, data, R.layout.item,from,to);
        listViewBooks.setAdapter(sAdapter);

        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ParthActivity.class);
                intent.putExtra("text",textOnFile);
                intent.putExtra("arrBooks", listForBooks);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        button_search = (ImageButton) findViewById(R.id.button_search);
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("allBooks",listForBooks); // тут будут передаваться названия всех книг
                intent.putExtra("allText",textOnFile); // тут будут передаваться весь текст
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void textsRead(){
        Resources res = getResources();
        InputStream in_s = res.openRawResource(R.raw.alltext);
        try {
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            textOnFile = new String(b);// тут весь текст
            if (textOnFile.contains("Book")){
                int start = 0;
                int end = 0;
                ArrayList<Character> list_2 = new ArrayList<>();
                arr = textOnFile.toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    if ((arr[i] == '<') && (arr[i + 1] == 'b') && (arr[i + 2] == 's') && (arr[i + 3] == '>')) {
                        start = i + 4;
                    }
                    if ((arr[i] == '<') && (arr[i + 1] == 'b') && (arr[i + 2] == 'e') && (arr[i + 3] == '>')) {
                        end = i;
                        String book = "";
                        for (int j = start; j < end; j++) {
                            list_2.add(arr[j]);
                            book = book + arr[j];
                        }
                        listForBooks.add(book);
                        list_2.add('\n');
                    }
                }
                /*for (int k = 0 ; k < listForBooks.size(); k++){
                    Log.d(LOG_TAG, listForBooks.get(k));
                }*/
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}
