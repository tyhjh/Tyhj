package com.example.tyhj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Validation extends Activity {
	public EditText adress, sex, perfession, psign, pname;
	public Button back, change, camoral, images, turnoff;
	public TextView number, email;
	public ImageButton headimage;
	AVFile file = null;
	String name;
	private Uri imageUri;
	AVUser user;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	AlertDialog.Builder di;
	int WHERE_PHOTO = 0;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalcenter);
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		init();
		getMessage();
		// 头像点击
		headimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog();
			}
		});
		// 点击返回
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(Validation.this, MainFace.class);
				startActivity(in);
				overridePendingTransition(R.anim.out, R.anim.enter);  
				Validation.this.finish();
			}
		});
		// 修改信息
		change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				user.put("pName", pname.getText().toString());
				user.put("sex", sex.getText().toString());
				user.put("location", adress.getText().toString());
				if (psign.getText().toString().length() > 19)
					user.put("pSignature", psign.getText().toString()
							.substring(0, 19)+"...");
				else
					user.put("pSignature", psign.getText().toString());
				user.put("perfession", perfession.getText().toString());
				user.signUpInBackground(new SignUpCallback() {
					public void done(AVException e) {
						if (e == null) {
							Toast.makeText(getApplicationContext(), "保存成功", 1)
									.show();
						} else {
							if (e.getMessage().equals(
									"java.net.UnknownHostException")) {
								Toast.makeText(getApplicationContext(),
										"网络出错，请检查网络连接", 1).show();
							}else{
								Toast.makeText(getApplicationContext(), e.getMessage(),
										1).show();
							}
						}
					}
				});
				getMessage();
				AVQuery<AVObject> query = new AVQuery<AVObject>("GetEssayImage");
				query.whereEqualTo("username", name);
				query.findInBackground(new FindCallback<AVObject>() {
				    public void done(List<AVObject> avObjects, AVException e) {
				        if (e == null&&avObjects.size()>0) {
				           for(int i=0;i<avObjects.size();i++){
				        	   avObjects.get(i).put("pName",pname.getText().toString() );
				        	   avObjects.get(i).saveInBackground();
				           }
				        } else {
				            Log.d("失败", "查询错误: " + e.getMessage());
				        }
				    }
				});
			}
		});
	}
	private void getMessage() {
		pname.setText(user.getString("pName"));
		sex.setText(user.getString("sex"));
		perfession.setText(user.getString("perfession"));
		psign.setText(user.getString("pSignature"));
		adress.setText(user.getString("location"));
		number.setText(user.getString("username"));
		email.setText(user.getString("email"));
		Picasso.with(Validation.this)
				.load(user.getAVFile("headImage").getUrl()).resize(182, 182)
				.centerCrop().into(headimage);
	}

	// 初始化
	private void init() {
		turnoff = (Button) findViewById(R.id.turnoff);
		headimage = (ImageButton) findViewById(R.id.head_image);
		change = (Button) findViewById(R.id.change);
		back = (Button) findViewById(R.id.button_back);
		adress = (EditText) findViewById(R.id.place);
		sex = (EditText) findViewById(R.id.sex);
		perfession = (EditText) findViewById(R.id.area);
		psign = (EditText) findViewById(R.id.psign);
		pname = (EditText) findViewById(R.id.pname);
		number = (TextView) findViewById(R.id.phone_number);
		email = (TextView) findViewById(R.id.email);
		turnoff.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				AVUser.logOut();
				Intent in = new Intent(Validation.this, MainActivity.class);
				startActivity(in);
				overridePendingTransition(R.anim.out, R.anim.enter);  
				Validation.this.finish();
			}
		});
		change.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					change.setBackgroundColor(Color.parseColor("#10c0c0c0"));
					break;
				case MotionEvent.ACTION_UP:
					change.setBackgroundColor(Color.parseColor("#00c0c0c0"));
					break;
				default:
					break;
				}
				return false;
			}
		});

		back.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					back.setBackgroundColor(Color.parseColor("#d0c0c0c0"));
					break;
				case MotionEvent.ACTION_UP:
					back.setBackgroundColor(Color.parseColor("#00c0c0c0"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		turnoff.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					turnoff.setBackgroundColor(Color.parseColor("#20ff3333"));
					break;
				case MotionEvent.ACTION_UP:
					turnoff.setBackgroundColor(Color.parseColor("#60ff3333"));
					break;
				default:
					break;
				}
				return false;
			}
		});

	}

	// 上传头像
	private void dialog() {
		di = new AlertDialog.Builder(Validation.this);
		di.setCancelable(true);
		LayoutInflater inflater = LayoutInflater.from(Validation.this);
		View layout = inflater.inflate(R.layout.headchoose, null);
		di.setView(layout);
		di.create();
		di.show();
		camoral = (Button) layout.findViewById(R.id.camoral);
		images = (Button) layout.findViewById(R.id.images);
		// 相机
		camoral.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File outputImage = new File(Environment
						.getExternalStorageDirectory(), "head.jpg");
				try {
					if (outputImage.exists()) {
						outputImage.delete();
					}
					outputImage.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);
				Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
				WHERE_PHOTO = 1;
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, TAKE_PHOTO);
			}
		});
		// 相册
		images.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				File outputImage = new File(Environment
						.getExternalStorageDirectory(), "head.jpg");
				try {
					if (outputImage.exists()) {
						outputImage.delete();
					}
					outputImage.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				imageUri = Uri.fromFile(outputImage);
				WHERE_PHOTO = 2;
				Intent intent = new Intent("android.intent.action.GET_CONTENT");
				intent.setType("image/*");
				intent.putExtra("crop", true);
				intent.putExtra("scale", true);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, 1);
			}
		});
		camoral.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					camoral.setBackgroundColor(Color.parseColor("#d0c0c0c0"));
					break;
				case MotionEvent.ACTION_UP:
					camoral.setBackgroundColor(Color.parseColor("#ffffff"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		images.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					images.setBackgroundColor(Color.parseColor("#d0c0c0c0"));
					break;
				case MotionEvent.ACTION_UP:
					images.setBackgroundColor(Color.parseColor("#ffffff"));
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == RESULT_OK) {
				if (data != null) {
					imageUri = data.getData();
				}
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(imageUri, "image/*");
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent, CROP_PHOTO);
			}
			break;
		case CROP_PHOTO:
			if (resultCode == RESULT_OK) {
				try {
					  Bitmap
					 bitmap=BitmapFactory.decodeStream(getContentResolver
					 ().openInputStream(imageUri)); Drawable drawable =new
					 BitmapDrawable(bitmap);
					 headimage.setBackgroundDrawable(drawable);
					 
					if (WHERE_PHOTO == 1) {
						file = AVFile.withAbsoluteLocalPath("head.jpg",
								Environment.getExternalStorageDirectory()
										+ "/head.jpg");
					} else if (WHERE_PHOTO == 2) {
						file = AVFile.withAbsoluteLocalPath(
								"head.jpg",
								getFilePathFromUri(getApplicationContext(),
										imageUri, null, null, null, null));
					}
					WHERE_PHOTO = 0;
					user.put("headImage", file);
					user.saveInBackground();
					getMessage();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}

	// 通过URI找path
	public static String getFilePathFromUri(Context context, Uri uri,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		Cursor cursor = context.getContentResolver().query(uri, projection,
				selection, selectionArgs, sortOrder);
		int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String path = cursor.getString(index);
		cursor.close();
		cursor = null;
		return path;
	}
}
