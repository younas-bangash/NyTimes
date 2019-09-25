package com.nytimes.populararticles.api;


/**
 * Keep all the service related constants here.
 */
public class ApiConstants {
    public static final String SERVICES_BASE_URL = "http://api.nytimes.com/";
    public static final long CONNECT_TIMEOUT = 30000;
    public static final long READ_TIMEOUT = 30000;
    public static final long WRITE_TIMEOUT = 30000;
    public static final String API_KEY = "GO2uSGakYyev27GpasLUDVDNmBAtdZPj";

    private ApiConstants(){
        // Private constructor to hide the implicit one
    }
}
