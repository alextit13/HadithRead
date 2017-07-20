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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MyLogs";
    private String allText;

    private ArrayList<String> books; // сюда будут сливаться все книги
    private ArrayList<String> titles; // сюда будут сливаться все титлы

    private Spinner bookSpinner; // тут будут хранится все книги из books
    private ArrayAdapter<String> adapterSpinnerBooks;

    private Spinner titleSpinner; // тут будут хранится все титлы из titles
    private ArrayAdapter <String> titleSpinnerAdapter;
    private ArrayList <String> spinnerList;
    private EditText word; // сюда будем вводить наше слово

    private ListView listView; // сюда будет выводится результат поиска
    private Button buttonGo; // кнопка поиска

    private ArrayList<String> list; // сюда будет выводится результат поиска
    private ArrayAdapter<String> adapter; // адаптер для листа куда будут выводится результаты поиска

    private String allTextFromSelectBook = "";
    private char [] arr;

    private HashMap<Integer, Integer> map;

    private String needBook; // есь текст из выбранной книги

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        word = (EditText) findViewById(R.id.enterWord);
        generateAllText();
        generateBookList();
        titles = new ArrayList<>();
        titleSpinner = (Spinner) findViewById(R.id.title_spinner);
        list = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listViewResultSearch);
        map = new HashMap<>();


        spinnerList = new ArrayList<>();

        bookSpinner = (Spinner) findViewById(R.id.book_spinner);
        bookSpinner.setPrompt("Book");
        bookSpinner.setSelected(true);
        bookSpinner.setSelection(0);
        adapterSpinnerBooks = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_spinner_item,books);
        bookSpinner.setAdapter(adapterSpinnerBooks);

        bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editTitleSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonGo = (Button) findViewById(R.id.btnSearch);
        buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,ReadFromSearch.class);
                intent.putExtra("item_word",map.get(position));
                intent.putExtra("allText",allText);
                startActivity(intent);
            }
        });
    }

    private void editTitleSpinner() {
        // тут будут выводится список тайтлов когда пользователь выбрал нужную ему книгу для поиска
        titleSpinnerAdapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,spinnerList);
        if (!bookSpinner.getSelectedItem().toString().equals("")){
            needBook = bookSpinner.getSelectedItem().toString();
            allTextFromSelectBook = allText.substring(allText.indexOf("<bs>" + needBook + "<be>"),
                    allText.indexOf("Title_end_title",allText.indexOf("<bs>" + needBook + "<be>")));
            generateTitleList();
            titleSpinner.setAdapter(titleSpinnerAdapter);
        }else if(bookSpinner.getSelectedItem().toString().equals("")){
            allTextFromSelectBook = allText;
            spinnerList.clear();
            titleSpinner.setAdapter(titleSpinnerAdapter);
        }
    }

    public void generateTitleList(){
        spinnerList.clear();
        ArrayList<Character> list_2 = new ArrayList<>();
        int start = 0;
        int end = 0;
        arr = allTextFromSelectBook.toCharArray();
        Log.d(LOG_TAG,arr.length+"");
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
                spinnerList.add(book);// тут храняться все титлы из выбранной нами книги
                Log.d(LOG_TAG,book);
                list_2.add('\n');
            }
        }
        spinnerList.add(0,"");
    }

    private void search() {
        // тут будет организован поиск
        String wordInWordField = word.getText().toString();
        if (word.getText().toString().equals("")){
            Toast.makeText(SearchActivity.this, "Enter a word", Toast.LENGTH_SHORT).show();
        }else if (!bookSpinner.getSelectedItem().toString().equals("")){
            Log.d(LOG_TAG,bookSpinner.getSelectedItem().toString());
                if (!titleSpinner.getSelectedItem().toString().equals("")){
                    Log.d(LOG_TAG,titleSpinner.getSelectedItem().toString());
                    String textFromSelectItem = "";
                    int fromIntex = allTextFromSelectBook.indexOf(titleSpinner.getSelectedItem().toString());
                    int toIndex = allTextFromSelectBook.indexOf("Title_",fromIntex);
                    textFromSelectItem = allTextFromSelectBook.substring(fromIntex,toIndex);
                    Log.d(LOG_TAG,"textFromSelectItem = " + textFromSelectItem);

                    list.clear();
                    String newString = textFromSelectItem.replaceAll(wordInWordField,"*");
                    char [] arr = newString.toCharArray();
                    int num = 0;
                    for (int i = 0 ; i<arr.length; i++){
                        if (arr[i]=='*'){
                            num++;
                            list.add(wordInWordField);
                            map.put(num,i);
                        }
                    }
                    list.add("");
                    refreshSearchResult(list);
                }else if (titleSpinner.getSelectedItem().toString().equals("")){
                    Log.d(LOG_TAG,titleSpinner.getSelectedItem().toString());
                    String textFromSelectItem = "";
                    int fromIntex = allText.indexOf(titleSpinner.getSelectedItem().toString());
                    int toIndex = allText.indexOf("Title_",fromIntex);
                    textFromSelectItem = allText.substring(fromIntex,toIndex);
                    Log.d(LOG_TAG,"textFromSelectItem = " + textFromSelectItem);

                    list.clear();
                    String newString = textFromSelectItem.replaceAll(wordInWordField,"*");
                    char [] arr = newString.toCharArray();
                    int num = 0;
                    for (int i = 0 ; i<arr.length; i++){
                        if (arr[i]=='*'){
                            num++;
                            list.add(wordInWordField);
                            map.put(num,i);
                        }
                    }
                    list.add("");
                    refreshSearchResult(list);
                }
                if (allTextFromSelectBook.contains(wordInWordField)){
                    list.clear();
                    String newString = allTextFromSelectBook.replaceAll(wordInWordField,"*");
                    char [] arr = newString.toCharArray();
                    int num = 0;
                    for (int i = 0 ; i<arr.length; i++){
                        if (arr[i]=='*'){
                            num++;
                            list.add(wordInWordField);
                            map.put(num,i);
                        }
                    }
                    list.add("");
                    refreshSearchResult(list);
                }else{
                    Toast.makeText(SearchActivity.this, "Thit book not have entered word", Toast.LENGTH_SHORT).show();
                }
            }else{
            if (bookSpinner.getSelectedItem().toString().equals("")){                                //поиск по всему тексту
                if (allText.contains(wordInWordField)){
                    list.clear();
                    String newString = allText.replaceAll(wordInWordField,"*");
                    char [] arr = newString.toCharArray();
                    int num = 0;
                    for (int i = 0 ; i<arr.length; i++){
                        if (arr[i]=='*'){
                            num++;
                            list.add(wordInWordField);
                            map.put(num,i);
                        }
                    }
                    list.add("");
                    refreshSearchResult(list);
                }else{
                    Toast.makeText(SearchActivity.this, "Text not have entered word", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void refreshSearchResult(ArrayList<String>list){
        //тут обновляются результаты поиска
        adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);
    }

    private void generateAllText() {
        Intent intent = getIntent();
        allText = intent.getStringExtra("allText");
    }

    private void generateBookList() {
        Intent intent = getIntent();
        books = intent.getStringArrayListExtra("allBooks");
        books.add(0,"");
    }
}
