package com.app.davic.routemix;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.davic.routemix.R;

public class TipoRuta extends AppCompatActivity  implements SeekBar.OnSeekBarChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private SeekBar seekBar;
    private RadioButton radioUrbano, radioMontania, radioPlaya, radioTuristico;
    private TextView prueba;
    private Spinner spMovilidad;
    private Button AceptarCambios;
    private int progreso = 10;
    String movilidad, shpMovilidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_ruta);

        seekBar = (SeekBar) findViewById(R.id.seekbarVelocidad);
        seekBar.setOnSeekBarChangeListener(this);

        radioUrbano = (RadioButton) findViewById(R.id.radioUrbano);
        radioMontania = (RadioButton) findViewById(R.id.radioMontania);
        radioPlaya = (RadioButton) findViewById(R.id.radioPlaya);
        radioTuristico = (RadioButton) findViewById(R.id.radioTuristico);

        prueba = (TextView) findViewById(R.id.textView4);

        spMovilidad = (Spinner) findViewById(R.id.spMovilidad);

        spMovilidad.setOnItemSelectedListener(this);


        AceptarCambios = (Button) findViewById(R.id.btnGuardarCamviosTipoRuta);
        AceptarCambios.setOnClickListener(this);
        cargarPreferencias();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tipo_ruta, menu);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGuardarCamviosTipoRuta) {
            String radio ="turistico", movilidadPasar, movilidadEnviar = "cocheMoto";
            if (radioTuristico.isChecked())radio = "turistico";
            if (radioPlaya.isChecked())radio = "playa";
            if (radioUrbano.isChecked())radio = "urbano";
            if (radioMontania.isChecked())radio = "montania";

            movilidadPasar = spMovilidad.getSelectedItem().toString();

            if (movilidadPasar.equals("Coche/Moto")) movilidadEnviar ="cocheMoto";
            if (movilidadPasar.equals("A Pie")) movilidadEnviar ="pie";
            if (movilidadPasar.equals("Bicicleta")) movilidadEnviar ="bicicleta";
            if (movilidadPasar.equals("Skate")) movilidadEnviar ="skate";

            guardarPreferencias(radio, movilidadEnviar);

            finish();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        movilidad = spMovilidad.getSelectedItem().toString();

        if (movilidad.equals("Coche/Moto")){
            radioUrbano.setVisibility(View.VISIBLE);

            radioMontania.setVisibility(View.VISIBLE);

            radioPlaya.setVisibility(View.VISIBLE);

            radioTuristico.setVisibility(View.INVISIBLE);
            radioUrbano.setChecked(true);
        }

        if (movilidad.equals("A Pie")){
            radioUrbano.setVisibility(View.VISIBLE);

            radioMontania.setVisibility(View.VISIBLE);

            radioPlaya.setVisibility(View.VISIBLE);

            radioTuristico.setVisibility(View.VISIBLE);
            radioUrbano.setChecked(true);
        }

        if (movilidad.equals("Bicicleta")){
            radioUrbano.setVisibility(View.VISIBLE);
            radioUrbano.setChecked(true);
            radioMontania.setVisibility(View.VISIBLE);

            radioPlaya.setVisibility(View.VISIBLE);

            radioTuristico.setVisibility(View.INVISIBLE);
            radioUrbano.setChecked(true);
        }

        if (movilidad.equals("Skate")){
            radioUrbano.setVisibility(View.INVISIBLE);

            radioMontania.setVisibility(View.INVISIBLE);

            radioPlaya.setVisibility(View.INVISIBLE);

            radioTuristico.setVisibility(View.INVISIBLE);
            radioUrbano.setChecked(true);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progreso = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



    public void guardarPreferencias(String tipoRuta, String transporte){


        SharedPreferences misPreferencias = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString("movilidad", transporte);
        editor.putString("tipoRuta", tipoRuta);
        editor.putInt("velocidadRuta", seekBar.getProgress());

        editor.commit();


    }

    public void cargarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
        String cargarMovilidad = misPreferencias.getString("movilidad", "cocheMoto");

        if (cargarMovilidad.equals("cocheMoto")) spMovilidad.setSelection(0);
        if (cargarMovilidad.equals("pie")) spMovilidad.setSelection(1);
        if (cargarMovilidad.equals("bicicleta")) spMovilidad.setSelection(2);
        if (cargarMovilidad.equals("skate")) spMovilidad.setSelection(3);


        String cargarTipoRuta = misPreferencias.getString("tipoRuta", "urbano");

        if (cargarTipoRuta.equals("urbano")) radioUrbano.setChecked(true);
        if (cargarTipoRuta.equals("playa")) radioPlaya.setChecked(true);
        if (cargarTipoRuta.equals("montania")) radioMontania.setChecked(true);
        if (cargarTipoRuta.equals("turistico")) radioTuristico.setChecked(true);


        int cargarVelocidadRuta = misPreferencias.getInt("velocidadRuta", 2);

        seekBar.setProgress(cargarVelocidadRuta);


    }
}
