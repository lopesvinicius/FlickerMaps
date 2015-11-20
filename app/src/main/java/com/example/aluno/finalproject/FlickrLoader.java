package com.example.aluno.finalproject;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlickrLoader {
	static String apiKey = "4268ed365f7879e06c0835e70e275ba2";
	//First of all, we need to get some images based up on an search query.
	public List<Image> getImages(String query, Integer limit) {
		List<Image> images = new ArrayList<Image>();
		
		String url = " https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+ apiKey +"&text=" + query.replace(" ", "%20") + "&safe_search=1&has_geo=1&geo_context=0&extras=url_s&format=json&nojsoncallback=1";
		String apiResponse = "";
		
		try {
			apiResponse = APIConnection.connect(url);
		} catch (IOException e) { e.printStackTrace(); }
		
		//Now we need to convert String to JSON and handle it, creating our Images objects.
		if (!apiResponse.isEmpty()) {
			try {
				JSONObject jsonObj = new JSONObject(apiResponse).getJSONObject("photos");
				JSONArray  photos  = (JSONArray) jsonObj.getJSONArray("photo");

				for(int i = 0; i < limit; i++) {
					JSONObject photo = photos.getJSONObject(i);
					
					String id    = photo.getString("id");
					String title = photo.getString("title");
					String path  = photo.getString("url_s");
					
					images.add(new Image(id, path, title));
				}				
			} catch (JSONException e) {	System.out.println(apiResponse); }
		}
		
		return images;
	}
	
	public ImageMarker getPhotoCoordinates(Image photo) {
		ImageMarker marker = null;
		String url = "https://api.flickr.com/services/rest/?method=flickr.photos.geo.getLocation&api_key="+ apiKey +"&photo_id="+ photo.getId() +"&extras=url_s&format=json&nojsoncallback=1";
		String apiResponse = "";
		
		try { apiResponse = APIConnection.connect(url); }
		catch (IOException ex) { ex.printStackTrace(); }
		
		if (!apiResponse.isEmpty()) {
			
			JSONObject photoObject;
			
			try {
				photoObject = new JSONObject(apiResponse).getJSONObject("photo");
				JSONObject location = photoObject.getJSONObject("location");
				
				Double latitude = location.getDouble("latitude");
				Double longitude = location.getDouble("longitude");

				LatLng coordinates = new LatLng(latitude, longitude);

				marker = new ImageMarker(photo, coordinates);
			} catch (JSONException e) { e.printStackTrace(); }
		}
		
		return marker;
	}
}
