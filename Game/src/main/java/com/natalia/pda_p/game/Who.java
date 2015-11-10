package com.natalia.pda_p.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;


public class Who extends Activity {
    ImageView viewImage;
    ImageButton b;
    Boolean log = false;
    public static Bitmap BUF = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_who);
        //Button for taking pictures
        b = (ImageButton) findViewById(R.id.imageButton);
        viewImage = (ImageView) findViewById(R.id.imageView2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        //Create new directory in app data
        File myFile = new File(getExternalFilesDir(null), "Dictionaries");
        if (!myFile.exists())
            myFile.mkdir();

    }

    private void selectImage() {
        //Set menu for taking photo button
        final CharSequence[] options = {"Take Photo", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Who.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(getExternalFilesDir(null), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                } else if (options[item].equals("Choose from Gallery")) {//Resolved_Not working with all photos in gallery (very high quality of photos)
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(getExternalFilesDir(null).toString());
                for (File temp : f.listFiles()) {
                    //create a temp version
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    //Can't use storage - no rights, it's no necessary to use it, so the code after is working but if you use another directory, I don't use it
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    //f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BUF = bitmap;
                    viewImage.setImageBitmap(bitmap);
                    log = true;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (requestCode == 2) {
                //Reading from gallery, working code, I don't use it
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");
                //thumbnail.compress(Bitmap.CompressFormat.JPEG,0,)
                viewImage.setImageBitmap(thumbnail);
            }
        }
    }

    public void Choice(View view) {
        String string = null;
        string = ((EditText) findViewById(R.id.editText)).getText().toString();
        //Save profile name
        try {
            String filename = "users.txt";

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
        //If user didn't set a photo, delete previous one
        if (log==false)
        {
            try
            {
                String filename = "temp.jpg";
                File myFile = new File(getExternalFilesDir(null), filename);
                myFile.delete();
            }
            catch (Exception e)
            {
            }

        }
        Intent intent = new Intent(Who.this, What.class);
        startActivity(intent);
    }

    public void Load(View view)
    {
        //Load user info
        try {
            //Get the name
            String filename = "users.txt";
            File myFile = new File(getExternalFilesDir(null), filename);
            FileInputStream inputStream = new FileInputStream(myFile);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                receiveString = bufferedReader.readLine();
                EditText et = (EditText)findViewById(R.id.editText);
                et.setText(receiveString.toString());
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        //Get the photo
        try {String filename = "temp.jpg";
            File myFile = new File(getExternalFilesDir(null), filename);
            ImageView im = (ImageView)findViewById(R.id.imageView2);
            FileInputStream inputStream = null;
            inputStream = new FileInputStream(myFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            // set image to ImageView
            im.setImageDrawable(d);
            log = true;
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
