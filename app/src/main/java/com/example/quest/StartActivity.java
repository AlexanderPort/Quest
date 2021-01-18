package com.example.quest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button newGameButton;
    Button continueGameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        newGameButton = (Button)findViewById(R.id.new_game);
        continueGameButton = (Button)findViewById(R.id.continue_game);
        newGameButton.setOnClickListener(this);
        continueGameButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.new_game) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("status", Status.NEW_GAME);
            startActivity(intent);
        } else if (id == R.id.continue_game) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("status", Status.CONTINUE_GAME);
            startActivity(intent);
        }
    }

}