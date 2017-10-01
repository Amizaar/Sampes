package com.coprorated.amizaar.myarchitecturedtest.ui.image_detail;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.coprorated.amizaar.myarchitecturedtest.data.Response;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.repository.ImageDetailRepository;
import com.coprorated.amizaar.myarchitecturedtest.ui.NetworkLiveData;
import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amizaar on 02.08.2017.
 */

public class ImageDetailViewModel extends ViewModel {
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Response<Image>> mImageLiveData = new MutableLiveData<>();

    private NetworkLiveData mNetworkLiveData;
    private ImageDetailRepository mImageDetailRepository;

    private String mRequestString;

    @Inject
    ImageDetailViewModel(Application application, ImageDetailRepository imageDetailRepository) {
        mImageDetailRepository = imageDetailRepository;
        mNetworkLiveData = NetworkLiveData.getInstance(application.getApplicationContext());
    }

    LiveData<Response<Image>> getImagesListLiveData() {
        return mImageLiveData;
    }

    void loadImage(String id) {
        if (id == null || id.trim().length() == 0)
            throw new IllegalMonitorStateException("Image ID can`t be null");
        mRequestString = id;

        createLoadImageQuery(mRequestString);
    }

    private void createLoadImageQuery(String id) {
        mImageDetailRepository.loadDetailImage(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doAfterTerminate(() -> mIsLoading.postValue(false))
                .subscribe((image, throwable) -> {
                    if (image != null)
                        mImageLiveData.postValue(Response.success(image));
                    else
                        mImageLiveData.postValue(Response.error(throwable));
                });
    }


    LiveData<Boolean> isNetworkOnline() {
        return mNetworkLiveData;
    }

    LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    void update() {
        LogUtils.logd("ImageDetailViewModel update");
        this.mNetworkLiveData.update();
        createLoadImageQuery(mRequestString);
    }
}
