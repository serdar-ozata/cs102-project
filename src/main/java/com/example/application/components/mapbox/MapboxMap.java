package com.example.application.components.mapbox;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Edited version of com.github.markhm.mapbox.MapboxMap slightly altered for our use case.
 */
public class MapboxMap extends Div {
    private static Log log = LogFactory.getLog(com.github.markhm.mapbox.MapboxMap.class);

    public Page page = null;

    boolean alreadyRendered = false;

    private com.github.markhm.mapbox.GeoLocation initialView = null;
    private int initialZoom;

    public MapboxMap(com.github.markhm.mapbox.GeoLocation initialView, int initialZoom) {
        this.initialView = initialView;
        this.initialZoom = initialZoom;

        setId("map");
        getStyle().set("align-self", "center");
        // getStyle().set("border", "1px solid black");

        setWidth("1200px");
        setHeight("815px");
        /*
        setWidth("1200px");
        setHeight("700px");
         */

        page = UI.getCurrent().getPage();

        if (!alreadyRendered) {
            render();
            alreadyRendered = true;
        }


    }

    private void render() {
        page.addStyleSheet("https://api.tiles.mapbox.com/mapbox-gl-js/v2.2.0/mapbox-gl.css");
        page.addJavaScript("https://api.tiles.mapbox.com/mapbox-gl-js/v2.2.0/mapbox-gl.js");
        page.addJavaScript("https://api.tiles.mapbox.com/mapbox.js/plugins/turf/v2.0.0/turf.min.js");
        //for Geocodes
        page.addJavaScript("https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-geocoder/v4.7.0/mapbox-gl-geocoder.min.js");
        page.addStyleSheet("https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-geocoder/v4.7.0/mapbox-gl-geocoder.css");
        page.addJavaScript("https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.auto.min.js");
        page.addJavaScript("https://cdn.jsdelivr.net/npm/es6-promise@4/dist/es6-promise.min.js");
        //for draw
        page.addStyleSheet("https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-draw/v1.2.0/mapbox-gl-draw.css");
        page.addJavaScript("https://api.mapbox.com/mapbox-gl-js/plugins/mapbox-gl-draw/v1.2.0/mapbox-gl-draw.js");


        page.addJavaScript("/js/mapbox/mapbox.js");

        String accessToken = loadAccessToken();
        page.executeJs("mapboxgl.accessToken = '" + accessToken + "';");

        page.executeJs("renderMapbox(" + initialView.getCoordinates() + "," + initialZoom + ");");


    }

    public void flyTo(com.github.markhm.mapbox.GeoLocation geoLocation) {
        page.executeJs("map.flyTo({center: " + geoLocation.getCoordinates() + ", zoom: 15});");
    }

    public void zoomTo(int zoomLevel) {
        page.executeJs("zoomTo(" + zoomLevel + ");");
    }

    public void startAnimation() {
        page.executeJs("startAnimation();");
    }

    public void executeJS(String javaScript) {
        page.executeJs(javaScript);
    }

    public void drawOriginDestinationFlight(com.github.markhm.mapbox.GeoLocation origin, GeoLocation destination) {
        page.executeJs("fromOriginToDestination(" + origin.getCoordinates() + ", " + destination.getCoordinates() + ");");
    }

    private String loadAccessToken() {
        String token = "";

        // https://www.mkyong.com/java/java-properties-file-examples/
        try (InputStream input = com.github.markhm.mapbox.MapboxMap.class.getClassLoader().getResourceAsStream("mapbox.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            token = prop.getProperty("mapboxgl.accessToken");

            // get the property value and print it out
            // System.out.println("Successfully loaded access token from mapbox.properties file.");

        } catch (IOException ex) {
            System.err.println("Something went wrong reading properties file: " + ex.getMessage());
            System.err.println("Did you create an account at Mapbox.com and save your API key in src/main/resources/mapbox.properties...?");
            ex.printStackTrace(System.err);
        }

        return token;
    }
}
