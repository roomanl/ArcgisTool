package cn.sddman.arcgistool.listener;

import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

interface OnTouchListener {
    boolean onMultiPointerTap(MotionEvent motionEvent);
    boolean onDoubleTouchDrag(MotionEvent motionEvent);
    boolean onUp(MotionEvent motionEvent);
    boolean onRotate(MotionEvent motionEvent, double v);
    boolean onSingleTapConfirmed(MotionEvent e);
    boolean onDoubleTap(MotionEvent e);
    boolean onDoubleTapEvent(MotionEvent e);
    boolean onDown(MotionEvent e);
    void onShowPress(MotionEvent e);
    boolean onSingleTapUp(MotionEvent e);
    boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    void onLongPress(MotionEvent e);
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
    boolean onScale(ScaleGestureDetector detector);
    boolean onScaleBegin(ScaleGestureDetector detector);
    void onScaleEnd(ScaleGestureDetector detector);
    boolean onTouch(View v, MotionEvent event);
}
