package com.example.shape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.example.getsome.OnTouchEvent;
import com.example.tyhj.MyCollcet;
import com.example.tyhj.MyEssay;
import com.example.tyhj.MyLeftNote;
import com.example.tyhj.MyPhoto;
import com.example.tyhj.R;
import com.example.tyhj.Validation;
import com.squareup.picasso.Picasso;

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
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PersonalCetenr extends Fragment {
	AVUser user;
	View view ;
	public LinearLayout bground;
	public TextView pname;
	public Button camoral, images;
	public ImageButton myessaysign,mylovesign,myeassayphotosign,myleftnotesign,setsign;
	public Button myessay,mylove,myphoto,myleftnote,set;
	public CircularImage header;
	private Uri imageUri;
	String bkground = "bground.jpg";
	String name;
	int[] bg=new int[38];
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	int WHERE_PHOTO = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		view = inflater.inflate(R.layout.personal, null);
		init();
		return view;
	}
	private void init() {
		bginit();
		WindowManager wm = (WindowManager) getActivity()
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	int xxxx=(int) (height/36);
	int xxx=(int) (height/25.7);
		int count=user.getInt("bg");
		myessaysign=(ImageButton) view.findViewById(R.id.myessay_sign);
		mylovesign=(ImageButton) view.findViewById(R.id.mylove_sign);
		myeassayphotosign=(ImageButton) view.findViewById(R.id.myphoto_sign);
		myleftnotesign=(ImageButton) view.findViewById(R.id.myleftnote_sign);
		setsign=(ImageButton) view.findViewById(R.id.set_sign);
		myessay=(Button) view.findViewById(R.id.myessay);
		mylove=(Button) view.findViewById(R.id.mylove);
		myphoto=(Button) view.findViewById(R.id.myphoto);
		myleftnote=(Button) view.findViewById(R.id.myleftnote);
		set=(Button) view.findViewById(R.id.set);
		header = (CircularImage) view.findViewById(R.id.head);
		pname=(TextView) view.findViewById(R.id.pname);
		bground = (LinearLayout) view.findViewById(R.id.background);
		pname.setText(user.getString("pSignature")+"——"+user.getString("pName")+"   ");
		bground.setBackgroundResource(bg[count]);
		Picasso.with(getActivity()).load(R.drawable.myessay).resize(xxxx , xxxx).centerCrop().into(myessaysign);
		Picasso.with(getActivity()).load(R.drawable.myphoto).resize(xxxx , xxxx).centerCrop().into(myeassayphotosign);
		Picasso.with(getActivity()).load(R.drawable.essaylove).resize(xxxx , xxxx).centerCrop().into(mylovesign);
		Picasso.with(getActivity()).load(R.drawable.leftnote).resize(xxxx , xxxx).centerCrop().into(myleftnotesign);
		Picasso.with(getActivity()).load(R.drawable.set).resize(xxxx , xxxx).centerCrop().into(setsign);
		//Touch事件
		OnTouchEvent tc=new OnTouchEvent();
		tc.touch(myessay);
		tc.touch(mylove);
		tc.touch(myphoto);
		tc.touch(myleftnote);
		tc.touch(set);
		//点击事件
		myleftnote.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(getActivity(),MyLeftNote.class);
				startActivity(in);
			}
		});
		myphoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(getActivity(),MyPhoto.class);
				startActivity(in);
			}
		});
		myessay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(getActivity(),MyEssay.class);
				startActivity(in);
			}
		});
		mylove.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(getActivity(),MyCollcet.class);
				startActivity(in);
			}
		});
		// 设置头像
				Picasso.with(getActivity()).load(user.getAVFile("headImage").getUrl())
						.resize(190, 190).centerCrop().into(header);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO 自动生成的方法存根
				final Bitmap bitmap1 = returnBitMap(user.getAVFile("bgImage")
						.getUrl());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				bground.post(new Runnable() {
					@Override
					public void run() {
						Drawable drawable = new BitmapDrawable(bitmap1);
						bground.setBackgroundDrawable(drawable);
					}
				});
			}
		}).start();
	}
	private void bginit() {
		bg[0]=R.drawable.bg0;
		bg[1]=R.drawable.bg1;
		bg[2]=R.drawable.bg2;
		bg[3]=R.drawable.bg3;
		bg[4]=R.drawable.bg4;
		bg[5]=R.drawable.bg5;
		bg[6]=R.drawable.bg6;
		bg[7]=R.drawable.bg7;
		bg[8]=R.drawable.bg8;
		bg[9]=R.drawable.bg9;
		bg[10]=R.drawable.bg10;
		bg[11]=R.drawable.bg11;
		bg[12]=R.drawable.bg12;
		bg[13]=R.drawable.bg13;
		bg[14]=R.drawable.bg14;
		bg[15]=R.drawable.bg15;
		bg[16]=R.drawable.bg16;
		bg[17]=R.drawable.bg17;
		bg[18]=R.drawable.bg18;
		bg[19]=R.drawable.bg19;
		bg[20]=R.drawable.bg20;
		bg[21]=R.drawable.bg21;
		bg[22]=R.drawable.bg22;
		bg[23]=R.drawable.bg23;
		bg[24]=R.drawable.bg24;
		bg[25]=R.drawable.bg25;
		bg[26]=R.drawable.bg26;
		bg[27]=R.drawable.bg27;
		bg[28]=R.drawable.bg28;
		bg[29]=R.drawable.bg29;
		bg[30]=R.drawable.bg30;
		bg[31]=R.drawable.bg31;
		bg[32]=R.drawable.bg32;
		bg[33]=R.drawable.bg33;
		bg[34]=R.drawable.bg34;
		bg[35]=R.drawable.bg35;
		bg[36]=R.drawable.bg36;
		bg[37]=R.drawable.bground;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onActivityCreated(savedInstanceState);
			set.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent in = new Intent(getActivity(), Validation.class);
					startActivity(in);
				}
			});
		// 个人资料
				header.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent in = new Intent(getActivity(), Validation.class);
						startActivity(in);
						getActivity().finish();
					}
				});
		//背景
		bground.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder di = new AlertDialog.Builder(getActivity());
				di.setCancelable(true);
				LayoutInflater inflater = LayoutInflater.from(getActivity());
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
						startActivityForResult(intent, 1);
					}
				});
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
					AVFile file = null;
					if (WHERE_PHOTO == 1) {
						file = AVFile.withAbsoluteLocalPath(bkground,
								Environment.getExternalStorageDirectory() + "/"
										+ bkground);
					} else if (WHERE_PHOTO == 2) {
						file = AVFile.withAbsoluteLocalPath(
								bkground,
								getFilePathFromUri(getActivity(), imageUri,
										null, null, null, null));
					}
					WHERE_PHOTO = 0;
					file.saveInBackground();
					user.put("bgImage", file);
					user.saveInBackground();
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
