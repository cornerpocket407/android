package com.imagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ImageResult implements Serializable{
    private static final long serialVersionUID = 7101539191488484110L;

    private String fullUrl;
    private String thumbUrl;

    public ImageResult(JSONObject jsonObject) {
        try {
            this.fullUrl = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
        } catch (JSONException e) {
            fullUrl = null;
            thumbUrl = null;
        }
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String toString() {
        return String.format("thumbUrl:%s\tfullUrl:%s", thumbUrl, fullUrl);
    }

    public static Collection<? extends ImageResult> fromJSONArray(JSONArray jsonArray) {
        ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                imageResults.add(new ImageResult(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return imageResults;
    }
}
