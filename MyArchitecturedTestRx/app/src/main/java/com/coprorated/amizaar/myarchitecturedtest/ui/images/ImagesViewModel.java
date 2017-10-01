package com.coprorated.amizaar.myarchitecturedtest.ui.images;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.coprorated.amizaar.myarchitecturedtest.data.Response;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.repository.ImagesRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amizaar on 02.08.2017.
 */

public class ImagesViewModel extends ViewModel {
    private MutableLiveData<Response<List<Image>>> mImagesListLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
//    private NetworkLiveData mNetworkLiveData;

    private String mRequestString;
    private ImagesRepository mImagesRepository;

    @Inject
    ImagesViewModel(Application application, ImagesRepository imagesRepository) {
        mImagesRepository = imagesRepository;
//        mNetworkLiveData = NetworkLiveData.getInstance(application.getApplicationContext());
    }

    LiveData<Response<List<Image>>> getImagesListLiveData() {
        return mImagesListLiveData;
    }

    LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    void setRequestString(String request) {
        mRequestString = request;
    }

    void loadImages() {
        mImagesRepository.loadImages(mRequestString)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.setValue(true))
                .doAfterTerminate(() -> mIsLoading.setValue(false))
                .subscribe(
                        mImagesListLiveData::setValue,
                        throwable -> mImagesListLiveData.setValue(Response.error(throwable, null))
                );
    }

//    public LiveData<Boolean> isNetworkOnline() {
//        return mNetworkLiveData;
//    }

    void retry() {
//        this.mNetworkLiveData.update();
        loadImages();
    }
}
