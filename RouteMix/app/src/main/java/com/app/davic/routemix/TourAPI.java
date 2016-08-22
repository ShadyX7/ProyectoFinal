package com.app.davic.routemix;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Victor on 15/06/2016.
 */
public interface TourAPI {

    @GET("/{wayPoints}.json")
    Observable<MapboxDirections>getDirections(@Path("wayPoints") String waypoints, @Query("access_token") String accessToken, @Query("alternatives") boolean alternatives);

}

