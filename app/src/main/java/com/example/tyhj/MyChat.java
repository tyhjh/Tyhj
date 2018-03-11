package com.example.tyhj;

import java.util.ArrayList;
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
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.getsome.ChatContent;
import com.example.getsome.ChatContentAdapter;
import com.example.getsome.OnTouchEvent;
import com.example.getsome.Zd;
import com.example.shape.Essay;
import com.example.shape.EssayAdapter;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyChat extends Activity{
	 public static class CustomMessageHandler extends AVIMMessageHandler{
		 String str,pname,head;
		   //接收到消息后的处理逻辑 
		   @Override
		   public void onMessage(final AVIMMessage message,AVIMConversation conversation,AVIMClient client){
		     if(message instanceof AVIMTextMessage){
		    	if(conversation.getName().equals(number+"&"+name)||conversation.getName().equals(name+"&"+number)){
		    	 ChatContent cc=new ChatContent( ((AVIMTextMessage)message).getText(),number, imageUrl);
					chatList.add(cc);
					cd.notifyDataSetChanged();
					list1.smoothScrollToPosition(list1.getCount() - 1);
					AVObject post = new AVObject("ChatRecord");
					post.put("username", name+number);
					post.put("username_z", number);
					post.put("user_head",imageUrl );
					post.put("Text", ((AVIMTextMessage)message).getText());
					post.saveInBackground(new SaveCallback() {
						
						@Override
						public void done(AVException e) {
							// TODO 自动生成的方法存根
							if(e==null){
								
							}else{
								Toast.makeText(con, "网络出错", 1).show();
							}
						}
					});
		   	 }else{
		    		List<String> list=conversation.getMembers();
		    		str=list.get(0);
		    		AVQuery<AVUser> query = AVUser.getQuery();
		    		query.whereEqualTo("username", str);
		    		query.findInBackground(new FindCallback<AVUser>() {
		    		    public void done(List<AVUser> objects, AVException e) {
		    		        if (e == null&&objects.size()>0) {
		    		            pname=objects.get(0).getString("pName");
		    		            head=objects.get(0).getAVFile("headImage").getUrl();
		    		        	 AVObject post = new AVObject("ChatRecord");
		    						post.put("username", name+str);
		    						post.put("username_z", str);
		    						post.put("user_head",head );
		    						post.put("Text", ((AVIMTextMessage)message).getText());
		    						post.saveInBackground();
		    						Toast.makeText(con,"来自"+pname+"的消息", 5).show();
		    		        } else {
		    		           
		    		        }
		    		    }
		    		});
		   	 }
		    	 if(!Zd.zd){
	        	 Vibrator vibrator = (Vibrator) con.getSystemService("vibrator");
					vibrator.vibrate(80);
		    	 }
		   }
		     }
		   public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){

		   }
		   }
	 AVIMTextMessage msg;
	 AVIMConversation Ac;
	static String number;
	TextView txv;
	static boolean is=false;
	static EditText text;
	ImageButton imgback;
	Button send;
	static AVUser user = AVUser.getCurrentUser();
	static String name;
	static String who;
	TextView back,zd;
	static ListView list1;
	static Context con;
	static String imageUrl;
	private static List<ChatContent> chatList=new ArrayList<ChatContent>();
	static ChatContentAdapter cd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		AVOSCloud.initialize(this, "TV2pl6bU8uu7eC0x083I0byd-gzGzoHsz",
				"kYtPwUPv3XQFtKW0dhVqq9p0");
		AVIMClient.setOfflineMessagePush(true);
		AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
		AVIMMessageManager.setConversationEventHandler(new CustomConversationEventHandler());
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mychat_main);
		Intent in=getIntent();
		number=in.getStringExtra("usernumber");
		name=user.getString("username");
			chatList=new ArrayList<ChatContent>();
		init();
		cd = new ChatContentAdapter(MyChat.this, R.layout.list_chat, chatList);
		list1.setAdapter(cd);
	}
	private void init() {
		con=MyChat.this;
		WindowManager wm = (WindowManager) MyChat.this
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	int xxxx=(int) (height/36);
	int xxx=(int) (height/25.7);
	
	AVQuery<AVObject> rd = new AVQuery<AVObject>("ChatRecord");
	rd.whereEqualTo("username", name+number);
	rd.orderByAscending("times");
	rd.findInBackground(new FindCallback<AVObject>() {
	    public void done(List<AVObject> avObjects, AVException e) {
	        if (e == null&&avObjects.size()>0) {
	        	if(avObjects.size()<10){
	           for(int i=0;i<avObjects.size();i++){
	        		ChatContent cc=new ChatContent(avObjects.get(i).getString("Text"),avObjects.get(i).getString("username_z"),avObjects.get(i).getString("user_head"));
					chatList.add(cc);
					cd.notifyDataSetChanged();
					list1.smoothScrollToPosition(list1.getCount() - 1);
	           }
	        }
	        	else{
	        		 for(int i=avObjects.size()-10;i<avObjects.size();i++){
	 	        		ChatContent cc=new ChatContent(avObjects.get(i).getString("Text"),avObjects.get(i).getString("username_z"),avObjects.get(i).getString("user_head"));
	 					chatList.add(cc);
	 					cd.notifyDataSetChanged();
	 	           }
	        	}
	        } else {
	            
	        }
	    }
	});
		
		list1=(ListView) findViewById(R.id.list1);
		zd=(TextView) findViewById(R.id.zd);
		zd.setText("振动");
		send=(Button) findViewById(R.id.send);
		text=(EditText) findViewById(R.id.text);
		txv=(TextView) findViewById(R.id.who);
		imgback=(ImageButton) findViewById(R.id.back_myessay);
		Picasso.with(MyChat.this).load(R.drawable.leftgo).resize(xxxx, xxxx)
		.centerCrop().into(imgback);
		back= (TextView) findViewById(R.id.back_text);
		sendMess(name,number);
		list1.smoothScrollToPosition(list1.getCount() - 1);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyChat.this.finish();
			}
		});
		imgback.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyChat.this.finish();
			}
		});
		OnTouchEvent ot=new OnTouchEvent();
		ot.touch(zd);
		zd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Zd.zd=!Zd.zd;
			}
		});
		send.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
					 msg = new AVIMTextMessage();
					 msg.setText(text.getText().toString());
					 Ac.sendMessage(msg, new AVIMConversationCallback() {
						@Override
						public void done(AVIMException arg0) {
							// TODO 自动生成的方法存根
							
						}
					});
				ChatContent cc=new ChatContent(text.getText().toString(),name, user.getAVFile("headImage").getUrl());
				chatList.add(cc);
				cd.notifyDataSetChanged();
				list1.smoothScrollToPosition(list1.getCount() - 1);
				AVObject post = new AVObject("ChatRecord");
				post.put("username", name+number);
				post.put("username_z", name);
				post.put("user_head",user.getAVFile("headImage").getUrl());
				post.put("Text", text.getText().toString());
				post.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(AVException e) {
						// TODO 自动生成的方法存根
						if(e==null){
							
						}else{
							Toast.makeText(con, "网络出错", 1).show();
						}
					}
				});
				text.setText("");
			}
		});
		AVQuery<AVUser> query = AVUser.getQuery();
		query.whereEqualTo("username", number);
		query.findInBackground(new FindCallback<AVUser>() {
			@Override
			public void done(List<AVUser> arg0, AVException e) {
		        if (e == null&&arg0.size()>0) {
		        	txv.setText(arg0.get(0).getString("pName"));
		        	imageUrl=arg0.get(0).getAVFile("headImage").getUrl();
		        }
			}
		});
	}
	public void sendMess(String str1,final String str2) {
	    // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
	    AVIMClient tom = AVIMClient.getInstance(str1);
	    tom.open(new AVIMClientCallback() {
			@Override
			public void done(AVIMClient arg0, AVIMException arg1) {
				if(arg1==null){
					arg0.createConversation(Arrays.asList(str2), name+"&"+number,null, new AVIMConversationCreatedCallback() {
						@Override
						public void done(AVIMConversation arg0, AVIMException arg1) {
							Ac=arg0;
						}
					});
				}else{
					Toast.makeText(getApplicationContext(), "网络出错", 5).show();
					send.setClickable(false);
				}
				
			}
		});
	  }
	class CustomConversationEventHandler extends AVIMConversationEventHandler {
		
		@Override
		public void onOfflineMessagesUnread(AVIMClient client,
				AVIMConversation conversation, int unreadCount) {
			if (unreadCount > 0) {
			    // 可以根据 readCount 更新 UI

			    // 也可以拉取对应的未读消息
			    conversation.queryMessages(unreadCount, new AVIMMessagesQueryCallback() {
			      @Override
			      public void done(List<AVIMMessage> list, AVIMException e) {
			        if (e == null&&list.size()>0) {
			         for(int i=0;i<list.size();i++){
			        	 ChatContent cc=new ChatContent( ((AVIMTextMessage)list.get(i)).getText(),number, imageUrl);
							chatList.add(cc);
							cd.notifyDataSetChanged();
							list1.smoothScrollToPosition(list1.getCount() - 1);
							AVObject post = new AVObject("ChatRecord");
							post.put("username", name+number);
							post.put("username_z", number);
							post.put("user_head",imageUrl );
							post.put("Text", ((AVIMTextMessage)list.get(i)).getText());
							post.saveInBackground();
			         }
			        }else{
			        	is=true;
			        }
			      }
			    });
			  }
			super.onOfflineMessagesUnread(client, conversation, unreadCount);
		}

		@Override
		public void onInvited(AVIMClient arg0, AVIMConversation arg1, String arg2) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onKicked(AVIMClient arg0, AVIMConversation arg1, String arg2) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onMemberJoined(AVIMClient arg0, AVIMConversation arg1,
				List<String> arg2, String arg3) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public void onMemberLeft(AVIMClient arg0, AVIMConversation arg1,
				List<String> arg2, String arg3) {
			// TODO 自动生成的方法存根
			
		}
		
	}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            
    if(keyCode == KeyEvent.KEYCODE_BACK){
    			MyChat.this.finish();
         }     
        return true;            
}

}