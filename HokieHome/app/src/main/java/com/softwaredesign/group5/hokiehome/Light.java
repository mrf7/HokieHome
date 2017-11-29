package com.softwaredesign.group5.hokiehome;

/**
 * Created by Jordan on 11/23/2017.
 */

public class Light {
    String MAC;
    private int currentBrightness;

    public Light(String mac){
        this.MAC=mac;
    }
    public String getMAC() {
        return MAC;
    }

    public int getCurrentBrightness() {
        return currentBrightness;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public void setCurrentBrightness(int currentBrightness) {
        this.currentBrightness = currentBrightness;
    }
}
