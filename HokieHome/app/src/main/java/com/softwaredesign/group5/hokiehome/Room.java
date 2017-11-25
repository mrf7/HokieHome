package com.softwaredesign.group5.hokiehome;

import java.util.ArrayList;

/**
 * Created by Jordan on 11/23/2017.
 */

class Room {
    private String name;
    private ArrayList<Light> lights;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void removeLight(Light l)
    {
        lights.remove(l);
    }

    public void addLight(Light l)
    {
        lights.add(l);
    }
}
