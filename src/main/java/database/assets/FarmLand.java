package database.assets;

import database.Person;
import database.assets.Info;

import java.util.ArrayList;

public class FarmLand extends Info {
    private int currentCrop;
    private String name;

    public FarmLand(Person owner, String description, String name, String color, String groupName, int currentCrop, ArrayList coordinates, double[] centerCords) {
        super(owner, description, name, color, groupName, coordinates, centerCords);
        this.name = name;
        this.currentCrop = currentCrop;
    }

    @Override
    public String toString() {
        return "FarmLand{" +
                "currentCrop=" + currentCrop +
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
