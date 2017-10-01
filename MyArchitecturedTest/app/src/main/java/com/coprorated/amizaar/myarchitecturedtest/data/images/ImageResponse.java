package com.coprorated.amizaar.myarchitecturedtest.data.images;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by amizaar on 03.07.2017.
 */

public class ImageResponse {
    private int total;
    private int totalHits;
    @SerializedName("hits")
    private ArrayList<Image> imagesList;

    public ImageResponse() {
    }

    public int getTotal() {
        return total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public ArrayList<Image> getImagesList() {
        return imagesList;
    }
}
