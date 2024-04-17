package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private WordList wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordList = new WordList();

        // easy game button
        Button easyGameButton = findViewById(R.id.easyGameButton);
        easyGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = wordList.getRandomWord("easy");
                GameActivity.start(MainActivity.this, word);
            }
        });

        // difficult game button
        Button hardGameButton = findViewById(R.id.hardGameButton);
        hardGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = wordList.getRandomWord("hard");
                GameActivity.start(MainActivity.this, word);
            }
        });

        // scores button
        Button viewScoresButton = findViewById(R.id.viewScoresButton);
        viewScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scores.start(MainActivity.this);
            }
        });
    }
    private void startGame(String difficulty) {
        String word = wordList.getRandomWord(difficulty);
        GameActivity.start(this, word);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}