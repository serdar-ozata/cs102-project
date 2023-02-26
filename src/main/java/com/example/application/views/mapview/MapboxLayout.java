package com.example.application.views.mapview;

import com.example.application.components.ComparableDetails;
import com.example.application.data.ControlService;
import com.example.application.data.LandService;
import com.example.application.views.about.AboutView;
import com.example.application.views.logout.LogoutView;
import com.example.application.views.settings.PersonSettingsView;
import com.example.application.views.settings.SettingsView;
import com.github.markhm.mapbox.GeoLocation;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.apache.commons.lang3.StringUtils;
import com.vaadin.flow.component.page.*;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import database.Person;
import database.assets.FarmLand;
import database.assets.Info;
import database.assets.RealEstate;



import java.util.*;

/**
 * Main content holder for views. Settings, Person Settings, Map and Info are shown inside this layout.
 * It also creates drawers for lands, tabs for views and logout option, add option, about link.
 */
@PageTitle("Map")
public class MapboxLayout extends AppLayout implements BeforeEnterObserver {
    private final LandService landService = new LandService();
    private final ControlService controlService = new ControlService();
    private WeatherNotification weatherNotification;
    private MapboxView mapboxView;
    private SettingsView settingsView;
    private final PersonSettingsView personSettingsView = new PersonSettingsView();
    private final Person user = VaadinSession.getCurrent().getAttribute(Person.class);
    private ArrayList<Info> rawData = landService.getAllLands(user.getMail());
    EditNotification editNotification;
    private String[] listingPreference;
    public VerticalLayout drawerLayout;
    private InfoView infoView = new InfoView();
    private final HorizontalLayout navComponents = new HorizontalLayout();
    private VerticalLayout drawerLandContent = new VerticalLayout();
    private Tabs tabs;
    private Tab mapTab;
    private Tab infoTab;
    private Tab settingsTab;
    private Tab personSettingsTab;
    private double[] mapCoordinates = new double[3];
    private InfoNotification oldNotification = null;


    public MapboxLayout() {
        weatherNotification = new WeatherNotification();
        if (user.getBooleanInfo() - 8 < 0)
            weatherNotification.open();
        mapboxView = new MapboxView(weatherNotification);
        settingsView = new SettingsView(this);
        setPrimarySection(AppLayout.Section.DRAWER);
        Button addButton = createAddButton();
        addToNavbar(true, new DrawerToggle(), addButton);
        addToDrawer(createDrawerContent());
        setDrawerOpened(false);

        addMenuTabs();
        addToNavbar(navComponents);
        navComponents.getElement().getStyle().set("margin-left", "auto");
        setContent(mapboxView);
        sendAllDataToJs();

    }

    public Page getMapPage() {
        return mapboxView.page;
    }

    private Button createAddButton() {
        Button addButton = new Button(new Icon(VaadinIcon.PLUS), buttonClickEvent -> {
            editNotification = new EditNotification(this);
            editNotification.open();
            setDrawerOpened(false);
            tabs.setSelectedTab(mapTab);
            getMapPage().executeJs("Draw()");
            getMapPage().executeJs("recordShapeCords();");
            getMapPage().executeJs("recordToTrue();");
        });
        addButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        return addButton;
    }

    private void addMenuTabs() {
        mapTab = new Tab(new Icon(VaadinIcon.MAP_MARKER));
        infoTab = new Tab(new Icon(VaadinIcon.RECORDS));
        settingsTab = new Tab(new Icon(VaadinIcon.COG));
        personSettingsTab = new Tab(new Icon(VaadinIcon.USER));

        Map<Tab, Div> tabsToContent = new HashMap<>();
        tabsToContent.put(mapTab, mapboxView);
        tabsToContent.put(infoTab, infoView);
        tabsToContent.put(settingsTab, settingsView);
        tabsToContent.put(personSettingsTab, personSettingsView);
        tabs = new Tabs(mapTab, infoTab, settingsTab, personSettingsTab);
        // Div contents = new Div(mapboxView, infoView);
        tabs.addSelectedChangeListener(event -> {
            Component selectedTab = tabs.getSelectedTab();
            if (selectedTab == mapTab) {
                if (user.getBooleanInfo() - 8 < 0)
                    weatherNotification.open();
                setContent(mapboxView);
                mapboxView.map.page.executeJs("renderMapbox([" + mapCoordinates[0] + ","
                        + mapCoordinates[1] + "]," + mapCoordinates[2] + ");");
                sendAllDataToJs();
            } else if (selectedTab == settingsTab) {
                saveMapCords();
                setContent(settingsView);
                if (user.getBooleanInfo() - 8 < 0)
                    weatherNotification.close();
            } else if (selectedTab == infoTab) {
                saveMapCords();
                setContent(infoView);
                if (user.getBooleanInfo() - 8 < 0)
                    weatherNotification.close();
            } else {
                saveMapCords();
                setContent(personSettingsView);
                if (user.getBooleanInfo() - 8 < 0)
                    weatherNotification.close();
            }
        });
        addToNavbar(tabs);

        RouterLink aboutLink = new RouterLink("About", AboutView.class);
        Tab aboutTab = new Tab(aboutLink);
        RouterLink logoutLink = new RouterLink("Logout", LogoutView.class);
        Tab logoutTab = new Tab(logoutLink);
        navComponents.setAlignItems(FlexComponent.Alignment.CENTER);
        navComponents.add(aboutTab, logoutTab);
    }


    private Component createDrawerContent() {
        drawerLayout = new VerticalLayout();
        drawerLayout.setSizeFull();
        drawerLayout.setPadding(false);
        drawerLayout.setSpacing(false);
        drawerLayout.getThemeList().set("spacing-s", true);
        drawerLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Image logo = new Image("images/Land-Tracker.png", "Land Tracker Logo");
        logo.getStyle().set("padding", "10px");

        listingPreference = controlService.getUserListingType(user);


        TextField searchBar = new TextField();
        searchBar.setPlaceholder("Search");
        Icon icon = VaadinIcon.SEARCH.create();
        searchBar.setPrefixComponent(icon);
        searchBar.setValueChangeMode(ValueChangeMode.LAZY);
        searchBar.addValueChangeListener(e -> createLandData(controlService.getUserListingType(user)[0],
                controlService.getUserListingType(user)[1], drawerLayout, e.getValue()));

        drawerLayout.add(logo, searchBar, drawerLandContent);

        createLandData(controlService.getUserListingType(user)[0], controlService.getUserListingType(user)[1], drawerLayout);
        return drawerLayout;
    }


    private ArrayList<Info> sortData(String sortType, ArrayList<Info> data) {
        switch (sortType) {
            case "Alphabetical":
                Collections.sort(data);
                return data;
            default:
                return data;
        }
    }


    private Component createDetails(Info land) {//TODO
        HorizontalLayout layout = new HorizontalLayout();
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        String color = land.getColor();
        Label label = new Label(land.getName());
        label.getStyle().set("color", color);
        label.getStyle().set("font-family", "Arial");

        Icon flyIcon = new Icon(VaadinIcon.LOCATION_ARROW_CIRCLE);
        flyIcon.getElement().addEventListener("mouseover", e -> {
            flyIcon.setSize("27px");
        });
        flyIcon.getElement().addEventListener("mouseout", e -> {
            flyIcon.setSize("25px");
        });
        flyIcon.setSize("25px");
        flyIcon.setColor(color);
        flyIcon.getStyle().set("cursor", "pointer");
        flyIcon.addClickListener(
                event -> {
                    double[] cords = land.getCenterCords();
                    mapboxView.map.flyTo(new GeoLocation("", cords[0], cords[1]));
                });

        Icon infoIcon = new Icon(VaadinIcon.USER_CARD);
        infoIcon.setSize("25px");
        infoIcon.getStyle().set("color", "#87bdd8");
        infoIcon.getStyle().set("cursor", "pointer");
        infoIcon.addClickListener(
                event -> showInfo(land, event));
        infoIcon.getElement().addEventListener("mouseover", e -> {
            infoIcon.setSize("27px");
        });
        infoIcon.getElement().addEventListener("mouseout", e -> {
            infoIcon.setSize("25px");
        });

        Icon editIcon = new Icon(VaadinIcon.EDIT);
        editIcon.setSize("25px");
        editIcon.getStyle().set("color", "#b2b2b2");
        editIcon.getStyle().set("cursor", "pointer");
        editIcon.getElement().addEventListener("mouseover", e -> {
            editIcon.setSize("27px");
        });
        editIcon.getElement().addEventListener("mouseout", e -> {
            editIcon.setSize("25px");
        });
        EditDialog editDialog = new EditDialog(land, this);
        editIcon.addClickListener(iconClickEvent -> editDialog.open());

        Icon exitIcon = new Icon(VaadinIcon.CLOSE_CIRCLE);
        exitIcon.setSize("25px");
        exitIcon.getStyle().set("color", "#c94c4c");
        exitIcon.getStyle().set("cursor", "pointer");
        ConfirmDialog exitDialog = new ConfirmDialog("Confirm delete",
                "Are you sure you want to delete the item?",
                "Delete", confirmEvent -> delete(land), "Cancel", cancelEvent -> {
        });
        exitDialog.setConfirmButtonTheme("error primary");
        exitIcon.addClickListener(event -> exitDialog.open());
        exitIcon.getElement().addEventListener("mouseover", e -> {
            exitIcon.setSize("27px");
        });
        exitIcon.getElement().addEventListener("mouseout", e -> {
            exitIcon.setSize("25px");
        });


        layout.add(label, flyIcon, infoIcon, editIcon, exitIcon, exitDialog);
        return layout;
    }

    private void showInfo(Info land, ClickEvent<Icon> event) {
        if (oldNotification != null)
            oldNotification.close();
        InfoNotification info = new InfoNotification(land);
        info.getInfoButton().addClickListener(buttonClickEvent -> {
            boolean reload = false;
            if (tabs.getSelectedTab() == infoTab)
                reload = true;
            infoView = new InfoView(land);
            if (reload)
                setContent(infoView);
            else
                tabs.setSelectedTab(infoTab);
            info.close();
            oldNotification = null;
        });
        info.open();
        oldNotification = info;
    }

    private void delete(Info land) {//TODO
        landService.deleteLand(land, user.getMail());
        String[] preference = controlService.getUserListingType(user);
        createLandData(preference[0], preference[1], drawerLayout);
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
        notification.setDuration(3000);
        Span label = new Span(land.getName() + " deleted");
        notification.add(label);
        label.getStyle().set("margin-right", "0.5rem");
        notification.open();
        updateRawData();
        createLandData(controlService.getUserListingType(user)[0],
                controlService.getUserListingType(user)[1], drawerLayout);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        int theme = settingsView.getUserPreferences()[1];
        if (theme == 1)
            settingsView.changeTheme(true);
    }

    public void createLandData(String groupType, String sortType, VerticalLayout mainLayout) {
        VerticalLayout newContent = new VerticalLayout();
        ArrayList<Info> data = new ArrayList<>(rawData);
        data = sortData(sortType, data);
        switch (groupType) {
            case "None":
                for (Info land : data) {
                    newContent.add(createDetails(land));
                }
                break;
            case "Land Type":
                int farmCount = 0;
                int realEstCount = 0;
                int govCount = 0;
                Details farmDetails = new Details();
                Details realEstDetails = new Details();
                Details govDetails = new Details();
                farmDetails.setSummaryText("Farm Land");
                realEstDetails.setSummaryText("Real Estate");
                govDetails.setSummaryText("Government");

                for (Info info : data) {
                    if (info instanceof FarmLand) {
                        farmDetails.addContent(createDetails(info));
                        farmCount++;
                    } else if (info instanceof RealEstate) {
                        realEstDetails.addContent(createDetails(info));
                        realEstCount++;
                    } else {
                        govDetails.addContent(createDetails(info));
                        govCount++;
                    }
                    if (farmCount != 0)
                        newContent.add(farmDetails);
                    if (realEstCount != 0)
                        newContent.add(realEstDetails);
                    if (govCount != 0)
                        newContent.add(govDetails);
                }

                break;
            case "Group Name":
                ArrayList<Details> createdDetails = new ArrayList<>();
                String header;
                Details detailsComponent = new Details();
                boolean notFound;
                for (Info land : data) {
                    header = land.getGroupName();
                    notFound = true;
                    for (Details details : createdDetails) {
                        if (details.getSummaryText().equals(header)) {
                            notFound = false;
                            detailsComponent = details;
                            break;
                        }
                    }
                    if (notFound) {
                        detailsComponent = new Details(header, createDetails(land));
                        createdDetails.add(detailsComponent);
                    } else {
                        detailsComponent.addContent(createDetails(land));
                    }
                }
                if (createdDetails.size() < 3)
                    createdDetails.forEach(details -> details.setOpened(true));
                createdDetails.forEach(newContent::add);
                break;
            default:
                Notification.show("An error occurred while sorting lands");
                return;
        }
        mainLayout.replace(drawerLandContent, newContent);
        drawerLandContent = newContent;
    }

    public void createLandData(String groupType, String sortType, VerticalLayout mainLayout, String filterText) {
        if (filterText.equals("")) {
            createLandData(groupType, sortType, mainLayout);
            return;
        }
        VerticalLayout newContent = new VerticalLayout();
        String preference = settingsView.getSearchBarPreference();

        ArrayList<Info> filteredNameData = null;
        HashMap<String, ArrayList<Info>> filteredGroupNameData = null;
        if (groupType.equals("Group Name")) {
            ArrayList<Details> groupDetailsList = new ArrayList<>();
            boolean nameSearchEmpty = true;
            boolean groupNameSearchEmpty = true;
            if (!preference.equals("Always search by name")) {
                ArrayList<Info> data = new ArrayList<>(rawData);
                data = sortData(sortType, data);
                filteredGroupNameData = getGroupNameSearchResults(createGroupNameMap(data), filterText);
                if (!filteredGroupNameData.isEmpty()) {
                    filteredGroupNameData.forEach((groupName, list) -> {
                        groupDetailsList.add(new ComparableDetails());
                        groupDetailsList.get(groupDetailsList.size() - 1).setSummaryText(groupName);
                        list.forEach(e -> groupDetailsList.get(groupDetailsList.size() - 1).addContent(createDetails(e)));
                    });
                    if (groupDetailsList.size() < 3)
                        groupDetailsList.forEach(details -> details.setOpened(true));
                    groupDetailsList.sort(null);
                    groupNameSearchEmpty = false;
                }
            }
            if (!preference.equals("Always search by group name")) {
                filteredNameData = getSearchResults(createNameMap(rawData), filterText);
                filteredNameData = sortData(sortType, filteredNameData);
                if (!filteredNameData.isEmpty()) {
                    nameSearchEmpty = false;
                }
            }
            switch (preference) {
                case "Always search by name":
                    if (nameSearchEmpty) {
                        newContent.add(new Text("No results..."));
                    } else {
                        for (Info land : filteredNameData) {
                            newContent.add(createDetails(land));
                        }
                    }
                    break;
                case "Always search by group name":
                    if (groupNameSearchEmpty) {
                        newContent.add(new Text("No results..."));
                    } else {
                        groupDetailsList.forEach(newContent::add);
                    }
                    break;
                case "Prioritize searching by land name":
                    if (nameSearchEmpty) {
                        if (groupNameSearchEmpty) {
                            newContent.add(new Text("No results..."));
                        } else {
                            groupDetailsList.forEach(newContent::add);
                        }
                    } else {
                        for (Info land : filteredNameData) {
                            newContent.add(createDetails(land));
                        }
                    }
                    break;
                default:
                    if (groupNameSearchEmpty) {
                        if (nameSearchEmpty) {
                            newContent.add(new Text("No results..."));
                        } else {
                            for (Info land : filteredNameData) {
                                newContent.add(createDetails(land));
                            }
                        }
                    } else {
                        groupDetailsList.forEach(newContent::add);
                    }
                    break;
            }

        } else {
            filteredNameData = getSearchResults(createNameMap(rawData), filterText);
            filteredNameData = sortData(sortType, filteredNameData);
            if (filteredNameData.isEmpty()) {
                newContent.add(new Text("No results..."));
            } else {
                for (Info land : filteredNameData) {
                    newContent.add(createDetails(land));
                }
            }
        }
        mainLayout.replace(drawerLandContent, newContent);
        drawerLandContent = newContent;
    }

    private ArrayList<Info> getSearchResults(HashMap<String, Info> map, String filterText) {
        ArrayList<Info> result = new ArrayList<>();
        map.forEach((str, obj) -> {
            if (StringUtils.containsIgnoreCase(str, filterText)) {
                result.add(obj);
            }
        });
        return result;
    }

    private HashMap<String, ArrayList<Info>> getGroupNameSearchResults(HashMap<String, ArrayList<Info>> map, String filterText) {
        HashMap<String, ArrayList<Info>> filteredMap = new HashMap<>();
        map.forEach((str, obj) -> {
            if (StringUtils.containsIgnoreCase(str, filterText)) {
                filteredMap.put(str, obj);
            }
        });
        return filteredMap;
    }

    private HashMap<String, ArrayList<Info>> createGroupNameMap(ArrayList<Info> data) {
        HashMap<String, ArrayList<Info>> result = new HashMap<>();
        String header;
        ArrayList<Info> hList;
        for (Info land : data) {
            header = land.getGroupName();
            if (result.containsKey(header)) {
                result.get(header).add(land);
            } else {
                hList = new ArrayList<>();
                hList.add(land);
                result.put(header, hList);
            }
        }
        return result;
    }

    private HashMap<String, Info> createNameMap(ArrayList<Info> data) {
        HashMap<String, Info> result = new HashMap<>();
        data.forEach(e -> {
            result.put(e.getName(), e);
        });
        return result;
    }

    private void sendAllDataToJs() {
        //getMapPage().executeJs("Draw();");
        ArrayList<Double> cords;
        String name;
        StringBuffer strCords;
        Page page = getMapPage();
        for (Info i : rawData) {
            name = i.getName();
            cords = i.getCoordinates();
            strCords = new StringBuffer();
            StringBuffer lastStr = new StringBuffer("[" + cords.get(0) + "," + cords.get(1) + "]");
            for (int j = 0; j < cords.size(); j += 2) {
                strCords.append("[" + cords.get(j) + "," + cords.get(j + 1) + "],");
            }
            strCords.append(lastStr);
            System.out.println("addShapes('" + name + "',[" + strCords.toString() + "]);");
            mapboxView.map.page.executeJs("addShapes('" + name + "',[" + strCords.toString() + "]);");
        }
    }


    public void updateRawData() {
        rawData = landService.getAllLands(user.getMail());
    }

    public ArrayList<Double> foundCordData = new ArrayList<>();


    private void saveMapCords() {
        PendingJavaScriptResult cordResult = mapboxView.map.page.executeJs("return getCenterCords();");
        cordResult.then(String.class, e -> getCordsFromStr(e));
        PendingJavaScriptResult zoomResult = mapboxView.map.page.executeJs("return getZoomLevel();");
        zoomResult.then(String.class, e -> mapCoordinates[2] = Double.parseDouble(e));
    }

    private void getCordsFromStr(String str) {
        int firstP = 0;
        int lastP = 0;
        int comma = 0;
        for (int i = 3; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                firstP = i;
            } else if (str.charAt(i) == ',') {
                comma = i;
            } else if (str.charAt(i) == ')') {
                lastP = i;
            }
        }
        mapCoordinates[0] = Double.parseDouble(str.substring(firstP + 1, comma));
        mapCoordinates[1] = Double.parseDouble(str.substring(comma + 1, lastP));

    }

    public ArrayList<Double> getAndSaveCordDataFromJs(String description, String name, String color, String groupName, boolean isGovernment) {
        mapboxView.map.page.executeJs("save();");
        PendingJavaScriptResult cordResult = mapboxView.map.page.executeJs("return returnCordsLists();");

        ArrayList<Double> cordData = new ArrayList<>();
        cordResult.then(String.class, e -> {
            addLand(cordData, e, description, name, color, groupName, isGovernment);
        });
        return cordData;
    }

    public ArrayList<Double> getAndSaveCordDataFromJs(String description, String name, String color, String groupName, int aptNo,
                                                      int floorNo, String type) {
        mapboxView.map.page.executeJs("save();");
        PendingJavaScriptResult cordResult = mapboxView.map.page.executeJs("return returnCordsLists();");

        ArrayList<Double> cordData = new ArrayList<>();
        cordResult.then(String.class, e -> {
            addLand(cordData, e, description, name, color, groupName, aptNo, floorNo, type);
            System.out.println("Initial cord list" + e);
        });
        return cordData;
    }


    private void addLand(ArrayList<Double> cordData, String str, String description, String name, String color, String groupName, int aptNo,
                         int floorNo, String type) {
        String[] cordsStrList = str.substring(1, str.length() - 2).split("],");
        ArrayList<Double> result = new ArrayList<>();
        System.out.println("Cords list  " + cordsStrList[0] + " " + cordsStrList[1]);
        for (String cords : cordsStrList) {
            for (int i = 1; i < cords.length(); i++) {
                if (cords.charAt(i) == ',') {
                    result.add(Double.parseDouble(cords.substring(1, i)));
                    result.add(Double.parseDouble(cords.substring(i + 1, cords.length())));
                    break;
                }
            }
        }
        foundCordData = result;
        controlService.addRealEstate(user.getMail(), foundCordData, description, name, color, groupName, aptNo, floorNo, type);
        updateRawData();
        createLandData(controlService.getUserListingType(user)[0],
                controlService.getUserListingType(user)[1], drawerLayout);
    }

    private void addLand(ArrayList<Double> cordData, String str, String description, String name, String color,
                         String groupName, boolean isGovernment) {
        String[] cordsStrList = str.substring(1, str.length() - 2).split("],");
        ArrayList<Double> result = new ArrayList<>();
        for (String cords : cordsStrList) {
            for (int i = 1; i < cords.length(); i++) {
                if (cords.charAt(i) == ',') {
                    result.add(Double.parseDouble(cords.substring(1, i)));
                    result.add(Double.parseDouble(cords.substring(i + 1, cords.length())));
                    break;
                }
            }
        }
        foundCordData = result;
        if (isGovernment)
            controlService.addGovernment(user.getMail(), foundCordData, description, name, color, groupName);
        else
            controlService.addFarmLand(user.getMail(), foundCordData, description, name, color, groupName);
        updateRawData();
        createLandData(controlService.getUserListingType(user)[0],
                controlService.getUserListingType(user)[1], drawerLayout);
    }
}

