package com.app.davic.routemix;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.geometry.LatLng;


import rx.subjects.BehaviorSubject;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;


import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Victor on 15/06/2016.
 */
public class Tours extends Fragment {


    View myView;




    private Double lat_origen = 40.433388;
    private Double lon_origen = -3.647603;
    private Double lat_destino = 40.454523;
    private Double lon_destino = -3.579342;

    private MapView mapa;
    private PuntosRutaTour puntosRutaTour;
    private CordenadasTour coordenadasTour;
    private LatLng comienzoRuta,finalRuta;
    private BehaviorSubject<LatLng> PosDriver = BehaviorSubject.create();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.tours, container, false);

        Bundle bundle = this.getArguments();

       /* if (bundle != null){

            lat_origen = bundle.getDouble("lat_origen", 40.433388);
            lon_origen = bundle.getDouble("lon_origen", -3.647603);
            lat_destino = bundle.getDouble("lat_destino", 40.454523);
            lon_destino = bundle.getDouble("lon_destino", -3.579342);
        }*/

        String recuperarRuta = cargarPreferencias();
        String[] recuperarRutaArray = recuperarRuta.split(":");


        comienzoRuta = new LatLng(Double.parseDouble(recuperarRutaArray[0]), Double.parseDouble(recuperarRutaArray[1]));
        finalRuta = new LatLng(Double.parseDouble(recuperarRutaArray[2]), Double.parseDouble(recuperarRutaArray[3]));



        mapa = (MapView) myView.findViewById(R.id.mapaTour);
        mapa.onCreate(savedInstanceState);
        mapa.setStyleUrl(Style.MAPBOX_STREETS);



        boolean vuelta = true;
        if (vuelta) {
            comienzoRuta = new LatLng(lat_origen, lon_origen);
        } else {
            LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.d("ERROR", "Esto es embarazoso...No tienes permisos.");
                return myView;
            }
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            comienzoRuta = new LatLng(latitude, longitude);
        }

        mapa.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mapboxMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(comienzoRuta)  //pone la camara en el centro del inicio de la ruta
                                .zoom(10)
                                .tilt(20)
                                .build()));
                mapboxMap.addMarker(new MarkerOptions().position(comienzoRuta));
                mapboxMap.addMarker(new MarkerOptions().position(finalRuta));

                mapa.onCreate(savedInstanceState);
                TourAPI apiClient = UsuarioTour.getClient(getActivity());
                puntosRutaTour = PuntosRutaTour.init(apiClient);
                puntosRutaTour.getDirections(comienzoRuta, finalRuta, getActivity().getResources().getString(R.string.access_token))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<MapboxDirections>() {
                            @Override
                            public void call(MapboxDirections mapboxDirections) {
                                Tours.this.metodoCorrecto(mapboxDirections);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Tours.this.metodoError(throwable);
                            }
                        });


            }

        });

        return myView;
    }

    private void metodoError(Throwable throwable) {
        throwable.printStackTrace();
    }

    private void metodoCorrecto(MapboxDirections mapboxDirections) {
        if (mapboxDirections != null && mapboxDirections.getRoutes() != null && !mapboxDirections.getRoutes().isEmpty()) {
            RutasTour route = mapboxDirections.getRoutes().get(0);
            if (route.getGeometry() != null && !route.getGeometry().getCoordinates().isEmpty()) {
                coordenadasTour = route.getGeometry();
                new PintaCordenadasJSON().execute();
                empiezaVisualizacionRuta(coordenadasTour.getCoordinates());
            }
            else {
                Toast.makeText(getActivity(), "Error inesperado!", Toast.LENGTH_LONG).show();
            }
        }
        else if (mapboxDirections != null && mapboxDirections.getError() != null) {
            Toast.makeText(getActivity(), mapboxDirections.getError(), Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getActivity(), "Error inesperado!", Toast.LENGTH_LONG).show();
        }

    }

    private void empiezaVisualizacionRuta(final ArrayList coordinates) {

        mapa.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(final MapboxMap mapboxMap) {

                Toast.makeText(getActivity(), "Iniciando la ruta...", Toast.LENGTH_LONG).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    private int id_posicion = 0;

                    @Override
                    public void run() {
                        if (id_posicion < coordinates.size()) {
                            ArrayList<Double> coordinate = (ArrayList) coordinates.get(id_posicion);
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .zoom(16)
                                    .target(new LatLng(coordinate.get(1), coordinate.get(0)))
                                    .bearing(180)// Recoge orientacion de la camara para visualizar el sur
                                    .tilt(85)// Pone la inclinación de la vista a unos 20 grados aprox.
                                    .build();

                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 250, null);
                            id_posicion++;
                            handler.postDelayed(this, 250);
                        }
                    }
                }, 2000);

            }
        });
    }


    private class PintaCordenadasJSON extends AsyncTask<Void, Void, List<LatLng>> {
        @Override
        protected List<LatLng> doInBackground(Void... voids) {

            ArrayList<LatLng> puntosArray = new ArrayList<LatLng>();

            ArrayList coordinates = coordenadasTour.getCoordinates();
            for (int localicacion_id = 0; localicacion_id < coordinates.size(); localicacion_id++) {
                ArrayList<Double> cordenada_lc = (ArrayList) coordinates.get(localicacion_id);
                LatLng latLng = new LatLng(cordenada_lc.get(1), cordenada_lc.get(0));
                puntosArray.add(latLng);
            }

            return puntosArray;
        }

        @Override
        protected void onPostExecute(final List<LatLng> puntosCordenadas) {
            super.onPostExecute(puntosCordenadas);

            mapa.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(final MapboxMap mapboxMap) {


                    if (puntosCordenadas.size() > 0) {
                        LatLng[] puntosArrayCordenadas = puntosCordenadas.toArray(new LatLng[puntosCordenadas.size()]);

                        // Pinta los puntos en el mapa
                        mapboxMap.addPolyline(new PolylineOptions()
                                .add(puntosArrayCordenadas)
                                .color(Color.parseColor("#13C0C6"))
                                .width(2));
                    }

                }


            });

        }
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapa.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapa.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapa.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapa.onSaveInstanceState(outState);
    }

    public String cargarPreferencias(){
        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        String rutaTotal = misPreferencias.getString("rutaMover", "40.433388:-3.647603:40.454523:-3.579342");


        return rutaTotal;





    }

}
