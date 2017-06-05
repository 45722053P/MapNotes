package com.project.mapnotes;

import android.app.Application;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.Firebase;

import org.greenrobot.eventbus.EventBus;

public class MyApplication extends Application implements LocationListener {

    Location location;
    private LocationManager locationManager;
    Firebase ref;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public Firebase getRef() {
        return ref;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //Le decimos a Firebase que este sera el contexto
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        //Creamos una referencia a nuestra bd de Firebase
        ref = new Firebase("https://proyectouf2-73f56.firebaseio.com/notas/");


        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLocationChanged(Location location) {

        this.location = location;
        //Enviamos la localizacion al EventBus (LocationChangedEvent)
        EventBus.getDefault().post(new LocationChangedEvent(location));
        Log.e("LATITUDE_ENVIANDO", this.location.getLatitude() + " -------------------------");
        Log.e("LONGITUDE_ENVIANDO", this.location.getLongitude() + " -------------------------");

        //Toast.makeText(MyApplication.this, location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();

          //No se puede hacer un intent porque se lo mando a un fragment!
         /*Intent intent = new Intent(this, MapFragment.class);
         intent.putExtra("latitude", this.location.getLatitude());
         intent.putExtra("longitude", this.location.getLongitude());*/

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

}