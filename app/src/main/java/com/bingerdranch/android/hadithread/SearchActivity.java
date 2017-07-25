package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.IdRes;
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
import android.widget.RadioGroup;
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
    private String ALL_TEXT = "";
    private EditText editTextForSearch;
    private ArrayList<String> listForBook; // сюда будут помещаться все книги с текстом
    private ArrayList<String> book_list;
    private ArrayList<String> listWithRadioButton; // тут будут все радиобаттоны с книгами
    ArrayList <String> listWithBookNames;
    private RadioGroup radioGroup;
    private String textForSearch = "";
    private Button search;
    private ArrayList<View> buttons;
    private ArrayList <RadioButton> radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);
        listWithBookNames= new ArrayList<>();
        Intent intent = getIntent();
        allText = intent.getStringExtra("allText");
        Log.d(LOG_TAG,allText.length()+"");
        book_list = new ArrayList<>();
        listWithRadioButton = new ArrayList<>();
        completeListWithRadioButton(allText);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        connectWithRadioButtons();
        editTextForSearch = (EditText)findViewById(R.id.editTextForSearch);
        search = (Button) findViewById(R.id.btnSearch);
        textForSearch = "";
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
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
            String str = listForBook.get(k);
            String s = str.substring(0,str.indexOf("<be>"));
            listWithBookNames.add(s);
        }
        for (int k = 0; k<listForBook.size();k++){
            ALL_TEXT = ALL_TEXT + listForBook.get(k);
        }
        listWithBookNames.add(0,"Search in all books");
        return listWithBookNames;
    }

    public void connectWithRadioButtons(){
        int numRadioButton = radioGroup.getChildCount();
        buttons =  radioGroup.getTouchables();
        radioButtons = new ArrayList<>();

        for (int i = 0; i<buttons.size();i++){
            //Log.d(LOG_TAG,buttons.get(i).toString());
            buttons.get(i).getId();
            RadioButton rb = (RadioButton)findViewById(buttons.get(i).getId());
            radioButtons.add(rb);
        }

        for (int i = 0; i<radioButtons.size();i++){
            radioButtons.get(i).setText(listWithBookNames.get(i));
        }
    }

    public void search(){
        String textForSearch = "";
        int idSelectRadioButton = radioGroup.getCheckedRadioButtonId();
        //Log.d(LOG_TAG,idSelectRadioButton + "");
        switch (idSelectRadioButton){
            case 2131492964:
                textForSearch = ALL_TEXT;
                break;
            case 2131492965:
                textForSearch = listForBook.get(0);
                break;
            case 2131492966:
                textForSearch = listForBook.get(1);
                break;
            case 2131492967:
                textForSearch = listForBook.get(2);
                break;
            case 2131492968:
                textForSearch = listForBook.get(3);
                break;
            case 2131492969:
                textForSearch = listForBook.get(4);
                break;
            case 2131492970:
                textForSearch = listForBook.get(5);
                break;
            case 2131492971:
                textForSearch = listForBook.get(6);
                break;
            case 2131492972:
                textForSearch = listForBook.get(7);
                break;
            case 2131492973:
                textForSearch = listForBook.get(8);
                break;
            case 2131492974:
                textForSearch = listForBook.get(9);
                break;
        }
        //Log.d(LOG_TAG,textForSearch);
        if (editTextForSearch.getText().toString().equals("")){
            Toast.makeText(SearchActivity.this,"Enter a word (s) or number of title",Toast.LENGTH_SHORT).show();
        }else{
            String text = editTextForSearch.getText().toString();

            if (text.contains("0")||text.contains("1")||text.contains("2")||text.contains("3")||
                    text.contains("4")||text.contains("5")||text.contains("6")||text.contains("7")||
                    text.contains("8")||text.contains("9")){
                Log.d(LOG_TAG,"Ищем по номеру хаддита");
                if (radioButtons.get(0).isChecked()){
                    Toast.makeText(SearchActivity.this,"Select one book only",Toast.LENGTH_SHORT).show();
                }else{

                }
            }else{
                if (textForSearch.contains(text)){
                    textForSearch = textForSearch.replaceAll(text,"*");
                    char[]arr;
                    ArrayList <Integer> ch = new ArrayList<>();
                    arr = textForSearch.toCharArray();
                    for (int i = 0; i<arr.length;i++){
                        if (arr[i]=='*'){
                            ch.add(i);
                        }
                    }
                    Toast.makeText(SearchActivity.this,"Finds: " + ch.size() ,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SearchActivity.this,"Nothing finds",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
