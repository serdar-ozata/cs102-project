package com.example.application.views.mapview;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.page.PendingJavaScriptResult;

/**
 *  This notification gets and shows weather information of the current location of the map. Openweather api is used
 *  to get the weather data openweather code implementation is in /js/mapbox/mapbox.js file.
 */

public class WeatherNotification extends Notification {
    VerticalLayout mainLayout = new VerticalLayout();
    VerticalLayout oldLayout = new VerticalLayout();
    VerticalLayout newLayout = new VerticalLayout();
    boolean firstUpdate = true;
    Page page = UI.getCurrent().getPage();


    public WeatherNotification() {
        page.addJavaScript("/js/mapbox/mapbox.js");
        add(mainLayout);
        setPosition(Position.BOTTOM_END);
    }

    public void update() {
        if (firstUpdate) {
            PendingJavaScriptResult degree = page.executeJs("return getTemp();");
            PendingJavaScriptResult weatherDesc = page.executeJs("return getDescription();");//°C
            VerticalLayout newLayout = new VerticalLayout();
            degree.then(String.class, e -> {
                newLayout.add(new H6("Degree: " + e + "°C"));
            });
            weatherDesc.then(String.class, e -> {
                newLayout.add(new H6(e));
                mainLayout.add(newLayout);
                newLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                firstUpdate = false;
                oldLayout = newLayout;
            });

        } else {
            newLayout = new VerticalLayout();
            PendingJavaScriptResult degree = page.executeJs("return getTemp();");
            PendingJavaScriptResult weatherDesc = page.executeJs("return getDescription();");//°C
            VerticalLayout newLayout = new VerticalLayout();
            degree.then(String.class, e -> {
                newLayout.add(new H6("Degree: " + e + "°C"));
            });
            weatherDesc.then(String.class, e -> {
                newLayout.add(new H6(e));
                newLayout.setAlignItems(FlexComponent.Alignment.CENTER);
                mainLayout.replace(oldLayout, newLayout);
                oldLayout = newLayout;
            });

        }
    }
}
