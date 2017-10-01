package com.coprorated.amizaar.myarchitecturedtest.ui.images;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coprorated.amizaar.myarchitecturedtest.R;
import com.coprorated.amizaar.myarchitecturedtest.bindings.FragmentDataBindingComponent;
import com.coprorated.amizaar.myarchitecturedtest.data.Status;
import com.coprorated.amizaar.myarchitecturedtest.databinding.ImagesFragmentBinding;
import com.coprorated.amizaar.myarchitecturedtest.di.Injectable;
import com.coprorated.amizaar.myarchitecturedtest.ui.common.NavigationController;
import com.coprorated.amizaar.myarchitecturedtest.util.AutoClearedValue;
import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

import javax.inject.Inject;

/**
 */
public class ImagesFragment extends Fragment implements LifecycleRegistryOwner, Injectable{
    private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private ImagesViewModel mImagesViewModel;

    @Inject
    NavigationController navigationController;

    private AutoClearedValue<ImagesFragmentBinding> mBinding;
    private AutoClearedValue<ImagesListAdapter> mAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this.getActivity().getApplicationContext());
        mImagesViewModel = ViewModelProviders.of(this, viewModelFactory).get(ImagesViewModel.class);
        mImagesViewModel.isNetworkOnline().observe(this, isOnline -> {
            if (Boolean.FALSE.equals(isOnline))
                Snackbar.make(mBinding.get().getRoot(),
                        getString(R.string.snackbar_no_internet_connection_error_text),
                        Snackbar.LENGTH_LONG).show();
        });
        mImagesViewModel.serRequestString("cats"); //todo move into bindings into editText/dialog

        ImagesListAdapter adapter = new ImagesListAdapter(dataBindingComponent, (image, view) -> {
            navigationController.navigateToImageDetails(image.getId(), view);
        });
        mBinding.get().imageList.setAdapter(adapter);
        mAdapter = new AutoClearedValue<>(this, adapter);
        mImagesViewModel.getImagesListLiveData().observe(this, listResource -> { //todo produce leak memory o_0
            LogUtils.logd(listResource == null ? "null" : listResource.toString());
            if (listResource == null) {
                return; //todo check
            }
            if(listResource.status == Status.LOADING) {
                mBinding.get().imageSrl.setRefreshing(true);
            } else {
                mBinding.get().imageSrl.setRefreshing(false);
                if (listResource.status == Status.ERROR) {
                    Snackbar.make(mBinding.get().getRoot(),
                            listResource.message == null ?
                                getString(R.string.snackbar_image_error_loading_text) :
                                listResource.message,
                            Snackbar.LENGTH_LONG).show();
                } else {
                    mAdapter.get().replace(listResource.data);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ImagesFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.images_fragment, container, false);
        LogUtils.logd("onCreateView " + getActivity());
        dataBinding.setRetryCallback(() -> mImagesViewModel.retry());
        dataBinding.imageList.setLayoutManager(new GridLayoutManager(getContext(), 3)); //todo think about move spanCount to xml
        mBinding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }
}
