package com.example.administrator.sc;

import android.app.Activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnLongClickListener {
  public Context context;
	WindowManager wm;
	Matrix matrix;
	Matrix savedMatrix ;

  private int mode = 0 ;             //默认模式为
  private  static  int ZOOM = 1;           //缩放模式
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		int   width = displayMetrics.widthPixels;
		int   height = displayMetrics.heightPixels;  //宽和高的单位全都是像素  pixel


		context= this.getBaseContext();
	//	Toast.makeText(context,"width="+width+"height="+height,Toast.LENGTH_LONG).show();
		  wm = this.getWindowManager();
	     initView();


	}

	private void initView() {
		matrix = new Matrix();
		savedMatrix = new Matrix();
		final ImageView image = (ImageView) findViewById(R.id.image);
		final  ImageView image2 = (ImageView)findViewById(R.id.image2);
		image2.setOnTouchListener(
				new View.OnTouchListener() {


					//图片不能在xml中图签加载
					private int mx, my;

					@Override

					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
							case MotionEvent.ACTION_MOVE:


								Toast.makeText(context,image.getWidth()+"is"+image.getHeight(),Toast.LENGTH_LONG).show();
								mx = (int) (event.getRawX());   //awX  RawY触点相对于屏幕的坐标  是屏幕的左上角
								my = (int) (event.getRawY() - 50);  //标题栏和状态栏都是25px
								int x = mx - image.getWidth() / 2;   //z-x k-y分别代表着你想要显示图片的宽高
								int y = my - image.getHeight() / 2;
								int z = mx + image.getWidth() / 2;
								int k = my + image.getHeight() / 2;
								//距离左上角xy分别为 1  3  活动范围为 2-1 * 4-3
								if (x < 200) {   //value1
									x = 200;
									z = image.getWidth()+200;
								} else if (z > 700) {   //valuie2
									x = 700 - image.getWidth();
									z = 700;
								}

								if (y < 500) {           //value3
									y = 500;
									k = image.getHeight()+500;
								} else if (k > 1200) {        //value4
									k = 1300;
									y = 1300 - image.getHeight();
								}

								Log.i("app", x + "=========" + y + "=========" + z + "=============" + k);
								v.layout(x, y, z, k);
								break;
							/**
							case MotionEvent.ACTION_POINTER_DOWN:
								float old = spacing(event);

								if (old > 10f) {

									matrix.set(image.getImageMatrix());
									savedMatrix.set(matrix);


								}
                          **/

						}

						return true;
					}





				}
		);


		final ImageView submit = (ImageView)findViewById(R.id.addMyPhoto);
		submit.setOnClickListener(new View.OnClickListener() {
									  @Override
									  public void onClick(View v) {
										  getMyBitmap();


									  }
								  }
		);

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

		image.setOnLongClickListener(this);



		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.im, opts);
		opts.inSampleSize = 1;
		opts.inJustDecodeBounds = false;
		//Bitmap mBitmap =BitmapFactory.decodeResource(getResources(), R.drawable.im, opts);
		final int  width=opts.outWidth;
		final int height=opts.outHeight;


		Toast.makeText(context,width+"width"+height+"",Toast.LENGTH_LONG).show();
		Log.i("app",width+"width"+height+"");

		image.setOnTouchListener(new View.OnTouchListener() {
			private int mx, my;
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
					case MotionEvent.ACTION_MOVE:
						mx = (int) (event.getRawX());
						my = (int) (event.getRawY() - 50);
						int x = mx - width / 2;
						int y = my - height / 2;
						int z = mx + width / 2;
						int k = my + height / 2;
						//这里就是将活动范围限制在左上角700*800的矩形里面
						//距离左上角xy为0 0
						if (x < 0) {
							x = 0;
							z = image.getWidth();
						} else if (z > 700) {
							x = 700 - image.getWidth();
							z = 700;
						}

						if (y < 0) {
							y = 0;
							k = image.getHeight();
						} else if (k > 800) {
							k = 800;
							y = 800 - image.getHeight();
						}

						Log.i("app", x + "=========" + y + "=========" + z + "=============" + k);
						v.layout(x, y, z, k);
						break;
					case MotionEvent.ACTION_POINTER_DOWN:
						float old = spacing(event);

						if (old > 10f) {

							matrix.set(image.getImageMatrix());
							savedMatrix.set(matrix);


						}


				}

				return true;
			}
		});
	}


	@Override
	public boolean onLongClick(View v) {
		return false;
	}

	private float spacing(MotionEvent event){
		float x=event.getX(0)-event.getX(1);
		float y=event.getY(0)-event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}


	private void getBitMap(){
		Resources resources = getResources();

		Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.photo2);  //中间透明的图
		Bitmap moveable = BitmapFactory.decodeResource(resources,R.drawable.image1) ;//可移动的图

		Bitmap lastbitmap  = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());  //最终的图
		Canvas canvas = new Canvas(lastbitmap);
		canvas.drawBitmap(bitmap,new Matrix(),null);
		canvas.drawBitmap(moveable,100,0,null);
      if(lastbitmap.equals(null))
	  {
		  Log.i("com.example","null");
	  }else {

		  Log.i("com.example","not null");
	  }
		try {
			saveMyBitmap("jason",lastbitmap);
			Log.i("com.example","save image");
		} catch (IOException e) {
			e.printStackTrace();
		}



	}

	public void saveMyBitmap(String bitName, Bitmap mBitmap) throws IOException {
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() ,  "jason.png");
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fOut.close();
		Toast.makeText(getApplicationContext(),"以保持",Toast.LENGTH_LONG).show();
	}


	//
	public void  getMyBitmap(){
		View view = MainActivity.this.getWindow().getDecorView();
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		try {
			saveMyBitmap("jason",bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
