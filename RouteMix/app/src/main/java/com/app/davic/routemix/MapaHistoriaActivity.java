package com.app.davic.routemix;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;



import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


public class MapaHistoriaActivity extends AppCompatActivity {

    private MapView mapView;

    String titulo;

    double latOrigen, lonOrigen, latDestino, lonDestino;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_historia);

        String parametros = getIntent().getStringExtra("parametros");
        String[] tratarParametros = parametros.split(":");

        titulo = tratarParametros[0];
        latOrigen = Double.parseDouble(tratarParametros[1]);
        lonOrigen = Double.parseDouble(tratarParametros[2]);
        latDestino = Double.parseDouble(tratarParametros[3]);
        lonDestino = Double.parseDouble(tratarParametros[4]);



        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latOrigen, lonOrigen))
                        .title("Inicio " + titulo)
                        .snippet("Esta maravillosa ruta tiene " + Redondear(Haversine.distancia(latOrigen, lonOrigen, latDestino, lonDestino)) + " kilómetros."));


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latDestino, lonDestino))
                        .title("Destino " + titulo)
                        .snippet("Aquí finaliza la ruta, despues de un apasionante y bonito recorrido."));

                LatLng[] puntos = new LatLng[2];
                puntos[0] = new LatLng(latOrigen, lonOrigen);
                puntos[1] = new LatLng(latDestino, lonDestino);

                mapboxMap.addPolyline(new PolylineOptions()
                        .add(puntos)
                        .color(Color.parseColor("#088A68"))
                        .width(5));

            }

        });





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mapa_historia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public double Redondear(double numero)
    {
        return Math.rint(numero*1000)/1000;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {

            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
