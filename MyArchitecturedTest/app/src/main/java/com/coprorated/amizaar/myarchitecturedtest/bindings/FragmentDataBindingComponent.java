package com.coprorated.amizaar.myarchitecturedtest.bindings;

import android.content.Context;
import android.databinding.DataBindingComponent;

/**
 * Created by amizaar on 12.09.2017.
 */

public class FragmentDataBindingComponent implements DataBindingComponent {
    private FragmentBindingAdapters mFragmentBindingAdapters;

    public FragmentDataBindingComponent(Context context) {
        mFragmentBindingAdapters = new FragmentBindingAdapters(context);
    }

    public FragmentBindingAdapters getFragmentBindingAdapters() {
        return mFragmentBindingAdapters;
    }
}
