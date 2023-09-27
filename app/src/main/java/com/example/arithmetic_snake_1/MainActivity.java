package com.example.arithmetic_snake_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent = new Intent(this, GameLevels_1.class);
        startActivity(intent);
    }
    public void practice(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent1 = new Intent(this, GameLevels_2.class);
        startActivity(intent1);
    }
    public void howToPlay(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent8 = new Intent(this, How_To_Play.class);
        startActivity(intent8);
    }
    public void highestScore(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent9 = new Intent(this, HIGHEST_SCORES.class);
        startActivity(intent9);
    }
}