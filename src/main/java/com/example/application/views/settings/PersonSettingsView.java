package com.example.application.views.settings;


import com.example.application.data.ControlService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;
import database.Person;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;


public class PersonSettingsView extends Div {
    private final ControlService controlService = new ControlService();
    private Person user = VaadinSession.getCurrent().getAttribute(Person.class);

    public PersonSettingsView() {
        HorizontalLayout horLayout1 = new HorizontalLayout();

        // Name
        TextField tf1 = new TextField("Your old name");
        tf1.setValue(user.getName());
        tf1.setReadOnly(true);
        TextField tf2 = new TextField("Your new name");
        Button nameButton = new Button("Apply", buttonClickEvent -> {
            if( !tf2.getValue().trim().isEmpty() ){
                controlService.updatePersonName(user.getMail(),tf2.getValue());
                user.setName(tf2.getValue());
                tf1.setValue(user.getName());
                Notification nameChanged = new Notification("Name has changed to "+ tf2.getValue(), 2000);
                nameChanged.open();
                //Page.getCurrent().reload();
                //UI.getCurrent().getPage().reload();
            }
            else {
                Notification nameErrorNotification = new Notification("Enter valid name", 2000);
                nameErrorNotification.setThemeName("error");
                nameErrorNotification.open();
            }
        });
        horLayout1.setAlignItems(FlexComponent.Alignment.BASELINE);
        horLayout1.setPadding(true);
        horLayout1.add(tf1,tf2,nameButton);

        // Password
        HorizontalLayout horLayout2 = new HorizontalLayout();
        TextField tf3 = new TextField("Your old password");
        TextField tf4 = new TextField("Your new password");
        TextField tf5 = new TextField("Your new password confirmed");
        Button passwordButton = new Button("Apply", buttonClickEvent -> {
            if( !tf3.getValue().equals(user.getPassword()) ){
                Notification passwordWrong = new Notification("You guessed your old password wrong", 2000);
                passwordWrong.setThemeName("error");
                passwordWrong.open();
            }
            else{
                if( tf4.getValue().trim().isEmpty() ){
                    Notification invalidProposedPassword = new Notification("Proposed password is invalid", 2000);
                    invalidProposedPassword.setThemeName("error");
                    invalidProposedPassword.open();
                }
                else{
                    if( tf4.getValue().equals(tf5.getValue()) ){
                        controlService.updatePersonPassword(user.getMail(), tf4.getValue());
                        Notification passwordChanged = new Notification("Password has been changed", 2000);
                        passwordChanged.open();
                    }

                    else{
                        Notification notification = new Notification("Confirmation does not match with the password",2000);
                        notification.setThemeName("error");
                        notification.open();
                    }
                }

            }
        });
        horLayout2.setAlignItems(FlexComponent.Alignment.BASELINE);
        horLayout2.setPadding(true);
        horLayout2.add(tf3,tf4,tf5,passwordButton);


        // Description
        HorizontalLayout horLayout3 = new HorizontalLayout();
        TextField tf6 = new TextField("Your old description");
        tf6.setValue(user.getDescription());
        tf6.setReadOnly(true);
        TextField tf7 = new TextField("Your new description");
        Button descButton = new Button("Apply", buttonClickEvent -> {
            if( !tf7.getValue().trim().isEmpty() ){
                controlService.updatePersonDescription(user.getMail(),tf7.getValue());
                user.setDescription(tf7.getValue());
                tf6.setValue(user.getDescription());
            }
            else {
                Notification descErrorNotification = new Notification("Enter valid description", 2000);
                descErrorNotification.setThemeName("error");
                descErrorNotification.open();
            }

        });
        horLayout3.setAlignItems(FlexComponent.Alignment.BASELINE);
        horLayout3.setPadding(true);
        horLayout3.add(tf6,tf7,descButton);


        VerticalLayout verLayout4 = new VerticalLayout();
        verLayout4.setPadding(true);
        verLayout4.add(horLayout1,horLayout2,horLayout3);
        add(verLayout4);


    }

}