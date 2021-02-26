package com.depromeet.team5.util;

import com.depromeet.team5.domain.Location;

public class LocationDistanceUtils {
    private LocationDistanceUtils() {
    }

    /**
     * 두 지점간의 거리 계산
     *
     * @param source 지점 1 위도, 경도
     * @param target 지점 2 위도, 경도
     * @return 두 지점의 거리 (단위: meter)
     */
    public static Integer getDistance(Location source, Location target) {
        if (source == null || source.isEmpty() || target == null || target.isEmpty()) {
            return null;
        }
        double lat1 = source.getLatitude();
        double lon1 = source.getLongitude();
        double lat2 = target.getLatitude();
        double lon2 = target.getLongitude();

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (int) (dist * 1609.344);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}
