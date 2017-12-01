package com.softwaredesign.group5.hokiehome;

/**
 * Created by Jordan on 11/23/2017.
 */

public class Light {
    int id;
    private int currentBrightness;

    public Light(int identifier) {
        id = identifier;
    }


    public int getCurrentBrightness() {
        return currentBrightness;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCurrentBrightness(int currentBrightness) {
        this.currentBrightness = currentBrightness;
    }
}
