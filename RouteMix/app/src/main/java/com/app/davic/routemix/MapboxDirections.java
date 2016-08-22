package com.app.davic.routemix;

import java.util.ArrayList;

/**
 * Created by Victor on 15/06/2016.
 */
public class MapboxDirections {
    private String error;
    private ArrayList<RutasTour> routes;

    public ArrayList<RutasTour> getRoutes() {
        return routes;
    }

    public String getError() {
        return error;
    }
}
