package com.example.tyhj;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.getsome.LeftNoteAdapter;
import com.example.getsome.OnTouchEvent;
import com.example.shape.Essay;
import com.example.shape.EssayAdapter;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class MyLeftNote extends Activity{
	public ListView list1;
	ImageButton imgbt;
	Button publish;
	TextView back;
	AVUser user;
	private List<Essay> essayList;
	LeftNoteAdapter dp;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myleftnote);
		essayList = new ArrayList<Essay>();
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		dp = new LeftNoteAdapter(MyLeftNote.this, R.layout.list_mynote_item, essayList);
		init();
		list1.setAdapter(dp);
	}
	private void init() {
		publish=(Button) findViewById(R.id.publish_essay);
		back=(TextView) findViewById(R.id.back_text);
		imgbt=(ImageButton) findViewById(R.id.back_myessay);
		OnTouchEvent tc=new OnTouchEvent();
		publish.setText("写说说");
		tc.touch(publish);
		tc.touch(back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyLeftNote.this,MainFace.class);
				startActivity(in);
				MyLeftNote.this.finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyLeftNote.this,Publish.class);
				startActivity(in);
				MyLeftNote.this.finish();
			}
		});
		
		imgbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(MyLeftNote.this,MainFace.class);
				startActivity(in);
				MyLeftNote.this.finish();
			}
		});
		Picasso.with(MyLeftNote.this).load(R.drawable.leftgo).resize(50, 50)
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
						Intent in = new Intent(MyLeftNote.this,MyLeftNote.class);
						MyLeftNote.this.finish();
						startActivity(in);
						MyLeftNote.this.overridePendingTransition(R.anim.out, R.anim.enter);  
						// 刷新完成
						swiperefresh.setRefreshing(false);// 结束刷新
					}
				}, 1000);// 刷新动画持续2秒
			}
		});
		list1 = (ListView)findViewById(R.id.list1);
		AVQuery<AVObject> query2 = new AVQuery<AVObject>("LeftNote");
		query2.whereEqualTo("username_to", user.getString("username"));
		query2.orderByDescending("times");
		query2.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null && avObjects.size() > 0) {
					for (int i = 0; i < avObjects.size(); i++) {
						try {
							Essay essay = new Essay(avObjects.get(i).getString(
									"from_pName"), avObjects.get(i).getString("headImageUrl"), avObjects.get(i).getString("username_from"),
									avObjects.get(i).getString("Text"),avObjects.get(i).getString("date"),1,"no");
							essayList.add(essay);
						} catch (Exception e2) {
							
						}
					}
					// dp.addAll(essayList);
					dp.notifyDataSetChanged();
				}
			}
		});
	}
}
