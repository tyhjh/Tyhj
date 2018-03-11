package com.example.shape;

import android.graphics.Bitmap;

public class Essay {
	private String essayName;
	private String headImageUrl;
	private String essayImageUrl;
	private String essayText, date,name;
	private int times;

	public Essay(String str1, String str2, String str3, String str4, String str5,int time,String name) {
		this.essayName = str1;
		this.headImageUrl = str2;
		this.essayImageUrl = str3;
		this.essayText = str4;
		this.date = str5;
		this.times=time;
		this.name=name;
	}

	public String getessayName() {
		return essayName;
	}

	public String getImageUrl() {
		return essayImageUrl;
	}

	public String getheadImageUrl() {
		return headImageUrl;
	}

	public String getEssayText() {
		return essayText;
	}

	public String getEssaydate() {
		return date;
	}
	public String getName() {
		return name;
	}
	public int getTimes(){
		return times;
	}
}
