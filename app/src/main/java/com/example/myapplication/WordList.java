package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WordList {
    private List<String> easyWords;
    private List<String> hardWords;
    private String lastWord;

    public WordList() {
        easyWords = Arrays.asList("door", "house", "silence", "chair", "table", "lamp", "couch", "bed", "window", "mirror", "picture", "television", "kitchen");
        hardWords = Arrays.asList("abruptly", "absurd", "abyss", "affix", "askew", "avenue", "awkward", "axiom", "azure", "bagpipes", "bandwagon", "banjo", "bayou", "beekeeper", "bikini", "blitz", "blizzard", "boggle", "bookworm", "boxcar", "boxful");
        lastWord = "";
    }

    public String getRandomWord(String difficulty) {
        Random random = new Random();
        String word;
        do {
            if (difficulty.equals("easy")) {
                word = easyWords.get(random.nextInt(easyWords.size()));
            } else {
                word = hardWords.get(random.nextInt(hardWords.size()));
            }
        } while (word.equals(lastWord));
        lastWord = word;
        return word;
    }
}
