package com.example.aluno.finalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    HashMap<Marker, Image> markers = new HashMap<Marker, Image>();
    private Activity activity;
    private String query;
    private Integer limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        String messageText = intent.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);

        String[] splitedMessage = messageText.split("\\|");

        query = splitedMessage[0];
        limit = Integer.valueOf(splitedMessage[1]);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        FlickrLoader fl = new FlickrLoader();
        List<Image> images = fl.getImages(query, limit);

        for(Image img: images) {
            ImageMarker marker = fl.getPhotoCoordinates(img);

            Marker imageMarker = map.addMarker(new MarkerOptions().position(marker.getCoordinates()).title(marker.getImage().getTitle()));

            Bitmap image = getImage(img);
            if (image != null) {
                img.setPhoto(image);
                imageMarker.setIcon(BitmapDescriptorFactory.fromBitmap(image));
            }

            markers.put(imageMarker, img);
        }

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Image img = markers.get(marker);

                if (img.hasPhoto()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(img.getTitle());

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    public Bitmap getImage(Image img) {
        Bitmap image = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            image = BitmapFactory.decodeStream((InputStream) new URL(img.getPath()).getContent());
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }
}