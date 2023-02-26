package com.example.application.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import com.example.application.views.mapview.MapboxLayout;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import database.DataSource;
import com.vaadin.flow.component.login.AbstractLogin;
import database.Person;
import org.springframework.stereotype.Service;

/**
 * Allows interactions between views and database for user and links
 */
@Service
public class ControlService implements Serializable {

    private final DataSource dataSource = new DataSource();
    private String mail;
    private boolean loggedIn = false;

    public boolean isAuthorized(AbstractLogin.LoginEvent e) {
        if (dataSource.checkPassword(e.getUsername(), e.getPassword())) {
            mail = e.getUsername();
            createRoutes();
            VaadinSession.getCurrent().setAttribute(Person.class, dataSource.getPerson(mail));
            return true;
        }
        return false;
    }


    private void createRoutes() {
        RouteConfiguration.forSessionScope().setRoute("map", MapboxLayout.class);
    }

    public void setUserListingType(Person person, int type) {
        dataSource.updatePersonListingType(person.getMail(), type);
    }

    public boolean addUser(String mail, String name, String password, int userType, String description) {
        return dataSource.addPerson(mail, name, password, userType, description, 2, 0);
    }

    public String[] getUserListingType(Person person) {
        int num = dataSource.getListingType(person.getMail());
        String secondOption;
        String firstOption;
        if (num > 2) {
            num -= 3;
            secondOption = "Alphabetical";
        } else {
            secondOption = "Default";
        }
        switch (num) {
            case 1:
                firstOption = "Land Type";
                break;
            case 2:
                firstOption = "Group Name";
                break;
            default:
                firstOption = "None";
                break;
        }
        String[] result = new String[2];
        result[0] = firstOption;
        result[1] = secondOption;
        return result;
    }


    public void setBooleanUserPreferences(int num, Person user) {
        dataSource.updateBooleanInfo(user.getMail(), num);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public boolean updatePersonDescription(String mail, String newDescription) {
        return dataSource.updatePersonDescription(mail, newDescription);
    }

    public boolean updatePersonName(String mail, String newName) {
        return dataSource.updatePersonName(mail, newName);
    }

    public boolean updatePersonPassword(String mail, String newPassword) {
        return dataSource.updatePersonPassword(mail, newPassword);
    }

    public void addRealEstate(String mail, ArrayList<Double> cords, String description, String name, String color, String groupName,
                              int aptNo, int floorNo, String type) {
        double x = 0;
        double y = 0;
        for (int i = 0; i < cords.size(); i += 2) {
            x += cords.get(i);
            y += cords.get(i + 1);
        }
        x /= cords.size() / 2;
        y /= cords.size() / 2;
        dataSource.addRealEstate(name, mail, floorNo, aptNo, type, description, color, groupName, cords, new double[]{x, y});
    }

    public void addFarmLand(String mail, ArrayList<Double> cords, String description, String name, String color, String groupName) {
        double x = 0;
        double y = 0;
        for (int i = 0; i < cords.size(); i += 2) {
            x += cords.get(i);
            y += cords.get(i + 1);
        }
        x /= cords.size() / 2;
        y /= cords.size() / 2;
        dataSource.addFarmland(mail, description, name, color, groupName, cords, new double[]{x, y});
    }

    public void addGovernment(String mail, ArrayList<Double> cords, String description, String name, String color, String groupName) {
        double x = 0;
        double y = 0;
        for (int i = 0; i < cords.size(); i += 2) {
            x += cords.get(i);
            y += cords.get(i + 1);
        }
        x /= cords.size() / 2;
        y /= cords.size() / 2;
        dataSource.addGovernment(name, mail, description, color, groupName, cords, new double[]{x, y});
    }
}