package com.coprorated.amizaar.myarchitecturedtest.ui.images;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coprorated.amizaar.myarchitecturedtest.R;
import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;
import com.coprorated.amizaar.myarchitecturedtest.databinding.ImageItemBinding;
import com.coprorated.amizaar.myarchitecturedtest.ui.common.DataBoundListAdapter;
import com.coprorated.amizaar.myarchitecturedtest.util.ObjectUtils;

import java.util.Objects;

/**
 * Created by amizaar on 11.09.2017.
 */

public class ImagesListAdapter extends DataBoundListAdapter<Image, ImageItemBinding>{
    private final DataBindingComponent mDataBindingComponent;
    private final ImageClickCallback mImageClickCallback;

    public interface ImageClickCallback {
        void onClick(Image image, View view);
    }

    ImagesListAdapter(DataBindingComponent dataBindingComponent, ImageClickCallback imageClickCallback) {
        this.mDataBindingComponent = dataBindingComponent;
        this.mImageClickCallback = imageClickCallback;
    }

    @Override
    protected ImageItemBinding createBinding(ViewGroup parent) {
        ImageItemBinding imageItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.image_item,
                parent, false, mDataBindingComponent);
        imageItemBinding.imageIv.setOnClickListener(view -> {
            mImageClickCallback.onClick(imageItemBinding.getImage(), view);
        });
        return imageItemBinding;
    }

    @Override
    protected void bind(ImageItemBinding binding, Image item) {
        binding.setImage(item);
        ViewCompat.setTransitionName(binding.imageIv, item.getId());
    }

    @Override
    protected boolean areItemsTheSame(Image oldItem, Image newItem) {
        return ObjectUtils.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(Image oldItem, Image newItem) {
        return ObjectUtils.equals(oldItem.getPreviewURL(), newItem.getPreviewURL());
    }
}
