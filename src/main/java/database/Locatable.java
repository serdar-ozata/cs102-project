package database;

public interface Locatable {
    int x = 0;
    int y = 0;
    String address = "";

    void setX(int newX);
    void setY(int newY);
    int getX();
    int getY();
    void setAddress(String newAddress);
    String getAddress();
}