package com.natalia.pda_p.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Dowload extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dowload);
        //Url to my server
        String _url = "http://sergey-jeffchenga.rhcloud.com/test/dictionaries/dic.txt";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String mes = "";
        final String filename = "dic.txt";
        try {
            //Try to make a connection
            int count;
            try {
                URL url = new URL(_url);
                URLConnection conection = url.openConnection();
                conection.connect();
                // this will be useful so that you can show a typical 0-100%
                // progress bar
                int lenghtOfFile = conection.getContentLength();
                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);
                // Output stream
                File file = new File(getExternalFilesDir(null), filename);
                OutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

        } catch (Exception e) {
        }
        //Set all names of the dictionaries from server to the listView
        File file = new File(getExternalFilesDir(null), filename);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader lnr = new BufferedReader(new InputStreamReader(inputStream));
        int j = 0;
        String[] values = new String[1000];
        String name = null;
        try {
            name = lnr.readLine();
            while (name != null) {
                values[j] = name;
                j++;
                name = lnr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       // j--;
        final ListView lv2 = (ListView) findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < j; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        lv2.setAdapter(adapter);
        try {
            //What to do
            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                        long id) {
                    TextView textView = (TextView) itemClicked;
                    String strText = textView.getText().toString();
                    //Download the chosen dictionary
                    String _url = "http://sergey-jeffchenga.rhcloud.com/test/dictionaries/" + strText + ".txt";
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String mes = "";
                    final String filename = strText + ".txt";
                    try {
                        int count;
                        try {
                            URL url = new URL(_url);
                            URLConnection conection = url.openConnection();
                            conection.connect();
                            // this will be useful so that you can show a typical 0-100%
                            // progress bar
                            int lenghtOfFile = conection.getContentLength();
                            // download the file
                            InputStream input = new BufferedInputStream(url.openStream(),
                                    8192);
                            // Output stream
                            File file = new File(getExternalFilesDir(null) + "/Dictionaries", filename);
                            OutputStream output = new FileOutputStream(file);
                            byte data[] = new byte[1024];
                            long total = 0;
                            while ((count = input.read(data)) != -1) {
                                output.write(data, 0, count);
                            }
                            // flushing output
                            output.flush();
                            // closing streams
                            output.close();
                            input.close();

                        } catch (Exception e) {
                            Log.e("Error: ", e.getMessage());
                        }

                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(Dowload.this, Language.class);
                    startActivity(intent);

                }
            });
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }
}
