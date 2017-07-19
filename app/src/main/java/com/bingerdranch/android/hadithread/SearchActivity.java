package com.bingerdranch.android.hadithread;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private String allText;

    private ArrayList<String> books; // сюда будут сливаться все книги
    private ArrayList<String> titles; // сюда будут сливаться все титлы

    private Spinner bookSpinner; // тут будут хранится все книги из books
    private ArrayAdapter<String> adapterSpinnerBooks;

    private Spinner titleSpinner; // тут будут хранится все титлы из itles
    private EditText word; // сюда будем вводить наше слово

    private ListView listView; // сюда будет выводится результат поиска
    private Button buttonGo; // кнопка поиска

    private ArrayList<String> list;
    private ArrayAdapter<String> adapter; // адаптер для листа куда будут выводится результаты поиска

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        generateAllText();
        generateBookList();


        bookSpinner = (Spinner) findViewById(R.id.book_spinner);
        bookSpinner.setPrompt("Book");
        bookSpinner.setSelected(true);
        bookSpinner.setSelection(0);
        adapterSpinnerBooks = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_spinner_item,books);
        bookSpinner.setAdapter(adapterSpinnerBooks);
    }

    private void generateAllText() {
        Intent intent = getIntent();
        allText = intent.getStringExtra("allText");
    }

    private void generateBookList() {
        Intent intent = getIntent();
        books = intent.getStringArrayListExtra("allBooks");
    }
}
