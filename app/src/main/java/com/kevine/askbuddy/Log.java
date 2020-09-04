package com.kevine.askbuddy;

public class Log {
    public static boolean DEBUG_MODE = false;
    private String message;

    public Log() {

    }

    public static void debug(String message) {
        if (BuildConfig.DEBUG) {
            //if(!Constants.NET_URL.contains(Constants.HOSTNAME)){
            System.out.println(message);

        }

    }

    public static void Error(Exception e) {
        if (BuildConfig.DEBUG) {
            // if(!Constants.NET_URL.contains(Constants.HOSTNAME)){
            e.printStackTrace();

            //}

        }
        //Crashlytics.log(e.toString());

    }

    public static void Error(String e) {
        if (BuildConfig.DEBUG) {
            //if(!Constants.NET_URL.contains(Constants.HOSTNAME)){
            debug(e);
            // }

        }
        // Crashlytics.log(e);

    }

    public static void debug(String title, String message) {
        if (BuildConfig.DEBUG) {

            //if(!Constants.NET_URL.contains(Constants.HOSTNAME)){
            System.out.println(title + ":" + message);
            // }
        }
    }
}
