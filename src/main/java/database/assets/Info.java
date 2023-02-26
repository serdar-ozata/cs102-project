package database.assets;

import database.Locatable;
import database.Person;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Info implements Locatable, Comparable {
    Person owner;
    String description;
    String name;
    String color;
    String groupName;
    ArrayList<Double> coordinates = new ArrayList<>();
    double[] centerCords = new double[2];

    public Info(Person owner, String description, String name, String color, String groupName, ArrayList coordinates, double[] centerCords) {
        this.coordinates = coordinates;
        this.owner = owner;
        this.description = description;
        this.name = name;
        this.color = color;
        this.groupName = groupName;
        this.centerCords = centerCords;

    }

    @Override
    public String toString() {
        return getName();
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setCenterCords(double[] centerCords) {
        this.centerCords = centerCords;
    }

    public double[] getCenterCords() {
        return centerCords;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof Info) {
            Info otherObj = (Info) o;
            return getName().compareTo(otherObj.getName());
        }
        return 0;
    }
}
