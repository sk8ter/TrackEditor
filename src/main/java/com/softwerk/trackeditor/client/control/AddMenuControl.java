package com.softwerk.trackeditor.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.Control;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.event.MapMouseMoveHandler;
import com.google.gwt.user.client.ui.*;
import com.softwerk.trackeditor.client.MapEdit;
import com.softwerk.trackeditor.client.overlay.MarkerInfo;

public class AddMenuControl extends Control.CustomControl implements ClickHandler {
    private Button addMarkerButton;
    private MapClickHandler mapClickHandler;
    private MapMouseMoveHandler mapMouseMoveHandler;
    private MapEdit mapEdit;
    private MapWidget map;
    private MarkerInfo markerInfo;

    protected AddMenuControl(MapEdit mapEdit) {
        super(new ControlPosition(ControlAnchor.BOTTOM_LEFT, 200, 7));
        this.mapEdit = mapEdit;
        this.map = mapEdit.getMap();
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
                        map.removeMapClickHandler(mapClickHandler);
                        map.removeMapMouseMoveHandler(mapMouseMoveHandler);
                        markerInfo = null;
                    }
                };
            }

            // Moving marker on the map
            if (mapMouseMoveHandler == null) {
                mapMouseMoveHandler = new MapMouseMoveHandler() {
                    @Override
                    public void onMouseMove(MapMouseMoveEvent mapMouseMoveEvent) {
                        if (markerInfo == null) {
                            markerInfo = new MarkerInfo(mapEdit, map.getCenter(), MapEdit.getNext() + "");
                            mapEdit.addOverlay(markerInfo);
                        }
                        markerInfo.getMarker().setPoint(mapMouseMoveEvent.getLatLng());
                    }
                };
            }

            map.addMapClickHandler(mapClickHandler);
            map.addMapMouseMoveHandler(mapMouseMoveHandler);
        }
    }
}
