package com.natalia.pda_p.game;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Language extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_language);
        //Set user info in the top of the screen
        TextView et = (TextView) findViewById(R.id.textView);
        try {
            String filename = "users.txt";
            File myFile = new File(getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(myFile);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                receiveString = bufferedReader.readLine();
                et.setText(receiveString.toString());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        try {
            String filename = "temp.jpg";
            File myFile = new File(getExternalFilesDir(null), filename);
            ImageView im = (ImageView) findViewById(R.id.imageView);
            FileInputStream inputStream = null;
            inputStream = new FileInputStream(myFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            // set image to ImageView
            im.setImageDrawable(d);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Dictionaries (downloaded)
        //Check directory for dictionaries
        File folder = new File(getExternalFilesDir(null)+"/Dictionaries");
        File[] listOfFiles = folder.listFiles();
        int j=0;
        String[] values = new String[1000];
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                values[j]= listOfFiles[i].getName();
                j++;
            }
        }
        //Add all downloaded dictionaries to the listView
        final ListView lv = (ListView) findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < j; ++i) {
            list.add(values[i].subSequence(0,values[i].length()-4).toString());
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //What to do
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = (textView.getText()+".txt").toString();
                try {
                    //Save user choice
                    String filename = "language.txt";

                    File myFile = new File(getExternalFilesDir(null), filename);
                    if (!myFile.exists())
                        myFile.createNewFile();
                    FileOutputStream fos;
                    byte[] data = strText.getBytes();
                    try {
                        fos = new FileOutputStream(myFile);
                        fos.write(data);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(Language.this, Levels.class);
                startActivity(intent);

            }
        });
    }

    public void download (View view)
    {
        //Check and download new dictionaries
        Intent intent = new Intent(Language.this, Dowload.class);
        startActivity(intent);
    }

}

