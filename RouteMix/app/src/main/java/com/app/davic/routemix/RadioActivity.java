package com.app.davic.routemix;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.app.davic.routemix.R;

public class RadioActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener  {

    private SeekBar seekbar;
    private TextView texto;
    private Button btnAceptar;
    private int progreso;
    private CheckBox checkNoKm;
    private LinearLayout layoutInvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);


        seekbar = (SeekBar) findViewById(R.id.seekbar_km);
        seekbar.setOnSeekBarChangeListener(this);
        texto = (TextView) findViewById(R.id.txt_km);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);
        checkNoKm = (CheckBox) findViewById(R.id.checkNoKm);
        checkNoKm.setOnClickListener(this);
        layoutInvisible = (LinearLayout) findViewById(R.id.layoutInvisible);


        cargarPreferencias();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_radio, menu);
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

        if (v.getId() == checkNoKm.getId()) {
            if (checkNoKm.isChecked()){
                layoutInvisible.setVisibility(View.INVISIBLE);
                seekbar.setProgress(999);
            } else {
                layoutInvisible.setVisibility(View.VISIBLE);
                texto.setText(5 +" km");
                seekbar.setProgress(5);
            }
        }
        if (v.getId() == btnAceptar.getId()){

            guardarPreferencias();
            finish();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        progreso = progress;
        // progreso = seekBar.getProgress();


        texto.setText(progreso + " km");
        if (progreso > 102) {
            layoutInvisible.setVisibility(View.INVISIBLE);
            checkNoKm.setChecked(true);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        texto.setText(progreso +" km");
        if (progreso > 102) {
            layoutInvisible.setVisibility(View.INVISIBLE);
            checkNoKm.setChecked(true);
        }
    }




    public void guardarPreferencias(){




        SharedPreferences misPreferencias = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = misPreferencias.edit();
        if (progreso == 999) {
            editor.putInt("km", 9999);
        } else {
            editor.putInt("km", progreso);
        }

        editor.commit();


    }

    public void cargarPreferencias(){
        SharedPreferences misPreferencias = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
        int cargarKm = misPreferencias.getInt("km", 5);

        if (cargarKm < 102){
            texto.setText(cargarKm + " km");
            seekbar.setProgress(cargarKm);
        } else {
            layoutInvisible.setVisibility(View.INVISIBLE);
            checkNoKm.setChecked(true);

        }


    }
}
