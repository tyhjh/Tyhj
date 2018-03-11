package com.example.getsome;

import java.util.List;

import com.avos.avoscloud.AVUser;
import com.example.shape.CircularImage;
import com.example.tyhj.R;
import com.example.tyhj.Visit;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatContentAdapter extends ArrayAdapter<ChatContent>{
	private int resourseId;
	AVUser user=AVUser.getCurrentUser();
	String name=user.getString("username");
	public ChatContentAdapter(Context context, int resource, List<ChatContent> objects) {
		super(context, resource, objects);
		resourseId = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ChatContent ct=getItem(position);
		ViewHolder vh=new ViewHolder();
		convertView = LayoutInflater.from(getContext()).inflate(resourseId,null);
		vh.head=(CircularImage) convertView.findViewById(R.id.headImage_chat);
		vh.tx=(TextView) convertView.findViewById(R.id.text_chat);
		vh.po=(LinearLayout) convertView.findViewById(R.id.position);
		vh.po1=(LinearLayout) convertView.findViewById(R.id.position1);
		vh.head1=(CircularImage) convertView.findViewById(R.id.headImage_chat1);
		vh.tx1=(TextView) convertView.findViewById(R.id.text_chat1);
		if(ct.getName().equals(name)){
			vh.po.setVisibility(View.GONE);
			vh.tx1.setText(ct.getText());
			vh.tx1.setTextSize(16);
			vh.tx1.setBackgroundResource(R.drawable.mychatbg);
		Picasso.with(getContext()).load(ct.getHeadImageUrl())
		.resize(130, 130).centerCrop().into(vh.head1);
		}else{
			vh.po1.setVisibility(View.GONE);
			vh.tx.setTextSize(16);
			vh.tx.setBackgroundResource(R.drawable.chatbg);
			vh.tx.setText(ct.getText());
			Picasso.with(getContext()).load(ct.getHeadImageUrl())
			.resize(130, 130).centerCrop().into(vh.head);
			vh.head.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent in=new Intent(getContext(),Visit.class);
					if(!ct.getName().equals(name)){
					in.putExtra("usernumber", ct.getName());
					getContext().startActivity(in);
					}
				}
			});
		}
		return convertView;
	}
}
class ViewHolder{
	public TextView tx;
	public CircularImage head;
	public LinearLayout po1,po;
	public TextView tx1;
	public CircularImage head1;
}