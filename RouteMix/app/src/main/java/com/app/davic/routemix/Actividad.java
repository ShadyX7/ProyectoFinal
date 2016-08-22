package com.app.davic.routemix;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mapbox.directions.DirectionsCriteria;
import com.mapbox.directions.MapboxDirections;
import com.mapbox.directions.service.models.DirectionsResponse;
import com.mapbox.directions.service.models.Waypoint;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;



public class Actividad extends Fragment implements View.OnClickListener,LocationListener{

    View myView;
    private MapView mapView;
    private ImageButton opciones_actividad;

    double  latitud = 40.433388, longitud = -3.647603;
    int radioMax = 100000, cargarVelocidadRuta;

    String inicio, destino, vehiculo, tipo, titulo,descripcion,   cargarMovilidad, cargarTipoRuta;

    //String rutas ="40.44924929656757:-3.611138773260558#40.4494241:-3.6081583@Vehiculo#Tipo@Ruta1#Descripcion&40.447698209577595:-3.6094477493315935#40.44704911851412:-3.611555965617299@Vehiculo#Tipo@Ruta2#Descripcion&40.36459630797086:-3.918970227241516#40.36267113514827:-3.9183416683226824@Vehiculo#Tipo@Ruta3#Descripcion&37.374522644077246:-5.745447278022766#37.37295383612657:-5.744901783764362@Vehiculo#Tipo@Ruta3#Descripcion&";
    String rutas = "40.44924929656757:-3.611138773260558#40.4494241:-3.6081583@cocheMoto#urbano@Ruta1#Descripcion&40.447698209577595:3.6094477493315935#40.44704911851412:-3.611555965617299@cocheMoto#urbano@Ruta2#Descripcion&40.36459630797086:-3.918970227241516#40.36267113514827:-3.9183416683226824@cocheMoto#urbano@Ruta3#Descripcion&37.374522644077246:-5.745447278022766#37.37295383612657:-5.744901783764362@cocheMoto#montania@Ruta4#Descripcion&40.4415907903353:-3.6192789673805237#40.43793260507625:-3.637497201561928@pie#urbano@Ruta5#Descripcion&40.444857:-3.6528608947992325#40.43845521515768:-3.651995211839676@pie#urbano@Ruta6#Descripcion&40.42813291388417:-3.687085211277008#40.43531949373335:-3.68828684091568@cocheMoto#urbano@Ruta7#Descripcion&40.40957492033851:-3.6944371461868286#36.6507925250347:-4.324836730957031@bicicleta#urbano@Ruta8#Descripcion&40.394411070497696:-3.656701147556305#40.38682786469453:-3.66906076669693@cocheMoto#playa@Ruta9#Descripcion&40.38839687388361:-3.7179842591285706#40.388789120469745:-3.739098608493805@bicicleta#urbano@Ruta10#Descripcion&40.33581517044043:-3.76207172870636#39.49556336059472:-4.608292579650879@cocheMoto#playa@Ruta11#Descripcion&37.35269280367273:-4.712190628051758#42.504502852990505:-5.313606262207031@skate#Tipo@Ruta12#Descripcion&";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.actividad, container, false);

        //rutas = cargarPreferenciasRutas();


        mapView = (MapView) myView.findViewById(R.id.mapView);
        opciones_actividad = (ImageButton) myView.findViewById(R.id.opciones_actividad);
        opciones_actividad.setOnClickListener(this);
        cargarPreferenciasRadio();

        mapView.onCreate(savedInstanceState);
        cargarMapa();

        return myView;
    }

    public void cargarMapa () {
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {

                /*mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.4494241, -3.6081583))
                        .title("Hola David!!")
                        .snippet("shit"));


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(40.44924929656757, -3.611138773260558))
                        .title("Hola Victor!!")
                        .snippet("Aqui esta tu neskuiq."));

                LatLng[] puntos = new LatLng[2];
                puntos[0] = new LatLng(40.44924929656757, -3.611138773260558);
                puntos[1] = new LatLng(40.4494241, -3.6081583);

                mapboxMap.addPolyline(new PolylineOptions()
                        .add(puntos)
                        .color(Color.parseColor("#3887be"))
                        .width(5));
                */





             /*   Waypoint puntoOrigen = new Waypoint(40.4494241, -3.6081583);

                Waypoint puntoDestino = new Waypoint(40.44924929656757, -3.611138773260558);

                MapboxDirections client = new MapboxDirections.Builder()
                        .setAccessToken(String.valueOf(mapView))
                        .setOrigin(puntoOrigen)
                        .setDestination(puntoDestino)
                        .setProfile(DirectionsCriteria.PROFILE_DRIVING)
                        .build();

                try {
                    Response<DirectionsResponse> response = client.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitud, longitud))
                        .title("Ubicación")
                        .snippet("Aqui estas tu."));


                String[] rutasArray = rutas.split("&");
                for (int i = 0; i < rutasArray.length; i++) {

                    String[] casillasRutaArray = rutasArray[i].split("@");

                    String[] rutaCordenadas = casillasRutaArray[0].split("#");
                    inicio = rutaCordenadas[0];
                    destino = rutaCordenadas[1];

                    String[] rutaCordenadasInicio = inicio.split(":");
                    double distanciaAyB = Haversine.distancia(Double.parseDouble(rutaCordenadasInicio[0]), Double.parseDouble(rutaCordenadasInicio[1]), latitud, longitud);

                    if (distanciaAyB < radioMax) {


                        String[] desplazamiento = casillasRutaArray[1].split("#");
                        vehiculo = desplazamiento[0];
                        tipo = desplazamiento[1];


                        String[] rutaCordenadasDestino = destino.split(":");

                        if (vehiculo.equals(cargarMovilidad) && tipo.equals(cargarTipoRuta)) {


                            if (cargarVelocidadRuta == 4) {

                                String[] informacion = casillasRutaArray[2].split("#");
                                titulo = informacion[0];
                                descripcion = informacion[1];


                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(rutaCordenadasInicio[0]), Double.parseDouble(rutaCordenadasInicio[1])))
                                        .title("Inicio " + titulo)
                                        .snippet(descripcion));


                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.parseDouble(rutaCordenadasDestino[0]), Double.parseDouble(rutaCordenadasDestino[1])))
                                        .title("Final " + titulo)
                                        .snippet(descripcion));

                               /* Waypoint puntoOrigen = new Waypoint(Double.parseDouble(rutaCordenadasInicio[0]), Double.parseDouble(rutaCordenadasInicio[1]));

                                Waypoint puntoDestino = new Waypoint(Double.parseDouble(rutaCordenadasDestino[0]), Double.parseDouble(rutaCordenadasDestino[1]));

                                MapboxDirections client = new MapboxDirections.Builder()
                                        //.setAccessToken(MAPBOX_ACCESS_TOKEN)
                                        .setOrigin(puntoOrigen)
                                        .setDestination(puntoDestino)
                                        .setProfile(DirectionsCriteria.PROFILE_DRIVING)
                                        .build();

                                try {
                                    Response<DirectionsResponse> response = client.execute();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
*/

                            } else {
                                int discriminarKm = 103;
                                if (cargarVelocidadRuta == 0) discriminarKm = 25;
                                if (cargarVelocidadRuta == 1) discriminarKm = 50;
                                if (cargarVelocidadRuta == 2) discriminarKm = 75;
                                if (cargarVelocidadRuta == 3) discriminarKm = 100;


                                double distanciaEstaRuta = Haversine.distancia(Double.parseDouble(rutaCordenadasInicio[0]), Double.parseDouble(rutaCordenadasInicio[1]), Double.parseDouble(rutaCordenadasDestino[0]), Double.parseDouble(rutaCordenadasDestino[1]));

                                if (distanciaEstaRuta < discriminarKm) {

                                    String[] informacion = casillasRutaArray[2].split("#");
                                    titulo = informacion[0];
                                    descripcion = informacion[1];


                                    mapboxMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(rutaCordenadasInicio[0]), Double.parseDouble(rutaCordenadasInicio[1])))
                                            .title("Inicio " + titulo)
                                            .snippet(descripcion));


                                    mapboxMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(rutaCordenadasDestino[0]), Double.parseDouble(rutaCordenadasDestino[1])))
                                            .title("Final " + titulo)
                                            .snippet(descripcion));


                                    LatLng[] puntos3 = new LatLng[2];
                                    puntos3[0] = new LatLng(Double.parseDouble(rutaCordenadasInicio[0]), Double.parseDouble(rutaCordenadasInicio[1]));
                                    puntos3[1] = new LatLng(Double.parseDouble(rutaCordenadasDestino[0]), Double.parseDouble(rutaCordenadasDestino[1]));

                                    mapboxMap.addPolyline(new PolylineOptions()
                                            .add(puntos3)
                                            .color(Color.parseColor("#3887be"))
                                            .width(5));



                        /*System.out.println("inicillos: " + inicio + "\n destinillo: " + destino + "\n vehiculillo: " + vehiculo + "\n tipillo: " + tipo +
                                "\n titulillo: " + titulo + "\n descripcioncilla: " + descripcion);
*/
             /*for (int j = 0; j < casillasRutaArray.length; j++) {
                 System.out.println(casillasRutaArray[j]);



             }*/
                                }
                            }
                        }
                    }
                }








                /*if ( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {

                    LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 60000, 10, this);
                }*/
            }
        });
    }

    public void setRadioMax (int nuevoRadio) {
        radioMax = nuevoRadio;
    }


    public void cargarPreferenciasRadio(){
        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
        int cargarKm = misPreferencias.getInt("km", 5);
        setRadioMax(cargarKm);

        cargarMovilidad = misPreferencias.getString("movilidad", "cocheMoto");

        cargarTipoRuta = misPreferencias.getString("tipoRuta", "urbano");

        cargarVelocidadRuta = misPreferencias.getInt("velocidadRuta", 10);




    }




    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {

        cargarPreferenciasRadio();

        if (v.getId() == opciones_actividad.getId()){
            //Intent opciones =new Intent(this, OpcionesActivity.class);
            //startActivity(opciones);
            //latitud = 40.448625;
            //longitud = -3.606084;
            cargarMapa();

            entrarOpciones();
        }


    }

    public void entrarOpciones() {
        Intent intent = new Intent(getActivity(), OpcionesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        longitud = location.getLongitude();
        latitud = location.getLatitude();
        cargarMapa();
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public String cargarPreferenciasHistorial(){
        String historialDefecto1 = "Ruta12&Ruta4&Ruta10&Ruta3&Ruta5&Ruta2&Ruta8&Ruta7&Ruta10&Ruta11&Ruta3&Ruta1&Ruta6&Ruta9&Ruta12&Ruta4&Ruta10&Ruta3&Ruta5&Ruta2&Ruta8&Ruta7&Ruta10&Ruta11&Ruta3&Ruta1&Ruta6&Ruta9";



        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        String rutaTotal = misPreferencias.getString("historialTotal", historialDefecto1);


        return rutaTotal;


    }


    public String cargarPreferenciasRutas(){
        String rutasdefecto = "40.44924929656757:-3.611138773260558#40.4494241:-3.6081583@cocheMoto#urbano@Ruta1#Descripcion&40.447698209577595:3.6094477493315935#40.44704911851412:-3.611555965617299@cocheMoto#urbano@Ruta2#Descripcion&40.36459630797086:-3.918970227241516#40.36267113514827:-3.9183416683226824@cocheMoto#urbano@Ruta3#Descripcion&37.374522644077246:-5.745447278022766#37.37295383612657:-5.744901783764362@cocheMoto#montania@Ruta4#Descripcion&40.4415907903353:-3.6192789673805237#40.43793260507625:-3.637497201561928@pie#urbano@Ruta5#Descripcion&40.444857:-3.6528608947992325#40.43845521515768:-3.651995211839676@pie#urbano@Ruta6#Descripcion&40.42813291388417:-3.687085211277008#40.43531949373335:-3.68828684091568@cocheMoto#urbano@Ruta7#Descripcion&40.40957492033851:-3.6944371461868286#36.6507925250347:-4.324836730957031@bicicleta#urbano@Ruta8#Descripcion&40.394411070497696:-3.656701147556305#40.38682786469453:-3.66906076669693@cocheMoto#playa@Ruta9#Descripcion&40.38839687388361:-3.7179842591285706#40.388789120469745:-3.739098608493805@bicicleta#urbano@Ruta10#Descripcion&40.33581517044043:-3.76207172870636#39.49556336059472:-4.608292579650879@cocheMoto#playa@Ruta11#Descripcion&37.35269280367273:-4.712190628051758#42.504502852990505:-5.313606262207031@skate#Tipo@Ruta12#Descripcion&";


        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        String rutaTotal = misPreferencias.getString("rutaTotal", rutasdefecto);


        return rutaTotal;


    }
}


