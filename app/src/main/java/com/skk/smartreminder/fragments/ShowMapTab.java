package com.skk.smartreminder.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skk.smartreminder.utils.DatabaseHelper;
import com.skk.smartreminder.R;

/**
 * Created by sandeepkannan on 12/16/14.
 */
public class ShowMapTab extends Fragment {
    private GoogleMap googleMap;
    Geocoder geocoder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_frag, container, false);

        Cursor cursor = DatabaseHelper.getInstance(getActivity()).loadReminders();
        cursor.moveToFirst();


        googleMap = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.mapForView)).getMap();
        googleMap.setMyLocationEnabled(true);
        while (!cursor.isAfterLast()) {
            String str_latitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.XCOORDS));
            String str_longitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.YCOORDS));
            String str_radius = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RADIUS));
            String str_todo = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE));
            if (str_radius!=null
                    && !str_todo.trim().isEmpty()
                    && !str_latitude.trim().isEmpty()
                    && !str_longitude.trim().isEmpty()
                    && !str_radius.trim().isEmpty()) {
                geocoder = new Geocoder(getActivity());
                final LatLng latLng = new LatLng(Double.parseDouble(str_latitude), Double.parseDouble(str_longitude));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 1);
                googleMap.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(Double.parseDouble(str_radius))
                        .strokeColor(Color.RED));
                googleMap.animateCamera(cameraUpdate);
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(str_todo)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
             }
            cursor.moveToNext();
        }
        return rootView;
    }

}
