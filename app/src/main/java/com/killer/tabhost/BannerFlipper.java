package com.killer.tabhost;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用ViewFlipper制作的广告显示条
 */
public class BannerFlipper extends RelativeLayout {
    private static final String TAG = "BannerFlipper";  
  
    private Context mContext;
    private BannerClickListener mListener;  
    private ViewFlipper mFlipper;
    private LinearLayout mLayout;
    private int mCount;  
    private List<Button> mButtonList = new ArrayList<>();
    private LayoutInflater mInflater;
    private GestureDetector mGesture;
    private float mFlipGap = 20f;  
  
    public BannerFlipper(Context context) {  
        super(context);  
        mContext = context;  
        init();  
    }  
  
    public BannerFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);  
        mContext = context;  
        init();  
    }  

    // 设置点击监听
    public void setOnBannerListener(BannerClickListener listener) {  
        mListener = listener;  
    }  

    public void start() {
         startFlip();  
     }  
  
    public void setImage(ArrayList<Integer> imageList) {
        for (int i = 0; i < imageList.size(); i++) {  
            Integer imageID =  imageList.get(i);
            ImageView ivNew = new ImageView(mContext);
            ivNew.setLayoutParams(new LayoutParams(  
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));  
            ivNew.setScaleType(ImageView.ScaleType.FIT_XY);  
            ivNew.setImageResource(imageID);  
            mFlipper.addView(ivNew);  
            Log.d(TAG, "i=" + i + ",image_id=" + imageID);
        }  

        // 根据图片数量添加下方位置点图标数量
        mCount = imageList.size();  
        for (int i = 0; i < mCount; i++) {  
            View view = mInflater.inflate(R.layout.banner_flipper_button, null);
            Button button = (Button) view.findViewById(R.id.banner_btn_num);  
            mLayout.addView(view);  
            mButtonList.add(button);  
        }  
        mFlipper.setDisplayedChild(mCount - 1);  
        startFlip();  
    }  
  
    private void setButton(int position) {  
        if (mCount > 1) {  
            for (int m = 0; m < mCount; m++) {  
                if (m == position % mCount) {  
                    mButtonList.get(m).setBackgroundResource(R.drawable.icon_point_c);  
                } else {  
                    mButtonList.get(m).setBackgroundResource(R.drawable.icon_point_n);  
                }  
            }  
        }  
    }  
  

    private void init() {  
        mInflater = ((Activity) mContext).getLayoutInflater();
        View view = mInflater.inflate(R.layout.banner_flipper, null);
        // 得到广告条ViewFlipper
        mFlipper = (ViewFlipper) view.findViewById(R.id.banner_flipper);
        // 得到下方小图标位置显示区域
        mLayout = (LinearLayout) view.findViewById(R.id.banner_flipper_num);  
        addView(view);  
        // 该手势的onSingleTapUp事件是点击时进入广告页
        mGesture = new GestureDetector(mContext,new BannerGestureListener(this));
    }  
  
    public boolean dispatchTouchEvent(MotionEvent event) {
        mGesture.onTouchEvent(event);  
        return true;  
    }  
  
    final class BannerGestureListener implements GestureDetector.OnGestureListener {  
        private BannerGestureListener(BannerFlipper bannerFlipper) {  
        }  
  
        @Override  
        public final boolean onDown(MotionEvent event) {  
            return true;  
        }  
  
        @Override  
        public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {  
            if (e1.getX() - e2.getX() > mFlipGap) {  // 往右滑超过20F
                startFlip();  
                return true;  
            }  
            if (e1.getX() - e2.getX() < -mFlipGap) {  // 往左滑超过20F
                backFlip();  
                return true;  
            }  
            return false;  
        }  
  
        @Override  
        public final void onLongPress(MotionEvent event) {  
        }  
  
        @Override  
        public final boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {  
            boolean result = false; // true表示要继续处理  
            if (Math.abs(distanceY) < Math.abs(distanceX)) {  
                BannerFlipper.this.getParent().requestDisallowInterceptTouchEvent(false);  
                result = true;  
            }  
            return result;  
        }  
  
        @Override  
        public final void onShowPress(MotionEvent event) {  
        }  
  
        @Override  
        public boolean onSingleTapUp(MotionEvent event) {  
            int position = mFlipper.getDisplayedChild();  
            mListener.onBannerClick(position);  
            return false;  
        }  
  
    }  
  
    private void startFlip() {  
        mFlipper.startFlipping();  
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));  
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));  
        mFlipper.showNext();  
    }  
  
    private void backFlip() {  
        mFlipper.startFlipping();  
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_in));  
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_right_out));  
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));  
        mFlipper.showPrevious();  
        mFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_in));  
        mFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_left_out));  
        mFlipper.getOutAnimation().setAnimationListener(new BannerAnimationListener(this));  
    }  
  
    private class BannerAnimationListener implements Animation.AnimationListener {
        private BannerAnimationListener(BannerFlipper bannerFlipper) {  
        }  
  
        @Override  
        public final void onAnimationEnd(Animation paramAnimation) {  
            int position = mFlipper.getDisplayedChild();  
            setButton(position);  
        }  
  
        @Override  
        public final void onAnimationRepeat(Animation paramAnimation) {  
        }  
  
        @Override  
        public final void onAnimationStart(Animation paramAnimation) {  
        }  
    }  
  
}  