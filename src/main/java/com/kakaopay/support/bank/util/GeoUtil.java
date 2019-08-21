package com.kakaopay.support.bank.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class GeoUtil {
    private static final double R = 6372.8;
    public static final Map<String, String> LEGION_GEOCODING = new HashMap<String, String>();

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

    public static String adjustArticleRegionName (String str) {
        str = str.replaceAll("충남", "충청남도");
        str = str.replaceAll("충북", "충청북도");
        str = str.replaceAll("전남", "전라남도");
        str = str.replaceAll("전북", "전라북도");
        str = str.replaceAll("경남", "경상남도");
        str = str.replaceAll("경북", "경상북도");
        return str;
    }

    static {
        ClassPathResource resource = new ClassPathResource("/default_lat_lon");
        try {
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            content.forEach(e -> setLegionGeocodingElement(e));
        } catch (IOException e) {
            log.error("{}", e.getMessage(), e);
        }
    }

    public static void setLegionGeocodingElement(String element) {
        String[] elementArr = element.split(",");
        LEGION_GEOCODING.put(elementArr[0], elementArr[1] + "," + elementArr[2]);
    }
}
