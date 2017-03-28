package com.soft.niuyi.springscrollview;

import android.content.Context;
import android.support.animation.SpringAnimation;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 作者：${牛毅}
 * 时间：2017/03/28 09：29
 * 邮箱：niuyi19900923@gmail.com
 * 描述：带有回弹效果的scrollView
 */
public class SpringScrollView extends NestedScrollView {

    private SpringAnimation mSpringAnim;

    private float startDragY;

    public SpringScrollView(Context context) {
        super(context);
        init();
    }

    public SpringScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpringScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSpringAnim = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0);
        //刚度 默认1200 值越大回弹的速度越快
        mSpringAnim.getSpring().setStiffness(800.0f);
        //阻尼 默认0.5 值越小，回弹之后来回的次数越多
        mSpringAnim.getSpring().setDampingRatio(0.50f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE://手指滑动时
                if (getScrollY() <= 0) {//顶部下拉
                    if (startDragY == 0) {//记录下按下的坐标
                        startDragY = ev.getRawY();
                    }
                    if (ev.getRawY() - startDragY > 0) {//滑动时
                        setTranslationY((ev.getRawY() - startDragY) / 3);
                        return true;
                    } else {
                        mSpringAnim.cancel();
                        setTranslationY(0);
                    }
                } else if ((getScrollY() + getHeight()) >= getChildAt(0).getMeasuredHeight()) {//底部上拉
                    if (startDragY == 0) {
                        startDragY = ev.getRawY();
                    }
                    if (ev.getRawY() - startDragY < 0) {
                        setTranslationY((ev.getRawY() - startDragY) / 3);
                        return true;
                    } else {
                        mSpringAnim.cancel();
                        setTranslationY(0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP://抬起取消
            case MotionEvent.ACTION_CANCEL:
                if (getTranslationY() != 0) {//当y坐标滑动的偏移量不是0时才执行动画
                    mSpringAnim.start();
                }
                startDragY = 0;
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }
}
