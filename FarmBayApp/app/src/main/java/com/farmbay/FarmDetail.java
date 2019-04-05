package com.farmbay;

/**
 * Created by manas on 4/28/2018.
 */

public class FarmDetail {
    private int area;
    private int zipcode;
    private String state;
    private int expectedReturns;

    public int getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    private int maxArea;

    public FarmDetail(){
        area=0;
        zipcode=94086;
        state="California";
        expectedReturns=1000;
        maxArea=0;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getExpectedReturns() {
        return expectedReturns;
    }

    public void setExpectedReturns(int expectedReturns) {
        this.expectedReturns = expectedReturns;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    private int duration;

}
