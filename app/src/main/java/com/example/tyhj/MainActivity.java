package com.example.tyhj;

import java.util.List;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SignUpCallback;
import com.example.shape.CircularImage;
import com.example.shape.Essay;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	public Button signUp, login;
	public EditText name, password, email;
	  AVFile headImage,bgImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		AVOSCloud.initialize(this, "TV2pl6bU8uu7eC0x083I0byd-gzGzoHsz",
				"kYtPwUPv3XQFtKW0dhVqq9p0");
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		AVUser currentUser = AVUser.getCurrentUser();
		if (currentUser != null) {
			Intent in1 = new Intent(MainActivity.this, MainFace.class);
			startActivity(in1);
			MainActivity.this.finish();
		} else {
			// 缓存用户对象为空时， 可打开用户注册界面…
		}
		setContentView(R.layout.activity_main);
		inti();
		signUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")
						&& !password.getText().toString().equals("")
						&& //
						!email.getText().toString().equals("")
						&& password.getText().toString().length() >= 6) {
					final AVUser user = new AVUser();
					AVQuery<AVObject> query = new AVQuery<AVObject>("initUser");
					query.whereEqualTo("one", 1);
					query.findInBackground(new FindCallback<AVObject>() {
					    public void done(List<AVObject> avObjects, AVException e) {
					        if (e == null) {
					           headImage=avObjects.get(0).getAVFile("headImage");
					           bgImage=avObjects.get(0).getAVFile("bgImage");
					       	user.setUsername(name.getText().toString());
							user.setPassword(password.getText().toString());
							user.setEmail(email.getText().toString());
					       	user.put("mobilePhoneNumber", name.getText());
							user.put("pName", name.getText());
							user.put("pSignature", "快去设置个性签名吧！");
							user.put("headImage", headImage);
							user.put("bgImage", bgImage);
							user.signUpInBackground(new SignUpCallback() {
								public void done(AVException e) {
									if (e == null) {
										Toast.makeText(getApplicationContext(),
												"发送请求成功,请到邮箱完成注册", 1).show();
									} else {

										if (e.getMessage().equals(
												"java.net.UnknownHostException")) {
											Toast.makeText(getApplicationContext(),
													"网络出错，请检查网络连接", 1).show();
										} else if (e.getMessage().substring(8, 11)
												.equals("203")) {
											Toast.makeText(getApplicationContext(),
													"邮箱已被注册，请重新输入", 1).show();
											email.setText("");
										} else if (e.getMessage().substring(8, 11)
												.equals("125")) {
											Toast.makeText(getApplicationContext(),
													"未找到该邮箱，请核对后输入", 1).show();
											email.setText("");
										} else if (e.getMessage().substring(8, 11)
												.equals("127")) {
											Toast.makeText(getApplicationContext(),
													"未找到该手机号，请核对后输入", 1).show();
											name.setText("");
										} else if (e.getMessage().substring(8, 11)
												.equals("214")) {
											Toast.makeText(getApplicationContext(),
													"手机号已被注册，请重新输入", 1).show();
											name.setText("");
										} else {
											Toast.makeText(getApplicationContext(),
													e.getMessage(), 2).show();
										}
									}
								}
							});
					        } else {
					            Toast.makeText(getApplicationContext(), e.getMessage()+"1",2).show();
					        }
					    }
					});
				} else {
					if (password.getText().toString().length() < 6) {
						Toast.makeText(getApplicationContext(), "密码不能少于6位", 1)
								.show();
					} else
						Toast.makeText(getApplicationContext(), "请完成注册信息的填写", 1)
								.show();
				}
			}
		});
		login.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					login.setBackgroundColor(Color.parseColor("#101aa2e1"));
					break;
				case MotionEvent.ACTION_UP:
					login.setBackgroundColor(Color.parseColor("#001aa2e1"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		signUp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO 自动生成的方法存根
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					signUp.setBackgroundColor(Color.parseColor("#2099ff66"));
					break;
				case MotionEvent.ACTION_UP:
					signUp.setBackgroundColor(Color.parseColor("#001aa2e1"));
					break;
				default:
					break;
				}
				return false;
			}
		});
		login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!name.getText().toString().equals("")
						&& !password.getText().toString().equals("")) {
					AVUser.logInInBackground(name.getText().toString(),
							password.getText().toString(), new LogInCallback() {
								public void done(AVUser user, AVException e) {
									if (e == null) {
										Intent in = new Intent(MainActivity.this,MainFace.class);
										startActivity(in);
										MainActivity.this.finish();
										overridePendingTransition(R.anim.out, R.anim.enter);  
									} else {
										if (e.getMessage()
												.equals("java.net.UnknownHostException")) {
											Toast.makeText(
													getApplicationContext(),
													"网络出错，请检查网络连接", 1).show();
										} else if (e.getMessage()
												.substring(8, 11).equals("211")) {
											Toast.makeText(
													getApplicationContext(),
													"未找到该账号", 1).show();
										} else if (e.getMessage()
												.substring(8, 11).equals("210")) {
											Toast.makeText(
													getApplicationContext(),
													"账号或密码错误", 1).show();
										} else if (e.getMessage()
												.substring(8, 11).equals("216")) {
											Toast.makeText(
													getApplicationContext(),
													"请先到邮箱完成邮箱验证", 1).show();
										} else {
											Toast.makeText(
													getApplicationContext(),
													e.getMessage(), 1).show();
										}
									}
								}
							});
				} else {
					Toast.makeText(getApplicationContext(), "账号或密码不能为空", 1)
							.show();
				}
			}
		});
	}

	private void inti() {
		signUp = (Button) findViewById(R.id.signup);
		login = (Button) findViewById(R.id.login);
		name = (EditText) findViewById(R.id.editText1);
		email = (EditText) findViewById(R.id.editText2);
		password = (EditText) findViewById(R.id.editText3);

	}
}
