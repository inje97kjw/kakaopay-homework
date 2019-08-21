package com.kakaopay.support.util;

public class GeoUtil {
    private static final double R = 6372.8; // km

    private GeoUtil() {
    }

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
        double deltaLat = Math.toRadians(tolat - fromlat);
        double deltaLon = Math.toRadians(tolon - fromlon);
        fromlat = Math.toRadians(fromlat);
        tolat = Math.toRadians(tolat);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) + Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2) * Math.cos(fromlat) * Math.cos(tolat);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }

}
