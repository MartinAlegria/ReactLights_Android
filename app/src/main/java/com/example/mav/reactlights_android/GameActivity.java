package com.example.mav.reactlights_android;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    LightingColorFilter blue = new LightingColorFilter(0xFFFFFFFF, Color.rgb(9,54,188));
    LightingColorFilter red = new LightingColorFilter(0xFFFFFFFF, Color.rgb(188,9,9));
    LightingColorFilter green = new LightingColorFilter(0xFFFFFFFF, Color.rgb(9,188,90));
    LightingColorFilter cyan = new LightingColorFilter(0xFFFFFFFF, Color.rgb(9,188,179));
    LightingColorFilter yellow = new LightingColorFilter(0xFFFFFFFF, Color.rgb(226,198,13));
    LightingColorFilter correctColor;
    LightingColorFilter fakeColor;

    LightingColorFilter[] filterArray = {blue, red, green, cyan, yellow};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        randomCircle();
        int count = 0;

        play();


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
    }

    private void play() {
        correctLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomCircle();
                play();
            }
        });

        fakeLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomCircle();
                colorsFunc();
                play();
            }
        });
    }

    private void setLights() {
        l1 = (Button) findViewById(R.id.light1);
        l2 = (Button) findViewById(R.id.light2);
        l3 = (Button) findViewById(R.id.light3);
        l4 = (Button) findViewById(R.id.light4);
        l5 = (Button) findViewById(R.id.light5);
        l6 = (Button) findViewById(R.id.light6);

    }


    private void randomCircle(){

        setLights();
        colorsFunc();
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


}
