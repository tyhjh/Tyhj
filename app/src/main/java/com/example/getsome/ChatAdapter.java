package com.example.getsome;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.shape.CircularImage;
import com.example.shape.Essay;
import com.example.shape.ViewHolder;
import com.example.tyhj.MainFace;
import com.example.tyhj.MyChat;
import com.example.tyhj.R;
import com.example.tyhj.Visit;
import com.squareup.picasso.Picasso;

public class ChatAdapter extends ArrayAdapter<LinkMan_Message>{
	private int resourseId;
	String name;
	String MASTER="13678141943";
	AVUser user = AVUser.getCurrentUser();
	public ChatAdapter(Context context, int resource,
			List<LinkMan_Message> objects) {
		super(context, resource, objects);
		resourseId =resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final LinkMan_Message mess = getItem(position);
		final Viewhold vh=new Viewhold();
		convertView = LayoutInflater.from(getContext()).inflate(resourseId,null);
		vh.headImage=(CircularImage) convertView.findViewById(R.id.chat_head);
		vh.pName=(TextView) convertView.findViewById(R.id.chat_pname);
		vh.pSignature=(TextView) convertView.findViewById(R.id.chat_pSignature);
		vh.ll=(LinearLayout) convertView.findViewById(R.id.ll);
		Picasso.with(getContext()).load(mess.getHeadImage()).resize(90, 90)
		.centerCrop().into(vh.headImage);
		vh.pName.setText(mess.getPname());
		vh.pSignature.setText(mess.getPsignature());
		name=user.getString("username");
		vh.ll.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AVQuery<AVObject> query = new AVQuery<AVObject>("Linkman");
				query.whereEqualTo("username", name);
				query.whereEqualTo("friend", mess.getUsername());
				query.findInBackground(new FindCallback<AVObject>() {
				    public void done(List<AVObject> avObjects, AVException e) {
				        if (e == null&&avObjects.size()>0) {
				        	avObjects.get(0).deleteInBackground();
				        	//
				    		AVQuery<AVObject> query = new AVQuery<AVObject>("Linkman");
							query.whereEqualTo("username", mess.getUsername());
							query.whereEqualTo("friend", name);
							query.findInBackground(new FindCallback<AVObject>() {
							    public void done(List<AVObject> avObjects, AVException e) {
							        if (e == null&&avObjects.size()>0) {
							        	avObjects.get(0).deleteInBackground();
							        	
							        } else {
							      
							        }
							    }
							});
				        	Toast.makeText(getContext(), "已删除", 1).show();
				        	Intent intent=new Intent();
				        	intent.setClass(getContext(), MainFace.class); 
				        	Vibrator vibrator = (Vibrator) getContext().getSystemService("vibrator");
							vibrator.vibrate(80);
							//在adapter中删除
				        	Activity ac=(Activity) getContext();
				        	ac.finish();
				        	getContext().startActivity(intent);
				        } else {
				        	if(mess.getUsername().equals(MASTER)){
				        		Toast.makeText(getContext(), "无法删除Tyhj团队", 1).show();
				        	}else{
				        		Toast.makeText(getContext(), "别闹", 1).show();
				        	}
				        }
				    }
				});
				return false;
			}
		});
		vh.ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent();
				in.setClass(getContext(), MyChat.class); 
				in.putExtra("usernumber", mess.getUsername());
				getContext().startActivity(in);
			}
		});
		return convertView;
	}
}
class Viewhold{
	public CircularImage headImage;
	public TextView pName;
	public TextView pSignature;
	public LinearLayout ll;
}
