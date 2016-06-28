package com.aporsoftware.lostpet;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private final static String TAG = "ListFragment";
    public final ArrayList<Pet> pets = new ArrayList<Pet>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase base = new Firebase("https://lostpet.firebaseio.com/pets");

        base.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Pet pet = new Pet();
                //String[] keys = {"id","pictureUrl","locationLon","locationLat","petName",
                //        "ownerName","phoneNumber", "emailAddress", "petDescription", "extraDescription"};

                pet.setId(Long.valueOf(dataSnapshot.getKey()));
                pet.setPictureUrl(dataSnapshot.child(Pet.keys[0]).getValue(String.class));
                pet.setPetName(dataSnapshot.child(Pet.keys[1]).getValue(String.class));
                pet.setOwnerName(dataSnapshot.child(Pet.keys[2]).getValue(String.class));
                pet.setPhoneNumber(dataSnapshot.child(Pet.keys[3]).getValue(String.class));
                pet.setEmailAddress(dataSnapshot.child(Pet.keys[4]).getValue(String.class));
                pet.setSpecies(dataSnapshot.child(Pet.keys[5]).getValue(String.class));
                pet.setAddress(dataSnapshot.child(Pet.keys[6]).getValue(String.class));
                pet.setPetDescription(dataSnapshot.child(Pet.keys[7]).getValue(String.class));
                pet.setExtraDescription(dataSnapshot.child(Pet.keys[8]).getValue(String.class));

                pets.add(pet);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int index = pets.indexOf(dataSnapshot.getKey());
                if(index != -1){
                    pets.get(index).setId(Long.valueOf(dataSnapshot.getKey()));
                    pets.get(index).setPictureUrl(dataSnapshot.child(Pet.keys[0]).getValue(String.class));
                    pets.get(index).setPetName(dataSnapshot.child(Pet.keys[1]).getValue(String.class));
                    pets.get(index).setOwnerName(dataSnapshot.child(Pet.keys[2]).getValue(String.class));
                    pets.get(index).setPhoneNumber(dataSnapshot.child(Pet.keys[3]).getValue(String.class));
                    pets.get(index).setEmailAddress(dataSnapshot.child(Pet.keys[4]).getValue(String.class));
                    pets.get(index).setSpecies(dataSnapshot.child(Pet.keys[5]).getValue(String.class));
                    pets.get(index).setAddress(dataSnapshot.child(Pet.keys[6]).getValue(String.class));
                    pets.get(index).setPetDescription(dataSnapshot.child(Pet.keys[7]).getValue(String.class));
                    pets.get(index).setExtraDescription(dataSnapshot.child(Pet.keys[8]).getValue(String.class));
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int index = pets.indexOf(dataSnapshot.getKey());
                if(index != -1){
                    pets.remove(index);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
