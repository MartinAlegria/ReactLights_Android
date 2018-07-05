package com.example.mav.reactlights_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    TextView score;
    Button menu, retry;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        score = (TextView) findViewById(R.id.scoreText);
        score.setText(preferences.getString("score",""));

        menu = (Button) findViewById(R.id.menu);
        retry = (Button) findViewById(R.id.retry);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(intent);
    }


}
