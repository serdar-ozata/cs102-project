package com.example.application.components.mapbox;

/**
 * Edited version of com.github.markhm.mapbox.GeoLocation slightly altered for our use case.
 */
public class GeoLocation
{
    private String name;
    private double longitude;
    private double latitude;

    public GeoLocation(String name, double longitude, double latitude)
    {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName()
    {
        return name;
    }

    public String getCoordinates()
    {
        return "[" + longitude + "," + latitude + "]";
    }

    // Map
    public static com.github.markhm.mapbox.GeoLocation Center = new com.github.markhm.mapbox.GeoLocation("World", 0, 0);

    public static com.github.markhm.mapbox.GeoLocation InitialView = new com.github.markhm.mapbox.GeoLocation("Initial view", 17.1733, 49.508);
    public static com.github.markhm.mapbox.GeoLocation InitialView_Turku_NY = new com.github.markhm.mapbox.GeoLocation("Initial view", -26, 50);

    // Europe
    public static com.github.markhm.mapbox.GeoLocation Turku = new com.github.markhm.mapbox.GeoLocation("Turku",22.266667, 60.45);

    public static com.github.markhm.mapbox.GeoLocation Paris = new com.github.markhm.mapbox.GeoLocation("Paris", 2.3522, 48.8566);
    public static com.github.markhm.mapbox.GeoLocation Amsterdam = new com.github.markhm.mapbox.GeoLocation("Amsterdam", 4.8952, 52.3702);
    public static com.github.markhm.mapbox.GeoLocation Copenhagen = new com.github.markhm.mapbox.GeoLocation("Copenhagen", 12.5683, 55.6761);
    public static com.github.markhm.mapbox.GeoLocation Madrid = new com.github.markhm.mapbox.GeoLocation("Madrid", -3.703790, 40.416775);
    public static com.github.markhm.mapbox.GeoLocation Moscow = new com.github.markhm.mapbox.GeoLocation("Moscow", 37.617298, 55.755825);

    // US
    public static com.github.markhm.mapbox.GeoLocation NewYork = new com.github.markhm.mapbox.GeoLocation("New York NY",  -73.935242, 40.730610);
    public static com.github.markhm.mapbox.GeoLocation SanFrancisco = new com.github.markhm.mapbox.GeoLocation("San Francisco", -122.414, 37.776);
    public static com.github.markhm.mapbox.GeoLocation WashingtonDC = new com.github.markhm.mapbox.GeoLocation("Washington DC", -77.032, 38.913);

}

