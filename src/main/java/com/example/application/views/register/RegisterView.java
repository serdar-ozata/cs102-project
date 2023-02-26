package com.example.application.views.register;


import com.example.application.data.ControlService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

@Route(value = "register")
@RouteAlias(value = "register")
@PageTitle("Register")
public class RegisterView extends Composite {
    private final Notification mailEmptyError = new Notification("Enter a Mail", 3000);
    private final Notification nameEmptyError = new Notification("Enter a Username", 3000);
    private final Notification passEmptyError = new Notification("Enter a Password", 3000);
    private final Notification passDontMatchError = new Notification("Passwords Don't Match", 3000);
    private final Notification noDescription = new Notification("Enter a Description", 3000);
    private final Notification invalidUserType = new Notification("You may only select Farmer,Real Estate and Government", 3000);
    private final Notification userAdded = new Notification("User has been added", 3000);
    ControlService controlService = new ControlService();

    @Override
    protected Component initContent() {
        TextField mail = new TextField("Mail");
        mail.setRequired(true);
        TextField userName = new TextField("Username");
        userName.setRequired(true);
        PasswordField password1 = new PasswordField("Password");
        password1.setRequired(true);
        password1.setMinLength(6);
        password1.setErrorMessage("Password should contain at least 6 characters");
        PasswordField password2 = new PasswordField("Confirm password");
        password2.setRequired(true);
        TextField description = new TextField("Description");
        description.setRequired(true);
        Label titleLabel = new Label("Register");

        setNotifications();

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);


        titleLabel.getStyle().set("font-family", "Arial");
        titleLabel.getStyle().set("font-size", "1.5em");

        ComboBox<String> userTypeBox = new ComboBox<>();
        userTypeBox.setItems("Farmer", "Real Estate", "Government");
        userTypeBox.setLabel("User Type");

        layout.add(titleLabel,
                mail,
                userName,
                password1,
                password2,
                description,
                userTypeBox,
                new Button("Send", event -> register(
                        mail.getValue(),
                        userName.getValue(),
                        password1.getValue(),
                        password2.getValue(),
                        description.getValue(),
                        userTypeBox.getValue()
                ))
        );
        return layout;

    }

    private void setNotifications() {
        mailEmptyError.setThemeName("error");
        nameEmptyError.setThemeName("error");
        passEmptyError.setThemeName("error");
        passDontMatchError.setThemeName("error");
        noDescription.setThemeName("error");
        invalidUserType.setThemeName("error");
        userAdded.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void register(String userMail, String userName, String password1, String password2, String description, String userType) {
        if (userMail.isEmpty()) {
            mailEmptyError.open();
        } else if (userName.trim().isEmpty()) {
            nameEmptyError.open();
        } else if (password1.isEmpty()) {
            passEmptyError.open();
        } else if (!password1.equals(password2)) {
            passDontMatchError.open();
        } else if (description.trim().isEmpty()) {
            noDescription.open();
        } else if (!(userType.equals("Farmer") || userType.equals("Real Estate") || userType.equals("Government"))) {
            invalidUserType.open();
        }
        else {
            controlService.addUser(userMail, userName, password1, userTypeAsInt(userType), description);
            userAdded.open();
            UI.getCurrent().navigate("login");
        }
    }

    // Temporarily and arbitrarily given values. Not true.
    private int userTypeAsInt(String userType) {
        if (userType.equals("Farmer"))
            return 1;
        else if (userType.equals("Real Estate"))
            return 2;
        return 3;
    }
}

