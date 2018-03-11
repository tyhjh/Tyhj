package com.example.tyhj;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.getsome.OnTouchEvent;
import com.example.shape.Essay;
import com.example.shape.EssayAdapter;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MyCollcet extends Activity{
	public ListView list1;
	ImageButton imgbt;
	Button publish;
	TextView back;
	AVUser user;
	private List<Essay> essayList;
	EssayAdapter dp;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycollect);
		essayList = new ArrayList<Essay>();
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		dp = new EssayAdapter(MyCollcet.this, R.layout.list_item, essayList);
		init();
		list1.setAdapter(dp);
	}
	private void init() {
		WindowManager wm = (WindowManager) MyCollcet.this
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	int xxxx=(int) (height/36);
	int xxx=(int) (height/25.7);
		publish=(Button) findViewById(R.id.publish_essay);
		back=(TextView) findViewById(R.id.back_text);
		imgbt=(ImageButton) findViewById(R.id.back_myessay);
		OnTouchEvent tc=new OnTouchEvent();
		tc.touch(publish);
		tc.touch(back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyCollcet.this,MainFace.class);
				startActivity(in);
				MyCollcet.this.finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyCollcet.this,Publish.class);
				startActivity(in);
				MyCollcet.this.finish();
			}
		});
		
		imgbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyCollcet.this,MainFace.class);
				startActivity(in);
				MyCollcet.this.finish();
			}
		});
		Picasso.with(MyCollcet.this).load(R.drawable.leftgo).resize(xxxx, xxxx)
		.centerCrop().into(imgbt);
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
						Intent in = new Intent(MyCollcet.this,MyCollcet.class);
						MyCollcet.this.finish();
						startActivity(in);
						MyCollcet.this.overridePendingTransition(R.anim.out, R.anim.enter);  
						// 刷新完成
						swiperefresh.setRefreshing(false);// 结束刷新
					}
				}, 1000);// 刷新动画持续2秒
			}
		});
		list1 = (ListView)findViewById(R.id.list1);
		AVQuery<AVObject> query2 = new AVQuery<AVObject>("CollectEssay");
		query2.whereEqualTo("username", name);
		query2.orderByDescending("times");
		query2.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null && avObjects.size() > 0) {
					for (int i = 0; i < avObjects.size(); i++) {
						try {
							Essay essay = new Essay(avObjects.get(i).getString(
									"pName"), avObjects.get(i).getString("headImageUrl"), avObjects.get(i).getString("photoUrl"),
									avObjects.get(i).getString("Text"),avObjects.get(i).getString("date"),avObjects.get(i).getInt("times"),avObjects.get(i).getString("username"));
							essayList.add(essay);
						} catch (Exception e2) {
							Essay essay = new Essay(avObjects.get(i).getString("pName"), avObjects.get(i).getAVFile("headImage").getUrl(),
									"noPhoto", avObjects.get(i).getString("EssayText"), avObjects.get(i).getString("date"),avObjects.get(i).getInt("times"),avObjects.get(i).getString("username"));
							essayList.add(essay);
						}
					}
					// dp.addAll(essayList);
					dp.notifyDataSetChanged();
				}
			}
		});
	}
}
