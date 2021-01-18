package com.example.quest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button menuButton;
    LinearLayout scrollLayout;
    Repository repository;
    ArrayList<View> buttons;
    ArrayList<Action> actions;
    LinearLayout linearLayout;
    TextView descriptionTextView;
    ColorStateList colorStateList;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            repository = new Repository(this, "data.xml");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled},
                new int[] { android.R.attr.state_pressed}
        };
        int[] colors = new int[] {
                Color.argb(50, 153, 255, 153),
                Color.argb(100, 153, 255, 153)
        };

        colorStateList = new ColorStateList(states, colors);

        Intent intent = getIntent();
        String startLocationId = getSavedLocationId();
        String status = intent.getStringExtra("status");
        if (status.equals(Status.NEW_GAME)) {
            startLocationId = "1";
        }

        buttons = new ArrayList<>();
        actions = new ArrayList<>();
        scrollLayout = (LinearLayout)findViewById(R.id.scroll_view);
        linearLayout = (LinearLayout)findViewById(R.id.buttons);
        descriptionTextView = (TextView)findViewById(R.id.description);
        createButtons(repository.getLocation(startLocationId));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createButtons(Location location) {
        actions = location.getActions();
        descriptionTextView.setText(location.getDescription());
        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            Button button = new Button(this);
            button.setTextColor(Color.argb(255, 0, 153, 51));
            button.setBackgroundTintList(colorStateList);

            //button.setBackgroundColor(Color.argb(50, 153, 255, 153));
            button.setText(action.getDescription());
            button.setOnClickListener(this);
            linearLayout.addView(button);
            buttons.add(button);
        }
        menuButton = new Button(this);
        menuButton.setTextColor(Color.argb(255, 0, 153, 51));
        menuButton.setBackgroundTintList(colorStateList);
        menuButton.setText("Вернуться в меню");
        menuButton.setOnClickListener(this);
        menuButton.setGravity(Gravity.CENTER);
        menuButton.setId(R.id.menu);
        menuButton.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

        scrollLayout.addView(menuButton);

    }
    private void deleteButtons() {
        actions = new ArrayList<>();
        buttons = new ArrayList<>();
        linearLayout.removeAllViews();
        scrollLayout.removeView(menuButton);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (buttons.contains(v)) {
            int index = buttons.indexOf(v);
            Action action = actions.get(index);
            String nextLocationId = action.getNextLocationId();
            if (!nextLocationId.equals("1")) {
                saveLocationId(nextLocationId);
            }
            Location location = repository.getLocation(nextLocationId);
            deleteButtons();
            createButtons(location);
        } else if (v.getId() == R.id.menu) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
        }
    }
    public void saveLocationId(String locationId) {
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("savedLocationId", locationId);
        editor.apply();
    }
    public String getSavedLocationId() {
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(MainActivity.this);
        return sharedPreferences.getString("savedLocationId", "1");
    }
}