package database.assets;

import database.Person;
import database.assets.Info;

import java.util.ArrayList;

public class Government extends Info {

    public Government(Person owner, String description, String name, String color, String groupName, ArrayList coordinates, double[] centerCords) {
        super(owner, description, name, color, groupName, coordinates, centerCords);
    }

    @Override
    public String toString() {
        return "Government{" +
                "government=" + super.getName() +
                '}';
    }

    @Override
    public void setX(int newX) {

    }

    @Override
    public void setY(int newY) {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setAddress(String newAddress) {

    }

    @Override
    public String getAddress() {
        return null;
    }
}
