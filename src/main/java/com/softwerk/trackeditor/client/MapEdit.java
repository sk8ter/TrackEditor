package com.softwerk.trackeditor.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapUIOptions;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.softwerk.trackeditor.client.control.EditMapControl;
import com.softwerk.trackeditor.client.overlay.MarkerInfo;

import java.util.ArrayList;

public class MapEdit extends Composite implements ClickHandler {
    // temporary
    private static int next = 0;

    private MapWidget map;
    private Button loadButton = new Button("Load");
    private Button saveButton = new Button("Save");
    private Button importButton = new Button("Import");
    private Button exportButton = new Button("Export");
    private VerticalPanel mainPanel = new VerticalPanel();

    // For editing
    private ArrayList<Overlay> overlays = new ArrayList<Overlay>();

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
        LatLng latLng = LatLng.newInstance(48.859068, 2.344894);
        map = new MapWidget(latLng, 12);
        map.setSize("700px", "500px");
        MapUIOptions mapUIOptions = map.getDefaultUI();
        mapUIOptions.setDoubleClick(false);
        mapUIOptions.setLargeMapControl3d(true);
        map.setUI(mapUIOptions);
        map.addControl(new EditMapControl(this));

        mainPanel.add(makeToolBar());
        mainPanel.add(map);
        return mainPanel;
    }

    public void addOverlay(Overlay overlay) {
        map.addOverlay(overlay);
        overlays.add(overlay);
    }

    public void removeOverlay(Overlay overlay) {
        map.removeOverlay(overlay);
        overlays.remove(overlay);
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

    public MapWidget getMap() {
        return map;
    }

    public ArrayList<Overlay> getOverlays() {
        return overlays;
    }

    public static int getNext() {
        return ++next;
    }

    public void setEditable(boolean isEditing) {
        for (Overlay overlay : overlays) {
            if (overlay instanceof MarkerInfo) {
                MarkerInfo markerInfo = (MarkerInfo) overlay;
                markerInfo.getMarker().setDraggingEnabled(isEditing);
            }
        }
    }
}