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
import android.widget.Toast;

import com.imagesearch.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Activity {

    private Settings settings;
    private Spinner spImageSize;
    private EditText etSiteFilter;
    private Spinner spColorFilter;
    private Spinner spImageType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        settings = (Settings) getIntent().getSerializableExtra("settings");
        if (settings == null) {
            settings = new Settings();
        }

        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);

        ArrayAdapter<Settings.ImageSize> imageSizeAdapater = new ArrayAdapter<Settings.ImageSize>(this,
                android.R.layout.simple_spinner_item, Settings.ImageSize.values());
        imageSizeAdapater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(imageSizeAdapater);

        ArrayAdapter<Settings.ColorFilter> colorFilterAdapter = new ArrayAdapter<Settings.ColorFilter>(this,
                android.R.layout.simple_spinner_item, Settings.ColorFilter.values());
        colorFilterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColorFilter.setAdapter(colorFilterAdapter);

        ArrayAdapter<Settings.ImageType> imageTypeAdapter = new ArrayAdapter<Settings.ImageType>(this,
                android.R.layout.simple_spinner_item, Settings.ImageType.values());
        imageTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(imageTypeAdapter);

        int imageSizePos = imageSizeAdapater.getPosition(settings.getImageSize());
        if (imageSizePos > -1) {
            spImageSize.setSelection(imageSizePos);
        }
        int colorFilterPos = colorFilterAdapter.getPosition(settings.getColorFilter());
        if (colorFilterPos > -1) {
            spColorFilter.setSelection(colorFilterPos);
        }
        int imageTypePos = imageTypeAdapter.getPosition(settings.getImageType());
        if (imageTypePos > -1) {
            spImageType.setSelection(imageTypePos);
        }
        etSiteFilter.setText(settings.getSiteFilter() == null ? "" : settings.getSiteFilter().toString());

        Button btnSave = (Button) findViewById(R.id.btnSave);
    }

    public void onSave(View v) {
        settings.setImageSize((Settings.ImageSize) spImageSize.getSelectedItem());
        settings.setColorFilter((Settings.ColorFilter) spColorFilter.getSelectedItem());
        settings.setImageType((Settings.ImageType) spImageType.getSelectedItem());

        try {
            URI siteFilter = URI.create(etSiteFilter.getText().toString());
            settings.setSiteFilter(siteFilter.toString());
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), R.string.invalid_url, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("settings", settings);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }
}
