package com.example.voicecontrol.util;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class DetectarToque {
    private GestureDetector gestureDetector;
    private OnGestureListener listener;

    public DetectarToque(Context context, View view, OnGestureListener listener) {
        this.listener = listener;

        gestureDetector = new GestureDetector(context, new GestureListener());

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (listener != null) {
                listener.onSingleTapUp();

            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (listener != null) {
                listener.onDoubleTap();

            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (listener != null) {
                listener.onLongPress();
            }
        }
    }

    public interface OnGestureListener {
        void onSingleTapUp();
        void onDoubleTap();
        void onLongPress();
    }
}
