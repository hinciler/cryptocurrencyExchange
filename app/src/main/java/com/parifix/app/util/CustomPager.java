package com.parifix.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import com.blankj.utilcode.constant.MemoryConstants;

public class CustomPager extends ViewPager {
    private View mCurrentView;

    public CustomPager(Context context) {
        super(context);
    }

    public CustomPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @SuppressLint("WrongConstant")
    public void onMeasure(int i, int i2) {
        if (this.mCurrentView == null) {
            super.onMeasure(i, i2);
            return;
        }
        this.mCurrentView.measure(i, MeasureSpec.makeMeasureSpec(0, 0));
        i2 = this.mCurrentView.getMeasuredHeight();
        if (i2 <= 0) {
            i2 = 0;
        }
        super.onMeasure(i, MeasureSpec.makeMeasureSpec(i2, MemoryConstants.GB));
    }

    public void measureCurrentView(View view) {
        this.mCurrentView = view;
        requestLayout();
    }

    public int measureFragment(View view) {
        if (view == null) {
            return 0;
        }
        view.measure(0, 0);
        return view.getMeasuredHeight();
    }
}