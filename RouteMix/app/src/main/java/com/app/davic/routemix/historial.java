package com.app.davic.routemix;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class historial extends Fragment implements View.OnClickListener{

    View myView;
    TableLayout tabla;

    private ImageView iv, imgVehiculo, imgTipo;

    //String rutas = "40.44924929656757:-3.611138773260558#40.4494241:-3.6081583@cocheMoto#urbano@Ruta1#Descripcion&40.447698209577595:3.6094477493315935#40.44704911851412:-3.611555965617299@cocheMoto#urbano@Ruta2#Descripcion&40.36459630797086:-3.918970227241516#40.36267113514827:-3.9183416683226824@cocheMoto#urbano@Ruta3#Descripcion&37.374522644077246:-5.745447278022766#37.37295383612657:-5.744901783764362@cocheMoto#montania@Ruta4#Descripcion&40.4415907903353:-3.6192789673805237#40.43793260507625:-3.637497201561928@pie#urbano@Ruta5#Descripcion&40.444857:-3.6528608947992325#40.43845521515768:-3.651995211839676@pie#urbano@Ruta6#Descripcion&40.42813291388417:-3.687085211277008#40.43531949373335:-3.68828684091568@cocheMoto#urbano@Ruta7#Descripcion&40.40957492033851:-3.6944371461868286#36.6507925250347:-4.324836730957031@bicicleta#urbano@Ruta8#Descripcion&40.394411070497696:-3.656701147556305#40.38682786469453:-3.66906076669693@cocheMoto#playa@Ruta9#Descripcion&40.38839687388361:-3.7179842591285706#40.388789120469745:-3.739098608493805@bicicleta#urbano@Ruta10#Descripcion&40.33581517044043:-3.76207172870636#39.49556336059472:-4.608292579650879@cocheMoto#playa@Ruta11#Descripcion&37.35269280367273:-4.712190628051758#42.504502852990505:-5.313606262207031@skate#Tipo@Ruta12#Descripcion&";
    String rutas = "40.44924929656757:-3.611138773260558#40.4494241:-3.6081583@cocheMoto#urbano@Ruta1#Descripcion&40.447698209577595:3.6094477493315935#40.44704911851412:-3.611555965617299@cocheMoto#urbano@Ruta2#Descripcion&40.36459630797086:-3.918970227241516#40.36267113514827:-3.9183416683226824@cocheMoto#urbano@Ruta3#Descripcion&37.374522644077246:-5.745447278022766#37.37295383612657:-5.744901783764362@skate#Tipo@Ruta4#Descripcion&40.4415907903353:-3.6192789673805237#40.43793260507625:-3.637497201561928@pie#urbano@Ruta5#Descripcion&40.444857:-3.6528608947992325#40.43845521515768:-3.651995211839676@pie#urbano@Ruta6#Descripcion&40.42813291388417:-3.687085211277008#40.43531949373335:-3.68828684091568@cocheMoto#urbano@Ruta7#Descripcion&40.40957492033851:-3.6944371461868286#36.6507925250347:-4.324836730957031@bicicleta#urbano@Ruta8#Descripcion&40.394411070497696:-3.656701147556305#40.38682786469453:-3.66906076669693@cocheMoto#playa@Ruta9#Descripcion&40.38839687388361:-3.7179842591285706#40.388789120469745:-3.739098608493805@bicicleta#urbano@Ruta10#Descripcion&40.33581517044043:-3.76207172870636#39.49556336059472:-4.608292579650879@cocheMoto#playa@Ruta11#Descripcion&37.35269280367273:-4.712190628051758#42.504502852990505:-5.313606262207031@cocheMoto#montania@Ruta12#Descripcion&";
    String rutasCompletadas = "Ruta12&Ruta4&Ruta10&Ruta3&Ruta5&Ruta2&Ruta8&Ruta7&Ruta10&Ruta11&Ruta3&Ruta1&Ruta6&Ruta9&Ruta12&Ruta4&Ruta10&Ruta3&Ruta5&Ruta2&Ruta8&Ruta7&Ruta10&Ruta11&Ruta3&Ruta1&Ruta6&Ruta9";

    String titulo, descripcion, vehiculo, tipo, coordenadas, rutaHistorial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.historial, container, false);

        //rutas = cargarPreferenciasRutas();
        //rutasCompletadas = cargarPreferenciasHistorial();

        tabla = (TableLayout) myView.findViewById(R.id.tabla);
        cargarTabla();


        return myView;
    }

    public void cargarTabla(){

        String[] rutasArray = rutas.split("&");
        String[] rutasArrayCompletadas = rutasCompletadas.split("&");


        for (int i = 0; i < rutasArrayCompletadas.length; i++) {


            for (int j = 0; j < rutasArray.length; j++) {
                // System.out.println("**********************************************");

                String[] sacarTituloArray = rutasArray[j].split("@");
                String[] sacarTituloArray2 = sacarTituloArray[2].split("#");
                rutaHistorial = sacarTituloArray2[0];

                if (rutasArrayCompletadas[i].equals(rutaHistorial)) {
                    System.out.println(rutasArray[j]);

                    String[] tratarResultado = rutasArray[j].split("@");
                    coordenadas = tratarResultado[0];

                    String[] tratarResultadoVehiculoTipo = tratarResultado[1].split("#");
                    vehiculo = tratarResultadoVehiculoTipo[0];
                    tipo = tratarResultadoVehiculoTipo[1];

                    String[] tratarResultadoTituloDescripcion = tratarResultado[2].split("#");
                    titulo = tratarResultadoTituloDescripcion[0];
                    descripcion = tratarResultadoTituloDescripcion[1];



                    TableRow tr = new TableRow(getActivity());
                    TextView tx1 = new TextView(getActivity());
                    tx1.setText(titulo);
                    tx1.setPadding(15, 15, 15, 15);
                    TextView tx2 = new TextView(getActivity());
                    tx2.setText(descripcion);
                    tx2.setPadding(15, 15, 15, 15);

                    /*TextView tx3 = new TextView(getActivity());
                    tx3.setText(vehiculo + " " + tipo);
                    tx3.setPadding(5, 5, 5, 5);*/

                    if (vehiculo.equals("skate")){
                        imgVehiculo = new ImageView(getActivity());
                        imgVehiculo.setImageResource(R.drawable.skate);
                        imgVehiculo.setPadding(15, 15, 15, 15);

                        imgTipo = new ImageView(getActivity());
                        imgTipo.setImageResource(R.drawable.traparente);

                    } else {
                        if (vehiculo.equals("cocheMoto")){

                            imgVehiculo = new ImageView(getActivity());
                            imgVehiculo.setImageResource(R.drawable.car);
                            imgVehiculo.setPadding(15, 15, 15, 15);

                            if (tipo.equals("urbano")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.urban);
                            }

                            if (tipo.equals("montania")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.mountain);
                            }

                            if (tipo.equals("playa")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.beach);
                            }

                        }

                        if (vehiculo.equals("bicicleta")){

                            imgVehiculo = new ImageView(getActivity());
                            imgVehiculo.setImageResource(R.drawable.bike);
                            imgVehiculo.setPadding(15, 15, 15, 15);

                            if (tipo.equals("urbano")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.urban);
                            }

                            if (tipo.equals("montania")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.mountain);
                            }

                            if (tipo.equals("playa")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.beach);
                            }




                        }

                        if (vehiculo.equals("pie")){

                            imgVehiculo = new ImageView(getActivity());
                            imgVehiculo.setImageResource(R.drawable.pie);

                            if (tipo.equals("urbano")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.urban);
                            }

                            if (tipo.equals("montania")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.mountain);
                            }

                            if (tipo.equals("playa")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.beach);

                            }

                            if (tipo.equals("playa")){
                                imgTipo = new ImageView(getActivity());
                                imgTipo.setImageResource(R.drawable.prismaticos);
                            }

                        }
                    }





                    String[] tratarcoordenadas = coordenadas.split("#");
                    String[] coordenadasOrigen = tratarcoordenadas[0].split(":");
                    String[] coordenadasDestino = tratarcoordenadas[1].split(":");

                    double kilometros = Haversine.distancia(Double.parseDouble(coordenadasOrigen[0]), Double.parseDouble(coordenadasOrigen[1]),Double.parseDouble(coordenadasDestino[0]), Double.parseDouble(coordenadasDestino[1]));
                    kilometros = Redondear(kilometros);

                    TextView tx4 = new TextView(getActivity());
                    tx4.setText(String.valueOf(kilometros));
                    tx4.setPadding(15, 15, 15, 15);

                    iv = new ImageView(getActivity());
                    iv.setImageResource(R.drawable.lupa);
                    iv.setTag(titulo + ":" + coordenadasOrigen[0] + ":" + coordenadasOrigen[1] + ":" + coordenadasDestino[0] + ":" + coordenadasOrigen[1]);
                    iv.setOnClickListener(this);


                    tr.addView(tx1);
                    tr.addView(tx2);
                    tr.addView(imgVehiculo);
                    tr.addView(imgTipo);
                    tr.addView(tx4);
                    tr.addView(iv);

                    tabla.addView(tr);

                    /*System.out.println("*********************");
                    System.out.println(titulo);
                    System.out.println(descripcion);
                    System.out.println(vehiculo);
                    System.out.println(tipo);
                    System.out.println(coordenadas);
                    System.out.println("***********************");*/


                }


            }



            /*System.out.println("**********************************************");
            System.out.println(rutasArrayCompletadas[i]);*/



        }







    }


    public double Redondear(double numero)
    {
        return Math.rint(numero*1000)/1000;
    }

    @Override
    public void onClick(View v) {
        String parametros = String.valueOf(v.getTag());

        abriIntent(parametros);

    }

    public void abriIntent(String parametros) {

        Intent intent = new Intent(getActivity(), MapaHistoriaActivity.class);
        intent.putExtra("parametros", parametros);
        startActivity(intent);
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
