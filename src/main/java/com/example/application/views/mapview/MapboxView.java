package com.example.application.views.mapview;


import com.example.application.components.mapbox.GeoLocation;
import com.example.application.components.mapbox.MapboxMap;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.page.Page;

/**
 * This view holds map and listener for weather updates. Check com.example.application.components.mapbox.MapboxMap for
 * map implementation
 */
public class MapboxView extends Div {
    MapboxMap map = new MapboxMap(GeoLocation.NewYork, 3);
    public Page page = UI.getCurrent().getPage();

    public MapboxView(WeatherNotification weatherNotification) {
        map.setEnabled(true);
        map.setWidthFull();
        add(map);
        addClickListener(e -> {
            weatherNotification.update();
            System.out.println("Bas");
        });
    }

}

