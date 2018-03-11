package com.example.getsome;

public class Photos {
	private String ImageUrl1,ImageUrl2,ImageUrl3;
	public Photos(String str1,String str2,String str3){
		ImageUrl1=str1;
		ImageUrl2=str2;
		ImageUrl3=str3;
	}
	public String getName1(){
		return ImageUrl1;
	}
	public String getName2(){
		return ImageUrl2;
	}
	public String getName3(){
		return ImageUrl3;
	}
}
