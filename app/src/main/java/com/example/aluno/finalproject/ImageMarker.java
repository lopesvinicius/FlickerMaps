package com.example.aluno.finalproject;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class ImageMarker{
	private Image image;
	private LatLng coordinates;
	
	public ImageMarker(Image image, LatLng coordinates) {
		super();
		this.image 	     = image;
		this.coordinates = coordinates;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public LatLng getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
}
