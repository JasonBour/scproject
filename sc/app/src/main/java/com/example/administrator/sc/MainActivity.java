package com.example.administrator.sc;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	initView();

	}

	private void initView() {

		final ImageView image = (ImageView) findViewById(R.id.image);
		image.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
			        switch (event.getAction())	{
						case  MotionEvent.ACTION_MOVE:
							int mx = (int)event.getRawX();
							int my = (int)event.getRawY();
							v.layout(mx - image.getWidth()/2, my - image.getHeight()/2, mx + image.getWidth()/2, my + image.getHeight()/2);
							break;
						default: break;
					}

			 return true;
			}
		});
	}


}
