package com.example.virtualenvironmentmon;

import android.os.Binder;
import android.os.IBinder;

import it.corsinvest.proxmoxve.api.PveClient;

public class ObjectWrapperForBinder extends Binder {
    private final PveClient mData;

    public ObjectWrapperForBinder(PveClient data) {
        mData = data;
    }

    public PveClient getData() {
        return mData;
    }
}
