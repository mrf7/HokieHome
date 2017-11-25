package com.softwaredesign.group5.hokiehome;

/**
 * Created by Jordan on 11/23/2017.
 */

class User {
    private String Username;
    private String Password;
    private int preBrightness;

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setPreBrightness(int preBrightness) {
        this.preBrightness = preBrightness;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public int getPreBrightness() {
        return preBrightness;
    }
}
