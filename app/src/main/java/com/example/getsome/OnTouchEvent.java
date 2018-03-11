package com.example.getsome;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class OnTouchEvent {
	public void touch(final Button bt){
		bt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					bt.setBackgroundColor(Color.parseColor("#dddddd"));
					break;
				case MotionEvent.ACTION_UP:
					bt.setBackgroundColor(Color.parseColor("#00000000"));
				default:
					break;
				}
				return false;
			}
		});
	}
	public void touch(final ImageButton bt){
		bt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					bt.setBackgroundColor(Color.parseColor("#eeeeee"));
					break;
				case MotionEvent.ACTION_UP:
					bt.setBackgroundColor(Color.parseColor("#00000000"));
				default:
					break;
				}
				return false;
			}
		});
	}
	public void touch(final TextView bt){
bt.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					bt.setBackgroundColor(Color.parseColor("#eeeeee"));
					break;
				case MotionEvent.ACTION_UP:
					bt.setBackgroundColor(Color.parseColor("#00000000"));
				default:
					break;
				}
				return false;
			}
		});
	}
}
