package com.example.administrator.sc;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/9/3.
 */
public class MyImageView extends ImageView {
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	/*
	 设置图片摆放以及活动的范围
	 */
	public void setImagePostion(int width ,int height){





	}





}
