package com.potato.gamevideo.view.slidingmenu;

import com.nineoldandroids.view.ViewHelper;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.SyncStateContract.Helpers;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ViewAnimator;

public class BaseSlidingMenu extends HorizontalScrollView {

	private LinearLayout mWrapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;
	private int mScreenHeight;
	private int mMenuRightPadding = 50;
	private int mMenuWidth;
	private boolean measured = false;
	private boolean isOpen;

	public BaseSlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 获取屏幕宽度
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMerMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMerMetrics);
		mScreenWidth = outMerMetrics.widthPixels;
		mScreenHeight = outMerMetrics.heightPixels;

		// 将50dp转化为50px
		mMenuRightPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources()
						.getDisplayMetrics());
	}

	/*
	 * 决定内部View的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!measured) {
			mWrapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWrapper.getChildAt(0);
			mContent = (ViewGroup) mWrapper.getChildAt(1);

			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
			Log.d("scroll", "mMenuWidth:" + mMenuWidth);
			mContent.getLayoutParams().width = mScreenWidth;

			measured = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/*
	 * 决定布局
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.d("tag", "Touch");
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			// 隐藏在左边的宽度
			int scrollWidth = getScrollX();

			Log.d("scroll", "scrollWidth:" + scrollWidth);
			if (scrollWidth >= mMenuWidth / 2) {
				Log.d("scroll", "hide");
				// 隐藏菜单栏
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			} else {
				Log.d("scroll", "show");
				// 显示菜单栏
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			return true;

		default:
			break;
		}
		return false;
	}

	public void OpenMenu() {
		if (isOpen) {
			return;
		}
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	public void HideMenu() {
		if (!isOpen) {
			return;
		}
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	public void ToggleMenu() {
		if (isOpen) {
			HideMenu();
		} else {
			OpenMenu();
		}
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);

		// 1 ~ 0
		float offset = (float) l / mMenuWidth;
		// ViewHelper.setTranslationX(mMenu, l);
//		ViewHelper.setAlpha(mMenu, 1 - offset);
		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mScreenHeight / 2f);
		ViewHelper.setScaleX(mContent, 0.7f + 0.3f * offset);
		ViewHelper.setScaleY(mContent, 0.7f + 0.3f * offset);
		ViewHelper.setTranslationX(mMenu, (0.8f + 0.2f * (1 - offset)) * l);
		ViewHelper.setScaleX(mMenu, 0.8f + 0.2f * (1 - offset));
		ViewHelper.setScaleY(mMenu, 0.8f + 0.2f * (1 - offset));
		ViewHelper.setPivotX(mMenu, 0);
		ViewHelper.setPivotY(mMenu, mScreenHeight / 2f);
		ViewHelper.setRotationY(mMenu,330f + 30f *(1 - offset));
	}
}