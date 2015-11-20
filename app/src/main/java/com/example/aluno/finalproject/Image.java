package com.example.aluno.finalproject;

import android.graphics.Bitmap;

public class Image {
	private String id;
	private String path;
	private String title;
	private Bitmap photo;
	
	public Image(String id, String path, String title) {
		this.id    = id;
		this.path  = path;
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Bitmap getPhoto() {
		return this.photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	public Boolean hasPhoto() {
		return this.photo != null;
	}
}
