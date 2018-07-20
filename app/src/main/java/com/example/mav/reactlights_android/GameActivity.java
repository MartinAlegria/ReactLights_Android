package com.example.mav.reactlights_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.*;

public class GameActivity extends AppCompatActivity {

    private Button l1;
    private Button l2;
    private Button l3;
    private Button l4;
    private Button l5;
    private Button l6;
    private Button correctLight;
    private Button fakeLight;
    private Button correctWindow;

    LightingColorFilter blue = new LightingColorFilter(0xFFFFFFFF, Color.rgb(9,54,188));
    LightingColorFilter red = new LightingColorFilter(0xFFFFFFFF, Color.rgb(188,9,9));
    LightingColorFilter green = new LightingColorFilter(0xFFFFFFFF, Color.rgb(9,188,90));
    LightingColorFilter cyan = new LightingColorFilter(0xFFFFFFFF, Color.rgb(9,188,179));
    LightingColorFilter yellow = new LightingColorFilter(0xFFFFFFFF, Color.rgb(226,198,13));
    LightingColorFilter correctColor;
    LightingColorFilter fakeColor;

    private TextView scoreText;
    private TextView countdownText;
    int score = 0;
    boolean gamePaused = false;

    private ImageView startScreen;
    private ImageView pauseScreen;
    private MediaPlayer correct;
    private MediaPlayer incorr;

    private CountDownTimer countDownTimer;
    private long milisecTime = 31000;
    private boolean timerRunning;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    LightingColorFilter[] filterArray = {blue, red, green, cyan, yellow};

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullscreen();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        correct = MediaPlayer.create(this,R.raw.correct);
        incorr = MediaPlayer.create(this, R.raw.incorrect);
        startScreen = (ImageView) findViewById(R.id.startScreen);
        pauseScreen = (ImageView) findViewById(R.id.pauseScreen);


        startScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScreen.setVisibility(View.INVISIBLE);
                randomCircle();
                play();
                runTimer();
            }

        });


    }//ON CREATE

    public void colorsFunc(){
        int randColor = new Random().nextInt(filterArray.length);
        int randColor2 = new Random().nextInt(filterArray.length);

        correctColor = filterArray[randColor];

        if(randColor2 == randColor){
            do {
                randColor2 = new Random().nextInt(filterArray.length);
            }while(randColor2 == randColor);

            fakeColor = filterArray[randColor2];
        }else{
            fakeColor = filterArray[randColor2];
        }
    } //COLORS FUNC

    private void play() {
        correctLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if(!gamePaused){
                    randomCircle();
                    score++;
                    scoreText.setText(Integer.toString(score));
                    correct.start();
                    play();
                }

            }
        });

        fakeLight.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if(!gamePaused){
                    randomCircle();
                    colorsFunc();
                    incorr.start();
                    play();
                }

            }
        });

        correctWindow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if(!gamePaused){
                    gamePaused = true;
                    countDownTimer.cancel();
                    pauseScreen.setVisibility(View.VISIBLE);
                }else{
                    gamePaused = false;
                    runTimer();
                    pauseScreen.setVisibility(View.INVISIBLE);
                }
            }//OnClick
        });
    } //PLAY

    private void setUI() {
        l1 = (Button) findViewById(R.id.light1);
        l2 = (Button) findViewById(R.id.light2);
        l3 = (Button) findViewById(R.id.light3);
        l4 = (Button) findViewById(R.id.light4);
        l5 = (Button) findViewById(R.id.light5);
        l6 = (Button) findViewById(R.id.light6);
        correctWindow = (Button) findViewById(R.id.correctWindow);
        scoreText = (TextView) findViewById(R.id.score);
        countdownText = (TextView) findViewById(R.id.countdown);
    } //SET LIGHTS

    private void runTimer(){
        countDownTimer = new CountDownTimer(milisecTime, 1000) {
            @Override
            public void onTick(long l) {
                milisecTime = l;
                int timeLeft = (int) milisecTime/1000;
                countdownText.setText(Integer.toString(timeLeft));
            }

            @Override
            public void onFinish() {
                saveData();
                Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                startActivity(intent);
            }
        }.start();
        timerRunning = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void randomCircle(){

        setUI();
        colorsFunc();
        correctWindow.setBackgroundColor(correctColor.getColorAdd());
        correctWindow.setVisibility(View.VISIBLE);
        Button[] buttonArray = {l1,l2,l3,l4,l5,l6};

        final int rand1 = new Random().nextInt(buttonArray.length);
        int rand2 = new Random().nextInt(buttonArray.length);

        if(rand2 == rand1){

            do {
                rand2 = new Random().nextInt(buttonArray.length);
            }while(rand2 == rand1);

        }

        for(int i = 0; i<buttonArray.length; i++){

            if(buttonArray[i] == buttonArray[rand1] || buttonArray[i] == buttonArray[rand2]){
                buttonArray[rand1].setVisibility(View.VISIBLE);
                buttonArray[rand1].getBackground().setColorFilter(correctColor);
                correctLight = buttonArray[rand1];


                buttonArray[rand2].setVisibility(View.VISIBLE);
                buttonArray[rand2].getBackground().setColorFilter(fakeColor);
                fakeLight = buttonArray[rand2];

            }else{
                buttonArray[i].setVisibility(View.INVISIBLE);
            }

        }

    }//RANDOM CIRCLE

    private void fullscreen(){
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        );
    }

    private void saveData() {
        preferences  = getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("score", scoreText.getText().toString());
        editor.commit();
    }

    @Override
    public void onResume(){
        fullscreen();
        super.onResume();
    }

    @Override
    public void onPause(){
        fullscreen();
        super.onPause();
    }

}


