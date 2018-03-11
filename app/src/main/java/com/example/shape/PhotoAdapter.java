package com.example.shape;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.getsome.Photos;
import com.example.tyhj.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

public class PhotoAdapter extends ArrayAdapter<Photos>{
	private int resourseId;
	Viewholde viewh;
	Bitmap bitmap1 ;
	ImageButton img;
	Vibrator vibrator;
	public PhotoAdapter(Context context, int textViewResourceId,
			List<Photos> objects) {
		super(context,textViewResourceId, objects);
		resourseId = textViewResourceId;
	}
	//得到屏幕的大小
	WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Photos photo=getItem(position);
		convertView = LayoutInflater.from(getContext()).inflate(resourseId,null);
		viewh=new Viewholde();
		viewh.image1= (ImageButton) convertView.findViewById(R.id.myimg1);
		viewh.image2= (ImageButton) convertView.findViewById(R.id.myimg2);
		viewh.image3= (ImageButton) convertView.findViewById(R.id.myimg3);
		if(!photo.getName1().equals("noPhoto")){
			Picasso.with(getContext()).load(new File(photo.getName1()))
			.resize(width/3-1,width/3-1).centerCrop().into(viewh.image1);
		}	
		if(!photo.getName2().equals("noPhoto")){
			Picasso.with(getContext()).load(new File(photo.getName2()))
			.resize(width/3-1,width/3-1).centerCrop().into(viewh.image2);
		}
		if(!photo.getName3().equals("noPhoto")){
			Picasso.with(getContext()).load(new File(photo.getName3()))
			.resize(width/3-1,width/3-1).centerCrop().into(viewh.image3);
		}
		viewh.image1.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				vibrator = (Vibrator) getContext().getSystemService("vibrator");
				vibrator.vibrate(80);
				File f=new File(photo.getName1());
				f.delete();
				Toast.makeText(getContext(), "已删除", 1).show();
				return false;
			}
		});
viewh.image2.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				vibrator = (Vibrator) getContext().getSystemService("vibrator");
				vibrator.vibrate(80);
				File f=new File(photo.getName2());
				f.delete();
				Toast.makeText(getContext(), "已删除", 1).show();
				return false;
			}
		});
viewh.image3.setOnLongClickListener(new OnLongClickListener() {
	
	@Override
	public boolean onLongClick(View v) {
		vibrator = (Vibrator) getContext().getSystemService("vibrator");
		vibrator.vibrate(80);
		File f=new File(photo.getName3());
		f.delete();
		Toast.makeText(getContext(), "已删除", 1).show();
		return false;
	}
});
		viewh.image1.setOnClickListener(new OnClickListener() {
			
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
				 Picasso.with(getContext()).load(new File(photo.getName1())).transform(new Transformation() {
	                 @Override
	                 public Bitmap transform(Bitmap source) {//从原图中间裁剪一个正方形
	                     float size = Math.max(source.getWidth(), source.getHeight());
	                     float x = source.getWidth();
	                     float y = source.getHeight();
	                     if(size>width){
	                    	 size=size/(width);
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
	             }).into(img);
			}
		});
	viewh.image2.setOnClickListener(new OnClickListener() {
			
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
				 Picasso.with(getContext()).load(new File(photo.getName2())).transform(new Transformation() {
	                 @Override
	                 public Bitmap transform(Bitmap source) {//从原图中间裁剪一个正方形
	                     float size = Math.max(source.getWidth(), source.getHeight());
	                     float x = source.getWidth();
	                     float y = source.getHeight();
	                     if(size>width){
	                    	 size=size/(width);
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
	             }).into(img);
				 
			}
		});
	viewh.image3.setOnClickListener(new OnClickListener() {
		
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
			 Picasso.with(getContext()).load(new File(photo.getName3())).transform(new Transformation() {
                 @Override
                 public Bitmap transform(Bitmap source) {//从原图中间裁剪一个正方形
                     float size = Math.max(source.getWidth(), source.getHeight());
                     float x = source.getWidth();
                     float y = source.getHeight();
                     if(size>width){
                    	 size=size/(width);
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
             }).into(img);
			 
		}
	});
		return convertView;
	}
}
class Viewholde{
	public ImageButton image1;
	public ImageButton image2;
	public ImageButton image3;
}
