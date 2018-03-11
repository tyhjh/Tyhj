package com.example.getsome;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.tyhj.MainActivity;
import com.example.tyhj.R;
import com.example.tyhj.Visit;
import com.squareup.picasso.Picasso;

import android.R.color;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class VMessage extends Activity {
	public EditText adress, sex, perfession, psign, pname;
	public Button back, change, camoral, images,off;
	public TextView number, email;
	public ImageButton headimage;
	String name;
	private Uri imageUri;
	AVUser user;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	AlertDialog.Builder di;
	int WHERE_PHOTO = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalcenter);
		Intent in=getIntent();
		String str=in.getStringExtra("usernumber");
		AVQuery<AVUser> query = AVUser.getQuery();
		query.whereEqualTo("username", str);
		query.findInBackground(new FindCallback<AVUser>() {
			@Override
			public void done(List<AVUser> arg0, AVException e) {
				if(e==null&&arg0.size()>0){
					 user=arg0.get(0);
					 name = user.getString("username");
						init();
						getMessage();
				}
			}
		});
	}
	private void getMessage() {
		pname.setText(user.getString("pName"));
		sex.setText(user.getString("sex"));
		perfession.setText(user.getString("perfession"));
		psign.setText(user.getString("pSignature"));
		adress.setText(user.getString("location"));
		number.setText(user.getString("username"));
		email.setText(user.getString("email"));
		Picasso.with(VMessage.this)
				.load(user.getAVFile("headImage").getUrl()).resize(182, 182)
				.centerCrop().into(headimage);
	}

	// 初始化
	private void init() {
		off=(Button) findViewById(R.id.turnoff);
		off.setBackgroundColor(Color.parseColor("#00000000"));
		headimage = (ImageButton) findViewById(R.id.head_image);
		change = (Button) findViewById(R.id.change);
		back = (Button) findViewById(R.id.button_back);
		adress = (EditText) findViewById(R.id.place);
		adress.setFocusable(false);
		sex = (EditText) findViewById(R.id.sex);
		sex.setFocusable(false);
		perfession = (EditText) findViewById(R.id.area);
		perfession.setFocusable(false);
		psign = (EditText) findViewById(R.id.psign);
		psign.setFocusable(false);
		pname = (EditText) findViewById(R.id.pname);
		pname.setFocusable(false);
		number = (TextView) findViewById(R.id.phone_number);
		email = (TextView) findViewById(R.id.email);
		back.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					back.setBackgroundColor(Color.parseColor("#d0c0c0c0"));
					break;
				case MotionEvent.ACTION_UP:
					back.setBackgroundColor(Color.parseColor("#00c0c0c0"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		// 点击返回
				back.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent in = new Intent(VMessage.this, Visit.class);
						startActivity(in);
						overridePendingTransition(R.anim.out, R.anim.enter);  
						VMessage.this.finish();
					}
				});
	}
}
