package com.example.getsome;

public class ChatContent {
	public String text;
	public String name;
	public String headImageUrl;
	public ChatContent(String str1,String str2,String str3) {
		text=str1;
		name=str2;
		headImageUrl=str3;
	}
	public String getText(){
		return text;
	}
	public String getName(){
		return name;
	}
	public String getHeadImageUrl(){
		return headImageUrl;
	}
}
