package com.example.Kernopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameMainActivity extends AppCompatActivity {
TextView welcomeuser;
Button playbutton, rulesbutton, historybutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamemain);

        welcomeuser = findViewById(R.id.Welcomeuser);
        playbutton = findViewById(R.id.PlayButton);
        rulesbutton = findViewById(R.id.RulesButton);
        historybutton = findViewById(R.id.HistoryButton);

        //add logged in user to welcome message
        welcomeuser.append(getIntent().getStringExtra("logged_user"));

        //Onclick listener for rules button
        rulesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startRules = new Intent(view.getContext(), RulesActivity.class);
                startActivity(startRules);
            }
        });
        //onclick listener for play button
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startQuiz = new Intent(view.getContext(), QuizActivity.class);
                startActivity(startQuiz);
            }
        });

        //onclick listener for quiz history button
        historybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent starthistory = new Intent(view.getContext(), GameHistory.class);
                startActivity(starthistory);
            }
        });




    }
}