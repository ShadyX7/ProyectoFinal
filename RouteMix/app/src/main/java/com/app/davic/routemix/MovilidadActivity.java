package com.app.davic.routemix;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.app.davic.routemix.R;

public class MovilidadActivity extends Activity implements View.OnClickListener {

        RadioGroup RadioGroupMovilidad;
        RadioButton radioCocheMoto, radioPie, radioBicicleta, radioSkate;
        Button btnGuardarCambios;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                setContentView(R.layout.activity_movilidad);
                this.setFinishOnTouchOutside(false);


                RadioGroupMovilidad = (RadioGroup) findViewById(R.id.RadioGrouoMovilidad);
                radioCocheMoto = (RadioButton) findViewById(R.id.radioCocheMoto);
                radioPie = (RadioButton) findViewById(R.id.radioPie);
                radioBicicleta = (RadioButton) findViewById(R.id.radioBicicleta);
                radioSkate = (RadioButton) findViewById(R.id.radioSkate);

                btnGuardarCambios = (Button) findViewById(R.id.btnGuardarCambios);
                btnGuardarCambios.setOnClickListener(this);

                cargarPreferencias();


        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_movilidad, menu);
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
                if (radioCocheMoto.isChecked())guardarPreferencias("cocheMoto");
                if (radioPie.isChecked())guardarPreferencias("pie");
                if (radioBicicleta.isChecked())guardarPreferencias("bicicleta");
                if (radioSkate.isChecked())guardarPreferencias("skate");

                finish();

        }


        public void guardarPreferencias(String transporte){


                SharedPreferences misPreferencias = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = misPreferencias.edit();
                editor.putString("movilidad", transporte);

                editor.commit();


        }

        public void cargarPreferencias(){
                SharedPreferences misPreferencias = getSharedPreferences("preferenciausuario", Context.MODE_PRIVATE);
                String cargarMovilidad = misPreferencias.getString("movilidad", "cocheMoto");

                if (cargarMovilidad.equals("cocheMoto")) radioCocheMoto.setChecked(true);
                if (cargarMovilidad.equals("pie")) radioPie.setChecked(true);
                if (cargarMovilidad.equals("bicicleta")) radioBicicleta.setChecked(true);
                if (cargarMovilidad.equals("skate")) radioSkate.setChecked(true);



        }
}
