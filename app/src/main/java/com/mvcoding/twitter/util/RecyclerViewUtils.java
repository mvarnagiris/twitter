package com.mvcoding.twitter.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.mvcoding.twitter.R;
import com.mvcoding.twitter.ui.Layout;

public final class RecyclerViewUtils {
    private RecyclerViewUtils() {
    }

    public static void setupGrid(@NonNull RecyclerView recyclerView, @NonNull Layout layout) {
        recyclerView.setHasFixedSize(true);

        final boolean isDefaultPortrait = layout.getSize() == Layout.Size.Default && layout.getOrientation() == Layout.Orientation.Portrait;
        if (isDefaultPortrait) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else {
            final int spanCount = recyclerView.getResources().getInteger(R.integer.grid_span_count);
            final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
            staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
