package com.imagesearch;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchActivity extends Activity {

    private EditText etQuery;
    private GridView gvResults;
    private Button btnSearch;
    private List<ImageResult> imageResults = new ArrayList<ImageResult>();
    private ArrayAdapter<ImageResult> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        adapter = new ArrayAdapter<ImageResult>(this, android.R.layout.simple_list_item_1, imageResults);
        gvResults.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_search, menu);
        return true;
    }

    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Search for " + query, Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = String.format("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s&q=android", Uri.encode(query));
        client.get(url, new JsonHttpResponseHandler() {
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
