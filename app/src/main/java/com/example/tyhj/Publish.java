package com.example.tyhj;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.shape.GetgoodBitMap;
import com.squareup.picasso.Picasso;

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
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Publish extends Activity {
	public EditText text;
	public ImageButton photo;
	public Button back, pubsh;
	public Button camoral, images;
	private Uri imageUri;
	int go=0;
	AVUser user;
	String bkground = "essayImage.jpg";
	String name, date, essayText, pName;
	boolean ifcan=false;
	int WHERE_PHOTO = 0;
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish);
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		pName = user.getString("pName");
		init();
	}

	private void init() {
		WindowManager wm = (WindowManager) Publish.this
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	int xxxx=(int) (height/36);
	final int xxx=(int) (height/7);
		photo = (ImageButton) findViewById(R.id.publishPhoto);
		text = (EditText) findViewById(R.id.pubtext);
		back = (Button) findViewById(R.id.pubback);
		pubsh = (Button) findViewById(R.id.pubpublish);
		Picasso.with(Publish.this).load(R.drawable.add).resize(xxx , xxx).centerCrop().into(photo);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(Publish.this, MainFace.class);
				startActivity(in);
				Publish.this.finish();
			}
		});
		back.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					back.setBackgroundColor(Color.parseColor("#30888888"));
					break;
				case MotionEvent.ACTION_UP:
					back.setBackgroundColor(Color.parseColor("#00000000"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		pubsh.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pubsh.setBackgroundColor(Color.parseColor("#30888888"));
					break;
				case MotionEvent.ACTION_UP:
					pubsh.setBackgroundColor(Color.parseColor("#00000000"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		// 说说
		photo.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.add);
				float height = bitmap.getHeight();
				float width = bitmap.getWidth();
				Picasso.with(Publish.this).load(R.drawable.add).resize(xxx , xxx).centerCrop().into(photo);
				ifcan=false;
				Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
				vibrator.vibrate(80);
				return false;
			}
		});
		photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder di = new AlertDialog.Builder(Publish.this);
				di.setCancelable(true);
				LayoutInflater inflater = LayoutInflater.from(Publish.this);
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
								.getExternalStorageDirectory(), bkground);
						try {
							if (outputImage.exists()) {
								outputImage.delete();
							}
							outputImage.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
						imageUri = Uri.fromFile(outputImage);
						Intent intent = new Intent(
								"android.media.action.IMAGE_CAPTURE");
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
								.getExternalStorageDirectory(), bkground);
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
						Intent intent = new Intent(
								"android.intent.action.GET_CONTENT");
						intent.setType("image/*");
						intent.putExtra("crop", true);
						intent.putExtra("scale", true);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
						startActivityForResult(intent, TAKE_PHOTO);
					}
				});
				// 相机
				camoral.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO 自动生成的方法存根
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							camoral.setBackgroundColor(Color
									.parseColor("#d0c0c0c0"));
							break;
						case MotionEvent.ACTION_UP:
							camoral.setBackgroundColor(Color
									.parseColor("#ffffff"));
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
							images.setBackgroundColor(Color
									.parseColor("#d0c0c0c0"));
							break;
						case MotionEvent.ACTION_UP:
							images.setBackgroundColor(Color
									.parseColor("#ffffff"));
							break;
						default:
							break;
						}
						return false;
					}
				});
			}
		});
		// 发表
		pubsh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AVObject gt = new AVObject("GetEssayImage");
				AVFile file = null;
				if((!text.getText().toString().equals(""))||ifcan){
				if (WHERE_PHOTO == 1&&ifcan) {
					pubsh.setClickable(false);
					try {
						file = AVFile.withAbsoluteLocalPath(bkground,
								Environment.getExternalStorageDirectory() + "/"
										+ bkground);
					} catch (FileNotFoundException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				} else if (WHERE_PHOTO == 2&&ifcan) {
					pubsh.setClickable(false);
					try {
						file = AVFile.withAbsoluteLocalPath(
								bkground,
								getFilePathFromUri(getApplicationContext(),
										imageUri, null, null, null, null));
					} catch (FileNotFoundException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String data = df.format(new Date()).substring(0, 19);
				date = data.substring(0, 4) + "/" + data.substring(5, 7) + "/"
						+ data.substring(8, 10) + "  " + data.substring(11, 13)
						+ ":" + data.substring(14, 16);
				if (file != null) {
					file.saveInBackground();
					gt.put("EssayImage", file);
				}
				gt.put("username", name);
				gt.put("date", date);
				gt.put("EssayText", text.getText().toString());
				gt.put("pName", pName);
				gt.put("headImage", user.getAVFile("headImage"));
				gt.saveInBackground(new SaveCallback() {

					@Override
					public void done(AVException e) {
						// TODO 自动生成的方法存根
						if (e == null) {
							Toast.makeText(getApplicationContext(), "发表成功", 1)
									.show();
							Intent in = new Intent(Publish.this, MainFace.class);
							WHERE_PHOTO = 0;
							startActivity(in);
							Publish.this.finish();
							overridePendingTransition(R.anim.out, R.anim.enter);  
						} else {
							pubsh.setClickable(true);
							if (e.getMessage().equals(
									"java.net.UnknownHostException")) {
								Toast.makeText(getApplicationContext(),
										"网络出错，请检查网络连接", 1).show();
							}else{
								Toast.makeText(getApplicationContext(),
										e.getMessage(), 1).show();
							}
						}
					}
				});
				
			}else{
				Toast.makeText(getApplicationContext(), "发表内容不能为空", 1).show();
			}
		}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PHOTO:
			if (resultCode == -1) {
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
			if (resultCode == -1) {
				try {
					Bitmap bitmap = BitmapFactory
							.decodeStream(getContentResolver().openInputStream(
									imageUri));
					float height = bitmap.getHeight();
					float width = bitmap.getWidth();
					GetgoodBitMap gbt = new GetgoodBitMap();
					photo.setImageBitmap(gbt.small(bitmap, height, width));
					ifcan=true;
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
}
