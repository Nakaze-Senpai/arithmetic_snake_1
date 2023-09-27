package com.example.arithmetic_snake_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameLevels_1 extends AppCompatActivity {
    String message;
    public static final String MSG_1 = "Easy_game_MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_levels1);
    }
    public void easyClick(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent2 = new Intent(this, Easy_game.class);
        message = "1";
        intent2.putExtra(MSG_1,message);
        startActivity(intent2);
    }
    public void hardClick(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent3 = new Intent(this, Easy_game.class);
        message = "2";
        intent3.putExtra(MSG_1,message);
        startActivity(intent3);
    }
    public void verydifficultClick(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent4 = new Intent(this, Easy_game.class);
        message = "3";
        intent4.putExtra(MSG_1,message);
        startActivity(intent4);
    }
}