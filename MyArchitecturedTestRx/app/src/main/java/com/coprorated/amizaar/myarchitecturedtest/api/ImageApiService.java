package com.coprorated.amizaar.myarchitecturedtest.api;

import android.arch.lifecycle.LiveData;

import com.coprorated.amizaar.myarchitecturedtest.data.images.ImageResponse;


import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 */
public interface ImageApiService {

    @GET("/api/?image_type=photo")
    Observable<ImageResponse> requestImages(@Query("q") String query);
}
