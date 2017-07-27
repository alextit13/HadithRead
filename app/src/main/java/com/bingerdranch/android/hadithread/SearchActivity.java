package com.bingerdranch.android.hadithread;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class SearchActivity extends Activity {
    private static final String LOG_TAG = "MyLogs";

    private EditText word;
    private RadioGroup radioGroup;
    private Button btnSearch;
    private CheckBox checkBox;
    private ArrayList<Integer>listWithSearchPositions;
    private  String myText = null;

    String [] text = {"Sunnah Ibn Majah","Comprehensive modification","Golden feminine", "Sunnah Abu Dawood",
            "Right Muslim","Sahih Bukhari","Sunni","Mushrikah Sharif","Motta Imam Boss","Shamal Tirmizi"};
    String [] text2 = {"مسند احمد", "سنن دارمی", "مشکوۃ شریف","موطا امام مالک","شمائل ترمذی","سنن ابن ماجہ",
            "جامع ترمذی","سنن نسائی","سنن ابوداؤد","صحیح مسلم"};
    private ArrayList<String> namesBooks; // названия всех книг

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);

        word = (EditText)findViewById(R.id.editTextForSearch);
        radioGroup = (RadioGroup)findViewById(R.id.radio_group);
        namesBooks = new ArrayList<>();

        listWithSearchPositions = new ArrayList<>();
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    radioGroup.clearCheck();
                    radioGroup.setVisibility(View.GONE);
                    //Toast.makeText(SearchActivity.this,"test",Toast.LENGTH_SHORT).show();
                }else{
                    radioGroup.setVisibility(View.VISIBLE);
                }
            }
        });

        createRadioButtons();//тут запоняем радиобаттонами радиогрупп
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myText = null;
                listWithSearchPositions.clear();
                search();//метод поиска
            }
        });

    }


    private void createRadioButtons() {
        ArrayList<String>list = new ArrayList<>();//тут перечень всех рессурсов в папке raw
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            if(fields[count].getName().contains("book")){
                list.add(fields[count].getName());
            }
        }
        for (int i=0; i < list.size(); i++){
            if (!list.get(i).equals("")){
                namesBooks.add(text[i]);
            }
        }
        for (int i=0; i < list.size(); i++){
            RadioButton rb = new RadioButton(this);
            rb.setId(View.generateViewId());
            rb.setText(text2[i] + " | " + text[i]);
            rb.setTextColor(Color.YELLOW);
            radioGroup.addView(rb);

            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(false);
                }
            });
        }
    }//заполняем радиобаттонами радиогрупп

    @SuppressLint("ResourceType")
    private void search() {
        if ((!checkBox.isChecked())&&(radioGroup.getCheckedRadioButtonId()==-1)){
            Toast.makeText(SearchActivity.this,"Select one or all books",Toast.LENGTH_SHORT).show();
        }else if ((!checkBox.isChecked()&&(radioGroup.getCheckedRadioButtonId()!=-1))){
            String s = null;
            int idRes = radioGroup.getCheckedRadioButtonId();// идентификатор выбранного чекбокса
            String nameBook = ((RadioButton) radioGroup.findViewById(idRes)).getText().toString();
            Field[] ID_Fields = R.raw.class.getFields();
            int[] resArray = new int[ID_Fields.length];
            for (int i = 0; i < ID_Fields.length; i++) {
                try {
                    resArray[i] = ID_Fields[i].getInt(null);
                    //Log.d(LOG_TAG,resArray[i]+""); // тут все индексы рессурсов
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            Resources res = getResources();
            //Log.d(LOG_TAG, resArray[idRes] + "");
            InputStream in_s = res.openRawResource(resArray[idRes]);
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = in_s.read();
                while (i != -1) {
                    baos.write(i);
                    i = in_s.read();
                }
                myText = baos.toString();
                //Log.d(LOG_TAG, myText);
                s = myText;
                in_s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            searchInOneBook(s);
        }else if (checkBox.isChecked()){
            Toast.makeText(SearchActivity.this,"Search in all books",Toast.LENGTH_SHORT).show();
            searchInAllBooks();
        }
    }//тут поиск

    private void searchInOneBook(String text) {
        String enteredWord = word.getText().toString();
        if (word.getText().toString().equals("")){
            Toast.makeText(SearchActivity.this,"Enter word or number of Hadith",Toast.LENGTH_SHORT).show();
        }else if (text.contains(enteredWord)){
            int index = text.indexOf(enteredWord);
            //Log.d(LOG_TAG,text);
            while (index >= 0) {
                Log.d(LOG_TAG,index+"");
                listWithSearchPositions.add(index);
                index = text.indexOf(enteredWord, index + 1);
            }
            if (text.contains(enteredWord)){
                Intent intent = new Intent(SearchActivity.this,SearchListActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("listIndex",listWithSearchPositions);
                startActivity(intent);
            }else{
                Toast.makeText(SearchActivity.this,"Nothing finds!",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(SearchActivity.this,"Selected book is not contain entered word",Toast.LENGTH_SHORT).show();
        }
    }

    private void searchInAllBooks() {

    } // поиск по всем книгам
}
