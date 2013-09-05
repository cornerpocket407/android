package com.imagesearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchActivity extends Activity {

    private EditText etQuery;
    private GridView gvResults;
    private List<ImageResult> imageResults = new ArrayList<ImageResult>();
    private ImageAdapter adapter;
    private Settings settings;
    private int currentPage;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Toast.makeText(this, "Settings Saved.",
                    Toast.LENGTH_SHORT).show();
            settings = (Settings) data.getSerializableExtra("settings");
        }
    }

    private void setupView() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        Button btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("settings", settings);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        adapter = new ImageAdapter(this, imageResults);
        gvResults.setAdapter(adapter);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(position);
                intent.putExtra("result", imageResult);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_search, menu);
        return true;
    }

    public void onLoadMore(View v) {
        if (imageResults.isEmpty()) {
            Toast.makeText(this, "Please enter a query first.", Toast.LENGTH_SHORT).show();
            return;
        }
        currentPage+=8;
        String query = etQuery.getText().toString();
        imageSearch(query);
    }

    public void onImageSearch(View v) {
        currentPage = 0;
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Search for " + query, Toast.LENGTH_SHORT).show();
        imageSearch(query);
    }

    private void imageSearch(String query) {
        AsyncHttpClient client = new AsyncHttpClient();
        StringBuilder sb = new StringBuilder(String.format("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s&rsz=8&start=%s", Uri.encode(query), currentPage));
        if (settings != null) {
            if (settings.getImageSize() != null) {
                sb.append("&imgsz=").append(settings.getImageSize().getUrlValue());
            }
            if (settings.getColorFilter() != null) {
                sb.append("&imgcolor=").append(settings.getColorFilter().getUrlValue());
            }
            if (settings.getSiteFilter() != null) {
                sb.append("&as_sitesearch=").append(settings.getSiteFilter().toString());
            }
            if (settings.getImageType() != null) {
                sb.append("&imgtype=").append(settings.getImageType().getUrlValue());
            }
        }
        client.get(sb.toString(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    adapter.addAll(ImageResult.fromJSONArray(jsonArray));
                    Log.d("DEBUG", imageResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
