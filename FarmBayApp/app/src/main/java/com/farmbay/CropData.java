package com.farmbay;

/**
 * Created by manas on 5/1/2018.
 */

public class CropData {

    private String Crop;
    private String Quantity;
    private String City;
    private String State;
    private String Min_bid;

    public CropData() {


    }
    public CropData(String crop, String quantity, String city, String state, String min_bid) {
        Crop = crop;
        Quantity = quantity;
        City = city;
        State = state;
        Min_bid = min_bid;
    }

    public String getCrop() {
        return Crop;
    }

    public void setCrop(String crop) {
        Crop = crop;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getMin_bid() {
        return Min_bid;
    }

    public void setMin_bid(String min_bid) {
        Min_bid = min_bid;
    }

}
