package com.example.virtualenvironmentmon.models;

import java.io.Serializable;

public class VM implements Serializable {

    public String ID;
    public String name;
    public String status;
    public String template;
    public int uptime;
    public int cpus;
    public long maxMem;
    public long maxDisk;
    public int image;

    /*The following attributes will be null/zero if the VM is stopped.*/
    public long currentMem;
    public double cpuLoad;

    public VM(String ID, String name, String status, String template, int uptime, int cpus, long maxMem, long maxDisk, int image, long currentMem, double cpuLoad) {
        this.ID = ID;
        this.name = name;
        this.status = status;
        this.template = template;
        this.uptime = uptime;
        this.cpus = cpus;
        this.maxMem = maxMem;
        this.maxDisk = maxDisk;
        this.image = image;
        this.currentMem = currentMem;
        this.cpuLoad = cpuLoad;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ID: " + ID +
                " name: " + name +
                " status: " + status;
    }



}
