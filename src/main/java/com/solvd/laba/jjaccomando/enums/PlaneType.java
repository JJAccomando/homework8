package com.solvd.laba.jjaccomando.enums;

public enum PlaneType {
    A380(16, 4, 90, 
    6, 410, 10, 518, "Airbus", "A380"),
    A320(14, 2, 20, 
    4, 112, 7, 146, "Airbus", "A320"),
    B737(4, 2, 12, 
    4, 102, 6, 118, "Boeing", "737");

    public final int seatsInFirst;
    public final int seatsInBusiness;
    public final int seatsInEcon;
    public final int totalSeats;
    public final int numColumnsFirst;
    public final int numColumnsBusiness;
    public final int numColumnsEcon;

    public final String company;
    public final String classification;

    PlaneType(int seatsInFirst, int numColumnFirst, int seatsInBusiness, int numColumnBus, 
    int seatsInEcon, int numColumnEcon, int totalSeats, String company, String classification) {
        this.seatsInFirst = seatsInFirst;
        this.seatsInBusiness = seatsInBusiness;
        this.seatsInEcon = seatsInEcon;
        this.totalSeats = totalSeats;
        this.company = company;
        this.classification = classification;
        this.numColumnsFirst = numColumnFirst;
        this.numColumnsBusiness = numColumnBus;
        this.numColumnsEcon = numColumnEcon;
    }

}
