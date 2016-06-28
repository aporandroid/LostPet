package com.aporsoftware.lostpet;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * Created by gellert on 2016. 05. 20..
 */
public class Pet {
    private long id;
    private String pictureUrl;
    private String petName;
    private String ownerName;
    private String phoneNumber;
    private String emailAddress;
    private String petDescription;
    private String extraDescription;
    private Bitmap image;

    public final static String[] keys = new String[]{
    "pictureUrl",
    "petName",
    "ownerName",
    "phoneNumber",
    "emailAddress",
    "petDescription",
    "extraDescription"};

    public Pet() {
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Pet(String emailAddress, String extraDescription, long id, String ownerName, String petDescription, String petName, String phoneNumber, String pictureUrl) {
        this.emailAddress = emailAddress;
        this.extraDescription = extraDescription;
        this.id = id;
        this.ownerName = ownerName;
        this.petDescription = petDescription;
        this.petName = petName;
        this.phoneNumber = phoneNumber;
        this.pictureUrl = pictureUrl;
    }

    public String getEmailAdsress() {
        return emailAddress;
    }

    public void setEmailAdsress(String emailAdsress) {
        this.emailAddress = emailAdsress;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public void setExtraDescription(String extraDescription) {
        this.extraDescription = extraDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void uploadImage(){
        if(image == null){
            return;
        }
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://lostpet-d6102.appspot.com");

        StorageReference imagesRef = storageRef.child("images");
        StorageReference imageRef = imagesRef.child(id + ".png");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                setPictureUrl(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                setPictureUrl(downloadUrl.toString());
            }
        });
    }
}
