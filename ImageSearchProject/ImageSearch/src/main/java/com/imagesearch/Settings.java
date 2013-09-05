package com.imagesearch;

import android.net.Uri;

import java.io.Serializable;

public class Settings implements Serializable{
    private static final long serialVersionUID = 6588072321026679895L;

    private ImageSize imageSize;
    private ColorFilter colorFilter;
    private String siteFilter;

    public ImageSize getImageSize() {
        return imageSize;
    }

    public void setImageSize(ImageSize imageSize) {
        this.imageSize = imageSize;
    }

    public ColorFilter getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    public enum ImageSize {
        SMALL("small"), MEDIUM("medium"), LARGE("large"), XLARGE("xlarge");

        private String urlValue;

        private ImageSize(String urlValue) {
            this.urlValue = urlValue;
        }

        public String getUrlValue() {
            return urlValue;
        }
    }

    public enum ColorFilter {
        BLUE, BROWN, BLACK, GRAY
    }
}
