package com.natalia.pda_p.game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class Load extends Activity {

    public boolean sound = true;
    public MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set portrait orientation and hide tha actionBar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set music
        player= MediaPlayer.create(Load.this, R.raw.gamesound);
        player.setLooping(true);
        player.start();
        setContentView(R.layout.activity_load);
    }

    public void Soundonoffclick(View view)
    {
        //Start-stop music
        ImageButton Imbtn =(ImageButton) findViewById(R.id.imageButton);
        if (sound==true)
        {
            Imbtn.setImageResource(R.drawable.musicoff);
            sound=false;
            player.pause();
        }
        else
        {
            Imbtn.setImageResource(R.drawable.musicon);
            sound=true;
            player.start();

        }

    }

    public void New_game(View view)
    {
        Intent intent = new Intent(Load.this, Who.class);
        startActivity(intent);

    }
}
