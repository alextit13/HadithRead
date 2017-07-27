package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TextActivity extends Activity {


    private ArrayList<String> listLang;

    private TextView text_viewAr;
    private TextView text_viewUr;
    private TextView text_viewEn;

    private ArrayAdapter<String> adapter;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.read_layout);

        listView = (ListView)findViewById(R.id.listView);
        listLang = new ArrayList<>();

        deleteAnyChars();
        collapse();

    }

    public void deleteAnyChars(){
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");

        String textAr;
        String textUr;
        String textEn;

        textAr = text.substring(text.indexOf("#"),text.lastIndexOf("<arte>"));
        textUr = text.substring(text.indexOf("<urduts>"),text.lastIndexOf("<urdute>"));
        textEn = text.substring(text.indexOf("~"),text.lastIndexOf("<ente>"));

        text = text.replaceAll("<arte>","\n");
        text = text.replaceAll("<urduts>","\n");
        text = text.replaceAll("<urdute>","\n");
        text = text.replaceAll("~","\n");
        text = text.replaceAll("<ente>","");

        listLang.add(textAr);
        listLang.add(textUr);
        listLang.add(textEn);
    }
    public void collapse(){
        final String[]arr = new String[listLang.size()];
        for (int i = 0; i<arr.length;i++){
            arr[i] = listLang.get(i);
        }

        adapter = new ArrayAdapter<String>(TextActivity.this,R.layout.item_reader,arr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (arr[position].equals("Expand")){
                    arr[position]=listLang.get(position);
                }else{
                    arr[position] = "Expand";
                    view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
