package com.example.application.views.settings;

import com.example.application.data.ControlService;
import com.example.application.views.mapview.MapboxLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import database.Person;

public class SettingsView extends Div {
    private final Person user = VaadinSession.getCurrent().getAttribute(Person.class);
    private final ControlService controlService = new ControlService();
    Button sortButton;
    RadioButtonGroup<String> listingOp1;
    RadioButtonGroup<String> listingOp2;
    RadioButtonGroup<String> searchBarOpts;
    RadioButtonGroup<String> themeOpts;
    RadioButtonGroup<String> weatherOpts;

    VerticalLayout layout = new VerticalLayout();

    ThemeList themeList = UI.getCurrent().getElement().getThemeList();

    int[] userBooleanPreferences;


    public SettingsView(MapboxLayout mapboxLayout) {
        H3 h3 = new H3("Settings");
        h3.getStyle().set("padding-left", "15px");
        if (themeList.contains(Lumo.LIGHT)) {
            h3.getStyle().set("color", "#202020");
        }
        if (themeList.contains(Lumo.DARK)) {
            h3.getStyle().set("color", "#e6e2d3");
        }
        add(h3);
        userBooleanPreferences = getUserPreferences();
        createListingTypeOptions(mapboxLayout);
        createSearchBarOptions(mapboxLayout);
        createThemeOptions(mapboxLayout);
        createWeatherOptions(mapboxLayout);
        add(layout);
    }

    private void createThemeOptions(MapboxLayout mapboxLayout) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        themeOpts = new RadioButtonGroup<>();
        themeOpts.setLabel("Theme");
        themeOpts.setItems("Light", "Dark");
        if (userBooleanPreferences[1] == 0) {
            themeOpts.setValue("Light");
        } else {
            themeOpts.setValue("Dark");
        }
        Button themeButton = new Button("Apply", buttonClickEvent -> {
            setUserBooleanPreferences();
        });
        horizontalLayout.add(themeOpts, themeButton);
        layout.add(horizontalLayout);
    }

    private void createListingTypeOptions(MapboxLayout mapboxLayout) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        String[] preference = controlService.getUserListingType(user);
        listingOp1 = new RadioButtonGroup<>();
        listingOp1.setLabel("Grouping");
        listingOp1.setItems("None", "Land Type", "Group Name");
        listingOp1.setValue(preference[0]);

        listingOp2 = new RadioButtonGroup<>();
        listingOp2.setLabel("Sorting");
        listingOp2.setItems("Default", "Alphabetical");
        listingOp2.setValue(preference[1]);

        sortButton = new Button("Apply", buttonClickEvent -> {
            mapboxLayout.createLandData(listingOp1.getValue(),
                    listingOp2.getValue(), mapboxLayout.drawerLayout);
            controlService.setUserListingType(user, getListingType());
        });
        horizontalLayout.add(listingOp1, listingOp2, sortButton);
        layout.add(horizontalLayout);
    }

    private void createSearchBarOptions(MapboxLayout mapboxLayout) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        searchBarOpts = new RadioButtonGroup<>();
        searchBarOpts.setLabel("Search bar finding method");
        searchBarOpts.setHelperText(/*"If you prioritize a search format," +
                " search bar will try to find results using the prioritized format first." +
                " If there is no results, second format will be used." +*/
                " Note that this option is important only if grouping is selected as group name." +
                        " Otherwise search bar will search by land name regardless.");
        searchBarOpts.setItems("Prioritize searching by group name", "Prioritize searching by land name",
                "Always search by name", "Always search by group name");
        searchBarOpts.setValue(getSearchBarPreference());
        Button searchButton = new Button("Apply", buttonClickEvent -> {
            setUserBooleanPreferences();
        });
        horizontalLayout.add(searchBarOpts, searchButton);
        layout.add(horizontalLayout);
    }

    private void createWeatherOptions(MapboxLayout mapboxLayout) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        weatherOpts = new RadioButtonGroup<>();
        weatherOpts.setLabel("Weather Info");
        weatherOpts.setItems("Show", "Hide");
        if (userBooleanPreferences[1] == 0) {
            weatherOpts.setValue("Show");
        } else {
            weatherOpts.setValue("Hide");
        }
        Button weatherButton = new Button("Apply", buttonClickEvent -> {
            setUserBooleanPreferences();
        });
        horizontalLayout.add(weatherOpts, weatherButton);
        layout.add(horizontalLayout);
    }

    private int getListingType() {
        int firstNum;
        int secondNum;
        switch (listingOp1.getValue()) {
            case "Land Type":
                firstNum = 1;
                break;
            case "Group Name":
                firstNum = 2;
                break;
            default:
                firstNum = 0;
                break;
        }
        switch (listingOp2.getValue()) {
            case "Alphabetical":
                secondNum = 3;
                break;
            default:
                secondNum = 0;
                break;
        }
        return secondNum + firstNum;
    }

    public int[] getUserPreferences() {
        String boolInfo = Integer.toBinaryString(user.getBooleanInfo());
        final int SETTINGS_COUNT = 4; // 1-2 searchBarOption 3-Dark Theme
        int[] result = new int[SETTINGS_COUNT - 1]; // 2si birleşik o yüzden SETTINGS_COUNT - 1
        //Burda yapılan şey sayıya ayar sayısı kadar 0 vermek böylece indexoutofbounds exceptionu almıcaz
        for (int i = boolInfo.length(); i < SETTINGS_COUNT; i++)
            boolInfo = "0" + boolInfo;
        int searchBarOption = Integer.parseInt(boolInfo.substring(boolInfo.length() - 2), 2);
        int darkThemeOption = Integer.parseInt(boolInfo.substring
                (boolInfo.length() - 3, boolInfo.length() - 2), 2);
        int weatherOption = Integer.parseInt(boolInfo.substring
                (boolInfo.length() - 4, boolInfo.length() - 3), 2);
        result[0] = searchBarOption;
        result[1] = darkThemeOption;
        result[2] = weatherOption;
        return result;
    }

    private void setUserBooleanPreferences() {
        String searchBarOption; // not used
        switch (searchBarOpts.getValue()) {
            default:
                searchBarOption = "00";
                userBooleanPreferences[0] = 0;
                break;
            case "Prioritize searching by land name":
                searchBarOption = "01";
                userBooleanPreferences[0] = 1;
                break;
            case "Always search by name":
                searchBarOption = "10";
                userBooleanPreferences[0] = 2;
                break;
            case "Always search by group name":
                searchBarOption = "11";
                userBooleanPreferences[0] = 3;
                break;
        }
        if (themeOpts.getValue().equals("Light")) {
            userBooleanPreferences[1] = 0;
            changeTheme(false);
        } else {
            userBooleanPreferences[1] = 1;
            changeTheme(true);
        }
        if (weatherOpts.getValue().equals("Show")) {
            userBooleanPreferences[2] = 0;
        } else {
            userBooleanPreferences[2] = 1;
        }
        controlService.setBooleanUserPreferences(userBooleanPreferences[0] +
                4 * userBooleanPreferences[1] + 8 * userBooleanPreferences[2], user);
        user.setBooleanInfo(userBooleanPreferences[0] +
                4 * userBooleanPreferences[1] + 8 * userBooleanPreferences[2]);
    }

    public void changeTheme(boolean isDark) {
        if (isDark)
            themeList.add(Lumo.DARK);
        else
            themeList.remove(Lumo.DARK);
    }

    public String getSearchBarPreference() {
        switch (userBooleanPreferences[0]) {
            default:
                return "Prioritize searching by group name";
            case 1:
                return "Prioritize searching by land name";
            case 2:
                return "Always search by name";
            case 3:
                return "Always search by group name";
        }
    }

}