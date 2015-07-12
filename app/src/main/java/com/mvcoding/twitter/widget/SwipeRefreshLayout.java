package com.mvcoding.twitter.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.util.ThemeUtils;

public class SwipeRefreshLayout extends android.support.v4.widget.SwipeRefreshLayout {
    private boolean isMeasured = false;
    private boolean preMeasureRefreshing = false;

    public SwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public SwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!isMeasured) {
            isMeasured = true;
            setRefreshing(preMeasureRefreshing);
        }
    }

    @Override public void setRefreshing(boolean refreshing) {
        if (isMeasured) {
            super.setRefreshing(refreshing);
        } else {
            preMeasureRefreshing = refreshing;
        }
    }

    private void init() {
        setColorSchemeColors(ThemeUtils.getColor(getContext(), R.attr.colorAccent));
    }
}
