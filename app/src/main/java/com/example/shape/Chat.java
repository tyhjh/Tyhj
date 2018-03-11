package com.example.shape;

import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.getsome.ChatAdapter;
import com.example.getsome.LinkMan_Message;
import com.example.getsome.OnTouchEvent;
import com.example.tyhj.MainFace;
import com.example.tyhj.R;
import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Chat extends Fragment {
	public CircularImage headImage;
	public Button addman,add;
	public EditText number;
	View view;
	private List<LinkMan_Message> chatman;
	ChatAdapter dp;
	OnTouchEvent ot;
	public ListView list1;
	String name;
	String MASTER="13678141943";
	AVUser user;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.chat_list, null);
		chatman=new ArrayList<LinkMan_Message>();
		headImage=(CircularImage) view.findViewById(R.id.head);
		addman=(Button) view.findViewById(R.id.addman);
		headImage=(CircularImage) view.findViewById(R.id.head);
		addman=(Button) view.findViewById(R.id.addman);
		user = AVUser.getCurrentUser();
		name=user.getString("username");
		list1=(ListView) view.findViewById(R.id.list1);
		init();
		dp = new ChatAdapter(getActivity(), R.layout.list_chatman, chatman);
		list1.setAdapter(dp);
		return view;
	}
	private void init() {
		ot=new OnTouchEvent();
		// 设置头像
				Picasso.with(getActivity()).load(user.getAVFile("headImage").getUrl())
						.resize(190, 190).centerCrop().into(headImage);
				
				//
				if(!name.equals("13678141943")){
				LinkMan_Message lkmsg=new LinkMan_Message("13678141943", "http://ac-tv2pl6bu.clouddn.com/S3DsVLAGt5gpA9UIJATYBLJ2zWpe992p7XrMun19.jpg", 
						"Tyhj团队", "为你服务是我的荣幸");
				chatman.add(lkmsg);
				AVQuery<AVObject> query2 = new AVQuery<AVObject>("Linkman");
				query2.orderByDescending("times");
				query2.whereEqualTo("username",name);
				query2.findInBackground(new FindCallback<AVObject>() {
					public void done(List<AVObject> avObjects, AVException e) {
						if (e == null && avObjects.size() > 0) {
							for (int i = 0; i < avObjects.size(); i++) {
								try {
								LinkMan_Message lk=new LinkMan_Message(avObjects.get(i).getString("friend"), avObjects.get(i).getString("headImageUrl"), 
										avObjects.get(i).getString("pName"), avObjects.get(i).getString("pSignature"));
								chatman.add(lk);
								} catch (Exception e2) {
								
								}
							}
							// dp.addAll(essayList);
							dp.notifyDataSetChanged();
						}
					}
				});
				}else{
					AVQuery<AVUser> query = AVUser.getQuery();
					query.whereNotEqualTo("username",name);
					query.findInBackground(new FindCallback<AVUser>() {
					    public void done(List<AVUser> avObjects, AVException e) {
					        if (e == null&& avObjects.size() > 0) {
					        	for (int i = 0; i < avObjects.size(); i++) {
					        	LinkMan_Message lk=new LinkMan_Message(avObjects.get(i).getString("username"), avObjects.get(i).getAVFile("headImage").getUrl(), 
										avObjects.get(i).getString("pName"), avObjects.get(i).getString("pSignature"));
								chatman.add(lk);
					        	}
					        	dp.notifyDataSetChanged();
					        } else {
					            // 查询出错
					        }
					    }
					});
				}
		final SwipeRefreshLayout swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
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
						chatman=new ArrayList<LinkMan_Message>();
						init();
						dp = new ChatAdapter(getActivity(), R.layout.list_chatman, chatman);
						list1.setAdapter(dp);
						// 刷新完成
						swiperefresh.setRefreshing(false);// 结束刷新
					}
				}, 1000);// 刷新动画持续2秒
			}
		});
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ot.touch(addman);
		if(!name.equals(MASTER)){
		addmanClick();
		}else{
			addman.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity(), "你已经加了所有人好不好", 1).show();
				}
			});
		}
	}
	private void addmanClick() {
		addman.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder di=new AlertDialog.Builder(getActivity());
				di.setCancelable(true);
				LayoutInflater inflater = LayoutInflater.from(getActivity());
				View layout = inflater.inflate(R.layout.addman, null);
				di.setView(layout);
				di.create();
				di.show();
				add=(Button) layout.findViewById(R.id.add);
				number=(EditText) layout.findViewById(R.id.number);
				ot.touch(add);
				add.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						final String str=number.getText().toString();
						if(!str.equals("")&&!str.equals(name)){
							AVQuery<AVObject> query = new AVQuery<AVObject>("Linkman");
							query.whereEqualTo("username", name);
							query.whereEqualTo("friend", str);
							query.findInBackground(new FindCallback<AVObject>() {
							    public void done(List<AVObject> avObjects, AVException e) {
							        if (e == null&&avObjects.size()>0) {
							            Toast.makeText(getActivity(), "联系人已存在", 1).show();
							        } else {
							        	//查询联系人
							        	AVQuery<AVUser> query = AVUser.getQuery();
							        	query.whereEqualTo("username", str);
							        	query.findInBackground(new FindCallback<AVUser>() {
							        	    public void done(List<AVUser> objects, AVException e) {
							        	        if (e == null&&objects.size()>0&&!objects.get(0).getString("username").equals(MASTER)) {
							        	        	AVObject post = new AVObject("Linkman");
							        	        	post.put("username", name);
							        	        	post.put("friend",objects.get(0).getString("username"));
							        	        	post.put("headImageUrl",objects.get(0).getAVFile("headImage").getUrl());
							        	        	post.put("pName", objects.get(0).getString("pName"));
							        	        	post.put("pSignature", objects.get(0).getString("pSignature"));
							        	        	post.saveInBackground();
							        	        	post = new AVObject("Linkman");
							        	        	post.put("username", objects.get(0).getString("username"));
							        	        	post.put("friend",name);
							        	        	post.put("headImageUrl",user.getAVFile("headImage").getUrl());
							        	        	post.put("pName", user.getString("pName"));
							        	        	post.put("pSignature", user.getString("pSignature"));
							        	        	post.saveInBackground();
							        	        	Toast.makeText(getActivity(), "添加成功", 1).show();
							        	        	chatman=new ArrayList<LinkMan_Message>();
							        	        	init();
							        	    		dp = new ChatAdapter(getActivity(), R.layout.list_chatman, chatman);
							        	        	list1.setAdapter(dp);
							        	        } else {
							        	        		Toast.makeText(getActivity(), "未查找到该用户", 1).show();
							        	        }
							        	    }
							        	});
							        }
							    }
							});
						}else{
							if(str.equals(name)){
								Toast.makeText(getActivity(), "你不能添加这个笨蛋", 1).show();
							}else{
								Toast.makeText(getActivity(), "请先输入账号", 1).show();
							}
						}
					}
				});
			}
		});
	}
}
