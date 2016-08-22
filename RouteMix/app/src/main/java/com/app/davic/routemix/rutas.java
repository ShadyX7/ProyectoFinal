package com.app.davic.routemix;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Victor on 19/06/2016.
 */
public class rutas extends Fragment implements View.OnClickListener{

    View myView;

    private ImageButton boton_enviar;
    private EditText latOrigen;
    private EditText lonOrigen;
    private EditText latDestino;
    private EditText lonDestino;

    private String vehiculo_texto;
    private String tipo_texto;
    private String titulo_texto;
    private String descripcion_texto;
    private String lat_origen;
    private String lon_origen;
    private String lat_destino;
    private String lon_destino;



    private RadioButton coche_moto;
    private RadioButton pie;
    private RadioButton skate;
    private RadioButton bicicleta;

    private RadioButton urbano;
    private RadioButton playa;
    private RadioButton montaña;
    private RadioButton turistico;

    private EditText titulo;
    private EditText descripcion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.rutas, container, false);



        coche_moto = (RadioButton)myView.findViewById(R.id.coche_moto);
        pie = (RadioButton)myView.findViewById(R.id.andando);
        skate = (RadioButton)myView.findViewById(R.id.skate);
        bicicleta = (RadioButton)myView.findViewById(R.id.bici);

        urbano = (RadioButton)myView.findViewById(R.id.urbano);
        playa = (RadioButton)myView.findViewById(R.id.playa);
        montaña = (RadioButton)myView.findViewById(R.id.montaña);
        turistico = (RadioButton)myView.findViewById(R.id.turistico);


        boton_enviar = (ImageButton)myView.findViewById(R.id.boton_enviar);
        latOrigen = (EditText)myView.findViewById(R.id.latOrigen);
        latDestino = (EditText)myView.findViewById(R.id.latDestino);
        lonOrigen = (EditText)myView.findViewById(R.id.lonOrigen);
        lonDestino = (EditText)myView.findViewById(R.id.lonDestino);


        titulo = (EditText)myView.findViewById(R.id.titulo);
        descripcion = (EditText)myView.findViewById(R.id.descripcion);

        boton_enviar.setOnClickListener(this);






        return myView;

    }


    public void escogeHistorial(){

        if (coche_moto.isChecked()){
        vehiculo_texto = "cocheMoto";
        }else if (pie.isChecked()){
            vehiculo_texto = "pie";
        }else if (skate.isChecked()){
            vehiculo_texto = "skate";
        }else if (bicicleta.isChecked()){
            vehiculo_texto = "bicicleta";
        }

        if (urbano.isChecked()){

            tipo_texto = "urbano";
        }else if (playa.isChecked()){
            tipo_texto = "playa";
        }else if (montaña.isChecked()){
            tipo_texto = "montania";
        }else if (turistico.isChecked()){
            tipo_texto = "turistico";
        }

        titulo_texto = titulo.getText().toString().trim();
        descripcion_texto = descripcion.getText().toString().trim();
        lat_origen = latOrigen.getText().toString().trim();
        lon_origen = lonOrigen.getText().toString().trim();
        lat_destino = lonDestino.getText().toString().trim();
        lon_destino = lonDestino.getText().toString().trim();

    }


    @Override
    public void onClick(View v) {


        escogeHistorial();

        guardarPreferencias(lat_origen + ":" + lon_origen + ":" + lat_destino + ":" + lon_destino);

        guardarPreferenciasRutas(lat_origen + ":" + lon_origen + "#" + lat_destino + ":" + lon_destino + "@" + vehiculo_texto + "#" + tipo_texto + "@" + titulo_texto + "#" + descripcion_texto);

     double kilo =Haversine.distancia(Double.parseDouble(lat_origen), Double.parseDouble(lon_origen), Double.parseDouble(lat_destino), Double.parseDouble(lon_destino));
      guardarPreferenciasHistorial(titulo_texto);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame
                        , new Tours())
                .commit();

        /*Bundle bundle = new Bundle();

        bundle.putDouble("lat_origen", Double.parseDouble(lat_origen));
        bundle.putDouble("lon_origen", Double.parseDouble(lon_origen));
        bundle.putDouble("lat_destino", Double.parseDouble(lat_destino));
        bundle.putDouble("lon_destino", Double.parseDouble(lon_destino));

        Fragment fragment = new Tours();

        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame
                        , new Tours())
                .commit();*/

    }

    public void guardarPreferencias(String rutaMover){



        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString("rutaMover", rutaMover);

        editor.commit();


    }

    public void guardarPreferenciasRutas(String rutas){

        String recuperarRuta = cargarPreferenciasRutas();

        String rutaTotal = recuperarRuta + "&" + rutas;
        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString("rutaTotal", rutaTotal);

        editor.commit();


    }

    public String cargarPreferenciasRutas(){
        String rutasdefecto = "40.44924929656757:-3.611138773260558#40.4494241:-3.6081583@cocheMoto#urbano@Ruta1#Descripcion&40.447698209577595:3.6094477493315935#40.44704911851412:-3.611555965617299@cocheMoto#urbano@Ruta2#Descripcion&40.36459630797086:-3.918970227241516#40.36267113514827:-3.9183416683226824@cocheMoto#urbano@Ruta3#Descripcion&37.374522644077246:-5.745447278022766#37.37295383612657:-5.744901783764362@cocheMoto#montania@Ruta4#Descripcion&40.4415907903353:-3.6192789673805237#40.43793260507625:-3.637497201561928@pie#urbano@Ruta5#Descripcion&40.444857:-3.6528608947992325#40.43845521515768:-3.651995211839676@pie#urbano@Ruta6#Descripcion&40.42813291388417:-3.687085211277008#40.43531949373335:-3.68828684091568@cocheMoto#urbano@Ruta7#Descripcion&40.40957492033851:-3.6944371461868286#36.6507925250347:-4.324836730957031@bicicleta#urbano@Ruta8#Descripcion&40.394411070497696:-3.656701147556305#40.38682786469453:-3.66906076669693@cocheMoto#playa@Ruta9#Descripcion&40.38839687388361:-3.7179842591285706#40.388789120469745:-3.739098608493805@bicicleta#urbano@Ruta10#Descripcion&40.33581517044043:-3.76207172870636#39.49556336059472:-4.608292579650879@cocheMoto#playa@Ruta11#Descripcion&37.35269280367273:-4.712190628051758#42.504502852990505:-5.313606262207031@skate#Tipo@Ruta12#Descripcion&";


        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        String rutaTotal = misPreferencias.getString("rutaTotal", rutasdefecto);


        return rutaTotal;


    }


    public void guardarPreferenciasHistorial(String rutas){

        String recuperarRuta = cargarPreferenciasHistorial();

        String historialTotal = recuperarRuta + "&" + rutas;
        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString("historialTotal", historialTotal);

        editor.commit();


    }

    public String cargarPreferenciasHistorial(){
        String historialDefecto = "Ruta12&Ruta4&Ruta10&Ruta3&Ruta5&Ruta2&Ruta8&Ruta7&Ruta10&Ruta11&Ruta3&Ruta1&Ruta6&Ruta9&Ruta12&Ruta4&Ruta10&Ruta3&Ruta5&Ruta2&Ruta8&Ruta7&Ruta10&Ruta11&Ruta3&Ruta1&Ruta6&Ruta9";



        SharedPreferences misPreferencias = this.getActivity().getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);

        String rutaTotal = misPreferencias.getString("historialTotal", historialDefecto);


        return rutaTotal;


    }


}
