package com.app.davic.routemix;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;


/**
 * Created by Victor on 15/06/2016.
 */
public class UsuarioTour {
    static TourAPI servicioTour;

    public static TourAPI getClient(final Context context) {
        if (servicioTour == null) {
            servicioTour = nuevoUsuario(context, "https://api.mapbox.com/v4/directions/mapbox.driving/");
        }
        return servicioTour;
    }

    public static TourAPI nuevoUsuario(final Context context, String apiEnpoint) {

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setEndpoint(apiEnpoint)
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("DATOS_ENDPOINT"))
                .setClient(new OkClient(okHttpClient))
                .build()
                .create(TourAPI.class);
    }
}
