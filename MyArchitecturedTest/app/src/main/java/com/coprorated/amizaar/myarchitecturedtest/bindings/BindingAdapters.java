package com.coprorated.amizaar.myarchitecturedtest.bindings;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by amizaar on 12.09.2017.
 */

public class BindingAdapters {

    @BindingAdapter("visibleGone")
    public static void showHide(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("visibleInvisible")
    public static void showHide2(View view, boolean show) {
        view.setVisibility(show ? View.INVISIBLE : View.GONE);
    }
}
