package com.bingerdranch.android.hadithread;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class TestThread extends Activity{

    ArrayList <ArrayList<String>> listInList;
    Context baseContext;


    private static final String LOG_TAG = "MyLogs";

    public void baseCont (Context context){
        baseContext = context;
    }

    protected String doInBackground() {

        String textOnFile;
        String arabTexts;

        Resources res = baseContext.getResources();
        InputStream in_s = null;
        InputStream in_arab = null;

        in_s = res.openRawResource(R.raw.test_en);
        in_arab = res.openRawResource(R.raw.test_ar);

        byte[] b = new byte[0];
        byte[] arab = new byte[0];
        try {
            b = new byte[in_s.available()];
            arab = new byte[in_arab.available()];

            in_s.read(b);
            in_arab.read(arab);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textOnFile = new String(b);// тут весь текст с выбранной книги
        arabTexts = new String(arab); // тут весь текст на арабском

        ArrayList<String> listHad = new ArrayList<>();
        ArrayList <String> listArab = new ArrayList<>();

        textOnFile = textOnFile.replaceAll("<item","@");
        arabTexts = arabTexts.replaceAll("<item","@");

        textOnFile = textOnFile.replaceAll("</item>","№");
        arabTexts = arabTexts.replaceAll("</item>","№");

        //Log.d(LOG_TAG,arabTexts);

        char[]arr = textOnFile.toCharArray();
        char[]arrArab = arabTexts.toCharArray();

        int k = 0;
        for (int i = 0; i<arr.length;i++){
            if (arr[i]=='@'){
                k++;
                String s = textOnFile.substring(i, textOnFile.indexOf('№',i));
                s = s.substring(2,s.length());
                s = k + " " + s;
                listHad.add(s);
            }
        }

        for (int i = 0; i<arrArab.length;i++){
            if (arrArab[i]=='@'){
                String s1 = arabTexts.substring(i, arabTexts.indexOf('№',i));
                listArab.add(s1);
            }
        }

        ArrayList<String> tom1 = new ArrayList<>();
        //ArrayList<String> tom2 = new ArrayList<>();
        ArrayList<String> tom3 = new ArrayList<>();
        ArrayList<String> tom4 = new ArrayList<>();
        ArrayList<String> tom5 = new ArrayList<>();
        ArrayList<String> tom6 = new ArrayList<>();
        //ArrayList<String> tom7 = new ArrayList<>();
        //ArrayList<String> tom8 = new ArrayList<>();
        ArrayList<String> tom9 = new ArrayList<>();
        ArrayList<String> tom10 = new ArrayList<>();
        ArrayList<String> tom11= new ArrayList<>();
        ArrayList<String> tom12 = new ArrayList<>();
        ArrayList<String> tom13 = new ArrayList<>();
        ArrayList<String> tom14 = new ArrayList<>();
        ArrayList<String> tom15 = new ArrayList<>();
        ArrayList<String> tom16 = new ArrayList<>();


        int num = 0;
        for (int i = 0; i<listHad.size();i++){
            num++;
            if (i<180){
                tom1.add(num + " " + listHad.get(i) + "\n" + listArab.get(i) + "\n" + listArab.get(i));
                Log.d(LOG_TAG,tom1.get(i));
            }
            if (i>=180&&i<=246){
                num = 1;
                tom3.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom3.get(i));
            }
            if (i>=247&&i<=297){
                num = 598;
                tom4.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom4.get(i));
            }
            if (i>=298&&i<=355){
                num = 650;
                tom5.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom5.get(i));
            }
            if (i>=356&&i<=429){
                num = 1;
                tom6.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom6.get(i));
            }
            if (i>=430&&i<=476){
                num = 1169;
                tom9.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom9.get(i));
            }
            if (i>=477&&i<=534){
                num = 1205;
                tom10.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom10.get(i));
            }
            if (i>=635&&i<=597){
                num = 1271;
                tom11.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom11.get(i));
            }
            if (i>=598&&i<=642){
                num = 1319;
                tom12.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom12.get(i));
            }if (i>=643&&i<=665){
                num = 1;
                tom13.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom13.get(i));
            }
            if (i>=666&&i<=701){
                num = 1;
                tom14.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom14.get(i));
            }
            if (i>=702&&i<=721){
                num = 1418;
                tom15.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom15.get(i));
            }
            if (i>=772&&i<=862){
                num = 1437;
                tom16.add(num + " " + listHad.get(i)+ "\n" + listArab.get(i) + "\n" + listArab.get(i));
                //Log.d(LOG_TAG,tom16.get(i));
            }
        }

        listInList = new ArrayList<>();

        listInList.add(tom1);
        listInList.add(tom3);
        listInList.add(tom4);
        listInList.add(tom5);
        listInList.add(tom6);
        listInList.add(tom9);
        listInList.add(tom10);
        listInList.add(tom11);
        listInList.add(tom12);
        listInList.add(tom13);
        listInList.add(tom14);
        listInList.add(tom15);
        listInList.add(tom16);

        return null;
    }

    ArrayList returnListInList(){
        Log.d(LOG_TAG,"doInBackground = " + listInList.size());
        return listInList;
    }
}