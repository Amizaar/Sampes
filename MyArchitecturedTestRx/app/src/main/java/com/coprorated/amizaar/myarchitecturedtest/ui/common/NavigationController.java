/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.coprorated.amizaar.myarchitecturedtest.ui.common;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.coprorated.amizaar.myarchitecturedtest.MainActivity;
import com.coprorated.amizaar.myarchitecturedtest.R;
import com.coprorated.amizaar.myarchitecturedtest.ui.image_detail.ImageDetailFragment;
import com.coprorated.amizaar.myarchitecturedtest.ui.images.ImagesFragment;
import com.coprorated.amizaar.myarchitecturedtest.util.LogUtils;

import javax.inject.Inject;

/**
 * A utility class that handles navigation in {@link MainActivity}.
 */
public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;
    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
        LogUtils.logd("navigationControler create " + fragmentManager.getFragments().size()); //todo check, remove after
    }

    public void navigateToImageList() {
        ImagesFragment fragment = new ImagesFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void navigateToImageDetails(String imageId, View sharedImageView) {
        String tag = "image" + "/" + imageId;
        String transactionName = ViewCompat.getTransitionName(sharedImageView);
        ImageDetailFragment imageDetailFragment = ImageDetailFragment.newInstance(imageId, transactionName);
        fragmentManager.beginTransaction()
                .addSharedElement(sharedImageView, transactionName)
                .add(containerId, imageDetailFragment, tag)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
