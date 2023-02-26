package com.example.application.views.login;

import com.example.application.data.ControlService;
import com.example.application.views.register.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.RouterLink;

@Route(value = "login")
@RouteAlias(value = "")
@PageTitle("Login")
@CssImport("./views/login/login-view.css")
public class LoginView extends Div {
    private final ControlService controlService = new ControlService();
    VerticalLayout layout = new VerticalLayout();

    public LoginView() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        addClassName("login-view");

        LoginForm loginForm = new LoginForm();
        verticalLayout.add(loginForm, new RouterLink("Register", RegisterView.class));
        add(verticalLayout);
        loginForm.addLoginListener(e -> {
            if (controlService.isLoggedIn()) {
                //doesnt work for now
                ConfirmDialog dialog = new ConfirmDialog("Already Logged In",
                        "You are logged in do you want to go to main page?",
                        "Go to Main Page", this::navigate,
                        "Cancel", cancelEvent -> {
                });
                dialog.open();
            } else {
                if (controlService.isAuthorized(e)) {
                    UI.getCurrent().navigate("map");
                } else {
                    loginForm.setError(true);
                }
            }

        });
    }

    private void navigate(ConfirmDialog.ConfirmEvent confirmEvent) {
        UI.getCurrent().navigate("map");
    }


}
