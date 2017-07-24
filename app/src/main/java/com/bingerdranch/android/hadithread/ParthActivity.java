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
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/*
* ДАННЫЙ КЛАСС БЕРЕТ ТЕКСТ ВЫБРАННОЙ КНИГИ И ДЕЛАЕТ ИЗ НЕГО ПЕРЕЧЕНЬ
* ТОМОВ, КОТОРЫЕ НАЗЫВАЮТСЯ ТИТЛАМИ. ВСЕ ЭТИ ТИТЛЫ СОДЕРЖАТ В СЕБЕ КАК
* НАЗВАНИЯ ТАК И ВЕСЬ ТЕКСТ ИЗ СЕБЯ
* */
public class ParthActivity extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private ArrayList <String> listForBooks;// тут пришел перечень всех книг
    private String textOnFile = ""; // тут ВЕСЬ текст из ВСЕХ книг
    private int position = 0; // тут пришла позиция, в которую мы тыкнули в первом экране


    private ArrayList<String> listForTitles; // тексты титлов на английском
    private ArrayList<String> listForTitlesArabian; // тексты на арабском
    private ListView list_for_titles; // сюда будем выводить перечень разделов из выбранной книги

    private String ATTRIBUTE_NAME_TEXT = "text";
    private String ATTRIBUTE_NAME_TEXT2 = "text2";

    private char [] arr;

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
        listForTitlesArabian = new ArrayList<>();
        generateTitlesList();

        ArrayList<Map<String, Object>> data = new ArrayList<>(listForTitles.size());
        Map<String, Object> m;

        for (int i= 0; i<listForTitles.size();i++){
            m = new HashMap<String, Object>();
            int j = i + 1;
            m.put(ATTRIBUTE_NAME_TEXT, listForTitlesArabian.get(i)+ " - " + j);
            m.put(ATTRIBUTE_NAME_TEXT2, i + 1 + ". " +  listForTitles.get(i));
            data.add(m);
        }

        String [] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_TEXT2};
        int [] to = {R.id.text_item_one, R.id.text_item_two};
        SimpleAdapter sAdapter = new SimpleAdapter(ParthActivity.this, data, R.layout.item_book,from,to);
        list_for_titles.setAdapter(sAdapter);

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
            for (int i = 0; i < arr.length; i++) { //тут забираем названия разделов - титлов english
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
                    listForTitles.add(book);// тут храняться все титлы из выбранной нами книги на английском
                    list_2.add('\n');
                }
            }

            start = 0;
            end = 0;
            list_2.clear();
            arr = positionBook.toCharArray();
            for (int i = 0; i < arr.length; i++) { //тут забираем названия разделов - титлов arabian
                if ((arr[i] == '<') && (arr[i + 1] == 'a') && (arr[i + 2] == 'r') && (arr[i + 3] == 's') && (arr[i + 4] == '>')) {
                    start = i + 4;
                }
                if ((arr[i] == '<') && (arr[i + 1] == 'a') && (arr[i + 2] == 'r') && (arr[i + 3] == 'e') && (arr[i + 4] == '>')) {
                    end = i;
                    String book = "";
                    for (int j = start; j < end; j++) {
                        list_2.add(arr[j]);
                        book = book + arr[j];
                    }
                    listForTitlesArabian.add(book);// тут храняться все титлы из выбранной нами книги на арабском
                    list_2.add('\n');
                }
            }
            /*for (int k = 0; k<listForTitles.size();k++){
                String txtTitle = listForTitles.get(k);
                txtTitle = k+1 + ". " + txtTitle;
                Log.d(LOG_TAG,txtTitle);
            }*/
        }
    }
}
