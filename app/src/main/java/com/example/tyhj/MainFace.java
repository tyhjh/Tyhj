package com.example.tyhj;

import java.util.Arrays;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationEventHandler;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.getsome.ChatContent;
import com.example.getsome.Zd;
import com.example.shape.Chat;
import com.example.shape.Home;
import com.example.shape.PersonalCetenr;
import com.example.tyhj.MyChat.CustomMessageHandler;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainFace extends FragmentActivity {
	public ImageButton home, chat, personal;
	public ViewPager viewPager;
	int xxxx;
	// 有多少个table
	AVUser user=AVUser.getCurrentUser();
	private static final int TAB_COUNT = 3;
	private int current_index = 0;
	private static int offset = 0;
	private static int lineWidth = 0;
	static String name;
	static Context con;
	@Override
	protected void onCreate(Bundle arg0) {
		AVOSCloud.initialize(this, "TV2pl6bU8uu7eC0x083I0byd-gzGzoHsz",
				"kYtPwUPv3XQFtKW0dhVqq9p0");
		super.onCreate(arg0);
		setContentView(R.layout.mainface);
		con=MainFace.this;
		init();
		viewPager.setAdapter(new FragmentPagerAdapter(
				getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return TAB_COUNT;
			}

			@Override
			public Fragment getItem(int arg0) {
				switch (arg0) {
				case 0:
					return new Home();
				case 1:
					return new Chat();
				case 2:
					return new PersonalCetenr();
				default:
					break;
				}
				return null;
			}
		});
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int index) {
				// TODO 自动生成的方法存根
				if (index == 0) {
					viewPagerChange();
					Picasso.with(MainFace.this).load(R.drawable.centerdown)
							.resize(xxxx, xxxx).centerCrop().into(home);
				} else if (index == 1) {
					viewPagerChange();
					Picasso.with(MainFace.this).load(R.drawable.iconchatdown)
							.resize(xxxx, xxxx).centerCrop().into(chat);
				
				} else if (index == 2) {
					viewPagerChange();
					Picasso.with(MainFace.this).load(R.drawable.personaldown)
							.resize(xxxx, xxxx).centerCrop().into(personal);
			
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO 自动生成的方法存根

			}
		});
	}

	private void init() {
		name=user.getString("username");
		WindowManager wm = (WindowManager) MainFace.this
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	xxxx=(int) (height/25.7);
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		home = (ImageButton) findViewById(R.id.home);
		chat = (ImageButton) findViewById(R.id.chat);
		personal = (ImageButton) findViewById(R.id.personalCenter);
		//可以缓存两个界面
		viewPager.setOffscreenPageLimit(3);
		viewPagerChange();
		Picasso.with(MainFace.this).load(R.drawable.centerdown).resize(xxxx, xxxx)
				.centerCrop().into(home);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(0,false);
			}
		});
		chat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(1,false);
			}
		});
	
		personal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewPager.setCurrentItem(2,false);
			}
		});

	}

	private void viewPagerChange() {
		Picasso.with(MainFace.this).load(R.drawable.centerup).resize(xxxx, xxxx)
				.centerCrop().into(home);
		Picasso.with(MainFace.this).load(R.drawable.iconchatup).resize(xxxx, xxxx)
				.centerCrop().into(chat);
		Picasso.with(MainFace.this).load(R.drawable.personalup).resize(xxxx, xxxx)
				.centerCrop().into(personal);
	}
	//监听返回键
	private long exitTime = 0;
	        @Override
	        public boolean onKeyDown(int keyCode, KeyEvent event) {
	                
	        if(keyCode == KeyEvent.KEYCODE_BACK){

	        	 if((System.currentTimeMillis()- exitTime) > 2000){    
	                 Toast.makeText(getApplicationContext(), "再按一次退出程序", 2000).show();                                  
	                 exitTime = System.currentTimeMillis();     
	             } else {  
	                MainFace.this.finish();
	             }     
	            return true;
	    }else{                
	            return super.onKeyDown(keyCode, event);
	    }
	        }
}
