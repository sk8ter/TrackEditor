package com.softwerk.trackeditor.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.Control;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.*;
import com.softwerk.trackeditor.client.MapEdit;

public class AddMenuControl extends Control.CustomControl implements ClickHandler {
    private Button addMarkerButton;
    private MapClickHandler mapClickHandler;
    private MapEdit mapEdit;

    protected AddMenuControl(MapEdit mapEdit) {
        super(new ControlPosition(ControlAnchor.BOTTOM_LEFT, 200, 7));
        this.mapEdit = mapEdit;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    protected Widget initialize(MapWidget map) {
        Panel container = new FlowPanel();

        // Creating handler for this button
        if (addMarkerButton == null) {
            addMarkerButton = new Button("AddMarker");
            addMarkerButton.addClickHandler(this);
        }

        container.add(addMarkerButton);
        return container;
    }

    @Override
    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == addMarkerButton) {
            // Add Marker to the map
            if (mapClickHandler == null) {
                mapClickHandler = new MapClickHandler() {
                    @Override
                    public void onClick(MapClickEvent mapClickEvent) {
                        mapEdit.getMap().removeMapClickHandler(mapClickHandler);

                        MarkerOptions opt = MarkerOptions.newInstance();
                        opt.setDraggable(true);
                        Marker marker = new Marker(mapClickEvent.getLatLng(), opt);
                        mapEdit.addOverlay(marker);
                    }
                };
            }

            mapEdit.getMap().addMapClickHandler(mapClickHandler);
        }
    }
}
