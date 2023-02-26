package database.assets;

import database.Person;
import database.assets.Info;

import java.util.ArrayList;

public class RealEstate extends Info {
    private String type;
    private int floorNo;
    private int aptNo;

    @Override
    public String toString() {
        return "RealEstate{" +
                ", type='" + type + '\'' +
                ", floorNo=" + floorNo +
                ", aptNo=" + aptNo +
                '}';
    }

    public RealEstate(Person owner, String type, int floorNo, int aptNo, String description, String name, String color, String groupName, ArrayList coordinates, double[] centerCords) {
        super(owner, description, name, color, groupName, coordinates, centerCords);
        this.type = type;
        this.floorNo = floorNo;
        this.aptNo = aptNo;
    }

    public int getAptNo() {
        return aptNo;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public String getType() {
        return type;
    }

    public void setAptNo(int aptNo) {
        this.aptNo = aptNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public void setType(String type) {
        this.type = type;
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