package com.coprorated.amizaar.myarchitecturedtest.ui.image_detail;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.coprorated.amizaar.myarchitecturedtest.data.Resource;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.repository.ImageDetailRepository;
import com.coprorated.amizaar.myarchitecturedtest.repository.ImageRepository;
import com.coprorated.amizaar.myarchitecturedtest.ui.NetworkLiveData;
import com.coprorated.amizaar.myarchitecturedtest.util.AbsentLiveData;
import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by amizaar on 02.08.2017.
 */

public class ImageDetailViewModel extends ViewModel {
    private MutableLiveData<String> mRequestString = new MutableLiveData<>();
    private LiveData<Image> mImagesListLiveData;
    private NetworkLiveData mNetworkLiveData;

    @Inject
    ImageDetailViewModel(Application application, ImageDetailRepository imageDetailRepository) {
        mNetworkLiveData = NetworkLiveData.getInstance(application.getApplicationContext());
        mImagesListLiveData = Transformations.switchMap(mRequestString, request -> {
            if (request == null || request.trim().length() == 0) {
                throw new IllegalMonitorStateException("Image ID can`t be null");
            } else {
                return imageDetailRepository.loadDetailImage(request);
            }
        });
    }

    LiveData<Image> getImagesListLiveData() {
        return mImagesListLiveData;
    }

    void setImageId(String id) {
        mRequestString.postValue(id);
    }

    LiveData<Boolean> isNetworkOnline() {
        return mNetworkLiveData;
    }

    void update() {
        LogUtils.logd("ImageDetailViewModel update");
        this.mNetworkLiveData.update();
        if (this.mRequestString.getValue() != null) {
            this.mRequestString.setValue(this.mRequestString.getValue());
        }
    }
}
