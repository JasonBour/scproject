package com.example.administrator.sc;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends Activity {
  public Context context;
	WindowManager wm;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context= this.getBaseContext();
		 wm = this.getWindowManager();
	initView();

	}

	private void initView() {
		final ImageView image = (ImageView) findViewById(R.id.image);

		/**
		image.setOnTouchListener(new View.OnTouchListener(){
			private float x, y;
			private int mx, my;
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						x = event.getX();
						y = event.getY();
					case MotionEvent.ACTION_MOVE:
						mx = (int)(event.getRawX() - x);
						my = (int)(event.getRawY() - 50 - y);

                         Log.i("app",mx+"------"+ my+"----------"+ mx+ v.getWidth()+"---------"+ my + v.getHeight());
						v.layout(mx, my, mx + v.getWidth(), my + v.getHeight());
						break;
				}
				return true;
			}});
		 **/



		image.setOnTouchListener(new View.OnTouchListener() {
			private int mx, my;

			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						mx = (int) (event.getRawX());
						my = (int) (event.getRawY() - 50);
						int x = mx - image.getWidth() / 2;int y = my - image.getHeight() / 2; int z= mx + image.getWidth() / 2; int k = my + image.getHeight() / 2;

						if(x<0){
							x=0;
							z=image.getWidth();
						}else if(z>700){
							x=700-image.getWidth();
							z= 700;
						}

						if(y<0){
							y=0;
							k= image.getHeight();
						}else if (k>800) {
							k=800;
							y=800-image.getHeight();
						}

						Log.i("app",x+"========="+y+"========="+z+"============="+k);
						v.layout(x,y,z,k);
						break;
				}
				return true;
			}});
	}



}
