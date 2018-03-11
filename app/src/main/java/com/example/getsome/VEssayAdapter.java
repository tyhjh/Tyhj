package com.example.getsome;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.tyhj.Publish;
import com.example.tyhj.R;
import com.example.tyhj.Validation;
import com.example.tyhj.Visit;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class VEssayAdapter extends ArrayAdapter<Essay> {
	private int resourseId;
	Bitmap bitmap1 ;
	ImageButton img;
	String date;
	boolean ifcollect;
	AVUser user = AVUser.getCurrentUser();
	Vibrator vibrator;
	public VEssayAdapter(Context context, int textViewResourceId,
			List<Essay> objects) {
		super(context, textViewResourceId, objects);
		resourseId = textViewResourceId;
	}
	WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);
float width = wm.getDefaultDisplay().getWidth();
float height = wm.getDefaultDisplay().getHeight();
int xxxx=(int) (height/36);
int xxx=(int) (height/25.7);
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Essay Essay = getItem(position);
		final ViewHolder viewH;
		convertView = LayoutInflater.from(getContext()).inflate(resourseId,
				null);
		viewH = new ViewHolder();
		viewH.image = (ImageButton) convertView.findViewById(R.id.list_image);
		viewH.collect=(ImageButton) convertView.findViewById(R.id.collect);
		viewH.comment=(ImageButton) convertView.findViewById(R.id.comment);
		viewH.headImage = (CircularImage) convertView.findViewById(R.id.list_head);
		viewH.essay = (TextView) convertView.findViewById(R.id.list_text);
		viewH.name = (TextView) convertView.findViewById(R.id.list_name);
		viewH.date = (TextView) convertView.findViewById(R.id.list_date);
		if (Essay.getImageUrl().equals("noPhoto")) {
			Picasso.with(getContext()).load(Essay.getheadImageUrl())
					.resize(300, 300).centerCrop().into(viewH.headImage);
		} else {
			/*Picasso.with(getContext()).load(Essay.getImageUrl())
					.resize(300, 300).centerCrop().into(viewH.image);*/
			 Picasso.with(getContext()).load(Essay.getImageUrl()).transform(new Transformation() {
                 @Override
                 public Bitmap transform(Bitmap source) {//从原图中间裁剪一个正方形
                     float size = Math.max(source.getWidth(), source.getHeight());
                     float x = source.getWidth();
                     float y = source.getHeight();
                     if(size>width*2/3){
                    	 size=size/(width*2/3);
                    	 size=1/size;
                     }else{
                    	 size=1;
                     }
                     Matrix matrix = new Matrix();
                     matrix.postScale(size, size);
                     
                     Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(),  source.getHeight(),matrix, true);
                     if (result != source) {
                         source.recycle();
                     }
                     return result;
                 }

                 @Override
                 public String key() {
                     return "square()";
                 }
             }).into(viewH.image);
			Picasso.with(getContext()).load(Essay.getheadImageUrl()).resize(300, 300).centerCrop().into(viewH.headImage);
		}
		Picasso.with(getContext()).load(R.drawable.nocollect).resize(xxxx, xxxx).centerCrop().into(viewH.collect);
		Picasso.with(getContext()).load(R.drawable.comment).resize(xxxx, xxxx).centerCrop().into(viewH.comment);
		viewH.name.setText(Essay.getessayName());
		viewH.essay.setText(Essay.getEssayText());
		viewH.date.setText(Essay.getEssaydate());
		AVQuery<AVObject> query2 = new AVQuery<AVObject>("CollectEssay");
		query2.whereEqualTo("username", user.getString("username"));
		query2.whereEqualTo("times", Essay.getTimes());
		query2.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null && avObjects.size() > 0) {
					Picasso.with(getContext()).load(R.drawable.havecollect).resize(xxx, xxx).centerCrop().into(viewH.collect);
					ifcollect=true;
				}else{
					Picasso.with(getContext()).load(R.drawable.nocollect).resize(xxxx, xxxx).centerCrop().into(viewH.collect);
					ifcollect=false;
				}
			}
		});
		viewH.comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "你走开，不要你评论", 1).show();
			}
		});
		viewH.collect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
					AVQuery<AVObject> query2 = new AVQuery<AVObject>("CollectEssay");
					query2.whereEqualTo("username", user.getString("username"));
					query2.whereEqualTo("times", Essay.getTimes());
					query2.findInBackground(new FindCallback<AVObject>() {
						public void done(List<AVObject> avObjects, AVException e) {
							if (e == null && avObjects.size() > 0) {
								Picasso.with(getContext()).load(R.drawable.nocollect).resize(xxxx, xxxx).centerCrop().into(viewH.collect);
								avObjects.get(0).deleteInBackground();
								ifcollect=false;
							}else{
								Picasso.with(getContext()).load(R.drawable.havecollect).resize(xxx, xxx).centerCrop().into(viewH.collect);
								ifcollect=true;
								AVObject av = new AVObject("CollectEssay");
								av.put("username", user.get("username"));
								av.put("pName",Essay.getessayName() );
								av.put("Text", Essay.getEssayText());
								av.put("date", Essay.getEssaydate());
								av.put("times", Essay.getTimes());
								av.put("photoUrl", Essay.getImageUrl());
								av.put("headImageUrl", Essay.getheadImageUrl());
								av.saveInBackground();
				}
						}
					});
			}
		});
		viewH.image.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				AlertDialog.Builder di = new AlertDialog.Builder(getContext());
				di.setCancelable(true);
				LayoutInflater inflater = LayoutInflater.from(getContext());
				View layout = inflater.inflate(R.layout.bigimage, null);
				di.setView(layout);
				di.create();
				di.show();
			    img=(ImageButton) layout.findViewById(R.id.bigphoto);
			    
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO 自动生成的方法存根
						 bitmap1 = returnBitMap(Essay.getImageUrl());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						img.post(new Runnable() {
							@Override
							public void run() {
								Drawable drawable = new BitmapDrawable(bitmap1);
								img.setBackgroundDrawable(drawable);
							}
						});
					}
				}).start();
				img.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");// 设置日期格式
						date =df.format(new Date()).substring(0, 19)+".JPEG";
						    vibrator = (Vibrator) getContext().getSystemService("vibrator");
							vibrator.vibrate(80);
						saveBitmap(date, bitmap1);
						return false;
					}
				});
			}
		});
		return convertView;
	}
	private Bitmap returnBitMap(String path) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(path);
			URLConnection conn = url.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	//图片保存，压缩
	 public void saveBitmap(String str,Bitmap bm) {
		  File f1 = new File(Environment.getExternalStorageDirectory()+"/TyhjPhoto");
		  if(!f1.exists()){
			  f1.mkdirs();
		  }
		  File f = new File(Environment.getExternalStorageDirectory()+"/TyhjPhoto",str);
		  int options = 80;
		   ByteArrayOutputStream baos = new ByteArrayOutputStream();
		   bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
		   while (baos.toByteArray().length / 1024 > 2000) { 
			      baos.reset();
			      options -= 10;
			      bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
			    }
		   try {
			      FileOutputStream fos = new FileOutputStream(f);
			      fos.write(baos.toByteArray());
			      fos.flush();
			      fos.close();
			    } catch (Exception e) {
			      e.printStackTrace();
			    }
		   Toast.makeText(getContext(), "已保存", 700).show();
		 }
}