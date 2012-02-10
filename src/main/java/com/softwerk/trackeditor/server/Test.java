package com.softwerk.trackeditor.server;

import de.micromata.opengis.kml.v_2_2_0.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        final Kml kml = new Kml();
        final Document document = new Document();
        kml.setFeature(document);
        document.setName("LineString.kml");
        document.setOpen(true);

        final LookAt lookat = new LookAt();
        lookat.setLongitude(-122.36415);
        lookat.setLatitude(37.824553);
        lookat.setRange(150.0d);
        lookat.setTilt(50.0d);
        lookat.setHeading(0.0d);

        document.setAbstractView(lookat);

        final Placemark placemark1 = new Placemark();
        document.getFeature().add(placemark1);
        placemark1.setName("unextruded");
        final LineString linestring1 = new LineString();
        placemark1.setGeometry(linestring1);
        linestring1.setExtrude(false);
        linestring1.setTessellate(true);
        List<Coordinate> coord1 = new ArrayList<Coordinate>();
        linestring1.setCoordinates(coord1);
        coord1.add(new Coordinate(-122.364383,37.824664,0));
        coord1.add(new Coordinate(-122.364152,37.824322,0));

        final Placemark placemark2 = new Placemark();
        document.getFeature().add(placemark2);
        placemark2.setName("extruded");
        final LineString linestring2 = new LineString();
        placemark2.setGeometry(linestring2);
        linestring2.setExtrude(true);
        linestring2.setTessellate(true);
        List<Coordinate> coord2 = new ArrayList<Coordinate>();
        linestring2.setCoordinates(coord2);
        coord2.add(new Coordinate(-122.364167,37.824787,50));
        coord2.add(new Coordinate(-122.363917,37.824423,50));

        try {
            kml.marshal(new File("wwww.kml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
