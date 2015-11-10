package com.natalia.pda_p.game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class What extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_what);
        //Set user name and photo in the top of the screen
        TextView et = (TextView)findViewById(R.id.textView4);
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

        try {String filename = "temp.jpg";
            File myFile = new File(getExternalFilesDir(null), filename);
            ImageView im = (ImageView)findViewById(R.id.imageView4);
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

    public void Kind(int n) {
        //Choose the kind of the game
        String string = null;
        switch (n)
        {
            case 1: string = "lesson"; break;
            case 2: string = "exam"; break;
            case 3: string = "challenge"; break;
        }
        try {
            String filename = "kind.txt";
            File myFile = new File(getExternalFilesDir(null), filename);
            if (!myFile.exists())
                myFile.createNewFile();
            FileOutputStream fos;
            byte[] data = string.getBytes();
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
    }

    public void answer1(View view)
    {
        //Lesson
        Kind(1);
        Intent intent = new Intent(What.this, Language.class);
        startActivity(intent);
    }

    public void answer2(View view)
    {
        //Exam
        Kind(2);
        Intent intent = new Intent(What.this, Language.class);
        startActivity(intent);
    }

    public void answer3(View view)
    {
        //Challenge
        Kind(3);
        Intent intent = new Intent(What.this, Language.class);
        startActivity(intent);
    }

}
