package com.solvd.laba.jjaccomando.enums;

public enum Airport {
    CDG("Charles de Gaulle Airport", "Paris", "France", 49.0079, 2.5508),
    ZRH("Zurich Airport", "Zurich", "Switzerland", 47.4597, 8.5510),
    FRA("Frankfurt Airport", "Frankfurt", "Germany", 50.0354, 8.5518),
    LAX("Los Angeles International Airport", "Los Angeles", "United States", 33.9422, -118.4036),
    HNL("Daniel K. Inouye International Airport", "Honolulu", "United States", 21.3186, -157.9254),
    JFK("John F. Kennedy International Airport", "New York City", "United States", 40.6446, -73.7797),
    DXB("Dubai International Airport", "Dubai", "United Arab Emirates", 25.2567, 55.3643),
    LHR("London Heathrow Airport", "London", "United Kingdom", 51.4680, 0.4551),
    HND("Haneda Airport", "Tokyo", "Japan", 35.5483, 139.7780),
    DFW("Dallas Fort Worth International Airport", "Dallas", "United States", 32.8990, -97.0336);
    public final String name;
    public final String city;
    public final String country;

    public final double latitude;
    public final double longitude;

    Airport(String name, String city, String country, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCoordinates() {
        char directionLat;
        char directionLong;

        if (latitude < 0)
            directionLat = 'S';
        else
            directionLat = 'N';

        if (longitude < 0)
            directionLong = 'W';
        else
            directionLong = 'E';

        return latitude + " " + directionLat + ", " + longitude + " " + directionLong;
    }

    @Override
    public final String toString() {
        return name;
    }
}
