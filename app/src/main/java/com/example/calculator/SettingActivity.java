package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;


public class SettingActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener, Constants {

    private static SharedPreferences myTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Switch switchTheme = findViewById(R.id.switchTheme);
        myTheme = getSharedPreferences(THEME, MODE_PRIVATE);
        initWidgets(switchTheme);
        initTheme();
    }

    private void initWidgets(Switch switchTheme) {
        if (switchTheme != null) {
            switchTheme.setOnCheckedChangeListener(this);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            myTheme.edit().putInt(KEY_THEME, THEME_DARK).apply();
        } else {
            myTheme.edit().putInt(KEY_THEME, THEME_LIGHT).apply();
        }
        initTheme();
    }

    private void initTheme() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
//            themeSystem.visibility = View.VISIBLE
//        } else {
//            themeSystem.visibility = View.GONE
//        }
        switch (getSavedTheme()) {
            case THEME_LIGHT:
                setTheme(AppCompatDelegate.MODE_NIGHT_NO, THEME_LIGHT);
                break;
            case THEME_DARK:
                setTheme(AppCompatDelegate.MODE_NIGHT_YES, THEME_DARK);
                break;
            case THEME_UNDEFINED:
                setTheme(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    private int getSavedTheme() {
        return myTheme.getInt(KEY_THEME, THEME_UNDEFINED);
    }

    private static void setTheme(int themeMode, int prefsMode) {
        AppCompatDelegate.setDefaultNightMode(themeMode);
        saveTheme(prefsMode);
    }

    private static void saveTheme(int theme) {
        myTheme.edit().putInt(KEY_THEME, theme).apply();
    }
}