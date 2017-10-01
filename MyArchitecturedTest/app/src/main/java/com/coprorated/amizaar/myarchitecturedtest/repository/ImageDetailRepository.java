package com.coprorated.amizaar.myarchitecturedtest.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.coprorated.amizaar.myarchitecturedtest.AppExecutors;
import com.coprorated.amizaar.myarchitecturedtest.api.ApiResponse;
import com.coprorated.amizaar.myarchitecturedtest.api.ImageApiService;
import com.coprorated.amizaar.myarchitecturedtest.data.Resource;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.data.images.ImageResponse;
import com.coprorated.amizaar.myarchitecturedtest.db.ImageDao;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amizaar on 29.08.2017.
 */

public class ImageDetailRepository {

    private final ImageDao mImageDao;

    private final AppExecutors mAppExecutors;

    @Inject
    public ImageDetailRepository(AppExecutors appExecutors,
                                 ImageDao imageDao) {
        mAppExecutors = appExecutors;
        mImageDao = imageDao;
    }

    public LiveData<Image> loadDetailImage(String imageId) {
        return mImageDao.loadImageById(imageId);
    }
}
