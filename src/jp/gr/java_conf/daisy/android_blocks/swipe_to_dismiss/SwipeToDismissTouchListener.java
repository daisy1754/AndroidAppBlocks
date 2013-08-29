package jp.gr.java_conf.daisy.android_blocks.swipe_to_dismiss;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.view.*;

/**
 * Basic implementation of touchlistner to detect horizontal swipe on view element, and animate
 * them to realize swipe-to-remove effect. This class is basically an hand copy of ones described
 * in the following URL, but with simplification and generalization.
 * Note: This class enables remove-like animation, but not really removing element from view tree.
 * If you want to do so, you need to implement
 * {@link SwipeAndDismissEventListener#onSwipeEndAnimationEnd(android.view.View, boolean)} to
 * remove the view after it's dismissing animation is over.
 *
 * {@see https://www.youtube.com/watch?v=NewCSg2JKLk}
 */
public class SwipeToDismissTouchListener implements View.OnTouchListener {

    /**
     * Event listener that is called during swipe-and-dismiss flow.
     */
    public static interface SwipeAndDismissEventListener {
        public void onSwipeStart(View view);
        public void onSwipeEndAnimationStarted(View view);
        public void onSwipeEndAnimationEnd(View view, boolean removed);
    }

    /**
     * Provide a blank implementation of {@link SwipeAndDismissEventListener} for inheritance.
     */
    public static class SwipeAndDismissEventListenerAdapter implements SwipeAndDismissEventListener {
        @Override
        public void onSwipeStart(View view) { }

        @Override
        public void onSwipeEndAnimationStarted(View view) { }

        @Override
        public void onSwipeEndAnimationEnd(View view, boolean removed) { }
    }

    public static SwipeToDismissTouchListener forViewInViewGroup(Context context) {
        return new SwipeToDismissTouchListener(context,
                new SwipeAndDismissEventListenerAdapter() {
                    @Override
                    public void onSwipeEndAnimationEnd(View view, boolean removed) {
                        if (removed) {
                            ((ViewGroup) view.getParent()).removeView(view);
                        }
                    }
                });
    }

    private static final float DELTA_TO_DISMISS_THRESHOLD = 0.25f;
    private static final int SWIPE_DURATION_MSEC = 250;

    private final Context context;
    private SwipeAndDismissEventListener swipeAndDismissEventListener;
    private boolean itemPressed = false;
    private boolean swiping = false;
    private float downX;
    private int swipeSlop = -1;

    public SwipeToDismissTouchListener(Context context) {
        this(context, new SwipeAndDismissEventListenerAdapter());
    }

    public SwipeToDismissTouchListener(
            Context context, SwipeAndDismissEventListener swipeAndDismissEventListener) {
        this.context = context;
        this.swipeAndDismissEventListener = swipeAndDismissEventListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (swipeSlop < 0) {
            swipeSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (itemPressed) {
                    return false;
                }
                itemPressed = true;
                downX = event.getX();
                break;
            case MotionEvent.ACTION_CANCEL:
                resetAlphaAndTranslation(v);
                itemPressed = false;
                break;
            case MotionEvent.ACTION_MOVE:{
                float x = event.getX() + v.getTranslationX();
                float deltaXAbs = Math.abs(x - downX);
                if (!swiping && deltaXAbs > swipeSlop) {
                    swiping = true;
                    swipeAndDismissEventListener.onSwipeStart(v);
                }
                if (swiping) {
                    v.setTranslationX(x -downX);
                    v.setAlpha(1 - deltaXAbs / v.getWidth());
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                if (swiping) {
                    executeSwipeAnimation(v, event.getX());
                }
                itemPressed = false;
                break;
            }
            default:
                return false;
        }

        return true;
    }

    private void executeSwipeAnimation(final View view, float eventX) {
        float x = eventX + view.getTranslationX();
        float deltaXAbs = Math.abs(x - downX);
        float fractionCovered;
        float endX;
        final boolean remove = deltaXAbs / view.getWidth() > DELTA_TO_DISMISS_THRESHOLD;
        if (remove) {
            fractionCovered = deltaXAbs / view.getWidth();
            endX = x < downX ? -view.getWidth() : view.getWidth();
        } else {
            fractionCovered = 1 - (deltaXAbs / view.getWidth());
            endX = 0;
        }
        long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION_MSEC);
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("alpha", remove ? 0f : 1f),
                PropertyValuesHolder.ofFloat("translationX", endX));
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                resetAlphaAndTranslation(view);
                swipeAndDismissEventListener.onSwipeEndAnimationEnd(view, remove);
            }
        });
        animator.start();
        swipeAndDismissEventListener.onSwipeEndAnimationStarted(view);
    }

    private void resetAlphaAndTranslation(View view) {
        view.setAlpha(1);
        view.setTranslationX(0);
    }
}
