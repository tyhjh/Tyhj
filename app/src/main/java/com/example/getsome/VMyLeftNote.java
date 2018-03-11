package com.example.getsome;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.getsome.LeftNoteAdapter;
import com.example.getsome.OnTouchEvent;
import com.example.shape.Essay;
import com.example.shape.EssayAdapter;
import com.example.tyhj.R;
import com.example.tyhj.Visit;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class VMyLeftNote extends Activity{
	public ListView list1;
	ImageButton imgbt;
	Button publish;
	TextView back;
	public EditText note;
	View layout ;
	AVUser user,Vuser;
	private List<Essay> essayList;
	LeftNoteAdapter dp;
	String name;
	String str;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myleftnote);
		Intent in=getIntent();
		str=in.getStringExtra("usernumber");
		essayList = new ArrayList<Essay>();
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		LayoutInflater inflater=getLayoutInflater();
		layout = inflater.inflate(R.layout.list_visit_head, null);
		note=(EditText) layout.findViewById(R.id.leftnote);
		list1 = (ListView)findViewById(R.id.list1);
		list1.addHeaderView(layout);
		dp = new LeftNoteAdapter(VMyLeftNote.this, R.layout.list_mynote_item, essayList);
		AVQuery<AVUser> query = AVUser.getQuery();
		query.whereEqualTo("username", str);
		query.findInBackground(new FindCallback<AVUser>() {
			@Override
			public void done(List<AVUser> arg0, AVException e) {
				if(e==null&&arg0.size()>0){
					 Vuser=arg0.get(0);
					 init();
					 list1.setAdapter(dp);
				}
			}
		});
	}
	private void init() {
		
		publish=(Button) findViewById(R.id.publish_essay);
		back=(TextView) findViewById(R.id.back_text);
		imgbt=(ImageButton) findViewById(R.id.back_myessay);
		OnTouchEvent tc=new OnTouchEvent();
		tc.touch(publish);
		tc.touch(back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(VMyLeftNote.this,Visit.class);
				startActivity(in);
				VMyLeftNote.this.finish();
			}
		});
		publish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(note.getText().toString().length()>0){
				AVObject post = new AVObject("LeftNote");
				post.put("username_from", name);
				post.put("headImageUrl", user.getAVFile("headImage").getUrl());
				post.put("username_to", str);
				post.put("Text", note.getText().toString());
				post.put("from_pName", user.getString("pName"));
				post.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(AVException e) {
						if(e==null){
							Intent in=new Intent(VMyLeftNote.this,VMyLeftNote.class);
							in.putExtra("usernumber", str);
							VMyLeftNote.this.finish();
							startActivity(in);
							overridePendingTransition(R.anim.out, R.anim.enter);  
						}
					}
				});
			}
			}
		});
		
		imgbt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(VMyLeftNote.this,VMyLeftNote.class);
				startActivity(in);
				VMyLeftNote.this.finish();
			}
		});
		Picasso.with(VMyLeftNote.this).load(R.drawable.leftgo).resize(50, 50)
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
						Intent in = new Intent(VMyLeftNote.this,VMyLeftNote.class);
						in.putExtra("usernumber", str);
						VMyLeftNote.this.finish();
						startActivity(in);
						VMyLeftNote.this.overridePendingTransition(R.anim.out, R.anim.enter);  
						// 刷新完成
						swiperefresh.setRefreshing(false);// 结束刷新
					}
				}, 1000);// 刷新动画持续2秒
			}
		});
		AVQuery<AVObject> query2 = new AVQuery<AVObject>("LeftNote");
		query2.whereEqualTo("username_to", str);
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
