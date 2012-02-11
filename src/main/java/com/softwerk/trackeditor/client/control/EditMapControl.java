package com.softwerk.trackeditor.client.control;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.Control;
import com.google.gwt.maps.client.control.ControlAnchor;
import com.google.gwt.maps.client.control.ControlPosition;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.softwerk.trackeditor.client.MapEdit;
import com.softwerk.trackeditor.client.overlay.MarkerInfo;

import java.util.Map;

public class EditMapControl extends Control.CustomControl {
    private MapEdit mapEdit;
    private AddMenuControl addMenuControl;

    public EditMapControl(MapEdit mapEdit) {
        super(new ControlPosition(ControlAnchor.BOTTOM_LEFT, 7, 7));
        this.mapEdit = mapEdit;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    protected Widget initialize(final MapWidget map) {
        Panel container = new FlowPanel();

        final ToggleButton editButton = new ToggleButton("Edit");
//        editButton.setStyleName("textualZoomControl");
        editButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                if (addMenuControl == null) {
                    addMenuControl = new AddMenuControl(mapEdit);
                }

                // Show/Hide AddMenuControl
                if (editButton.getValue()) {
                    map.addControl(addMenuControl);
                } else {
                    map.removeControl(addMenuControl);
                }

                // Enable/Disable editing mode
                setEditable(editButton.getValue());
            }
        });

        container.add(editButton);
        return container;
    }

    private void setEditable(boolean isEditing) {
        for (Overlay overlay : mapEdit.getOverlays().values()) {
            if (overlay instanceof MarkerInfo) {
                MarkerInfo markerInfo = (MarkerInfo) overlay;
                markerInfo.getMarker().setDraggingEnabled(isEditing);
            }
        }
    }
}