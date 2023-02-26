package database.assets;

public class Crop {
    String name;
    double yield;
    double cost;
    double revenue;
    int    year;
    int id;
    String description;

    @Override
    public String toString() {
        return "Crop{" +
                "name='" + name + '\'' +
                ", yield=" + yield +
                ", cost=" + cost +
                ", revenue=" + revenue +
                ", year=" + year +
                ", id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    public Crop(int id, String name, double yield, double cost, double revenue, int year, String description) {
        this.id = id;
        this.name = name;
        this.yield = yield;
        this.cost = cost;
        this.revenue = revenue;
        this.year = year;
        this.description = description;
    }

    public double getProfit() {
        return revenue-cost;
    }
    public String getName() {
        return name;
    }

    public double getYield() {
        return yield;
    }

    public double getCost() {
        return cost;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }
}