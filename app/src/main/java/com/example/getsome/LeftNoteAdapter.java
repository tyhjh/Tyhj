package com.example.getsome;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import com.example.tyhj.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class LeftNoteAdapter extends ArrayAdapter<Essay> {
	private int resourseId;
	Bitmap bitmap1 ;
	ImageButton img;
	String date;
	boolean ifcollect;
	AVUser user = AVUser.getCurrentUser();
	Vibrator vibrator;
	public LeftNoteAdapter(Context context, int resource, List<Essay> objects) {
		super(context, resource, objects);
		// TODO 自动生成的构造函数存根
		resourseId = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Essay Essay = getItem(position);
		final ViewHolder viewH;
		convertView = LayoutInflater.from(getContext()).inflate(resourseId,
				null);
		viewH = new ViewHolder();
		viewH.headImage = (CircularImage) convertView
				.findViewById(R.id.list_head);
		viewH.essay = (TextView) convertView.findViewById(R.id.list_text);
		viewH.name = (TextView) convertView.findViewById(R.id.list_name);
		viewH.date = (TextView) convertView.findViewById(R.id.list_date);
		viewH.name.setText(Essay.getessayName());
		viewH.essay.setText(Essay.getEssayText());
		viewH.date.setText(Essay.getEssaydate());
		Picasso.with(getContext()).load(Essay.getheadImageUrl())
		.resize(300, 300).centerCrop().into(viewH.headImage);
		return convertView;
	}
}
