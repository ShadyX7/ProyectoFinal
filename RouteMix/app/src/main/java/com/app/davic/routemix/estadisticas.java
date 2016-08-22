package com.app.davic.routemix;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;


public class estadisticas extends Fragment{

    View myView;
    private PieChart pieChart;
    private TextView txt_rutasTotales, txt_totalKm,txt_promedio,txt_tituloUltimaRuta,txt_ultRuta, contadorCoche, contadorPie,contadorSkate, contadorBicicleta;
    int numCoches = 0, numAndando = 0, numSkate = 0,numBicicletas = 0, rutasTotales = 0;
    double promedio = 0, ultRuta = 0, totalKm = 0;
    String tituloUltimaRuta = "";

    //String estadisticas = "Ruta1@100.023@cocheMoto&Ruta2@12.321@skate&Ruta3@87.126@cocheMoto";

    String estadisticas = "Ruta1@100.023@cocheMoto&Ruta2@12.321@skate&Ruta3@36.897@pie&Ruta4@24.256@bicicleta&Ruta5@89.786@bicicleta&Ruta6@78.982@pie&Ruta7@87.126@cocheMoto";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.estadisticas, container, false);


        txt_rutasTotales = (TextView) myView.findViewById(R.id.txt_rutasTotales);
        txt_totalKm = (TextView) myView.findViewById(R.id.txt_totalKm);
        txt_promedio = (TextView) myView.findViewById(R.id.txt_promedio);
        txt_tituloUltimaRuta = (TextView) myView.findViewById(R.id.txt_tituloUltimaRuta);
        txt_ultRuta = (TextView) myView.findViewById(R.id.txt_ultRuta);
        contadorCoche = (TextView) myView.findViewById(R.id.contadorCoche);
        contadorPie = (TextView) myView.findViewById(R.id.contadorPie);
        contadorBicicleta = (TextView) myView.findViewById(R.id.contadorBicicleta);
        contadorSkate = (TextView) myView.findViewById(R.id.contadorSkate);



        pieChart = (PieChart) myView.findViewById(R.id.pieChart);

        /*definimos algunos atributos*/
        pieChart.setHoleRadius(40f);
        pieChart.setDrawYValues(true);
        pieChart.setDrawXValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        String[] rutaSplit = estadisticas.split("&");
        for (int i = 0; i < rutaSplit.length; i++) {
            String[] casillasSplit = rutaSplit[i].split("@");
            totalKm = totalKm + Double.parseDouble(casillasSplit[1]);

            if (casillasSplit[2].equals("cocheMoto")) numCoches++;
            if (casillasSplit[2].equals("pie")) numAndando++;
            if (casillasSplit[2].equals("bicicleta")) numBicicletas++;
            if (casillasSplit[2].equals("skate")) numSkate++;

            ultRuta = Double.parseDouble(casillasSplit[1]);
            tituloUltimaRuta = casillasSplit[0];





        }

        rutasTotales = rutaSplit.length;

        promedio = totalKm/rutasTotales;

        totalKm = Redondear(totalKm);
        promedio = Redondear(promedio);
        ultRuta = Redondear(ultRuta);


        txt_rutasTotales.setText(" " + rutasTotales);
        txt_totalKm.setText(" " + totalKm);
        txt_promedio.setText(" " + promedio);
        txt_tituloUltimaRuta.setText(" " + tituloUltimaRuta);
        txt_ultRuta.setText(" " + ultRuta);

        contadorCoche.setText(" " + numCoches);
        contadorPie.setText(" " + numAndando);
        contadorBicicleta.setText("" + numBicicletas);
        contadorSkate.setText("" + numSkate);




		/*creamos una lista para los valores Y*/

        ArrayList<Entry> valsY = new ArrayList<Entry>();
        valsY.add(new Entry(numCoches * 1000 / 25,0 ));
        valsY.add(new Entry(numBicicletas * 100 / 25,1));
        valsY.add(new Entry(numAndando * 1000 / 25,1));
        valsY.add(new Entry(numSkate * 1000 / 25,1));

 		/*creamos una lista para los valores X*/
        ArrayList<String> valsX = new ArrayList<String>();
        valsX.add("Coche/Moto");
        valsX.add("Bicicleta");
        valsX.add("A pie/ Corriendo");
        valsX.add("Skate");

 		/*creamos una lista de colores*/
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.red_flat));
        colors.add(getResources().getColor(R.color.purple));
        colors.add(getResources().getColor(R.color.green_flat));
        colors.add(getResources().getColor(R.color.orange));

 		/*seteamos los valores de Y y los colores*/
        PieDataSet set1 = new PieDataSet(valsY, "Resultados");
        set1.setSliceSpace(3f);
        set1.setColors(colors);

		/*seteamos los valores de X*/
        PieData data = new PieData(valsX, set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();

        /*Ocutar descripcion*/
        pieChart.setDescription("Estadisticas RouteMix 2016");
        /*Ocultar leyenda*/
        pieChart.setDrawLegend(true);

        return myView;
    }

    public double Redondear(double numero)
    {
        return Math.rint(numero*1000)/1000;
    }
}


