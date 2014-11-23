package com.example.qq5slidingmenu;

import com.example.view.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

	private SlidingMenu slidingMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		this.slidingMenu = (SlidingMenu) findViewById(R.id.menu);
	}
	
	public void toggleMenu(View v){
		slidingMenu.toggle();
	}
}
