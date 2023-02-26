package com.example.application.views.logout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
@Route(value = "logout")
public class LogoutView extends Div {
    public LogoutView(){
        UI.getCurrent().getPage().setLocation("login");
        VaadinSession.getCurrent().getSession().invalidate();
        VaadinSession.getCurrent().close();
    }
}
