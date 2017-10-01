package com.coprorated.amizaar.myarchitecturedtest.repository;

import android.support.annotation.NonNull;

import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.data.images.ImageResponse;
import com.coprorated.amizaar.myarchitecturedtest.db.ImageDao;
import com.coprorated.amizaar.myarchitecturedtest.data.Response;
import com.coprorated.amizaar.myarchitecturedtest.api.ImageApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by amizaar on 29.08.2017
 */

public class ImagesRepository {

    private final ImageDao mImageDao;

    private final ImageApiService mImageApiService;

    @Inject
    public ImagesRepository(ImageDao imageDao,
                            ImageApiService imageApiService) {
        mImageDao = imageDao;
        mImageApiService = imageApiService;
    }

/*    public Observable<Response<List<Image>>> loadImages(String query) {
        return mImageApiService.requestImages(query)
                .map(response -> Response.success(response.getImagesList()))
                .doOnNext(imagesResponse -> {
                    List<Image> dbImages = mImageDao.loadImages();
                    mImageDao.removeAll(dbImages);
                    mImageDao.insertImages(imagesResponse.data);
                })
                .onErrorResumeNext(throwable -> {
                        List<Image> dbImages = mImageDao.loadImages();
                        Response<List<Image>> apiResponse = Response.error(throwable, dbImages);
                        return Observable.just(apiResponse);
                });

    }*/

    public Observable<Response<List<Image>>> loadImages(String query) {
        return new NetworkBoundResource<List<Image>, ImageResponse>() {
            @NonNull
            @Override
            protected List<Image> processResponse(ImageResponse response) {
                return response.getImagesList();
            }

            @Override
            protected void saveCallResult(List<Image> result) {
                List<Image> dbImages = mImageDao.loadImages();
                mImageDao.removeAll(dbImages);
                mImageDao.insertImages(result);
            }

            @NonNull
            @Override
            protected List<Image> loadFromDb() {
                return mImageDao.loadImages();
            }

            @NonNull
            @Override
            protected Observable<ImageResponse> createCall() {
                return mImageApiService.requestImages(query);
            }
        }.asObservable();
    }
}
