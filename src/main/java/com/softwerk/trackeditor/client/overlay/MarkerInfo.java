package com.softwerk.trackeditor.client.overlay;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.softwerk.trackeditor.client.MapEdit;

public class MarkerInfo extends Overlay {
    private final Marker marker;
    private String name;
    private String content;
    private InfoWindowContent infoWindowContent;
    private MapWidget map;
    private MapEdit mapEdit;

    public MarkerInfo(MapEdit mapEdit, LatLng point) {
        this(mapEdit, point, "", null);
    }

    public MarkerInfo(MapEdit mapEdit, LatLng point, String name) {
        this(mapEdit, point, name, null);
    }

    public MarkerInfo(MapEdit mapEdit, LatLng point, String name, String content) {
        this.name = name;
        this.content = content;
        this.mapEdit = mapEdit;

        MarkerOptions options = MarkerOptions.newInstance();
        options.setDraggable(true);
        this.marker = new Marker(point, options);
        this.marker.addMarkerClickHandler(createMarkerClickHandler());
        this.content = content;
        createContent();
    }

    public Marker getMarker() {
        return marker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        createContent();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        createContent();
    }

    @Override
    protected Overlay copy() {
        return new MarkerInfo(mapEdit, marker.getLatLng(), name, content);
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
                if (!map.getInfoWindow().isVisible()) {
                    map.getInfoWindow().open(marker, infoWindowContent);
                }
            }
        };
    }

    // Create InfoWindowContent to show under the marker
    private void createContent() {
        final VerticalPanel panel = new VerticalPanel();
        final Label deleteLabel = new Label("Delete");
        panel.add(new Label("Marker #<b>" + name + "</b>"));
        panel.add(deleteLabel);

        deleteLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                mapEdit.removeOverlay(MarkerInfo.this);
            }
        });

        infoWindowContent = new InfoWindowContent(panel);
    }
}
