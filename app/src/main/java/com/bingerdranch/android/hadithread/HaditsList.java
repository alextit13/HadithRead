package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HaditsList extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private String text;
    private ArrayList <String> numberOfHariths;
    private ArrayList<String> engHad;
    private ArrayList<String> arabHad;
    private String ATTRIBUTE_NAME_TEXT = "texts";
    private String ATTRIBUTE_NAME_TEXT2 = "texts2";
    private ListView listHaddis;
    private ArrayList<String> listWithSelectTextHadith;
    private char[] arr;

    String arab;
    String urdu;
    String eng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hadits_list);
        engHad = new ArrayList<>();
        arabHad = new ArrayList<>();
        Intent intent = getIntent();
        listWithSelectTextHadith = new ArrayList<>();
        text = intent.getStringExtra("text");
        numberOfHariths = intent.getStringArrayListExtra("hadiths");
        completeHadisMeth();
        listHaddis = (ListView)findViewById(R.id.listHaddis);
        //Log.d(LOG_TAG, numberOfHariths.size() + "");
        ArrayList<Map<String, Object>> data = new ArrayList<>(arabHad.size());
        Map<String, Object> m;

        for (int i= 0; i<arabHad.size();i++){
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, i+1 + " - " + engHad.get(i) + "...");
            m.put(ATTRIBUTE_NAME_TEXT2, i+1 + " - " + arabHad.get(i) + "...");
            data.add(m);
        }

        String [] from = {ATTRIBUTE_NAME_TEXT,ATTRIBUTE_NAME_TEXT2};
        int [] to = {R.id.eng, R.id.arab};
        SimpleAdapter sAdapter = new SimpleAdapter(HaditsList.this, data, R.layout.item_haddis,from,to);
        listHaddis.setAdapter(sAdapter);
        listHaddis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openReader(position);

                Intent intent = new Intent(HaditsList.this,TextActivity.class);
                intent.putExtra("text", listWithSelectTextHadith.get(position));
                startActivity(intent);
            }
        });
    }

    private void openReader(int pos) {
        int start = text.indexOf("Title_")+1;
        int end = text.indexOf("End_title")+1;
        //Log.d(LOG_TAG,start+":"+end);
        String textFromTom = text.substring(start,end);


        char [] arr1 = textFromTom.toCharArray();
        String s = "";
        for (int m = 0; m<arr1.length;m++){
            if (arr1[m]=='#'){
                s = textFromTom.substring(m,textFromTom.indexOf("<hade>",m));
                //m = textFromTom.indexOf("<hade>");
                listWithSelectTextHadith.add(s);
                Log.d(LOG_TAG,s);
            }
        }
    }

    public void completeHadisMeth(){
        //Log.d(LOG_TAG, text);
        text = text.replaceAll("<arts>", "`");
        text = text.replaceAll("<ents>", "~");
        char[]arr = text.toCharArray();
        for (int i = 0; i<arr.length;i++){
            if (arr[i]=='`'){
                String s = "";
                int j= 0;
                while (j<30){
                    s = s + arr[i+1];
                    j++;
                    i++;
                }
                arabHad.add(s);
            }
            if (arr[i]=='~'){
                String s = "";
                int j= 0;
                while (j<30){
                    s = s + arr[i+1];
                    j++;
                    i++;
                }
                engHad.add(s);
            }
        }
    }
}
