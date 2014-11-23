package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;

	private int mMenuWidth;
	
	/** 屏幕宽度 */
	private int mScreenWidth;

	private int mMenuRightPadding = 50;

	private boolean once = false;

	public SlidingMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;
		// 把dp转化为px
		mMenuRightPadding = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources()
						.getDisplayMetrics());
	}

	/**
	 * 设置子view的宽和高 设置自己的宽和高
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if (!once) {
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);

			// 设置菜单栏的宽度
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(mMenuWidth, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			//隐藏在左边的宽度
			int scrollX = getScrollX();
			if(scrollX >= mMenuWidth / 2) {
				this.smoothScrollTo(mMenuWidth, 0);
			}else{
				this.smoothScrollTo(0, 0);
			}
			return true;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}
}
