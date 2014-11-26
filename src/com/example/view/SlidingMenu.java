package com.example.view;

import com.example.qq5slidingmenu.R;
import com.nineoldandroids.view.ViewHelper;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
	private static final String TAG = SlidingMenu.class.getName();

	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;

	private int mMenuWidth;

	/** 屏幕宽度 */
	private int mScreenWidth;

	private int mMenuRightPadding = 50;

	private boolean once = false;

	/** 菜单是否开启 */
	private boolean isOpen;

	/**
	 * 未自定义属性时调用此构造方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	private void init(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(dm);
		mScreenWidth = dm.widthPixels;

		// // 把dp转化为px
		// mMenuRightPadding = (int) TypedValue.applyDimension(
		// TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources()
		// .getDisplayMetrics());
	}

	/**
	 * 当使用自定义属性时会调用此构造方法
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		Log.d(TAG, "执行了这个构造方法");
		TypedArray ta = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.SliddingMenu, defStyleAttr, 0);
		int n = ta.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = ta.getIndex(i);
			switch (attr) {
			case R.styleable.SliddingMenu_rightPadding:
				mMenuRightPadding = ta.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50, context
										.getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}

		}
		ta.recycle();
		init(context);

	}

	public SlidingMenu(Context context) {
		this(context, null);
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
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
					- mMenuRightPadding;
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
			// 隐藏在左边的宽度
			int scrollX = getScrollX();
			if (scrollX >= mMenuWidth / 2) {
				this.smoothScrollTo(mMenuWidth, 0);
			} else {
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			return true;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		else {
			this.smoothScrollTo(0, 0);
			isOpen = true;
		}
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	/**
	 * 菜单状态的切换
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}
	
	
	/**
	 * 滚动发生时调用此方法
	 */
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth; // 1~0
		//调用属性动画，设置TranslationX
		//mMenuWidth * scale 会根据菜单出现的宽度动态度化，为实现抽屉效果
		ViewHelper.setTranslationX(mMenu, mMenuWidth * scale);
	}
}
