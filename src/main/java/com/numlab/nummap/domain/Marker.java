package com.numlab.nummap.domain;

/**
 * Created by eisti on 4/3/15.
 */
public class Marker {
    private Double lat;
    private Double lng;
    private String message;
    private boolean focus;
    private boolean draggable;

    public Marker(Double lat, Double lng, String message, boolean focus, boolean draggable) {
        this.lat = lat;
        this.lng = lng;
        this.message = message;
        this.focus = focus;
        this.draggable = draggable;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }
}
