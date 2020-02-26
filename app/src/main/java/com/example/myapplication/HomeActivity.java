package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
   // MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        mp = MediaPlayer.create(this,R.raw.heart_beat);

    }

    public void GKbtn(View view) {
        Intent intent = new Intent(HomeActivity.this, GeneralKnowledgeActivity.class);
        startActivity(intent);
     //   mp.start();
//        finish();
    }
    public void Historybtn(View view) {
        Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
        startActivity(intent);

//        finish();
    }

    public void Geobtn(View view) {
        Intent intent = new Intent(HomeActivity.this, GeographyActivity.class);
        startActivity(intent);
//        finish();
    }


    public void Fruitbtn(View view) {
        Intent intent = new Intent(HomeActivity.this, FruitActivity.class);
        startActivity(intent);
//        finish();
    }
}
