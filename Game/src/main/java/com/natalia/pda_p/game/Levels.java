package com.natalia.pda_p.game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Levels extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_levels);
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
    }

    public void Beginner (View view)
    {
        try {
            //Save user choice
            String strText = "beginner";
            String filename = "level.txt";
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
        Intent intent = new Intent(Levels.this, YourGame.class);
        startActivity(intent);
    }

    public void Intermediate (View view)
    {
        try {
            //Save user choice
            String strText = "intermediate";
            String filename = "level.txt";
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
        Intent intent = new Intent(Levels.this, YourGame.class);
        startActivity(intent);
    }

    public void Advanced (View view)
    {
        try {
            //Save user choice
            String strText = "advanced";
            String filename = "level.txt";
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
        Intent intent = new Intent(Levels.this, YourGame.class);
        startActivity(intent);
    }

}
