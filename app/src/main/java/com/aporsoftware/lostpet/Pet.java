package com.aporsoftware.lostpet;

/**
 * Created by gellert on 2016. 05. 20..
 */
public class Pet {
    private long id;
    private String pictureUrl;
    private double locationLon;
    private double locationLat;
    private String petName;
    private String ownerName;
    private String phoneNumber;
    private String emailAdsress;
    private String petDescription;
    private String extraDescription;

    public Pet() {
    }

    public Pet(String emailAdsress, String extraDescription, long id, double locationLat, double locationLon, String ownerName, String petDescription, String petName, String phoneNumber, String pictureUrl) {
        this.emailAdsress = emailAdsress;
        this.extraDescription = extraDescription;
        this.id = id;
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.ownerName = ownerName;
        this.petDescription = petDescription;
        this.petName = petName;
        this.phoneNumber = phoneNumber;
        this.pictureUrl = pictureUrl;
    }

    public String getEmailAdsress() {
        return emailAdsress;
    }

    public void setEmailAdsress(String emailAdsress) {
        this.emailAdsress = emailAdsress;
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

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(double locationLon) {
        this.locationLon = locationLon;
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
}
