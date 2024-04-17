package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class Scores extends AppCompatActivity {
    private static final String PREFS_NAME = "HangmanPrefs";
    private static final String PREFS_WINS = "wins";
    private static final String PREFS_LOSSES = "losses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int wins = prefs.getInt(PREFS_WINS, 0);
        int losses = prefs.getInt(PREFS_LOSSES, 0);

        TextView winsTextView = findViewById(R.id.winsTextView);
        TextView lossesTextView = findViewById(R.id.lossesTextView);

        winsTextView.setText("Wins: " + wins);
        lossesTextView.setText("Losses: " + losses);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Scores.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, Scores.class);
        context.startActivity(intent);
    }
}