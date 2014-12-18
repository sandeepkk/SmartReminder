package com.skk.smartreminder.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skk.smartreminder.activities.NewReminder;
import com.skk.smartreminder.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by Archie on 12/8/2014.
 */
public class LocationFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static LocationFragment newInstance(int sectionNumber) {
        LocationFragment fragment = new LocationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    private static final String TAG = "LocationFragment";
    Double latitude;
    Double longitude;
    NewReminder remiderActivity;
    Geocoder geocoder;
    List<Address> addresses;
    Long radiusValue;
    EditText textAddress;
    EditText textRadius;
    View view;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_frag, container, false);
        textAddress = (EditText) view.findViewById(R.id.address);
        textRadius = (EditText) view.findViewById(R.id.radius);

        remiderActivity = (NewReminder) getActivity();
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().
                    findFragmentById(R.id.map)).getMap();
            geocoder = new Geocoder(view.getContext());
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if (!textRadius.getText().toString().equals("")) {
                        radiusValue = Long.parseLong(textRadius.getText().toString());
                        dropMarker(latLng);
                    } else {
                        showDialogForRadius();
                    }

                }
            });
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Button button = (Button) view.findViewById(R.id.buttonShow);
        button.setOnClickListener(this);
        return view;
    }

    private void showDialogForRadius() {
        AlertDialog.Builder alert = new AlertDialog.Builder(remiderActivity);
        alert.setMessage("Please enter the Radius");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        alert.show();
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(remiderActivity);
        alert.setMessage("Please enter the Location and Radius");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        alert.show();
    }

    private void dropMarker(LatLng position) {
        googleMap.clear();

        try {
            addresses = geocoder.getFromLocation(position.latitude, position.longitude, 1);
            if (addresses.size() > 0) {
                latitude = addresses.get(0).getLatitude();
                longitude = addresses.get(0).getLongitude();
                Address address = addresses.get(0);

                textAddress.setText(address.getAddressLine(0)
                        + " " + address.getAddressLine(1));

            }
        } catch (IOException e) {
            Log.e(TAG, "Error occured on dropMarker() " + e.getMessage());
        }
        updateCamera(position);

    }

    private void updateCamera(LatLng position) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 15);
        googleMap.addCircle(new CircleOptions()
                .center(position)
                .radius(radiusValue)
                .strokeColor(Color.RED));
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        remiderActivity.setLatitude(latitude);
        remiderActivity.setLongitude(longitude);
        remiderActivity.setRadius(radiusValue);
        remiderActivity.setLocationName(textAddress.getText().toString());
    }

    @Override
    public void onClick(View v) {
        if (textAddress.getText() == null || textRadius.getText() == null
                || textAddress.getText().toString().isEmpty() ||
                textRadius.getText().toString().isEmpty()) {
            showDialog();
        } else {
            radiusValue = Long.parseLong(textRadius.getText().toString());
            try {
                addresses = geocoder.getFromLocationName(textAddress.getText().toString(), 1);
                if (addresses.size() > 0) {
                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();
                    final LatLng latLng = new LatLng(latitude, longitude);
                    updateCamera(latLng);
                }
            } catch (IOException e) {
                Log.e(TAG, "Error occured on Click() " + e.getMessage());
            }
        }
    }
}
