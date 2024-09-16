package com.solvd.laba.jjaccomando.enums;

public enum PlaneType {
    A380(16, 4, 90, 
    6, 410, 10, 518, "Airbus", "A380"),
    A320(14, 2, 20, 
    4, 112, 7, 146, "Airbus", "A320"),
    B737(4, 2, 12, 
    4, 102, 6, 118, "Boeing", "737");

    public final int SEATS_IN_FIRST;
    public final int SEATS_IN_BUSINESS;
    public final int SEATS_IN_ECON;
    public final int TOTAL_SEATS;
    public final int NUM_COLUMNS_FIRST;
    public final int NUM_COLUMNS_BUSINESS;
    public final int NUM_COLUMNS_ECON;

    public final String COMPANY;
    public final String CLASSIFICATION;

    PlaneType(int seatsInFirst, int numColumnFirst, int seatsInBusiness, int numColumnBus, 
    int seatsInEcon, int numColumnEcon, int totalSeats, String company, String classification) {
        this.SEATS_IN_FIRST = seatsInFirst;
        this.SEATS_IN_BUSINESS = seatsInBusiness;
        this.SEATS_IN_ECON = seatsInEcon;
        this.TOTAL_SEATS = totalSeats;
        this.COMPANY = company;
        this.CLASSIFICATION = classification;
        this.NUM_COLUMNS_FIRST = numColumnFirst;
        this.NUM_COLUMNS_BUSINESS = numColumnBus;
        this.NUM_COLUMNS_ECON = numColumnEcon;
    }

}
