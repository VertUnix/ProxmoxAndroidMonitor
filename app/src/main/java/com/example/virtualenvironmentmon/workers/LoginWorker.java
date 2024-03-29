package com.example.virtualenvironmentmon.workers;

import android.app.Activity;

/**
 * Class that executes the login asynchronously, on a separate thread.
 * @author VertUnix
 */
public class LoginWorker extends Thread{

    private final Activity activity;
    private final String name;
    private final String password;
    private final String IP;
    private final int port;

    /**
     * The constructor of the class.
     */
    public LoginWorker(Activity activity, String name, String password, String IP, int port) {
        this.activity = activity;
        this.name = name;
        this.password = password;
        this.IP = IP;
        this.port = port;
    }

    @Override
    public void run() {
        super.run();

    }
}
