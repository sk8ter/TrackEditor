package com.softwerk.trackeditor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

public class MapEdit extends Composite implements ClickHandler {
    private MapWidget map;
    private Button loadButton = new Button("Load");
    private Button saveButton = new Button("Save");
    private Button importButton = new Button("Import");
    private Button exportButton = new Button("Export");
    private VerticalPanel mainPanel = new VerticalPanel();

    public boolean isMapLoaded() {
        if (!Maps.isLoaded()) {
            Window.alert("The Maps API is not installed."
                    + "  The <script> tag that loads the Maps API may be missing or your Maps key may be wrong.");
            return false;
        }

        if (!Maps.isBrowserCompatible()) {
            Window.alert("The Maps API is not compatible with this browser.");
            return false;
        }

        return true;
    }
    
    public Widget loadMap() {
        map = new MapWidget(LatLng.newInstance(48.859068, 2.344894), 12);
        map.setSize("700px", "500px");
//        MapUIOptions options = map.getDefaultUI();
//        options.setDoubleClick(false);
        map.setUIToDefault();
        map.setDoubleClickZoom(false);


        mainPanel.add(makeToolBar());
        mainPanel.add(map);
        return mainPanel;
    }

    private Widget makeToolBar() {
        DockPanel p = new DockPanel();
        p.setWidth("100%");
        HorizontalPanel buttonPanel = new HorizontalPanel();

        loadButton.addClickHandler(this);
        saveButton.addClickHandler(this);
        importButton.addClickHandler(this);
        exportButton.addClickHandler(this);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);

        p.add(buttonPanel, DockPanel.CENTER);
        return p;
    }

    @Override
    public void onClick(ClickEvent event) {
        Widget sender = (Widget) event.getSource();

        if (sender == loadButton) {
            Window.alert("Pressed Load button");
        } else if (sender == saveButton) {
            Window.alert("Pressed Save button");
        } else if (sender == importButton) {
            Window.alert("Pressed Import button");
        } else if (sender == exportButton) {
            Window.alert("Pressed Export button");
        }
    }
}

//        Maps.loadMapsApi("AIzaSyDhEb7O5rSjmuHneI6TH8FVx6vdcjqzlQQ", "2", false, new Runnable() {
//            public void run() {
//                buildMap();
//
//                GeoXmlOverlay.load(KML_DEMO_URI, new GeoXmlLoadCallback() {
//                    @Override
//                    public void onFailure(String s, Throwable throwable) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(String s, GeoXmlOverlay geoXmlOverlay) {
//                        if (geoXml != null) {
//                            return;
//                        }
//                        geoXml = geoXmlOverlay;
//                        map.addOverlay(geoXml);
//                    }
//                });
//            }
//        });