package com.example.application.views.mapview;


import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.server.VaadinSession;
import database.Person;

import java.awt.*;


public class EditNotification extends Notification {
    HorizontalLayout mainLayout = new HorizontalLayout();
    private HorizontalLayout firstOptionalLayout = new HorizontalLayout();
    private HorizontalLayout existingLayout = new HorizontalLayout();
    private Icon newIcon;
    private Icon oldIcon = new Icon();
    private MapboxLayout mapboxLayout;
    TextField realEstateType;
    TextField realEstateFloorNo;
    TextField realEstateAptNo;
    TextField generalGroupName;
    TextField generalName;
    TextField generalDescription;
    RadioButtonGroup<String> landType;
    String color;
    private final Person user = VaadinSession.getCurrent().getAttribute(Person.class);

    public EditNotification(MapboxLayout mapboxLayout) {
        super();
        this.mapboxLayout = mapboxLayout;
        setPosition("top-stretch");
        createFirstLine();
        //createSecondLine();

        mainLayout.add(firstOptionalLayout, existingLayout);

        add(mainLayout);
    }

    //General Properties are: Desc, name, color, groupName

    //Farmland Properties are: N/A
    //Crop properties are: name, yield, cost, revenue, year, description

    //Real Estate Properties are: type, floorNo, aptNo

    //Government: N/A

    private void createFirstLine() {

        firstOptionalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);

        // Land Type Selection
        landType = new RadioButtonGroup<>();
        landType.setLabel("Land Type");
        landType.setItems("Farm Land", "Real Estate", "Government");

        // General
        generalDescription = new TextField("Description");
        generalName = new TextField("Name");
        generalGroupName = new TextField("Group Name");

        firstOptionalLayout.add(landType, generalName, generalDescription, generalGroupName);
        switch (user.getUserType()) {
            default:
                landType.setValue("Farm Land");
                break;
            case 2:
                landType.setValue("Real Estate");
                break;
            case 3:
                landType.setValue("Government");
                break;
        }

        // Specific layouts
        landType.addValueChangeListener(e -> {
            switch (e.getValue()) {
                case "Farm Land":
                    HorizontalLayout farmlandLayout = createFarmLand();
                    firstOptionalLayout.replace(existingLayout, farmlandLayout);
                    existingLayout = farmlandLayout;
                    break;
                case "Real Estate":
                    HorizontalLayout realEstateLayout = createRealEstateLand();
                    firstOptionalLayout.replace(existingLayout, realEstateLayout);
                    existingLayout = realEstateLayout;
                    break;
                case "Government":
                    HorizontalLayout governmentLayout = createGovernmentLand();
                    firstOptionalLayout.replace(existingLayout, governmentLayout);
                    existingLayout = governmentLayout;
                    break;
            }
        });

        // Color
        newIcon = new Icon(VaadinIcon.CIRCLE);


        TextField redField = new TextField("Red");
        redField.setHelperText("0-255");
        redField.setValueChangeMode(ValueChangeMode.LAZY);

        TextField blueField = new TextField("Blue");
        blueField.setHelperText("0-255");
        blueField.setValueChangeMode(ValueChangeMode.LAZY);

        TextField greenField = new TextField("Green");
        greenField.setHelperText("0-255");
        greenField.setValueChangeMode(ValueChangeMode.LAZY);

        redField.setWidth("50px");
        blueField.setWidth("50px");
        greenField.setWidth("50px");
        newIcon.setColor("rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")");
        color = "rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")";

        redField.addValueChangeListener(e -> {
            newIcon.setColor("rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")");
            color = "rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")";
        });

        blueField.addValueChangeListener(e -> {
            newIcon.setColor("rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")");
            color = "rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")";
        });

        greenField.addValueChangeListener(e -> {
            newIcon.setColor("rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")");
            color = "rgb(" + redField.getValue() + "," + greenField.getValue() + "," + blueField.getValue() + ")";
        });


        firstOptionalLayout.add(redField, greenField, blueField, newIcon);

        switch (landType.getValue()) {
            case "Farm Land":
                HorizontalLayout farmlandLayout = createFarmLand();
                firstOptionalLayout.replace(existingLayout, farmlandLayout);
                existingLayout = farmlandLayout;
                break;
            case "Real Estate":
                HorizontalLayout realEstateLayout = createRealEstateLand();
                firstOptionalLayout.replace(existingLayout, realEstateLayout);
                existingLayout = realEstateLayout;
                break;
            case "Government":
                HorizontalLayout governmentLayout = createGovernmentLand();
                firstOptionalLayout.replace(existingLayout, governmentLayout);
                existingLayout = governmentLayout;
                break;
        }
        mainLayout.add(firstOptionalLayout);
    }

    private HorizontalLayout createFarmLand() {
        return createButtons();
    }

    private HorizontalLayout createGovernmentLand() {
        return createButtons();
    }

    private HorizontalLayout createRealEstateLand() {
        HorizontalLayout result = new HorizontalLayout();
        result.setAlignItems(FlexComponent.Alignment.BASELINE);

        //Real Estate
        realEstateType = new TextField("Type");
        realEstateFloorNo = new TextField("Floor No");
        realEstateFloorNo.setWidth("60px");
        realEstateAptNo = new TextField("Apt No");
        realEstateAptNo.setWidth("60px");

        result.add(realEstateType, realEstateFloorNo, realEstateAptNo, createButtons());
        return result;
    }


    /**
     * bu method bütün bilgileri alıp control serviceten add methodlarını çağıracak
     */
    private void saveLand() {
        switch (landType.getValue()) {
            case "Farm Land":
                mapboxLayout.getAndSaveCordDataFromJs(generalDescription.getValue(), generalName.getValue(),
                        color, generalGroupName.getValue(),false);
                break;
            case "Real Estate":
                mapboxLayout.getAndSaveCordDataFromJs(generalDescription.getValue(), generalName.getValue(),
                        color, generalGroupName.getValue(), Integer.parseInt(realEstateAptNo.getValue()),
                        Integer.parseInt(realEstateFloorNo.getValue()), realEstateType.getValue());
                break;
            case "Government":
                mapboxLayout.getAndSaveCordDataFromJs(generalDescription.getValue(), generalName.getValue(),
                        color, generalGroupName.getValue(), true);
                break;
        }


    }

    private Button createSaveButton() {
        Button saveButton = new Button(new Icon(VaadinIcon.CHECK));
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        saveButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        saveButton.addClickListener(e -> {
            saveLand();
            close();
        });
        return saveButton;
    }

    private Button createCloseButton() {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Discard");
        String textHtml = "<p>Are you sure you want to <b>discard</b> the item?</p>";
        dialog.setText(new Html(textHtml).getElement());
        Button rejectButton = new Button("Discard", VaadinIcon.TRASH.create());
        rejectButton.addClickListener(e -> {
            dialog.close();
            close();
        });
        rejectButton.getElement().setAttribute("theme", "error tertiary");
        dialog.setConfirmButton(rejectButton.getElement());
        dialog.setCancelButton("Cancel", e -> dialog.close());
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        closeButton.addClickListener(buttonClickEvent -> dialog.open());
        return closeButton;
    }

    private HorizontalLayout createButtons() {
        HorizontalLayout result = new HorizontalLayout();
        result.setAlignItems(FlexComponent.Alignment.BASELINE);

        Button closeButton = createCloseButton();
        Button saveButton = createSaveButton();

        result.add(closeButton, saveButton);
        return result;
    }

}