package com.example.application.views.mapview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.component.textfield.TextField;
import database.assets.FarmLand;
import database.assets.Info;
import database.assets.RealEstate;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import database.DataSource;

import java.util.ArrayList;

public class InfoView extends Div {
    private Details todayDetails;
    private Details tomorrowDetails;
    private Details nextDayDetails;

    Page page = UI.getCurrent().getPage();


    public InfoView(Info land) {

        DataSource dataSource = new DataSource();
        dataSource.open();
        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.START);
        Label label1 = new Label("General Properties:");
        label1.getStyle().set("font-size", "1.5em");
        label1.getStyle().set("font-family", "Arial");

        HorizontalLayout layout1 = new HorizontalLayout();
        Icon icon1 = new Icon(VaadinIcon.INFO_CIRCLE);
        icon1.setColor("#87bdd8");
        TextField tf1 = new TextField("Land Name");
        tf1.setReadOnly(true);
        layout1.setAlignItems(FlexComponent.Alignment.BASELINE);
        tf1.setValue(land.getName());
        layout1.add(label1, icon1, tf1);

        HorizontalLayout layout2 = new HorizontalLayout();
        Icon icon2 = new Icon(VaadinIcon.FILE_TEXT);
        icon2.setColor("#dbceb0");
        TextField tf2 = new TextField("Land Description");
        tf2.setReadOnly(true);
        layout2.setAlignItems(FlexComponent.Alignment.BASELINE);
        tf2.setValue(land.getDescription());
        layout2.add(icon2, tf2);

        HorizontalLayout layout3 = new HorizontalLayout();
        Icon icon3 = new Icon(VaadinIcon.PALETE);
        icon3.setColor("#f7786b");
        TextField tf3 = new TextField("Color");
        tf3.setReadOnly(true);
        layout3.setAlignItems(FlexComponent.Alignment.BASELINE);
        tf3.setValue(land.getColor());
        layout3.add(icon3, tf3);

        HorizontalLayout layout4 = new HorizontalLayout();
        Icon icon4 = new Icon(VaadinIcon.GROUP);
        icon4.setColor("Black");
        TextField tf4 = new TextField("Land Group Name");
        tf4.setReadOnly(true);
        layout4.setAlignItems(FlexComponent.Alignment.BASELINE);
        tf4.setValue(land.getGroupName());
        layout4.add(icon4, tf4);

        HorizontalLayout horLayout1 = new HorizontalLayout();
        horLayout1.add(layout1, layout2, layout3, layout4);

        layout.add(horLayout1);

        if (land instanceof FarmLand) {
            if (dataSource.getCurrentCrop(dataSource.getFarmlandID(land.getName(), land.getOwner().getMail())) != null) {
                //Label
                Label label2 = new Label("Detailed Properties:   ");
                label2.getStyle().set("font-size", "1.5em");
                label2.getStyle().set("font-family", "Arial");

                //Crop Name
                HorizontalLayout layout5 = new HorizontalLayout();
                Icon icon5 = new Icon(VaadinIcon.INFO_CIRCLE);
                icon5.setColor("#87bdd8");
                TextField tf5 = new TextField("Crop Name");
                tf5.setReadOnly(true);
                layout5.setAlignItems(FlexComponent.Alignment.BASELINE);
                tf5.setValue("" + dataSource.getCurrentCrop(dataSource.getFarmlandID(land.getName(), land.getOwner().getMail())).getName());
                layout5.add(label2, icon5, tf5);


                //Crop Yield
                HorizontalLayout layout6 = new HorizontalLayout();
                Icon icon6 = new Icon(VaadinIcon.MONEY_DEPOSIT);
                icon6.setColor("#86af49");
                TextField tf6 = new TextField("Crop Yield");
                tf6.setReadOnly(true);
                layout6.setAlignItems(FlexComponent.Alignment.BASELINE);
                tf6.setValue("" + dataSource.getCurrentCrop(dataSource.getFarmlandID(land.getName(), land.getOwner().getMail())).getYield());
                layout6.add(icon6, tf6);

                //Crop Cost
                HorizontalLayout layout7 = new HorizontalLayout();
                Icon icon7 = new Icon(VaadinIcon.MONEY_WITHDRAW);
                icon7.setColor("#86af49");
                TextField tf7 = new TextField("Crop Cost");
                tf7.setReadOnly(true);
                layout7.setAlignItems(FlexComponent.Alignment.BASELINE);
                tf7.setValue("" + dataSource.getCurrentCrop(dataSource.getFarmlandID(land.getName(), land.getOwner().getMail())).getCost());
                layout7.add(icon7, tf7);

                //Revenue
                HorizontalLayout layout8 = new HorizontalLayout();
                Icon icon8 = new Icon(VaadinIcon.MONEY);
                icon8.setColor("#86af49");
                TextField tf8 = new TextField("Crop Revenue");
                tf8.setReadOnly(true);
                layout8.setAlignItems(FlexComponent.Alignment.BASELINE);
                tf8.setValue("" + dataSource.getCurrentCrop(dataSource.getFarmlandID(land.getName(), land.getOwner().getMail())).getRevenue());
                layout8.add(icon8, tf8);

                // Year
                HorizontalLayout layout9 = new HorizontalLayout();
                Icon icon9 = new Icon(VaadinIcon.DATE_INPUT);
                icon9.setColor("#484f4f");
                TextField tf9 = new TextField("Year");
                tf9.setWidth("60px");
                tf9.setReadOnly(true);
                layout9.setAlignItems(FlexComponent.Alignment.BASELINE);
                tf9.setValue("" + dataSource.getCurrentCrop(dataSource.getFarmlandID(land.getName(), land.getOwner().getMail())).getYear());
                layout9.add(icon9, tf9);


                HorizontalLayout horLayout2 = new HorizontalLayout();
                horLayout2.add(layout5, layout6, layout7, layout8, layout9);
                layout.add(horLayout2);
            } else {
                Label label3 = new Label("Detailed Properties:   ");
                label3.getStyle().set("font-size", "1.5em");
                label3.getStyle().set("font-family", "Arial");

                HorizontalLayout layout10 = new HorizontalLayout();
                layout10.setAlignItems(FlexComponent.Alignment.BASELINE);
                TextField tf10 = new TextField();
                tf10.setReadOnly(true);
                tf10.setValue("No crop info");
                Icon icon10 = new Icon(VaadinIcon.BAN);
                icon10.setColor("Red");
                layout10.add(label3, icon10, tf10);

                layout.add(layout10);
            }

        }

        if (land instanceof RealEstate) {

            HorizontalLayout layout11 = new HorizontalLayout();
            layout11.setAlignItems(FlexComponent.Alignment.BASELINE);
            TextField typeText = new TextField("Type");
            typeText.setReadOnly(true);
            typeText.setValue(((RealEstate) land).getType());
            Icon icon11 = new Icon(VaadinIcon.HOME);
            layout11.add(icon11, typeText);

            HorizontalLayout layout12 = new HorizontalLayout();
            layout12.setAlignItems(FlexComponent.Alignment.BASELINE);
            TextField floorNoText = new TextField("Floor Number");
            floorNoText.setReadOnly(true);
            floorNoText.setValue("" + ((RealEstate) land).getFloorNo());
            Icon icon12 = new Icon(VaadinIcon.HOME_O);
            layout11.add(icon12, floorNoText);

            HorizontalLayout layout13 = new HorizontalLayout();
            layout1.setAlignItems(FlexComponent.Alignment.BASELINE);
            TextField aptNoText = new TextField("Apartment Number");
            aptNoText.setReadOnly(true);
            aptNoText.setValue("" + ((RealEstate) land).getAptNo());
            Icon icon13 = new Icon(VaadinIcon.HOME_O);
            layout13.add(icon13, aptNoText);


            layout.add(layout11, layout12, layout13);

        }
        layout.setPadding(true);
        add(layout);

        HorizontalLayout thirdLayout = new HorizontalLayout();
        page.addJavaScript("/js/mapbox/mapbox.js");
        page.executeJs("getWeather(" + land.getCenterCords()[0] + ","
                + land.getCenterCords()[1] + ");");


        HorizontalLayout horLayout3 = new HorizontalLayout();

        todayDetails = new Details("Today's Weather",
                new Text(""));


        tomorrowDetails = new Details("Tomorrow's Weather",
                new Text(""));


        nextDayDetails = new Details("Next Day's Weather",
                new Text(""));


        horLayout3.setPadding(true);
        horLayout3.add(todayDetails, tomorrowDetails, nextDayDetails);
        add(horLayout3);

        ArrayList<String> today = new ArrayList<>();
        PendingJavaScriptResult degreeToday = page.executeJs("return getTemp();");
        degreeToday.then(String.class, todayDegreeStr -> today.add(todayDegreeStr + "°C"));
        PendingJavaScriptResult weatherDescToday = page.executeJs("return getDescription();");
        weatherDescToday.then(String.class, weatherDescTodayStr -> today.add(weatherDescTodayStr));
        PendingJavaScriptResult humdityToday = page.executeJs("return getHum();");//°C
        humdityToday.then(String.class, humidtyTodayStr -> createTodayData(today.get(0), humidtyTodayStr, today.get(1)));

        ArrayList<String> tomorrow = new ArrayList<>();
        PendingJavaScriptResult degreeTomorrow = page.executeJs("return getTemp2();");
        degreeTomorrow.then(String.class, tomDegreeStr -> tomorrow.add(tomDegreeStr + "°C"));
        PendingJavaScriptResult weatherDescTomorrow = page.executeJs("return getDescription2();");
        weatherDescTomorrow.then(String.class, weatherDescTomStr -> tomorrow.add(weatherDescTomStr));
        PendingJavaScriptResult humdityTomorrow = page.executeJs("return getHum2();");//°C
        humdityTomorrow.then(String.class, humidtyTomStr -> tomorrow.add(humidtyTomStr));
        PendingJavaScriptResult maxTomorrow = page.executeJs("return getTemp2Max();");
        maxTomorrow.then(String.class, maxStr -> tomorrow.add(maxStr + "°C"));
        PendingJavaScriptResult minTomorrow = page.executeJs("return getTemp2Min();");
        minTomorrow.then(String.class, minStr -> createTomorrowData(tomorrow.get(0), tomorrow.get(2),
                tomorrow.get(1), minStr + "°C", tomorrow.get(3)));

        ArrayList<String> nextDay = new ArrayList<>();
        PendingJavaScriptResult degreeNextDay = page.executeJs("return getTemp3();");
        degreeNextDay.then(String.class, tomDegreeStr -> nextDay.add(tomDegreeStr));
        PendingJavaScriptResult weatherDescNextDay = page.executeJs("return getDescription3();");
        weatherDescNextDay.then(String.class, weatherDescTomStr -> nextDay.add(weatherDescTomStr));
        PendingJavaScriptResult humdityNextDay = page.executeJs("return getHum3();");//°C
        humdityNextDay.then(String.class, humidtyTomStr -> nextDay.add(humidtyTomStr));
        PendingJavaScriptResult maxNextDay = page.executeJs("return getTemp3Max();");
        maxNextDay.then(String.class, maxStr -> nextDay.add(maxStr));
        PendingJavaScriptResult minNextDay = page.executeJs("return getTemp3Min();");
        minNextDay.then(String.class, minStr -> createNextDayData(nextDay.get(0) + "°C",
                nextDay.get(2), nextDay.get(1), minStr + "°C", nextDay.get(3) + "°C"));


    }

    public InfoView() {
        Text text = new Text("No land is selected");
        add(text);
    }

    private void createTodayData(String degree, String humidity, String description) {
        TextField degreeField = new TextField("Degree in Celsius");
        degreeField.setValue(degree);
        degreeField.setReadOnly(true);

        TextField humidityField = new TextField("Humidity");
        humidityField.setValue(humidity);
        humidityField.setReadOnly(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(description);
        descriptionField.setReadOnly(true);

        VerticalLayout layout = new VerticalLayout();
        layout.add(degreeField, humidityField, descriptionField);
        todayDetails.addContent(layout);
    }

    // tomorrow ve nextDayde min ve max degree değerleri var. Parametreleri değiştirmek gerekebilir
    private void createTomorrowData(String degree, String humidity, String description, String minDegree, String maxDegree) {
        TextField degreeField = new TextField("Degree in Celsius");
        degreeField.setValue(degree);
        degreeField.setReadOnly(true);

        TextField minDegreeField = new TextField("Minimum degree in Celsius");
        //Dikkat
        minDegreeField.setValue(minDegree);
        minDegreeField.setReadOnly(true);

        TextField maxDegreeField = new TextField("Maximum degree in Celsius");
        //Dikkat
        maxDegreeField.setValue(maxDegree);
        maxDegreeField.setReadOnly(true);

        TextField humidityField = new TextField("Humidity");
        humidityField.setValue(humidity);
        humidityField.setReadOnly(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(description);
        descriptionField.setReadOnly(true);

        VerticalLayout layout = new VerticalLayout();
        layout.add(degreeField, minDegreeField, maxDegreeField, humidityField, descriptionField);
        tomorrowDetails.addContent(layout);
    }

    private void createNextDayData(String degree, String humidity, String description, String minDegree, String maxDegree) {
        TextField degreeField = new TextField("Degree in Celsius");
        degreeField.setValue(degree);
        degreeField.setReadOnly(true);

        TextField minDegreeField = new TextField("Minimum degree in Celsius");
        //Dikkat
        minDegreeField.setValue(minDegree);
        minDegreeField.setReadOnly(true);

        TextField maxDegreeField = new TextField("Maximum degree in Celsius");
        //Dikkat
        maxDegreeField.setValue(maxDegree);
        maxDegreeField.setReadOnly(true);

        TextField humidityField = new TextField("Humidity");
        humidityField.setValue(humidity);
        humidityField.setReadOnly(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(description);
        descriptionField.setReadOnly(true);

        VerticalLayout layout = new VerticalLayout();
        layout.add(degreeField, minDegreeField, maxDegreeField, humidityField, descriptionField);
        nextDayDetails.addContent(layout);
    }

}