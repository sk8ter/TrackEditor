package com.softwerk.trackeditor.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.Control;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;

import java.util.Map;

public class EditMapControl extends Control.CustomControl {
    private Map<String, Overlay> overlayMap;

    public EditMapControl(final Map<String, Overlay> overlayMap) {
        super(new ControlPosition(ControlAnchor.BOTTOM_LEFT, 7, 7));
        this.overlayMap = overlayMap;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    protected Widget initialize(final MapWidget map) {
        Panel container = new FlowPanel();

        final ToggleButton editButton = new ToggleButton("Edit");
//            editButton.setStyleName("textualZoomControl");
        editButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                setEditable(editButton.getValue());
            }
        });

        container.add(editButton);
        return container;
    }

    private void setEditable(boolean isEditing) {
        for (Overlay overlay : overlayMap.values()) {
            if (overlay instanceof Marker) {
                Marker marker = (Marker) overlay;
                marker.setDraggingEnabled(isEditing);
            }
        }
    }
}