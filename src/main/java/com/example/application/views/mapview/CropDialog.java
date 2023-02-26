package com.example.application.views.mapview;

import com.example.application.data.LandService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import database.Person;
import database.assets.FarmLand;
import database.assets.Info;

/**
 * Adds a crop to a specific farmland. The configuration of the crop can also be made inside of this class's interface
 */
public class CropDialog extends ConfirmDialog {
    VerticalLayout mainLayout = new VerticalLayout();
    LandService landService = new LandService();
    private final Person user = VaadinSession.getCurrent().getAttribute(Person.class);

    public CropDialog(FarmLand land, MapboxLayout mapboxLayout) {
        setHeader("Add Crop to " + land.getName());
        Button saveButton = new Button("Save", VaadinIcon.ENVELOPE_OPEN.create());
        saveButton.getElement().setAttribute("theme", "primary");
        setConfirmButton(saveButton.getElement());
        setCancelButton("Cancel", this::onCancel);


        HorizontalLayout firstLayout = new HorizontalLayout();
        TextField name = new TextField("Name");
        TextField year = new TextField("Year");
        firstLayout.add(name, year);

        HorizontalLayout secondLayout = new HorizontalLayout();
        TextField yield = new TextField("Yield");
        TextField cost = new TextField("Cost");
        secondLayout.add(yield, cost);

        HorizontalLayout thirdLayout = new HorizontalLayout();
        TextField revenue = new TextField("Revenue");
        thirdLayout.add(revenue);

        TextField description = new TextField("Description");
        description.setValue(land.getDescription());
        description.setWidth("400px");
        mainLayout.add(firstLayout, secondLayout, thirdLayout, description);
        saveButton.addClickListener(e -> {
            close();
            landService.addCrop(land, user.getMail(), name.getValue(), Double.parseDouble(yield.getValue()),
                    Double.parseDouble(cost.getValue()), Double.parseDouble(revenue.getValue()), Integer.valueOf(year.getValue()),
                    description.getValue());
        });
        add(mainLayout);
    }

    private void onCancel(CancelEvent cancelEvent) {

    }
}