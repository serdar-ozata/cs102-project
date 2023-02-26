package com.example.application.views.mapview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import database.DataSource;
import database.assets.Info;

import java.awt.*;

public class InfoNotification extends Notification {
    private final VerticalLayout layout = new VerticalLayout();
    Button navigateInfoView = new Button("Expand");

    public InfoNotification(Info land) {
        super();

        TextField text1 = new TextField("Owner Name");
        text1.setValue(""+ land.getOwner().getName() );
        text1.setReadOnly(true);

        TextField text2 = new TextField("Land Type");
        if(land.getClass().getSimpleName().endsWith("d")) {
            text2.setValue("Farm Land");
        }
        else {
            text2.setValue(land.getClass().getSimpleName());
        }
        text2.setReadOnly(true);

        TextField text3 = new TextField("Description");
        text3.setValue(land.getDescription());
        text3.setReadOnly(true);

        TextField text4 = new TextField("Group Name");
        text4.setValue(land.getGroupName());
        text4.setReadOnly(true);

        HorizontalLayout horLayout = new HorizontalLayout();
        horLayout.add(text1,text2);
        HorizontalLayout horLayout2 = new HorizontalLayout();
        horLayout2.add(text3,text4);

        layout.add(horLayout,horLayout2);
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.getElement().getStyle().set("margin-left", "auto");

        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE));
        closeButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        closeButton.addClickListener(buttonClickEvent -> this.close());
        buttonLayout.add(navigateInfoView,closeButton);
        layout.add(buttonLayout);
        add(layout);
        setDuration(0);
        //isim type des
    }

    protected Button getInfoButton() {
        return navigateInfoView;
    }
}