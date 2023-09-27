package com.example.arithmetic_snake_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameLevels_2 extends AppCompatActivity {
    String message;
    public static final String MSG_1 = "Easy_game_MSG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_levels2);
    }
    public void practiceadditionClick(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent6 = new Intent(this, Easy_game.class);
        message = "4";
        intent6.putExtra(MSG_1,message);
        startActivity(intent6);
    }
    public void practicemultiplicationClick(View view){
        //we will handle click on the button here
        //build intent to open another activity
        Intent intent7 = new Intent(this, Easy_game.class);
        message = "5";
        intent7.putExtra(MSG_1,message);
        startActivity(intent7);
    }
}