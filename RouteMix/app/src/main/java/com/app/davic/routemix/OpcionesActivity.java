package com.app.davic.routemix;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.app.davic.routemix.R;

public class OpcionesActivity extends Activity implements View.OnClickListener {

private Button btnMovilidad, btnMusica, btnRadioDistancia, btnTipoRuta;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_opciones);
        this.setFinishOnTouchOutside(false);



        btnMovilidad = (Button) findViewById(R.id.btnMovilidad);
        btnMovilidad.setOnClickListener(this);
        btnMusica = (Button) findViewById(R.id.btnMusica);
        btnMusica.setOnClickListener(this);
        btnRadioDistancia = (Button) findViewById(R.id.btnRadioDistancia);
        btnRadioDistancia.setOnClickListener(this);
        btnTipoRuta = (Button) findViewById(R.id.btnTipoRuta);
        btnTipoRuta.setOnClickListener(this);


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

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
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
        if (v.getId() == R.id.btnMovilidad) {
        Intent movilidad = new Intent(OpcionesActivity.this, MovilidadActivity.class);
        startActivity(movilidad);

        }


        if (v.getId() == R.id.btnRadioDistancia) {
        Intent radio = new Intent(OpcionesActivity.this, RadioActivity.class);
        startActivity(radio);

        }

        if (v.getId() == R.id.btnTipoRuta) {
        Intent tipoRuta = new Intent(OpcionesActivity.this, TipoRuta.class);
        startActivity(tipoRuta);

        }


        }
        }
