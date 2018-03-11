package com.example.tyhj;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.getsome.OnTouchEvent;
import com.example.getsome.Photos;
import com.example.shape.Essay;
import com.example.shape.EssayAdapter;
import com.example.shape.PhotoAdapter;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyPhoto extends Activity{
	ImageButton imgbt;
	Button publish;
	TextView back;
	PhotoAdapter dp;
	public ListView list1;
	String str1,str2,str3;
	private List<Photos> photoList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allphoto);
		init();
		File f1 = new File(Environment.getExternalStorageDirectory()+"/TyhjPhoto");
		 if(f1.exists()){
			 File[] files = f1.listFiles();
			 int len=files.length;
			 while(len>0){
				 str1=files[len-1].getPath();
				 
				 len=len-1;
				 if(len>0){
					 str2=files[len-1].getPath();
					 len=len-1;
				 }else{
					 str2="noPhoto";
				 }
				
				 if(len>0){
					 str3=files[len-1].getPath();
					 len=len-1;
				 }else{
					 str3="noPhoto";
				 }
				 Photos ph=new Photos(str1, str2, str3);
				 photoList.add(ph);
			 }
		  }
			dp=new PhotoAdapter(MyPhoto.this, R.layout.list_myphoto, photoList);
			list1.setAdapter(dp);
	}
	private void init() {
		list1=(ListView) findViewById(R.id.list1);
		photoList = new ArrayList<Photos>();
		publish=(Button) findViewById(R.id.publish_essay);
		back=(TextView) findViewById(R.id.back_text);
		imgbt=(ImageButton) findViewById(R.id.back_myessay);
		OnTouchEvent tc=new OnTouchEvent();
		tc.touch(publish);
		tc.touch(back);
		final SwipeRefreshLayout swiperefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
		swiperefresh.setColorSchemeResources(
				android.R.color.holo_red_light,
				android.R.color.holo_green_light);// 设置刷新动画的颜色
		swiperefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO 自动生成的方法存根
				swiperefresh.setRefreshing(true);// 开始刷新
				// 执行刷新后需要完成的操作
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent in = new Intent(MyPhoto.this,MyPhoto.class);
						MyPhoto.this.finish();
						startActivity(in);
						MyPhoto.this.overridePendingTransition(R.anim.out, R.anim.enter);  
						// 刷新完成
						swiperefresh.setRefreshing(false);// 结束刷新
					}
				}, 1000);// 刷新动画持续2秒
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyPhoto.this,MainFace.class);
				startActivity(in);
				MyPhoto.this.finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyPhoto.this,Publish.class);
				startActivity(in);
				MyPhoto.this.finish();
			}
		});
		
		imgbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyPhoto.this,MainFace.class);
				startActivity(in);
				MyPhoto.this.finish();
			}
		});
		Picasso.with(MyPhoto.this).load(R.drawable.leftgo).resize(50, 50)
		.centerCrop().into(imgbt);
	}
}
