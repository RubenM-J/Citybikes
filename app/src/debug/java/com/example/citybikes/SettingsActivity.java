package com.example.citybikes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.citybikes.utils.LocaleHelper;

public class SettingsActivity extends AppCompatActivity {

    Context context;
    //Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,
                        Dashboard.class);
                startActivity(intent);
            }
        });

        Switch auto = findViewById(R.id.automaticswitch);
        RadioButton dark = findViewById(R.id.darkradio);
        RadioButton light = findViewById(R.id.lightradio);
        RadioButton metric = findViewById(R.id.metricradio);
        RadioButton imperial = findViewById(R.id.imperialradio);
        RadioGroup values = findViewById(R.id.valuegroup);
        Spinner spinnerLanguages =findViewById(R.id.spinnerLanguages);

        ArrayAdapter<CharSequence>adapter= ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        spinnerLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch(Long.toString(id)) {
                    case "en":
                        context = LocaleHelper.setLocale(SettingsActivity.this, "en");
                        break;
                    case "be":
                        context = LocaleHelper.setLocale(SettingsActivity.this, "be");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                context = LocaleHelper.setLocale(SettingsActivity.this, "en");
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (auto.isChecked()){
                    dark.setTextColor(Color.GRAY);
                    light.setTextColor(Color.GRAY);
                    dark.setClickable(false);
                    light.setClickable(false);
                }
                else {
                    dark.setClickable(true );
                    dark.setTextColor(Color.BLACK);
                    light.setTextColor(Color.BLACK);
                    light.setClickable(true);
                }
            }
        });

        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.metric_values), Context.MODE_PRIVATE);
        values.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (metric.isChecked()){
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.metric_values), "true");
                    editor.apply();
                }
                else {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.metric_values), "false");
                    editor.apply();
                }
            }
        });

    }
}