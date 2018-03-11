package com.example.getsome;

public class LinkMan_Message {
	private String usernumber;
	private String headImage;
	private String pName;
	private String pSignatrue;
	public LinkMan_Message(String str1,String str2,String str3,String str4) {
		usernumber=str1;
		headImage=str2;
		pName=str3;
		pSignatrue=str4;
	}
	public String getHeadImage(){
		return headImage;
	}
	public String getPname(){
		return pName;
	}
	public String getPsignature(){
		return pSignatrue;
	}
	public String getUsername(){
		return usernumber;
	}
}
