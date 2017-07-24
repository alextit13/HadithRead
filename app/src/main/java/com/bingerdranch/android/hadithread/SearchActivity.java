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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends Activity {
    private static final String LOG_TAG = "MyLogs";
    private String allText;

    private String ATTRIBUTE_NAME_RB = "checked";
    private String ATTRIBUTE_NAME_TEXT = "text";
    private ListView listForradioBitton; // лист с радиобаттонами

    private ArrayList<String> listForBook; // сюда будут помещаться все титлы из книги
    private ArrayList<String> book_list;

    private ArrayList<Boolean> listRB; // тру или фолс

    private EditText enterWord; // сюда мы вводим слово
    private Button btnSearch; // кнопка вывода результата

    private ArrayList<String> listWithRadioButton; // тут будут все радиобаттоны с книгами

    private RadioButton rb;
    private TextView tv;

    ArrayList <String> listWithBookNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);

        listRB = new ArrayList<>();
        for (int i = 0; i<10;i++){
            listRB.add(false);
        }

        listWithBookNames= new ArrayList<>();

        rb = (RadioButton) findViewById(R.id.rb);
        tv = (TextView) findViewById(R.id.tv);

        Intent intent = getIntent();
        allText = intent.getStringExtra("allText");

        book_list = new ArrayList<>();
        listWithRadioButton = new ArrayList<>();

        completeListWithRadioButton(allText);
        completeResults();

    }

    private ArrayList<String> completeListWithRadioButton(String allText) {
        listForBook = new ArrayList<>();
        listForBook.clear();
        ArrayList<Character> list_2 = new ArrayList<>();
        int start = 0;
        int end = 0;
        String atr = allText.replaceAll("Title_end_title", "*");
        char[] arr = atr.toCharArray();

        for (int i = 0; i < arr.length; i++) { // тут забираем весь текст разделенный на книги
            if ((arr[i] == '<') && (arr[i + 1] == 'b') && (arr[i + 2] == 's') && (arr[i + 3] == '>')) {
                start = i + 4;
            }
            if (arr[i] == '*') {
                end = i;
                String book = "";
                for (int j = start; j < end; j++) {
                    list_2.add(arr[j]);
                    book = book + arr[j];
                }
                listForBook.add(book);// тут храняться все книги c текстом
            }
        }
        for (int k = 0; k<listForBook.size();k++){
            String s = listForBook.get(k).substring(0,20);
            listWithBookNames.add(s);
        }
        return listWithBookNames;
    }
    private void completeResults(){
        RadioButton radioButton = new RadioButton(SearchActivity.this);
        ArrayList<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m;
        for (int i= 0; i<listForBook.size();i++){
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_RB, radioButton);
            m.put(ATTRIBUTE_NAME_TEXT,listWithBookNames.get(i));
            data.add(m);
        }
        String [] from = {ATTRIBUTE_NAME_RB, ATTRIBUTE_NAME_TEXT};
        int [] to = {R.id.rb, R.id.tv};
        SimpleAdapter sAdapter = new SimpleAdapter(SearchActivity.this, data, R.layout.search_result,from,to);
        listForradioBitton = (ListView)findViewById(R.id.list_for_titles);
        listForradioBitton.setAdapter(sAdapter);
    }
}
