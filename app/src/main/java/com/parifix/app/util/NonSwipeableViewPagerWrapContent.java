package com.parifix.app.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import com.blankj.utilcode.constant.MemoryConstants;
import java.lang.reflect.Field;

public class NonSwipeableViewPagerWrapContent extends ViewPager {

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        public void startScroll(int i, int i2, int i3, int i4, int i5) {
            super.startScroll(i, i2, i3, i4, 350);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public NonSwipeableViewPagerWrapContent(Context context) {
        super(context);
        setMyScroller();
    }

    public NonSwipeableViewPagerWrapContent(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setMyScroller();
    }

    private void setMyScroller() {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    protected void onMeasure(int i, int i2) {
        int i3 = 0;
        int i4 = i3;
        while (i3 < getChildCount()) {
            View childAt = getChildAt(i3);
            childAt.measure(i, MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredHeight > i4) {
                i4 = measuredHeight;
            }
            i3++;
        }
        if (i4 != 0) {
            i2 = MeasureSpec.makeMeasureSpec(i4, MemoryConstants.GB);
        }
        super.onMeasure(i, i2);
    }
}
