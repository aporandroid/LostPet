package com.aporsoftware.lostpet;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;


public class ListFragment extends Fragment {
    private final static String TAG = "ListFragment";
    public final ArrayList<Pet> pets = new ArrayList<Pet>();
    private ListView mPetsListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list,container);

        mPetsListView = (ListView) rootView.findViewById(R.id.petsListView);
        mPetsListView.setAdapter(new PetsAdapter(getActivity()));
        return rootView;
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

                pet.setId(dataSnapshot.getKey());
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
                    pets.get(index).setId(dataSnapshot.getKey());
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
    class PetsAdapter extends BaseAdapter{
        Context ctxt;
        PetsAdapter(Context context){
            ctxt = context;
        }
        @Override
        public int getCount() {
            return pets.size();
        }

        @Override
        public Pet getItem(int position) {
            return pets.get(position);
        }

        @Override
        public long getItemId(int position) {
            return Long.valueOf(pets.get(position).getId());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inf = (LayoutInflater) ctxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inf.inflate(R.layout.pets_single_row,parent);
            TextView detailText = (TextView) row.findViewById(R.id.pet_detail_row);
            ImageView petImage = (ImageView) row.findViewById(R.id.pet_picture_row);
            Pet currentPet =(getItem(position));
            detailText.setText(currentPet.getPetName());
            //petImage.setImageBitmap(currentPet.getImage()); //TODO: ha megvan a függvéy, a zárójeleket ki kell venni!!!!!
            return row;
        }
    }
}
