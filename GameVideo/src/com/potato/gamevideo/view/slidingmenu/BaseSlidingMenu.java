package com.potato.gamevideo.view.slidingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class BaseSlidingMenu extends HorizontalScrollView {
	private LinearLayout mWrapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;
	private int mMenuRightPadding = 50;
	private int mMenuWidth;
	private boolean measured = false;

	public BaseSlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 获取屏幕宽度
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMerMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMerMetrics);
		mScreenWidth = outMerMetrics.widthPixels;

		// 将50dp转化为50px
		mMenuRightPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources()
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
			} else {
				Log.d("scroll", "show");
				// 显示菜单栏
				this.smoothScrollTo(0, 0);
			}
			return true;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
}