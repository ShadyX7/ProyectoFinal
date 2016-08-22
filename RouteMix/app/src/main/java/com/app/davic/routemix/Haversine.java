package com.app.davic.routemix;

/**
 * Created by David on 14/06/2016.
 */
public class Haversine {
    private static final int RadioTierra = 6371; // utlilizo el radio equivolumen es de (6371 km) no el radio ecuatorial es de (6378 km).

    public static double distancia(double inicioLat, double inicioLong,
                                   double finLat, double finLong) {

        double dLat  = Math.toRadians((finLat - inicioLat));
        double dLong = Math.toRadians((finLong - inicioLong));

        inicioLat = Math.toRadians(inicioLat);
        finLat   = Math.toRadians(finLat);

        double a = haversin(dLat) + Math.cos(inicioLat) * Math.cos(finLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RadioTierra * c;
    }

    public static double haversin(double valor) {
        return Math.pow(Math.sin(valor / 2), 2);
    }
}


