package com.example.application.views.mapview;


import com.example.application.data.ControlService;
import com.example.application.data.LandService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import database.Person;
import database.assets.FarmLand;
import database.assets.Government;
import database.assets.Info;
import database.assets.RealEstate;

/**
 * When user wants to add a land, an instance of this class is created and
 * it allows user to set the properties of the land except coordinates.
 * Also user can save or cancel the land creation by using buttons.
 */
public class EditDialog extends ConfirmDialog {
    VerticalLayout mainLayout = new VerticalLayout();
    LandService landService = new LandService();
    private final Person user = VaadinSession.getCurrent().getAttribute(Person.class);

    public EditDialog(Info land, MapboxLayout mapboxLayout) {
        setHeader("Edit: " + land.getName());
        Button saveButton = new Button("Save", VaadinIcon.ENVELOPE_OPEN.create());
        saveButton.addClickListener(e -> {
            close();
        });
        saveButton.getElement().setAttribute("theme", "primary");
        setConfirmButton(saveButton.getElement());
        setCancelButton("Cancel", this::onCancel);


        HorizontalLayout firstLayout = new HorizontalLayout();
        TextField color = new TextField("Color");
        color.setValue(land.getColor());
        TextField groupName = new TextField("Group Name");
        groupName.setValue(land.getGroupName());
        firstLayout.add(color, groupName);

        TextField description = new TextField("Description");
        description.setValue(land.getDescription());
        description.setWidth("400px");
        mainLayout.add(firstLayout, description);

        if (land instanceof FarmLand) {
            FarmLand farmLand = (FarmLand) land;
            CropDialog cropDialog = new CropDialog(farmLand, mapboxLayout);
            setRejectButtonTheme("primary");
            setRejectButton("Add Crop", e -> {
                cropDialog.open();
            });
            saveButton.addClickListener(e -> {
                landService.updateFarmLand(user.getMail(), land.getName(), description.getValue(), groupName.getValue());
                farmLand.setGroupName(groupName.getValue());
                farmLand.setDescription(description.getValue());
                mapboxLayout.updateRawData();
            });
        } else if (land instanceof RealEstate) {
            RealEstate realEstate = (RealEstate) land;
            HorizontalLayout secondLayout = new HorizontalLayout();
            TextField type = new TextField("Type");
            type.setValue(realEstate.getType());
            TextField apartmentNo = new TextField("Apartment No");
            apartmentNo.setValue(Integer.toString(realEstate.getAptNo()));
            TextField floorNo = new TextField("Floor No");
            floorNo.setValue(Integer.toString(realEstate.getFloorNo()));
            secondLayout.add(apartmentNo, type);
            HorizontalLayout thirdLayout = new HorizontalLayout();
            thirdLayout.add(floorNo);
            mainLayout.add(secondLayout, thirdLayout);
            saveButton.addClickListener(e -> {
                landService.updateRealEstate(user.getMail(), land.getName(), description.getValue(), groupName.getValue(), type.getValue()
                        , Integer.valueOf(apartmentNo.getValue()), Integer.valueOf(floorNo.getValue()));
                realEstate.setGroupName(groupName.getValue());
                realEstate.setDescription(description.getValue());
                realEstate.setAptNo(Integer.valueOf(apartmentNo.getValue()));
                realEstate.setFloorNo(Integer.valueOf(floorNo.getValue()));
                mapboxLayout.updateRawData();
            });
        } else if (land instanceof Government) {
            Government government = (Government) land;
            saveButton.addClickListener(e -> {
                landService.updateGovernment(user.getMail(), land.getName(), description.getValue(), groupName.getValue());
                government.setGroupName(groupName.getValue());
                government.setDescription(description.getValue());
                mapboxLayout.updateRawData();
            });
        }
        add(mainLayout);
    }

    private void onCancel(CancelEvent cancelEvent) {
        close();
    }
}
