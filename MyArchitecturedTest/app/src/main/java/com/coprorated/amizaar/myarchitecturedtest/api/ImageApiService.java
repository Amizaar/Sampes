package com.coprorated.amizaar.myarchitecturedtest.api;

import android.arch.lifecycle.LiveData;

import com.coprorated.amizaar.myarchitecturedtest.data.images.ImageResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 */
public interface ImageApiService {

    @GET("/api/?image_type=photo")
    LiveData<ApiResponse<ImageResponse>> requestImages(@Query("q") String query);
}
