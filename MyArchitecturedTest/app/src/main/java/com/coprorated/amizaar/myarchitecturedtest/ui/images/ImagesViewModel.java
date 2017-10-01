package com.coprorated.amizaar.myarchitecturedtest.ui.images;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.text.TextUtils;

import com.coprorated.amizaar.myarchitecturedtest.data.Resource;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.repository.ImageRepository;
import com.coprorated.amizaar.myarchitecturedtest.ui.NetworkLiveData;
import com.coprorated.amizaar.myarchitecturedtest.ui.common.RetryCallback;
import com.coprorated.amizaar.myarchitecturedtest.util.AbsentLiveData;
import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

import java.util.List;
import java.util.function.Predicate;

import javax.inject.Inject;

import io.reactivex.functions.Function;

/**
 * Created by amizaar on 02.08.2017.
 */

public class ImagesViewModel extends ViewModel {
    private MutableLiveData<String> mRequestString = new MutableLiveData<>();
    private LiveData<Resource<List<Image>>> mImagesListLiveData;
    private NetworkLiveData mNetworkLiveData;

    @Inject
    ImagesViewModel(Application application, ImageRepository imageRepository) {
        mNetworkLiveData = NetworkLiveData.getInstance(application.getApplicationContext());
        mImagesListLiveData = Transformations.switchMap(mRequestString, request -> {
            if (request == null || request.trim().length() == 0) {
                return AbsentLiveData.create();
            } else {
                return imageRepository.loadImages(request);
            }
        });
    }

    LiveData<Resource<List<Image>>> getImagesListLiveData() {
        return mImagesListLiveData;
    }

    void serRequestString(String request) {
        mRequestString.postValue(request);
    }

    public LiveData<Boolean> isNetworkOnline() {
        return mNetworkLiveData;
    }

    void retry() {
        this.mNetworkLiveData.update();
        if (this.mRequestString.getValue() != null) {
            this.mRequestString.setValue(this.mRequestString.getValue());
        }
    }
}
