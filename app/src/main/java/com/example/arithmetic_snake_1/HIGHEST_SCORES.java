package com.example.arithmetic_snake_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class HIGHEST_SCORES extends AppCompatActivity {

    private TextView mEditText;
    private TextView mEditText_1;
    private TextView mEditText_2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_scores);

         mEditText = findViewById(R.id.TEXT_VIEW_id);
         mEditText_1 = findViewById(R.id.TEXT_VIEW_1_id);
         mEditText_2 =  findViewById(R.id.TEXT_VIEW_2_id);

        //Dsisplay what is inside saved file
        FileInputStream fis = null;
        try {
            fis = openFileInput(Easy_game.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = br.readLine())!=null){
                sb.append(text).append("\n");
            }
            mEditText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            }
        }

        FileInputStream fis_1 = null;
        try {
            fis_1 = openFileInput(Easy_game.FILE_NAME_1);
            InputStreamReader isr_1 = new InputStreamReader(fis_1);
            BufferedReader br_1 = new BufferedReader(isr_1);
            StringBuilder sb_1 = new StringBuilder();
            String text_1;
            while((text_1 = br_1.readLine())!=null){
                sb_1.append(text_1).append("\n");
            }
            mEditText_1.setText(sb_1.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }finally {
            if(fis_1 != null){
                try {
                    fis_1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            }
        }

        FileInputStream fis_2 = null;
        try {
            fis_2 = openFileInput(Easy_game.FILE_NAME_2);
            InputStreamReader isr_2 = new InputStreamReader(fis_2);
            BufferedReader br_2 = new BufferedReader(isr_2);
            StringBuilder sb_2 = new StringBuilder();
            String text_2;
            while((text_2 = br_2.readLine())!=null){
                sb_2.append(text_2).append("\n");
            }
            mEditText_2.setText(sb_2.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            //throw new RuntimeException(e);
        }finally {
            if(fis_2 != null){
                try {
                    fis_2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            }
        }

    }
}