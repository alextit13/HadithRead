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

    private ArrayList <String> listForBook; // сюда будут помещаться все книги из документа

    private Spinner book_spinner; // спиннер с книгами
    private ArrayList <String> book_list; // лист для книжного спиннера
    private ArrayAdapter <String> book_adapter; // адаптер для спиннера с книгами

    private Spinner title_spinner; // спиннет с титлами
    private ArrayList <String> title_list; // лист для титл спиннера
    private ArrayList <String> title_list_names; // лист для титл спиннера
    private ArrayAdapter <String> title_adapter; // адаптер для спиннера с титлами

    private EditText enterWord; // сюда мы вводим слово
    private ListView listViewResultSearch; // лист куда мы выводим результаты
    private ArrayList <String> search_result_list; // лист для вывода результатаов
    private ArrayAdapter <String> search_adapter_list; // адаптер для списка результатов поиска

    private Button btnSearch; // кнопка вывода результата



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        allText = intent.getStringExtra("allText");

        listForBook = new ArrayList<>();
        title_list_names = new ArrayList<>();

        book_spinner = (Spinner) findViewById(R.id.book_spinner);
        book_list = new ArrayList<>();


        title_spinner = (Spinner) findViewById(R.id.title_spinner);
        title_list = new ArrayList<>();

        enterWord = (EditText) findViewById(R.id.enterWord);

        listViewResultSearch = (ListView) findViewById(R.id.listViewResultSearch);
        search_result_list = new ArrayList<>();

        btnSearch = (Button) findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_result_list.clear();
                search();
            }
        });

        createBookList(allText);

        book_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (book_spinner.getSelectedItemPosition()!=0){
                    createTitleNameList();
                }else{
                    cleanTitleList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void cleanTitleList() {
        title_list_names.clear();
        title_adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,title_list_names);
        title_spinner.setAdapter(title_adapter);
    } // чистит список титлов когда мы вновь выбираем пустую книгу

    public void createBookList(String allText){// этот метод будет принимать весь текст и делать из него список книг
        listForBook.clear();
        ArrayList<Character> list_2 = new ArrayList<>();
        int start = 0;
        int end = 0;
        String atr = allText.replaceAll("Title_end_title", "*");
        char [] arr = atr.toCharArray();

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
        createBookNamesList();
    } // тут создается список всех книг с полным их текстом

    public void createBookNamesList(){
        for (int i=0;i<listForBook.size();i++){
            String book_name = listForBook.get(i).substring(0,listForBook.get(i).indexOf("<be>",0));
            book_list.add(book_name);
        }
        book_list.add(0,"");
        book_adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,book_list);
        book_spinner.setAdapter(book_adapter);

        // если мы выбрали книгу то будет автоматически подгружаться список титлов в его спиннер

    } // тут создается список книг с названиями

    public void createTitleList(){// этот метод будет принимать весь текст книги и делать из него список титлов
            int k = book_spinner.getSelectedItemPosition()-1;
            String select_book = listForBook.get(k);
            title_list.clear();
            ArrayList<Character> list_2 = new ArrayList<>();
            int start = 0;
            int end = 0;
            String atr = select_book.replaceAll("Title_", "*");
            String atr1 = atr.replaceAll("End_title", "^");
            char [] arr = atr1.toCharArray();
            for (int i = 0; i < arr.length; i++) { // тут забираем весь текст разделенный на книги
                if (arr[i] == '*') {
                    start = i + 1;
                }
                if (arr[i] == '^') {
                    end = i;
                    String title = "";
                    for (int j = start; j < end; j++) {
                        list_2.add(arr[j]);
                        title = title + arr[j];
                    }
                    title_list.add(title);// тут храняться все титлы c текстом
                }
            }
    } // тут создается список всех титов в выбранной книге с полным их текстом

    public void createTitleNameList(){
        createTitleList();
        for (int i=0;i<title_list.size();i++){
            int start =  title_list.get(i).indexOf("<ts>",0);
            int end = title_list.get(i).indexOf("<te>",start);
            String title_name = title_list.get(i).substring(start,end);
            title_name = title_name.replace("<ts>","");
            title_list_names.add(title_name);
        }

        title_list_names.add(0,"");
        title_adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,title_list_names);
        title_spinner.setAdapter(title_adapter);

    } //  тут создается список титлов с названиями

    public void search(){ // этот метод будет включать поиск при нажатии кнопки поиск
        String enter_word = enterWord.getText().toString();
        if (book_spinner.getSelectedItem().toString().equals("")){
            // если нихуя не выбрано
            if (allText.contains(enter_word)){
                String text = allText.replaceAll(enter_word,"#");
                char [] arr = text.toCharArray();
                for (int i=0;i<arr.length;i++){
                    if (arr[i]=='#'){
                        search_result_list.add(enter_word);
                        searchAdapter(search_result_list);
                    }
                }
            }else{
                search_result_list.clear();
                searchAdapter(search_result_list);
                Toast.makeText(this,"Not have this text",Toast.LENGTH_SHORT).show();
            }
        }
        if ((!book_spinner.getSelectedItem().toString().equals(""))&&(title_spinner.getSelectedItem().toString().equals(""))){
            // если книгу выбрали а титл не выбрали
            String text_from_book;
            int index = book_spinner.getSelectedItemPosition()-1;
            text_from_book = listForBook.get(index);
            if (text_from_book.contains(enter_word)){
                String text = text_from_book.replaceAll(enter_word,"#");
                char [] arr = text.toCharArray();
                for (int i=0;i<arr.length;i++){
                    if (arr[i]=='#'){
                        search_result_list.add(enter_word);
                        searchAdapter(search_result_list);
                    }
                }
            }else{
                search_result_list.clear();
                searchAdapter(search_result_list);
                Toast.makeText(this,"This book not have this text",Toast.LENGTH_SHORT).show();
            }
        }
        if ((!book_spinner.getSelectedItem().toString().equals(""))&&(!title_spinner.getSelectedItem().toString().equals(""))){
            // если мы все повыбирали
            String text_from_title;
            int index = title_spinner.getSelectedItemPosition()-1;
            text_from_title = title_list.get(index);
            if (text_from_title.contains(enter_word)){
                String text = text_from_title.replaceAll(enter_word,"#");
                char [] arr = text.toCharArray();
                for (int i=0;i<arr.length;i++){
                    if (arr[i]=='#'){
                        search_result_list.add(enter_word);
                        searchAdapter(search_result_list);
                    }
                }
            }else{
                search_result_list.clear();
                searchAdapter(search_result_list);
                Toast.makeText(this,"This title not have this text",Toast.LENGTH_SHORT).show();
            }
        }
    } // тут организован поиск по нажатию кнопки

    public void searchAdapter(ArrayList <String> list){
        search_adapter_list = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listViewResultSearch.setAdapter(search_adapter_list);
    } // генерирует адаптер для листа результатов поиска
    /*
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

    private String needBook; // есь текст из выбранной книги*/


        /*word = (EditText) findViewById(R.id.enterWord);
        generateAllText();
        generateBookList();
        titles = new ArrayList<>();
        titleSpinner = (Spinner) findViewById(R.id.title_spinner);
        list = new ArrayList<>();
        listView = (ListView)findViewById(R.id.listViewResultSearch);
        map = new HashMap<>();


        spinnerList = new ArrayList<>();

        bookSpinner = (Spinner) findViewById(R.id.book_spinner);
        bookSpinner.setPrompt("Books");
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
    }*/

   /* private void editTitleSpinner() {
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

        if (!word.getText().toString().equals("")){
            String wordText = word.getText().toString();
            String bookText = bookSpinner.getSelectedItem().toString();
            String titleText = titleSpinner.getSelectedItem().toString();
            if ((!bookText.equals(""))&&(!titleText.equals(""))){
                // тут поиск только по титлу
                // ниже проверка есть ли в данном титле введенное слово
                if (allTextFromSelectBook.contains(wordText)){
                    int start = allTextFromSelectBook.indexOf(titleText);
                    int end = allTextFromSelectBook.indexOf("Title_",start);
                    Log.d(LOG_TAG,start+"");
                    Log.d(LOG_TAG,end+"");


                    String textTitle = allTextFromSelectBook.substring(start,end);
                    list.clear();
                    String newString = textTitle.replaceAll(wordText,"*");
                    char [] arr = newString.toCharArray();
                    int num = 0;
                    for (int i = 0 ; i<arr.length; i++){
                        if (arr[i]=='*'){
                            num++;
                            list.add(wordText);
                            map.put(num,i);
                        }
                    }
                    list.add("");
                    refreshSearchResult(list);
                }else{
                    Toast.makeText(SearchActivity.this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
                }
            }
            if (!bookText.equals("")){
                // тут поиск по книге

                // ниже проверка есть ли в данном титле введенное слово
                if (allTextFromSelectBook.contains(wordText)){
                    list.clear();
                    String newString = allTextFromSelectBook.replaceAll(wordText,"*");
                    char [] arr = newString.toCharArray();
                    int num = 0;
                    for (int i = 0 ; i<arr.length; i++){
                        if (arr[i]=='*'){
                            num++;
                            list.add(wordText);
                            map.put(num,i);
                        }
                    }
                    list.add("");
                    refreshSearchResult(list);
                }else{
                    Toast.makeText(SearchActivity.this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(SearchActivity.this, "Введи слово", Toast.LENGTH_SHORT).show();
        }*/
        /*String wordInWordField = word.getText().toString();
        if (word.getText().toString().equals("")){
            Toast.makeText(SearchActivity.this, "Enter a word", Toast.LENGTH_SHORT).show();
        }else if (!bookSpinner.getSelectedItem().toString().equals("")){
                if (!titleSpinner.getSelectedItem().toString().equals("")){
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
                    String textFromSelectItem = "";
                    int fromIntex = allText.indexOf(titleSpinner.getSelectedItem().toString());
                    int toIndex = allText.indexOf("Title_",fromIntex);
                    textFromSelectItem = allText.substring(fromIntex,toIndex);
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
        }*/
   /* }

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
    }*/
}
