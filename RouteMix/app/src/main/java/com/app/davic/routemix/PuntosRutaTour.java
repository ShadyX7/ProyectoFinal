package com.app.davic.routemix;

import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Victor on 15/06/2016.
 */
public class PuntosRutaTour {
    private TourAPI usuarioTour;

    public Observable<MapboxDirections> getDirections(LatLng comienzo, LatLng finalRuta, String accessToken){
        String wayPoints = comienzo.getLongitude() + "," + comienzo.getLatitude() + ";" + finalRuta.getLongitude() + "," + finalRuta.getLatitude();
        return usuarioTour.getDirections(wayPoints, accessToken, false)
                .subscribeOn(Schedulers.io());
    }


    private static PuntosRutaTour instancia;

    public static PuntosRutaTour init(TourAPI client) {
        instancia = new PuntosRutaTour();
        instancia.usuarioTour = client;
        return instancia;
    }


    private PuntosRutaTour() {

    }
}
