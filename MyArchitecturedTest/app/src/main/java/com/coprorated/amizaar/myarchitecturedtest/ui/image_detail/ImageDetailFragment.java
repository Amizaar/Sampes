package com.coprorated.amizaar.myarchitecturedtest.ui.image_detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coprorated.amizaar.myarchitecturedtest.R;
import com.coprorated.amizaar.myarchitecturedtest.bindings.FragmentDataBindingComponent;
import com.coprorated.amizaar.myarchitecturedtest.databinding.ImageDetailFragmentBinding;
import com.coprorated.amizaar.myarchitecturedtest.di.Injectable;
import com.coprorated.amizaar.myarchitecturedtest.ui.common.NavigationController;
import com.coprorated.amizaar.myarchitecturedtest.util.AutoClearedValue;
import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

import javax.inject.Inject;

/**
 */
public class ImageDetailFragment extends Fragment implements LifecycleRegistryOwner, Injectable{
    private final static String IMAGE_ID_KEY = "image_key";
    private final static String IMAGE_TRANSACTION_KEY = "image_tran_key";

    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ImageDetailViewModel mImageDetailViewModel;

    @Inject
    NavigationController navigationController;

    private AutoClearedValue<ImageDetailFragmentBinding> mBinding;

    public static ImageDetailFragment newInstance(String imageId, String imageTransactionName) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_ID_KEY, imageId);
        bundle.putString(IMAGE_TRANSACTION_KEY, imageTransactionName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImageDetailViewModel = ViewModelProviders.of(this,
                viewModelFactory).get(ImageDetailViewModel.class);

        mImageDetailViewModel.isNetworkOnline().observe(this, isOnline -> {
            LogUtils.logd("isOnline " + isOnline);
            if (Boolean.FALSE.equals(isOnline))
                Snackbar.make(mBinding.get().getRoot(),
                        getString(R.string.snackbar_no_internet_connection_error_text),
                        Snackbar.LENGTH_LONG).show();
        });
        mImageDetailViewModel.setImageId(getArguments().getString(IMAGE_ID_KEY));
        mImageDetailViewModel.getImagesListLiveData().observe(this, image -> {
            mBinding.get().imageSrl.setRefreshing(false);
            mBinding.get().setImage(image);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(
                this.getActivity().getApplicationContext());

        ImageDetailFragmentBinding dataBinding = DataBindingUtil.inflate(
                inflater, R.layout.image_detail_fragment,
                container, false, dataBindingComponent);
        dataBinding.setRetryCallback(() -> mImageDetailViewModel.update());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dataBinding.imageDetailIv.setTransitionName(getArguments().getString(IMAGE_TRANSACTION_KEY));
        }
        mBinding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
