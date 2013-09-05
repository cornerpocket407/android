package com.imagesearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.imagesearch.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Activity {

    private Settings settings;
    private Spinner spImageSize;
    private EditText etSiteFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        settings = (Settings) getIntent().getSerializableExtra("settings");
        if (settings == null) {
            settings = new Settings();
        }

        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        ArrayAdapter<Settings.ImageSize> dataAdapter = new ArrayAdapter<Settings.ImageSize>(this,
                android.R.layout.simple_spinner_item, Settings.ImageSize.values());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(dataAdapter);
        if (settings != null) {
            int imageSizePos = dataAdapter.getPosition(settings.getImageSize());
            if (imageSizePos > -1) {
                spImageSize.setSelection(imageSizePos);
            }
            etSiteFilter.setText(settings.getSiteFilter() == null ? "" : settings.getSiteFilter().toString());
        }
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setImageSize((Settings.ImageSize) spImageSize.getSelectedItem());
                Uri siteFilter = Uri.parse(etSiteFilter.getText().toString());
                if (siteFilter != null) {
                    settings.setSiteFilter(siteFilter.toString());
                }
                Intent intent = new Intent(getApplicationContext(), ImageSearchActivity.class);
                intent.putExtra("settings", settings);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
    
}
