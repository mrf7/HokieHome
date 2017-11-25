package com.softwaredesign.group5.hokiehome;

import org.altbeacon.beacon.Beacon;

import java.util.ArrayList;

/**
 * Created by Jordan on 11/23/2017.
 */

class Room {
    private String name;
    private ArrayList<Light> lights;
    private Beacon b;

    public Room(String n, ArrayList<Light> l, Beacon beacon)
    {
        name = n;
        lights = l;
        b = beacon;

    }

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
