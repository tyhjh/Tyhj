package com.example.shape;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.tyhj.MainActivity;
import com.example.tyhj.MainFace;
import com.example.tyhj.Publish;
import com.example.tyhj.R;
import com.example.tyhj.Validation;
import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Fragment {
	public CircularImage header;
	public LinearLayout bground;
	public Button camoral, images;
	public ImageButton addphoto;
	public View view;
	public ListView list1;
	public TextView pname;
	boolean ss = true;
	public EditText publish;
	private Uri imageUri;
	String bkground = "bground.jpg";
	public static final int TAKE_PHOTO = 1;
	public static final int CROP_PHOTO = 2;
	int count = 0;
	int WHERE_PHOTO = 0;
	EssayAdapter dp;
	String name;
	int xxxx,xxx;
	AVUser user;
	String pnameurl,headurl;
	int[] bg=new int[38];
	private List<Essay> essayList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		essayList = new ArrayList<Essay>();
		user = AVUser.getCurrentUser();
		name = user.getString("username");
		count=user.getInt("bg");
		view = inflater.inflate(R.layout.home, null);// 注意不要指定父视图
		//
		View headerView = inflater.inflate(R.layout.list_header, null);
		header = (CircularImage) headerView.findViewById(R.id.head);
		bground = (LinearLayout) headerView.findViewById(R.id.background);
		addphoto = (ImageButton) headerView.findViewById(R.id.addphotos);
		publish = (EditText) headerView.findViewById(R.id.publish);
		pname=(TextView) headerView.findViewById(R.id.pname);
		list1 = (ListView) view.findViewById(R.id.list1);
		init();
		bground.setBackgroundResource(bg[count]);
		list1.addHeaderView(headerView);
		dp = new EssayAdapter(getActivity(), R.layout.list_item, essayList);
		list1.setAdapter(dp);
		// 设置ListView
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}

	private void init() {
		WindowManager wm = (WindowManager) getActivity()
	            .getSystemService(Context.WINDOW_SERVICE);
	float width = wm.getDefaultDisplay().getWidth();
	float height = wm.getDefaultDisplay().getHeight();
	xxxx=(int) (height/20);
	xxx=(int) (height/12);
		bginit();
		final SwipeRefreshLayout swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
		swiperefresh.setColorSchemeResources(
				android.R.color.holo_red_light,
				android.R.color.holo_blue_light,
				android.R.color.holo_green_light);// 设置刷新动画的颜色
		swiperefresh.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// TODO 自动生成的方法存根
				swiperefresh.setRefreshing(true);// 开始刷新
				// 执行刷新后需要完成的操作
				Intent in = new Intent(getActivity(),MainFace.class);
				getActivity().finish();
				startActivity(in);
				getActivity().overridePendingTransition(R.anim.out, R.anim.enter);  
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
				
						// 刷新完成
						swiperefresh.setRefreshing(false);// 结束刷新
					}
				}, 3000);// 刷新动画持续2秒
			}
		});
		Picasso.with(getActivity()).load(R.drawable.addphoto).resize(xxxx, xxxx)
				.centerCrop().into(addphoto);
		// 设置头像
		Picasso.with(getActivity()).load(user.getAVFile("headImage").getUrl())
				.resize(xxx, xxx).centerCrop().into(header);
		//个性签名
		pname.setText(user.getString("pSignature")+"——"+user.getString("pName")+"   ");
/*		new Thread(new Runnable() {
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
		}).start();*/
		AVQuery<AVObject> query2 = new AVQuery<AVObject>("GetEssayImage");
		query2.orderByDescending("times");
		query2.findInBackground(new FindCallback<AVObject>() {
			public void done(List<AVObject> avObjects, AVException e) {
				if (e == null && avObjects.size() > 0) {
					for (int i = 0; i < avObjects.size(); i++) {
						try {
							Essay essay = new Essay(avObjects.get(i).getString("pName"), avObjects.get(i).getAVFile("headImage").getUrl(), avObjects.get(i).getAVFile("EssayImage").getUrl(),
									avObjects.get(i).getString("EssayText"),avObjects.get(i).getString("date"),avObjects.get(i).getInt("times"),avObjects.get(i).getString("username"));
							essayList.add(essay);
						} catch (Exception e2) {
							Essay essay = new Essay(avObjects.get(i).getString("pName"), avObjects.get(i).getAVFile("headImage").getUrl(),
									"noPhoto", avObjects.get(i).getString("EssayText"), avObjects.get(i).getString("date"),avObjects.get(i).getInt("times"),avObjects.get(i).getString("username"));
							essayList.add(essay);
						}
					}
					// dp.addAll(essayList);
					dp.notifyDataSetChanged();
				}
			}
		});
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
		super.onActivityCreated(savedInstanceState);
		// list
		addphoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in = new Intent(getActivity(), Publish.class);
				startActivity(in);
			}
		});
		addphoto.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					addphoto.setBackgroundColor(Color.parseColor("#cccccc"));
					break;
				case MotionEvent.ACTION_UP:
					addphoto.setBackgroundColor(Color.parseColor("#eeeeee"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		publish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Intent in = new Intent(getActivity(), Publish.class);
				startActivity(in);
			}
		});
		bground.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				if(count<37){
					count++;
				}else{
					count=0;
				}
				user.put("bg", count);
				user.saveInBackground();
				Intent in = new Intent(getActivity(),MainFace.class);
				getActivity().finish();
				startActivity(in);
				getActivity().overridePendingTransition(R.anim.out, R.anim.enter);  
			}
		});
/*		// 背景
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
		});*/
		// 个人资料
		header.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent in = new Intent(getActivity(), Validation.class);
				startActivity(in);
				getActivity().finish();
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
