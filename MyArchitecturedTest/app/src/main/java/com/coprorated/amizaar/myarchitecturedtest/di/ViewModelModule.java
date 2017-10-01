package com.coprorated.amizaar.myarchitecturedtest.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.coprorated.amizaar.myarchitecturedtest.ui.image_detail.ImageDetailViewModel;
import com.coprorated.amizaar.myarchitecturedtest.ui.images.ImagesViewModel;
import com.coprorated.amizaar.myarchitecturedtest.viewmodel.MyArchitecturedTestViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel.class)
    abstract ViewModel bindImagesViewModel(ImagesViewModel imagesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ImageDetailViewModel.class)
    abstract ViewModel bindImageDetailViewModel(ImageDetailViewModel imageDetailViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(MyArchitecturedTestViewModelFactory factory);
}
