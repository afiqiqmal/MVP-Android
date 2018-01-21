package com.java.mvp.mvpandroid.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.mvp.client.entity.response.map.DirectionPath;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Inject;

/**
 * @author : hafiq on 26/02/2017.
 */

public class GMapUtils {

    public enum Mode{
        DRIVING("driving"),
        BICYCLING("bicycling"),
        WALKING("walking");

        private final String s;

        Mode(String s) {
            this.s = s;
        }

        public String getName() {
            return s;
        }
    }

    private String distance;
    private String time;
    private Mode modeType;
    private Context context;

    private static final String MODE = "mode";

    @Inject
    public GMapUtils(Context context){
        this.context = context;
    }

    public void setMode(Mode modeType){
        this.modeType = modeType;
    }

    public LinkedHashMap<String,String> getDirectionsUrl(LatLng origin, LatLng dest){

        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("origin",origin.latitude+","+origin.longitude);
        map.put("destination",dest.latitude+","+dest.longitude);
        map.put("sensor","false");
        map.put("units","metric");

        if (modeType!=null){
            map.put(MODE,modeType.getName());
        }
        else{
            map.put(MODE, Mode.DRIVING.getName());
        }

        return map;
    }

    @Nullable
    public List<LatLng> parse(DirectionPath path) {
        for (int i = 0; i < path.getRoutes().size(); i++) {
            distance = path.getRoutes().get(i).getLegs().get(i).getDistance().getText();
            time = path.getRoutes().get(i).getLegs().get(i).getDuration().getText();
            String encodedString = path.getRoutes().get(0).getOverviewPolyline().getPoints();
            return decodePoly(encodedString);
        }

        return null;
    }

    @Nullable
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }
}
