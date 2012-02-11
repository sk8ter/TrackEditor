package com.softwerk.trackeditor.client.overlay;

import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;

public class MarkerInfo extends Overlay {
    private final Marker marker;
    private String name;
    private InfoWindowContent content;
    private MapWidget map;

    public MarkerInfo(LatLng point) {
        MarkerOptions options = MarkerOptions.newInstance();
        options.setDraggable(true);
        this.marker = new Marker(point, options);
        this.marker.addMarkerClickHandler(createMarkerClickHandler());
    }

    public MarkerInfo(LatLng point, String name) {
        this(point);
        this.name = name;
    }

    public MarkerInfo(LatLng point, String name, InfoWindowContent content) {
        this(point, name);
        this.content = content;
    }

    public Marker getMarker() {
        return marker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InfoWindowContent getContent() {
        return content;
    }

    public void setContent(InfoWindowContent content) {
        this.content = content;
    }

    @Override
    protected Overlay copy() {
        return new MarkerInfo(marker.getLatLng(), name, content);
    }

    @Override
    protected void initialize(MapWidget mapWidget) {
        this.map = mapWidget;
    }

    @Override
    protected void redraw(boolean force) {
        if (force) {
            map.removeOverlay(marker);
            map.addOverlay(marker);
        }
    }

    @Override
    protected void remove() {
        map.removeOverlay(marker);
    }

    private MarkerClickHandler createMarkerClickHandler() {
        return new MarkerClickHandler() {
            @Override
            public void onClick(MarkerClickEvent markerClickEvent) {
//                if (!map.getInfoWindow().isVisible()) {
                    map.getInfoWindow().open(marker, new InfoWindowContent("Marker #<b>" + name + "</b>"));
//                }
            }
        };
    }
}
