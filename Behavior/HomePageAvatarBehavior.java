package com.test.app.homepage.user.drawer.behavior;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.Space;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.test.app.R;
import com.test.app.z_extra.custom_view.FrescoBorderGradient;

/**
 * Created by user on 9/19/16.
 */
public class HomePageAvatarBehavior extends CoordinatorLayout.Behavior<FrescoBorderGradient> {
    private Context mContext;

    private int mChildHeight;
    private int mChildStartY;
    private int mParentStartY;
    private int mEndAnimationPoint = 0;
    private int mMinSize;
    private int mMaxScrollDistance;
    private int mMinChildY;

    private int mDeltaChildDistanse;
    private int mDeltaChildSize;

    private float mHeaderSize;


    public HomePageAvatarBehavior(Context context, AttributeSet attrs) {
        mContext = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ImageCoordinatorBehavior);
            mHeaderSize = a.getDimension(R.styleable.ImageCoordinatorBehavior_headerSize, 0);

            a.recycle();
        }
        mMinSize = (int) (2 * mHeaderSize / 3);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FrescoBorderGradient child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FrescoBorderGradient child, View dependency) {
        maybeInitProperties(child, dependency);

        float expandedPercentageFactor = (dependency.getY() - mMinChildY) / (mMaxScrollDistance - mMinChildY); //сколько прокрутили parent

        Log.d("Amizaar", "onDependentViewChanged " + mMaxScrollDistance + ", expandedPercentageFactor " + expandedPercentageFactor + ", dependency.getY() " + dependency.getY());
        if (expandedPercentageFactor >= 1) {
            child.setY(mChildStartY);

            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            lp.width = mChildHeight;
            lp.height = mChildHeight;
            child.setLayoutParams(lp);
        } else {

            if (dependency.getY() > mMinChildY) {
                child.setY(mChildStartY - mDeltaChildDistanse * (1 - expandedPercentageFactor));

                float heightToSubtract = mChildHeight  - mDeltaChildSize * (1 - expandedPercentageFactor);

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                lp.width = (int) (heightToSubtract);
                lp.height = (int) (heightToSubtract);
                child.setLayoutParams(lp);
            } else {
                child.setY(mMinChildY);

                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
                lp.width = mMinSize;
                lp.height = mMinSize;
                child.setLayoutParams(lp);
                return true;
            }
        }
        return true;
    }

    private void maybeInitProperties(FrescoBorderGradient child, View dependency) {
        if (mChildHeight == 0)
            mChildHeight = child.getHeight();

        if (mChildStartY == 0)
            mChildStartY = (int) child.getY();

        if (mParentStartY == 0)
            mParentStartY = (int) dependency.getY();

        if (mMaxScrollDistance == 0)
            mMaxScrollDistance = mParentStartY - mEndAnimationPoint;

        if (mMinChildY == 0)
            mMinChildY = (int)(mHeaderSize - mMinSize)/2;

        if (mDeltaChildDistanse == 0)
            mDeltaChildDistanse = mChildStartY - mMinChildY;

        if (mDeltaChildSize == 0)
            mDeltaChildSize = mChildHeight - mMinSize;
    }
}