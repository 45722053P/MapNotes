package com.project.mapnotes;

import android.location.Location;


public class LocationChangedEvent {
    Location location;

    public LocationChangedEvent(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
