package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HaditsList extends Activity {

    private String text;
    private ArrayList <String> numberOfHariths;
    private ArrayList<String> engHad;
    private ArrayList<String> arabHad;
    private String ATTRIBUTE_NAME_TEXT = "text";
    private String ATTRIBUTE_NAME_TEXT2 = "text2";
    private ListView listHaddis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadits_list);
        engHad = new ArrayList<>();
        arabHad = new ArrayList<>();
        Intent intent = getIntent();
        text = intent.getStringExtra("text");
        numberOfHariths = intent.getStringArrayListExtra("hadiths");
        completeHadisMeth();
        listHaddis = (ListView)findViewById(R.id.listHaddis);
        ArrayList<Map<String, Object>> data = new ArrayList<>(numberOfHariths.size());
        Map<String, Object> m;

        for (int i= 0; i<numberOfHariths.size();i++){
            m = new HashMap<String, Object>();
            int j = i + 1;
            m.put(ATTRIBUTE_NAME_TEXT2, i + 1 + ". " +  numberOfHariths.get(i) + arabHad.get(i));
            m.put(ATTRIBUTE_NAME_TEXT, engHad.get(i));
            data.add(m);
        }

        String [] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_TEXT2};
        int [] to = {R.id.arab, R.id.eng};
        SimpleAdapter sAdapter = new SimpleAdapter(HaditsList.this, data, R.layout.item_haddis,from,to);
        listHaddis.setAdapter(sAdapter);
    }

    public void completeHadisMeth(){
        text = text.replaceAll("<arts>", "`");
        text = text.replaceAll("<ents>", "~");
        char[]arr = text.toCharArray();
        for (int i = 0; i<arr.length;i++){
            if (arr[i]=='`'){
                arabHad.add(text.substring(i+1,i+30));
            }
            if (arr[i]=='~'){
                engHad.add(text.substring(i+1,i+30));
            }
        }
    }
}
