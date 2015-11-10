package com.natalia.pda_p.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;


public class Exam extends Activity {


    String lang = null;
    String answer = null;
    int numberofanswers = 0;
    int numberofstrings = 0;
    int stringnumber = 0;
    int stringnumbertransl = 0;
    String s = new String();
    Integer looser = 0;
    Integer count = 0;
    File f;
    ShakeDetectActivity shk;
    public void triggerShakeDetected() {
        Intent intent = new Intent(Exam.this, Exam.class);
        startActivity(intent);
        Exam.this.finish();
        shk.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exam);
        //Shake listener
        shk = new ShakeDetectActivity(this);
        shk.addListener(
                new ShakeDetectActivityListener() {
                    @Override
                    public void shakeDetected() {
                        Exam.this.triggerShakeDetected();
                    }
                });
        shk.onPause();
        //Set the user info
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
        //Set the user preferences
        String level = null;
        try {
            String filename = "level.txt";
            File myFile = new File(getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(myFile);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                receiveString = bufferedReader.readLine();
                level = receiveString.toString();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        try {
            String filename = "language.txt";
            File myFile = new File(getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(myFile);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                receiveString = bufferedReader.readLine();
                lang = receiveString.toString();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        f = new File(getExternalFilesDir(null) + "/Dictionaries", lang);
        try {
            numberofstrings = countLines(lang + ".txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (level.equals("beginner")) {
            numberofanswers = 2;
            Button b = (Button) findViewById(R.id.button);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button2);
            b.setVisibility(View.VISIBLE);
        }
        if (level.equals("intermediate")) {
            numberofanswers = 3;
            Button b = (Button) findViewById(R.id.button);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button2);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button3);
            b.setVisibility(View.VISIBLE);
        }
        if (level.equals("advanced")) {
            numberofanswers = 4;
            Button b = (Button) findViewById(R.id.button);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button2);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button3);
            b.setVisibility(View.VISIBLE);
            b = (Button) findViewById(R.id.button4);
            b.setVisibility(View.VISIBLE);
        }
        //First word
        try {
            new_word();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextView txt = (TextView) findViewById(R.id.textView3);
        txt.setText(s);
        translation();
    }

    public void answer1(View view) {
        check(1);
    }

    public void answer2(View view) {
        check(2);
    }

    public void answer3(View view) {
        check(3);
    }

    public void answer4(View view) {
        check(4);
    }

    public void check(int num) {
        //Check the answer
        TextView txt = (TextView) findViewById(R.id.textView4);
        TextView score = (TextView) findViewById(R.id.textView2);
        Button b = (Button) findViewById(R.id.button);
        Button b2 = (Button) findViewById(R.id.button2);
        Button b3 = (Button) findViewById(R.id.button3);
        Button b4 = (Button) findViewById(R.id.button4);
        if (num == 1) {
            if (answer.equals( b.getText().toString())) {
                b.setTextColor(Color.GREEN);
                txt.setTextColor(Color.GREEN);
                txt.setText("Right!+10");
                count = count + 10;
                score.setText(count.toString());
                try {
                    new_word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextView txtn = (TextView) findViewById(R.id.textView3);
                txtn.setText(s);
                translation();
            } else {
                b.setTextColor(Color.RED);
                txt.setTextColor(Color.RED);
                txt.setText("Wrong:(-20");
                count = count - 20;
                score.setText(count.toString());
                checkforlooser();
            }
        }
        if (num == 2) {
            if (answer.equals( b2.getText().toString())) {
                b2.setTextColor(Color.GREEN);
                txt.setTextColor(Color.GREEN);
                txt.setText("Right!+10");
                count = count + 10;
                score.setText(count.toString());
                try {
                    new_word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextView txtn = (TextView) findViewById(R.id.textView3);
                txtn.setText(s);
                translation();
            } else {
                b2.setTextColor(Color.RED);
                txt.setTextColor(Color.RED);
                txt.setText("Wrong:(-20");
                count = count - 20;
                score.setText(count.toString());
                checkforlooser();
            }
        }
        if (num == 3) {
            if (answer.equals( b3.getText().toString())) {
                b3.setTextColor(Color.GREEN);
                txt.setTextColor(Color.GREEN);
                txt.setText("Right!+10");
                count = count + 10;
                score.setText(count.toString());
                try {
                    new_word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextView txtn = (TextView) findViewById(R.id.textView3);
                txtn.setText(s);
                translation();
            } else {
                b3.setTextColor(Color.RED);
                txt.setTextColor(Color.RED);
                txt.setText("Wrong:(-20");
                count = count - 20;
                score.setText(count.toString());
                checkforlooser();
            }
        }
        if (num == 4) {
            if (answer.equals( b4.getText().toString())) {
                b4.setTextColor(Color.GREEN);
                txt.setTextColor(Color.GREEN);
                txt.setText("Right!+10");
                count = count + 10;
                score.setText(count.toString());
                try {
                    new_word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                TextView txtn = (TextView) findViewById(R.id.textView3);
                txtn.setText(s);
                translation();
            } else {
                b4.setTextColor(Color.RED);
                txt.setTextColor(Color.RED);
                txt.setText("Wrong:(-20");
                count = count - 20;
                score.setText(count.toString());
                checkforlooser();
            }
        }
    }

    public void translation() {
        //Set the other answers
        Button but = (Button)findViewById(R.id.button);
        but.setTextColor(Color.BLACK);
        but = (Button)findViewById(R.id.button2);
        but.setTextColor(Color.BLACK);
        but = (Button)findViewById(R.id.button3);
        but.setTextColor(Color.BLACK);
        but = (Button)findViewById(R.id.button4);
        but.setTextColor(Color.BLACK);
        Random rnd = new Random();
        stringnumbertransl = rnd.nextInt(numberofanswers);
        if (numberofanswers == 2) {
            if (stringnumbertransl == 0) {
                Button b = (Button) findViewById(R.id.button);
                b.setText(answer);
                b = (Button) findViewById(R.id.button2);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);

            }
            if (stringnumbertransl == 1) {
                Button b = (Button) findViewById(R.id.button2);
                b.setText(answer);
                b = (Button) findViewById(R.id.button);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);

            }

        }
        if (numberofanswers == 3) {
            if (stringnumbertransl == 0) {
                Button b = (Button) findViewById(R.id.button);
                b.setText(answer);
                b = (Button) findViewById(R.id.button2);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button3);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
            if (stringnumbertransl == 1) {
                Button b = (Button) findViewById(R.id.button2);
                b.setText(answer);
                b = (Button) findViewById(R.id.button);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button3);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
            if (stringnumbertransl == 2) {
                Button b = (Button) findViewById(R.id.button3);
                b.setText(answer);
                b = (Button) findViewById(R.id.button2);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
        }
        if (numberofanswers == 4) {
            if (stringnumbertransl == 0) {
                Button b = (Button) findViewById(R.id.button);
                b.setText(answer);
                b = (Button) findViewById(R.id.button2);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button3);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button4);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
            if (stringnumbertransl == 1) {
                Button b = (Button) findViewById(R.id.button2);
                b.setText(answer);
                b = (Button) findViewById(R.id.button);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button3);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button4);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
            if (stringnumbertransl == 2) {
                Button b = (Button) findViewById(R.id.button3);
                b.setText(answer);
                b = (Button) findViewById(R.id.button2);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button4);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
            if (stringnumbertransl == 3) {
                Button b = (Button) findViewById(R.id.button4);
                b.setText(answer);
                b = (Button) findViewById(R.id.button2);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
                b = (Button) findViewById(R.id.button3);
                try {
                    word();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                b.setText(s);
            }
        }
    }

    public void new_word() throws IOException {
        //Create new main word
        Random rnd = new Random();
        FileInputStream fr = null;
        stringnumber = rnd.nextInt(numberofstrings);
        if (stringnumber % 2 != 0)
            stringnumber = stringnumber + 1;
        try {
            fr = new FileInputStream(f);
            BufferedReader lnr = new BufferedReader(new InputStreamReader(fr));
            for (int i = 0; i < stringnumber; i++)
                lnr.readLine();
            s = lnr.readLine().toString();
            answer = lnr.readLine().toString();
            fr.close();
            if (s == "rEvErCe")
                new_word();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void word() throws IOException {
        //Create random word
        Random rnd = new Random();
        FileInputStream fr = null;
        stringnumber = rnd.nextInt(numberofstrings);
        if (stringnumber % 2 != 1)
            stringnumber = stringnumber + 1;
        try {
            fr = new FileInputStream(f);
            BufferedReader lnr = new BufferedReader(new InputStreamReader(fr));
            for (int i = 0; i < stringnumber; i++)
                lnr.readLine();
            s = lnr.readLine().toString();
            fr.close();
            if (s == "rEvErCe")
                new_word();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int countLines(String filename) throws IOException {
        //How many lines in dictionary
        int count = 0;
        FileInputStream fr = null;
        String filename2 = lang;
        String name;
        File my = new File(getExternalFilesDir(null)+"/Dictionaries",filename2 );
        fr = new FileInputStream(my);
        BufferedReader lnr = new BufferedReader(new InputStreamReader(fr));
        try {
            name = lnr.readLine();
            while (name != null) {
                count++;
                name = lnr.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lnr.close();
        return count;
    }

    public void checkforlooser()
    {
        //Count mistakes
        ImageView im = (ImageView )findViewById(R.id.imageView2);
        looser++;
        if (looser==1)
        {
            im.setImageResource(R.drawable.loose1);
        }
        if (looser==2)
        {
            im.setImageResource(R.drawable.loose2);
        }
        if (looser==3)
        {
            //Loose
            TextView tx1 = (TextView)findViewById(R.id.textView);
            im.setImageResource(R.drawable.loose3);
            TextView tx = (TextView)findViewById(R.id.textView3);
            //Count new score
            String endscore = count.toString()+' '+tx1.getText().toString()+' '+lang.toString()+"|";
            File file = new File(getExternalFilesDir(null), "scoretable.txt");
            if (!file.exists())
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Read previous scores
            BufferedReader lnr = new BufferedReader(new InputStreamReader(inputStream));
            int j = 1;
            String[] values = new String[1000];
            values[0]=endscore;
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
            //Create dialog
            AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
            alert1.setTitle("Score Table");
            final TextView input = new TextView(this);
            String text=null;
            text = values[0];
            input.setLines(100);
            for (int i=1;i<j;i++)
            {
                text +=values[i].toString();
            }
            input.setText(text.replace("|","\n"));
            alert1.setView(input);
            alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    return;
// Canceled.
                }
            });

            alert1.show();
            FileOutputStream outt = null;
            try {
                outt = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                lnr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedWriter br = new BufferedWriter(new OutputStreamWriter(outt));
            try {
                br.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            tx.setText("Game over! Want a new game? Just shake the phone!");
            //Start shake listener
            shk.onResume();
            Button but = (Button)findViewById(R.id.button);
            but.setText("");
            but = (Button)findViewById(R.id.button2);
            but.setText("");
            but = (Button)findViewById(R.id.button3);
            but.setText("");
            but = (Button)findViewById(R.id.button4);
            but.setText("");
        }
    }

}
