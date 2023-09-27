package com.example.arithmetic_snake_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.helpers.AttributesImpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Easy_game extends AppCompatActivity implements SurfaceHolder.Callback {

    //list of snake points / snake length
    private final List<SnakePoints> snakePointsList = new ArrayList<>();

    private SurfaceView surfaceView;
    private TextView scoreTV;
    private TextView evaluateTV;

    //surface holder to draw snake on surface's canvas
    private SurfaceHolder surfaceHolder;

    //snake moving position. Values must be right, left, top, bottom
    //by default snake move to right
    private String movingPosition = "right";

    //score
    private int score = 0;

    //snake size / point size
    //you can change this value to make bigger size snake
    private static int pointSize = 28;//28

    //default snake tale
    private static int defaultTalePoints = 3;//3

    //snake color
    private static final int snakeColor = Color.YELLOW;

    //snake moving speed. value must lie between 1-1000
    private static int snakeMovingSpeed = 700;//800

    //check if we are in practice or in game
    private static int practice_game = 0;
    //check which type of practice we are doing
    private static int addormult =0;

    //file name for static storage
    public static final String FILE_NAME = "example.txt";
    public static final String FILE_NAME_1 = "example_1.txt";
    public static final String FILE_NAME_2 = "example_2.txt";
    public static String FILE;

    //random position cordinates on the surfaceview
    private int positionX=0, positionY=0;

    //timer tot move snake / change snake position after a specific time (snakeMovingSpeed)
    private Timer timer;

    //canvas to draw snake and show on surface view
    private Canvas canvas = null;

    //point color / single point color of a snake
    private Paint pointColor = null;

    //check if the expression is correct
    private int expression_correct = 0;

    String tag = "Lifecyle step";
    public String ssp;
    public int prev_score;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_game);

        //getting messages from previous node
        Intent intent = getIntent();
        String message = intent.getStringExtra(GameLevels_1.MSG_1);

        // ssetting game for different levels
        if(Integer.parseInt(message) == 1){
            pointSize = 28;
            defaultTalePoints = 3;
            snakeMovingSpeed = 700;
            practice_game = 0;
            FILE=FILE_NAME;
        }
        else if(Integer.parseInt(message) == 2){
            pointSize = 14;
            defaultTalePoints = 15;
            snakeMovingSpeed = 850;
            practice_game = 0;
            FILE=FILE_NAME_1;
        }
        else if (Integer.parseInt(message) == 3) {
            pointSize = 28;
            defaultTalePoints = 15;
            snakeMovingSpeed = 900;
            practice_game = 0;
            FILE=FILE_NAME_2;
        }

        else if (Integer.parseInt(message) == 4) {
            pointSize = 28;
            defaultTalePoints = 3;
            snakeMovingSpeed = 400;
            practice_game = 1;
            addormult = 0;
        }

        else if (Integer.parseInt(message) == 5) {
            pointSize = 28;
            defaultTalePoints = 3;
            snakeMovingSpeed = 400;
            practice_game = 1;
            addormult = 1;
        }

        //getting surfaceview and score textview from xml file
        surfaceView = findViewById(R.id.surfaceView);
        scoreTV = findViewById(R.id.scoreTV);
        evaluateTV = findViewById(R.id.EvaluateTV);
        generate_expression();

        //getting imagebuttons from xml file
        final AppCompatImageButton topBtn = findViewById(R.id.topBtn);
        final AppCompatImageButton leftBtn = findViewById(R.id.leftBtn);
        final AppCompatImageButton rightBtn = findViewById(R.id.rightBtn);
        final AppCompatImageButton bottomBtn = findViewById(R.id.bottomBtn);

        final AppCompatImageButton topBtn_1 = findViewById(R.id.topBtn_1);
        final AppCompatImageButton leftBtn_1 = findViewById(R.id.leftBtn_1);
        final AppCompatImageButton rightBtn_1 = findViewById(R.id.rightBtn_1);
        final AppCompatImageButton bottomBtn_1 = findViewById(R.id.bottomBtn_1);

        //adding callback to surfaceview
        surfaceView.getHolder().addCallback(this);

        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if previous moving position is not bottom. snake can't move
                //for example if snake is moving to bottom then snake can't directly start moving to top
                //snake must take right or left first then top
                if(!movingPosition.equals("bottom")){
                    if(expression_correct == 1){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "top";
                }
                generate_expression();
            }
        });

        topBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if previous moving position is not bottom. snake can't move
                //for example if snake is moving to bottom then snake can't directly start moving to top
                //snake must take right or left first then top
                if(!movingPosition.equals("bottom")){
                    if(expression_correct == 0){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "top";
                }
                generate_expression();
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("right")){
                    if(expression_correct == 1){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "left";
                }
                generate_expression();
            }
        });

        leftBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("right")){
                    if(expression_correct == 0){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "left";
                }
                generate_expression();
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("left")){
                    if(expression_correct == 1){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "right";
                }
                generate_expression();
            }
        });

        rightBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("left")){
                    if(expression_correct == 0){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "right";
                }
                generate_expression();
            }
        });

        bottomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("top")){
                    if(expression_correct == 1){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score-=10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "bottom";
                }
                generate_expression();
            }
        });

        bottomBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!movingPosition.equals("top")){
                    if(expression_correct == 0){
                        score++;
                        scoreTV.setText(String.valueOf(score));
                    }
                    else{
                        score=-10;
                        scoreTV.setText(String.valueOf(score));
                    }
                    movingPosition = "bottom";
                }
                generate_expression();
            }
        });
    }
    public void onStart() {
        super.onStart();
        Log.d(tag,"in the onStart() event");
    }
    public void onRestart(){
        super.onRestart();
        Log.d(tag,"in the onRestart() event");
    }
    public void onResume(){
        super.onResume();
        Log.d(tag,"in the onResume() event");
    }
    public void onPause(){
        super.onPause();
        timer.purge();
        timer.cancel();
        finish();
        Log.d(tag,"in the onPause() event");
    }
    public void onStop(){
        super.onStop();
        Log.d(tag,"in the onStop() event");
    }
    public void onDestroy(){
        super.onDestroy();
        Log.d(tag,"in the onDestroy() event");
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

        //when surface is created then get surfaceHolder from it and assign to surfaceHolder
        this.surfaceHolder = surfaceHolder;

        //init data for snake / surfaceview
        init();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    private void generate_expression(){
        int randomSymbol = new Random().nextInt(2);
        if(practice_game == 1){
            if(addormult == 0){
                randomSymbol = 0;
            }
            else if(addormult == 1){
                randomSymbol = 1;
            }
        }
        if(randomSymbol == 0) {
            int randomExp_1 = new Random().nextInt(21);
            int randomExp_2 = new Random().nextInt(21);
            int randomExp_3 = new Random().nextInt(41);
            int randomEquate = new Random().nextInt(2);
            if(randomEquate == 0){
                evaluateTV.setText(Integer.toString(randomExp_1)+" + "+Integer.toString(randomExp_2)+" = "+Integer.toString(randomExp_1+randomExp_2)+" ");
                expression_correct=1;
            }
            if(randomEquate == 1){
                if(randomExp_1+randomExp_2==randomExp_3){
                    evaluateTV.setText(Integer.toString(randomExp_1)+" + "+Integer.toString(randomExp_2)+" = "+Integer.toString(randomExp_3+1)+" ");
                }
                else{
                    evaluateTV.setText(Integer.toString(randomExp_1)+" + "+Integer.toString(randomExp_2)+" = "+Integer.toString(randomExp_3)+" ");
                }
                expression_correct=0;
            }
        }
        if(randomSymbol == 1){
            int randomExp_4 = new Random().nextInt(11);
            int randomExp_5 = new Random().nextInt(11);
            int randomExp_6 = new Random().nextInt(101);
            int randomEquate_1 = new Random().nextInt(2);
            if(randomEquate_1 == 0){
                evaluateTV.setText(Integer.toString(randomExp_4)+" X "+Integer.toString(randomExp_5)+" = "+Integer.toString(randomExp_4*randomExp_5)+" ");
                expression_correct=1;
            }
            if(randomEquate_1 == 1){
                if(randomExp_4*randomExp_5==randomExp_6){
                    evaluateTV.setText(Integer.toString(randomExp_4)+" X "+Integer.toString(randomExp_5)+" = "+Integer.toString(randomExp_6+1)+" ");
                }
                else{
                    evaluateTV.setText(Integer.toString(randomExp_4)+" X "+Integer.toString(randomExp_5)+" = "+Integer.toString(randomExp_6)+" ");
                }
                expression_correct=0;
            }
        }
    }

    private void init(){

        // clear snake points / snake length
        snakePointsList.clear();

        //set default score as 0
        scoreTV.setText("0");

        //make score 0
        score = 0;

        //setting default moving position
        movingPosition = "right";

        //default snake starting position on the screen
        int startPositionX = (pointSize) * defaultTalePoints;

        //making snake's default length / points
        for(int i=0; i<defaultTalePoints; i++){
            //adding points to snake's tale
            SnakePoints snakePoints = new SnakePoints(startPositionX,pointSize);
            snakePointsList.add(snakePoints);

            //increasing value for next point as snake's tale
            startPositionX = startPositionX - (pointSize*2);
        }
        //add random point on the screen to be eaten by the snake
        addPoint();

        //start moving snake / start game
        moveSnake();
    }

    private void addPoint(){

        //getting surface view width and height to add point on the surface to be eaten by the snake
        int surfaceWidth = surfaceView.getWidth() - (pointSize*2);
        int surfaceHeight = surfaceView.getHeight() - (pointSize*2);

        int randomXPosition = new Random().nextInt(surfaceWidth/pointSize);
        int randomYPosition = new Random().nextInt(surfaceHeight/pointSize);

        //check if randomXPosition is even or odd value. we only need even number
        if((randomXPosition%2) != 0) {
            randomXPosition = randomXPosition + 1;
        }

        if((randomYPosition%2) != 0) {
            randomYPosition = randomYPosition + 1;
        }

        positionX = (pointSize * randomXPosition) + pointSize;
        positionY = (pointSize * randomYPosition) + pointSize;
    }

    private void moveSnake(){

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //getting head position
                int headPositionX = snakePointsList.get(0).getPositionX();
                int headPositionY = snakePointsList.get(0).getPositionY();

                //check if snake eaten a point
                if(headPositionX == positionX && headPositionY == positionY){
                    //grow snake after eaten points
                    growSnake();

                    //add another random point on the screen
                    addPoint();
                }

                //check of which side snake is moving
                switch (movingPosition){
                    case "right":
                        //move snake's head to right
                        //other points follow snakes head point to move the snake
                        snakePointsList.get(0).setPositionX(headPositionX + (pointSize*2));
                        snakePointsList.get(0).setPositionY(headPositionY);
                        break;
                    case "left":
                        //move snake's head to left
                        //other points follow snakes head point to move the snake
                        snakePointsList.get(0).setPositionX(headPositionX - (pointSize*2));
                        snakePointsList.get(0).setPositionY(headPositionY);
                        break;
                    case "top":
                        //move snake's head to top
                        //other points follow snakes head point to move the snake
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0).setPositionY(headPositionY - (pointSize*2));
                        break;
                    case "bottom":
                        //move snake's head to bottom
                        //other points follow snakes head point to move the snake
                        snakePointsList.get(0).setPositionX(headPositionX);
                        snakePointsList.get(0).setPositionY(headPositionY + (pointSize*2));
                        break;
                }
                //check if game over. wether snake touch edges or itself
                if(checkGameOver(headPositionX,headPositionY)){
                    //stop timer/stop moving snake
                    timer.purge();
                    timer.cancel();
                    // Just change
                    //show game over dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Easy_game.this);
                    builder.setMessage("Your Score = " + score);
                    builder.setTitle("Game Over");
                    builder.setCancelable(false);
                    builder.setPositiveButton("[Start Again]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //restart game / re-init data
                            init();
                        }
                    });
                    builder.setNegativeButton("[Go Back to Menu]", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //restart game / re-init data
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK,resultIntent);
                            finish();
                        }
                    });
                    //timer runs in background so we need to show dialog on main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            builder.show();
                        }
                    });

                    //String ssp;
                    //Dsisplay what is inside saved file
                    if(practice_game == 0){
                        FileInputStream fis = null;
                        try {
                            fis = openFileInput(Easy_game.FILE);
                            InputStreamReader isr = new InputStreamReader(fis);
                            BufferedReader br = new BufferedReader(isr);
                            StringBuilder sb = new StringBuilder();
                            String text;
                            while((text = br.readLine())!=null){
                                sb.append(text);
                            }
                            //mEditText.setText(sb.toString());
                            ssp=sb.toString();
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
                        //done displaying

                        //save score in a static file

                        //prev_score = Integer.valueOf(ssp);
                        try {
                            prev_score = Integer.parseInt(ssp);
                            // Now, intValue contains the integer value of the string
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            //Log.d(tag, "run: "+ Integer.toString(Integer.parseInt(ssp)));
                            // Handle the case where the string cannot be converted to an integer
                        }

                        if(score > prev_score) {
                            //save score in a static file
                            String score_text = Integer.toString(score);
                            //String score_text = ssp;
                            FileOutputStream fos = null;
                            try {
                                fos = openFileOutput(FILE, MODE_PRIVATE);
                                fos.write(score_text.getBytes());
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Easy_game.this, "Saved to " + getFilesDir() + "/" + FILE, Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                //throw new RuntimeException(e);
                            } catch (IOException e) {
                                e.printStackTrace();
                                //throw new RuntimeException(e);
                            } finally {
                                if (fos != null) {
                                    try {
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        //throw new RuntimeException(e);
                                    }
                                }
                            }

                            //done saving
                        }
                        //done saving
                    }
                }
                else{
                    //lock canvas on surfaceHolder to draw on it
                    canvas = surfaceHolder.lockCanvas();

                    //clear canvas with white color
                    canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);

                    //change snakes head position. other points will follow snake's head
                    canvas.drawCircle(snakePointsList.get(0).getPositionX(),snakePointsList.get(0).getPositionY(),pointSize,createPointColor());

                    //draw random point circle on the surface to be eaten by the snake
                    canvas. drawCircle(positionX,positionY,pointSize,createPointColor());

                    //other points are following snake's head. position 0 is head of snake
                    for(int i=1; i < snakePointsList.size(); i++){

                        int getTempPositionX = snakePointsList.get(i).getPositionX();
                        int getTempPositionY = snakePointsList.get(i).getPositionY();

                        //move points across the head
                        snakePointsList.get(i).setPositionX(headPositionX);
                        snakePointsList.get(i).setPositionY(headPositionY);
                        canvas.drawCircle(snakePointsList.get(i).getPositionX(),snakePointsList.get(i).getPositionY(),pointSize,createPointColor());

                        //change head position
                        headPositionX = getTempPositionX;
                        headPositionY = getTempPositionY;
                    }

                    //unlock canvas to draw on surfaceview
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        },1000-snakeMovingSpeed,1000-snakeMovingSpeed);
    }

    public static int stringCompare(String str1, String str2)
    {

        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int)str1.charAt(i);
            int str2_ch = (int)str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        // Edge case for strings like
        // String 1="Geeks" and String 2="Geeksforgeeks"
        if (l1 != l2) {
            return l1 - l2;
        }

        // If none of the above conditions is true,
        // it implies both the strings are equal
        else {
            return 0;
        }
    }
    private void growSnake(){
        //create new snake points
        SnakePoints snakePoints = new SnakePoints(0,0);

        //add point to snake's tale
        snakePointsList.add(snakePoints);

        //increase score
        score += 5;

        //setting score to TextViews
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTV.setText(String.valueOf(score));
            }
        });
    }
    private boolean checkGameOver(int headPositionX, int headPositionY){
        boolean gameOver = false;

        //check if snake's head touches edges
        if(snakePointsList.get(0).getPositionX() < 0 ||
                snakePointsList.get(0).getPositionY() < 0 ||
                snakePointsList.get(0).getPositionX() >= surfaceView.getWidth() ||
                snakePointsList.get(0).getPositionY() >= surfaceView.getHeight())
        {

            gameOver = true;
        }
        else{
            //check if snake's head touches snake itself
            for(int i=1; i<snakePointsList.size(); i++){
                if(headPositionX == snakePointsList.get(i).getPositionX() &&
                        headPositionY == snakePointsList.get(i).getPositionY()){
                    gameOver =true;
                    break;
                }
            }
        }
        return gameOver;
    }

    private Paint createPointColor(){

        //check if color not defined before
        if(pointColor == null){
            pointColor = new Paint();
            pointColor.setColor(snakeColor);
            pointColor.setStyle(Paint.Style.FILL);
            pointColor.setAntiAlias(true); //Smoothness
        }
        return pointColor;
    }
}