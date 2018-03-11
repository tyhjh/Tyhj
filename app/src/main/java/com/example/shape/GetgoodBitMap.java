package com.example.shape;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class GetgoodBitMap {
	public static Bitmap big(Bitmap bitmap, int x, int y) {
		Matrix matrix = new Matrix();
		matrix.postScale(1.5f, 1.5f); // ���Ϳ�Ŵ���С�ı���
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	/** Bitmap��С�ķ��� */
	public static Bitmap small(Bitmap bitmap, float x, float y) {
		Matrix matrix = new Matrix();
		float a = 270 / x;
		matrix.postScale(a, a); // ���Ϳ�Ŵ���С�ı���
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}

	public static Bitmap smallEssay(Bitmap bitmap, float x, float y) {
		Matrix matrix = new Matrix();
		float a = 450 / x;
		matrix.postScale(a, a); // ���Ϳ�Ŵ���С�ı���
		Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return resizeBmp;
	}
}
