package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchListActivity extends Activity {

    private static final String LOG_TAG = "MyLogs";
    private ListView search_result;

    private String text;
    private ArrayList<Integer> indexes;
    ArrayAdapter <String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        indexes = new ArrayList<>();
        search_result = (ListView)findViewById(R.id.search_result);

        final Intent intent = getIntent();
        text = intent.getStringExtra("text");
        indexes = intent.getIntegerArrayListExtra("listIndex");

        ArrayList<String> list = new ArrayList<>();
        list.clear();
        for (int i=0;i<indexes.size();i++){
            list.add("..." + text.substring( indexes.get(i)-5,indexes.get(i)+5)+ "...");
        }

        adapter = new ArrayAdapter<String>(SearchListActivity.this,android.R.layout.simple_list_item_1,list);
        search_result.setAdapter(adapter);

        search_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent(SearchListActivity.this, ReadFromSearch.class);
                String textForRead = null;
                int start = 0;
                int end = 0;

                Log.d(LOG_TAG,indexes.get(position)+"");
                Log.d(LOG_TAG,text.indexOf("<hads>")+"");
                Log.d(LOG_TAG,text.indexOf("<hade>")+"");

                start = indexes.get(position);
                while (start==text.indexOf("<hads>")){
                    start--;
                }
                end = indexes.get(position);
                while (end==text.indexOf("<hade>")){
                    start++;
                }


                /*for (int i = indexes.get(position); i>=text.indexOf("<hads>");i--){
                    if (i==text.indexOf("<hads>")){
                        start = i;
                    }
                }
                for (int i = indexes.get(position);i<=text.indexOf("<hade>");i++){
                    if (i==text.indexOf("<hade>")){
                        end = i;
                    }
                }*/
                textForRead = text.substring(start,end);



                intent1.putExtra("text",textForRead);
                startActivity(intent1);
            }
        });
    }
}
