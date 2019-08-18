package com.ibk.support.localgov.util;

public class GeoUtil {
    private static final double R = 6372.8; // km

    /**
     * Harversine method.
     * <p>
     * @param fromlat - from lat
     * @param fromlon - from lon
     * @param tolat - to lat
     * @param tolon - to lon
     * @return great circle distance
     */
    public static double calculateDistance(double fromlat, double fromlon, double tolat, double tolon) {
        double delta_Lat = Math.toRadians(tolat - fromlat);
        double delta_Lon = Math.toRadians(tolon - fromlon);
        fromlat = Math.toRadians(fromlat);
        tolat = Math.toRadians(tolat);

        double a = Math.sin(delta_Lat / 2) * Math.sin(delta_Lat / 2) + Math.sin(delta_Lon / 2) * Math.sin(delta_Lon / 2) * Math.cos(fromlat) * Math.cos(tolat);
        double c = 2 * Math.asin(Math.sqrt(a));
        double distance = R * c;
        return distance;
    }

}
