package com.coprorated.amizaar.myarchitecturedtest.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.coprorated.amizaar.myarchitecturedtest.AppExecutors;
import com.coprorated.amizaar.myarchitecturedtest.data.Resource;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.data.images.ImageResponse;
import com.coprorated.amizaar.myarchitecturedtest.db.ImageDao;
import com.coprorated.amizaar.myarchitecturedtest.api.ApiResponse;
import com.coprorated.amizaar.myarchitecturedtest.api.ImageApiService;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amizaar on 29.08.2017.
 */

public class ImageRepository {

//    private final ImageDb mImageDb;

    private final ImageDao mImageDao;

    private final ImageApiService mImageApiService;

    private final AppExecutors mAppExecutors;

    @Inject
    public ImageRepository(AppExecutors appExecutors,
                           ImageDao imageDao,
                           ImageApiService imageApiService) {
        mAppExecutors = appExecutors;
        mImageDao = imageDao;
        mImageApiService = imageApiService;
    }

    public LiveData<Resource<List<Image>>> loadImages(String query) {
        return new NetworkBoundResource<List<Image>, ImageResponse>(mAppExecutors) {
            @Override
            protected void saveCallResult(@NonNull ImageResponse result) {
                mImageDao.insertImages(result.getImagesList());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Image> data) {
                return data == null || data.size() == 0;
            }

            @NonNull
            @Override
            protected LiveData<List<Image>> loadFromDb() {
                return mImageDao.loadImages();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ImageResponse>> createCall() {
                return mImageApiService.requestImages(query);
            }
        }.asLiveData();
    }
}
