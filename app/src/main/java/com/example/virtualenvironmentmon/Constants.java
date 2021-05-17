package com.example.virtualenvironmentmon;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }

    /*
    Networking-related codes
     */
    public static final int FORBIDDEN = 401;
    public static final int PAGE_NOT_FOUND = 404;
    public static final int CONN_TIMEOUT = 408;

    /*
    Login activity codes
     */
    public static final int LOGIN_OK = 0;
    public static final int WRONG_NODE = 1;
    public static final int WRONG_CREDENTIALS = 2;
    public static final int UNKNOWN_ERROR = -2;

    /*
    Unit measures conversions
     */
    public static final int bytes_to_gigabytes = 1024 * 1024 * 1024;
    public static final int bytes_to_megabytes = 1024 * 1024;

}