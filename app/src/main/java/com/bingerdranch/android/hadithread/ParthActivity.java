package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ParthActivity extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private ArrayList <String> listForBooks;// тут пришел перечень всех книг
    private String textOnFile = ""; // тут ВЕСЬ текст из ВСЕХ книг
    private int position = 0; // тут пришла позиция, в которую мы тыкнули в первом экране


    private ArrayList<String> listForTitles;
    private ListView list_for_titles; // сюда будем выводить перечень разделов из выбранной книги
    private ArrayAdapter <String> adapter;

    private char [] arr;
    private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_parth);
        final Intent intent = getIntent();
        textOnFile = intent.getStringExtra("text");
        listForBooks = new ArrayList<>();
        listForBooks = intent.getStringArrayListExtra("arrBooks");
        position = intent.getIntExtra("position",0);
        list_for_titles = (ListView) findViewById(R.id.list_for_titles);
        listForTitles = new ArrayList<String>();
        generateTitlesList();

        adapter = new ArrayAdapter<>(ParthActivity.this,R.layout.book_item,listForTitles);
        list_for_titles.setAdapter(adapter);

        list_for_titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(ParthActivity.this, TextActivity.class);
                intent1.putExtra("text",generateTextFromItemSelected(position));
                startActivity(intent1);
                Log.d(LOG_TAG, "position = " + position);
            }
        });
    }

    public String generateTextFromItemSelected(int position){
        String text = textOnFile.substring(textOnFile.indexOf(listForTitles.get(position)),
                textOnFile.indexOf("Title_",textOnFile.indexOf(listForTitles.get(position))));
        return text;
    }

    public void generateTitlesList(){
        String positionBook = textOnFile.substring(textOnFile.indexOf(listForBooks.get(position))
                ,textOnFile.indexOf("Book_",textOnFile.indexOf(listForBooks.get(position))));      // тут весь текст из выбранной книги
        //Log.d(LOG_TAG, positionBook);
        if (textOnFile.contains("Book")){
            int start = 0;
            int end = 0;
            ArrayList<Character> list_2 = new ArrayList<>();
            arr = positionBook.toCharArray();
            for (int i = 0; i < arr.length; i++) { //тут забираем названия разделов - титлов
                if ((arr[i] == '<') && (arr[i + 1] == 't') && (arr[i + 2] == 's') && (arr[i + 3] == '>')) {
                    start = i + 4;
                }
                if ((arr[i] == '<') && (arr[i + 1] == 't') && (arr[i + 2] == 'e') && (arr[i + 3] == '>')) {
                    end = i;
                    String book = "";
                    for (int j = start; j < end; j++) {
                        list_2.add(arr[j]);
                        book = book + arr[j];
                    }
                    listForTitles.add(book);// тут храняться все титлы из выбранной нами книги
                    list_2.add('\n');
                }
                //Log.d(LOG_TAG, "position = " + position);
                //text = positionBook.substring()
            }
        }
    }
}
