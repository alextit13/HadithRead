package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
    private ArrayList<String> listForTitlesArabs; // тексты титлов на арабском
    private ListView list_for_titles; // сюда будем выводить перечень разделов из выбранной книги
    private String positionBook;
    private String ATTRIBUTE_NAME_TEXT = "text";
    private String ATTRIBUTE_NAME_TEXT2 = "text2";
    private char [] arr;
    private ArrayList <String> numbersHadiths; // тут номера всех хадитов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_parth);
        final Intent intent = getIntent();
        textOnFile = intent.getStringExtra("text");// тут текст из выбранной нами книги
        listForBooks = new ArrayList<>();
        listForBooks = intent.getStringArrayListExtra("arrBooks");
        position = intent.getIntExtra("position",0);
        list_for_titles = (ListView) findViewById(R.id.list_for_titles);
        listForTitles = new ArrayList<String>();
        listForTitlesArabs = new ArrayList<String>();
        generateTitlesList();
        numbersHadiths = new ArrayList<>();
        ArrayList<Map<String, Object>> data = new ArrayList<>(listForTitles.size());
        Map<String, Object> m;

        for (int i= 0; i<listForTitles.size();i++){
            m = new HashMap<String, Object>();
            int j = i + 1;
            m.put(ATTRIBUTE_NAME_TEXT2, i + 1 + ". " +  listForTitles.get(i));
            m.put(ATTRIBUTE_NAME_TEXT, i + 1 + ". " + listForTitlesArabs.get(i));
            data.add(m);
        }
        String [] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_TEXT2};
        int [] to = {R.id.text_item_one, R.id.text_item_two};
        SimpleAdapter sAdapter = new SimpleAdapter(ParthActivity.this, data, R.layout.item_book,from,to);
        list_for_titles.setAdapter(sAdapter);
        generateNamesHadiths();
        list_for_titles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(ParthActivity.this, HaditsList.class);
                intent1.putExtra("text",generateTextFromItemSelected(position));
                intent1.putExtra("hadiths",numbersHadiths);
                startActivity(intent1);
                //Log.d(LOG_TAG, "position = " + position);
            }
        });
    }
    public String generateTextFromItemSelected(int position){
        String text = textOnFile.substring(textOnFile.indexOf(listForTitles.get(position)),
                textOnFile.indexOf("Title_",textOnFile.indexOf(listForTitles.get(position))));
        return text;
    }
    public void generateTitlesList(){
        //Log.d(LOG_TAG, textOnFile);
        if (textOnFile.contains("Book")){
            int start = 0;
            int end = 0;
            ArrayList<Character> list_2 = new ArrayList<>();
            arr = new char[textOnFile.length()];
            arr = textOnFile.toCharArray();
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

            for (int i = 0; i < arr.length; i++) { //тут забираем названия разделов - титлов arab
                if ((arr[i] == '<') && (arr[i + 1] == 'a') && (arr[i + 2] == 'r') && (arr[i + 3] == 's')&& (arr[i + 4] == '>')) {
                    start = i + 5;
                }
                if ((arr[i] == '<') && (arr[i + 1] == 'a') && (arr[i + 2] == 'r') && (arr[i + 3] == 'e')&& (arr[i + 4] == '>')) {
                    end = i;
                    String book = "";
                    for (int j = start; j < end; j++) {
                        list_2.add(arr[j]);
                        book = book + arr[j];
                    }
                    listForTitlesArabs.add(book);// тут храняться все титлы из выбранной нами книги на арабском
                    list_2.add('\n');
                }
            }
        }
    }
    public void generateNamesHadiths(){
        String s = "";
        textOnFile = textOnFile.replaceAll("<hads>", "#");
        //Log.d(LOG_TAG, textOnFile);
        char[]arr = textOnFile.toCharArray();
        for (int i = 0; i<textOnFile.length();i++){
            if (arr[i]=='#'){
                s = textOnFile.substring(i+1,textOnFile.indexOf("<",i));
                numbersHadiths.add(s);
            }
        }
    } // тут будем делать перечень хадитов
}
