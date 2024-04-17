package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private static final String EXTRA_WORD = "com.example.myapplication.EXTRA_WORD";

    private String word;

    private char[] hiddenWordArray;
    private int incorrectGuesses = 0;
    private static int MAX_INCORRECT_GUESSES = 6;
    private static final String PREFS_NAME = "HangmanPrefs"; //save the score in preferences
    private static final String PREFS_WINS = "wins";
    private static final String PREFS_LOSSES = "losses";
    private SharedPreferences prefs;
    private int wins;
    private int losses;


    public static void start(Context context, String word) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // init preferences
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // get loses and wins from preferences
        wins = prefs.getInt(PREFS_WINS, 0);
        losses = prefs.getInt(PREFS_LOSSES, 0);

        // get the word from the intent
        word = getIntent().getStringExtra(EXTRA_WORD);

        // init array with the word
        hiddenWordArray = generateHiddenWordArray(word);

        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(String.valueOf(hiddenWordArray));

        for (char letter = 'a'; letter <= 'z'; letter++) {
            final char finalLetter = letter;
            int buttonId = getResources().getIdentifier(String.valueOf(letter).toLowerCase(), "id", getPackageName());
            Button letterButton = findViewById(buttonId);

            letterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleLetterClick(finalLetter);
                }
            });
        }
        Button hintButton = findViewById(R.id.hintButton);
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint();
                hintButton.setEnabled(false); // unable btn
            }
        });
    }

    private void hint() {
        Random random = new Random();
        char letter;
        do {
            int randomIndex = random.nextInt(word.length());
            letter = word.charAt(randomIndex);
        } while (isLetterRevealed(letter));
        handleLetterClick(letter);
    }

    private boolean isLetterRevealed(char letter) {
        for (char c : hiddenWordArray) {
            if (c == letter) {
                return true;
            }
        }
        return false;
    }


    private void handleLetterClick(char letter) {
        boolean letterFound = false;


        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                // replace the underscore with the letter
                hiddenWordArray[i] = letter;
                letterFound = true;
            }
        }

        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(String.valueOf(hiddenWordArray));

        if (!letterFound) {
            incorrectGuesses++;
            updateHangmanImage(incorrectGuesses);
            if (incorrectGuesses >= MAX_INCORRECT_GUESSES) {
                losses++;
                prefs.edit().putInt(PREFS_LOSSES, losses).apply();
                showMessage("You Lose :c , Try again. Your current score is: " + wins + " wins and " + losses + " losses.");
                return;
            }
            disableButtonForLetter(letter);
        }

        updateWordRepresentation();

        if (!String.valueOf(hiddenWordArray).contains("_")) {
            wins++;
            prefs.edit().putInt(PREFS_WINS, wins).apply();
            showMessage("¡Congratulations! ¡You Win! Your current score is: " + wins + " wins and " + losses + " losses.");
        }

        Log.d("GameActivity", "Word: " + word);
        Log.d("GameActivity", "Actual Representation: " + String.valueOf(hiddenWordArray));

    }

    private void updateHangmanImage(int incorrectGuesses) {
        ImageView hangmanImageView = findViewById(R.id.hangmanImageView); // Asegúrate de tener un ImageView con este id en tu layout
        int resourceId = getResources().getIdentifier("hangman_" + incorrectGuesses, "drawable", getPackageName());
        hangmanImageView.setImageResource(resourceId);
    }

    private void updateWordRepresentation() {
        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(String.valueOf(hiddenWordArray));
    }

    private void disableButtonForLetter(char letter) {
        int buttonId = getResources().getIdentifier(String.valueOf(letter).toLowerCase(), "id", getPackageName());
        if (buttonId != 0) {
            Button letterButton = findViewById(buttonId);
            if (letterButton != null) {
                letterButton.setEnabled(false);
                letterButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cierra la actividad actual
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private char[] generateHiddenWordArray(String word) {
        // create an array with the same length as the word, filled with underscores
        char[] hiddenWordArray = new char[word.length()];
        Arrays.fill(hiddenWordArray, '_');
        return hiddenWordArray;
    }
}