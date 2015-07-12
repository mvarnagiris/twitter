package com.mvcoding.twitter.ui;

import android.content.Context;
import android.support.annotation.NonNull;

import com.mvcoding.twitter.AppContext;
import com.mvcoding.twitter.R;

import javax.inject.Inject;


public class Layout {
    private final Size size;
    private final Orientation orientation;

    @Inject public Layout(@NonNull @AppContext Context context) {
        size = Size.from(context.getResources().getInteger(R.integer.screen_size));
        orientation = Orientation.from(context.getResources().getInteger(R.integer.orientation));
    }

    public Size getSize() {
        return size;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public enum Size {
        Default, SmallTablet, BigTablet;

        public static Size from(int size) {
            switch (size) {
                case 0:
                    return Default;
                case 1:
                    return SmallTablet;
                case 2:
                    return BigTablet;
                default:
                    throw new IllegalArgumentException("Size " + size + " is not supported.");
            }
        }
    }

    public enum Orientation {
        Portrait, Landscape;

        public static Orientation from(int orientation) {
            switch (orientation) {
                case 0:
                    return Portrait;
                case 1:
                    return Landscape;
                default:
                    throw new IllegalArgumentException("Orientation " + orientation + " is not supported.");
            }
        }
    }
}
