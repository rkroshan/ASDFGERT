package com.rajatv.surajv.roshank.sac.CoronaMelange;

/**
 * Created by CREATOR on 11/22/2017.
 */

public class CoronaObject {
    private String name;
    private int imageResId;

    public CoronaObject(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }
    public CoronaObject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
