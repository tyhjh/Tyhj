package com.example.tyhj;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.getsome.OnTouchEvent;
import com.example.getsome.VMessage;
import com.example.getsome.VMyCollect;
import com.example.getsome.VMyLeftNote;
import com.example.getsome.VmyEssay;
import com.example.shape.CircularImage;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Visit extends Activity{
	public LinearLayout bground;
	public TextView pname,who,back_text;
	public ImageButton mylovesign,myleftnotesign,myessaysign,back_myessay;
	public Button myessay,mylove,myleftnote;
	public CircularImage header;
	String usernumber;
	AVUser Vuser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visit);
		Intent in=getIntent();
		usernumber=in.getStringExtra("usernumber");
	AVQuery<AVUser> query = AVUser.getQuery();
		query.whereEqualTo("username", usernumber);
		query.findInBackground(new FindCallback<AVUser>() {
			@Override
			public void done(List<AVUser> arg0, AVException e) {
				if(e==null&&arg0.size()>0){
					 Vuser=arg0.get(0);
					 init();
				}
			}
		});
	}
	private void init() {
		WindowManager wm = (WindowManager) Visit.this
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	int xxxx=(int) (height/36);
	int xxx=(int) (height/25.7);
		back_text=(TextView) findViewById(R.id.back_text);
		back_myessay=(ImageButton) findViewById(R.id.back_myessay);
		who=(TextView) findViewById(R.id.who);
		myessaysign=(ImageButton) findViewById(R.id.myessay_sign);
		mylovesign=(ImageButton) findViewById(R.id.mylove_sign);
		myleftnotesign=(ImageButton) findViewById(R.id.myleftnote_sign);
		myessay=(Button) findViewById(R.id.myessay);
		mylove=(Button) findViewById(R.id.mylove);
		myleftnote=(Button) findViewById(R.id.myleftnote);
		header = (CircularImage) findViewById(R.id.head);
		pname=(TextView) findViewById(R.id.pname);
		bground = (LinearLayout) findViewById(R.id.background);
		pname.setText(Vuser.getString("pSignature")+"——"+Vuser.getString("pName")+"   ");
		bground.setBackgroundResource(R.drawable.bground);
		Picasso.with(Visit.this).load(R.drawable.essaylove).resize(xxxx , xxxx).centerCrop().into(mylovesign);
		Picasso.with(Visit.this).load(R.drawable.myessay).resize(xxxx , xxxx).centerCrop().into(myessaysign);
		Picasso.with(Visit.this).load(R.drawable.leftnote).resize(xxxx , xxxx).centerCrop().into(myleftnotesign);
		Picasso.with(Visit.this).load(R.drawable.leftgo).resize(xxxx , xxxx).centerCrop().into(back_myessay);
		//Touch事件
		OnTouchEvent tc=new OnTouchEvent();
		tc.touch(myessay);
		tc.touch(mylove);
		tc.touch(myleftnote);
		who.setText(Vuser.getString("pName")+"的主页");
		//点击事件
		back_myessay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(Visit.this,MainFace.class);
				startActivity(in);
				overridePendingTransition(R.anim.out, R.anim.enter);  
				Visit.this.finish();
			}
		});
		back_text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(Visit.this,MainFace.class);
				startActivity(in);
				overridePendingTransition(R.anim.out, R.anim.enter);  
				Visit.this.finish();
			}
		});
		myleftnote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(Visit.this,VMyLeftNote.class);
				in.putExtra("usernumber", usernumber);
				startActivity(in);
			}
		});
		myessay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(Visit.this,VmyEssay.class);
				in.putExtra("usernumber", usernumber);
				startActivity(in);
			}
		});
		mylove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(Visit.this,VMyCollect.class);
				in.putExtra("usernumber", usernumber);
				startActivity(in);
			}
		});
		// 设置头像
				Picasso.with(Visit.this).load(Vuser.getAVFile("headImage").getUrl())
						.resize(190, 190).centerCrop().into(header);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				final Bitmap bitmap1 = returnBitMap(Vuser.getAVFile("bgImage")
						.getUrl());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				bground.post(new Runnable() {
					@Override
					public void run() {
						Drawable drawable = new BitmapDrawable(bitmap1);
						bground.setBackgroundDrawable(drawable);
					}
				});
			}
		}).start();
		// 个人资料
		header.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(Visit.this, VMessage.class);
				in.putExtra("usernumber", usernumber);
				startActivity(in);
			}
		});
	}
	private Bitmap returnBitMap(String path) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(path);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
