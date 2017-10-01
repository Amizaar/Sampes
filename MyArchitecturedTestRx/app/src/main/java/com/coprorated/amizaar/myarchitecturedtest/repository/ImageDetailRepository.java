package com.coprorated.amizaar.myarchitecturedtest.repository;

import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.db.ImageDao;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by amizaar on 29.08.2017.
 */

public class ImageDetailRepository {

    private final ImageDao mImageDao;

    @Inject
    public ImageDetailRepository(ImageDao imageDao) {
        mImageDao = imageDao;
    }

    public Single<Image> loadDetailImage(String imageId) {
        return mImageDao.loadImageById(imageId);
    }
}
