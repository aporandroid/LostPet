package com.aporsoftware.lostpet;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;

import java.util.ArrayList;


public class NewPetFragment extends Fragment implements LocationListener {
    private final static String TAG = "NewPetFragment";

    private final static int LOCATION_REQUEST_CODE = 1;

    public Location mLocation;
    private LocationManager locationManager;
    private ArrayList<Pet> uploadQueue;
    private boolean haveValidLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_pet, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.INTERNET},
                        LOCATION_REQUEST_CODE);
                return;
            }
            locationManager.removeUpdates(this);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uploadQueue = new ArrayList<Pet>();

        mLocation = new Location("");
        haveValidLocation = false;

        startLocationUpdate();

        Pet pet = new Pet();

        pet.setPictureUrl("sample url");
        pet.setPetName("Bob");
        pet.setOwnerName("John");
        pet.setPhoneNumber("06205932138");
        pet.setEmailAddress("asdkm@gmail.com");
        pet.setPetDescription("Cute dog");
        pet.setExtraDescription("Missing her");


        addToUploadQueue(pet);
    }

    private void startLocationUpdate() {
        if(locationManager == null){
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        }

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.INTERNET},
                    LOCATION_REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startLocationUpdate();
                }
                break;
        }
    }

    public void addToUploadQueue(Pet pet){
        if(pet!=null){
            uploadQueue.add(pet);
            if(haveValidLocation){
                uploadPets();
            }
        }
    }

    public void uploadPets() {
        for(Pet pet:uploadQueue) {
            if (pet != null && mLocation != null) {
                Firebase base = new Firebase("https://lostpet-d6102.firebaseio.com/pets");
                Firebase p = base.push();

                //Log.i(TAG, p.getKey());

                p.child(Pet.keys[0]).setValue(pet.getPictureUrl());
                p.child(Pet.keys[1]).setValue(pet.getPetName());
                p.child(Pet.keys[2]).setValue(pet.getOwnerName());
                p.child(Pet.keys[3]).setValue(pet.getPhoneNumber());
                p.child(Pet.keys[4]).setValue(pet.getEmailAddress());
                p.child(Pet.keys[5]).setValue(pet.getPetDescription());
                p.child(Pet.keys[6]).setValue(pet.getExtraDescription());

                GeoLocation mGeoLocation = new GeoLocation(mLocation.getLatitude(), mLocation.getLongitude());
                //Log.i(TAG, mLocation.getLatitude() + " " + mLocation.getLongitude());

                GeoFire geoFire = new GeoFire(p);
                geoFire.setLocation(p.getKey(), mGeoLocation);

                p.push();
            }
        }
        uploadQueue.clear();
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null){
            mLocation.setLatitude(location.getLatitude());
            mLocation.setLongitude(location.getLongitude());
            haveValidLocation = true;
            uploadPets();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }
}
