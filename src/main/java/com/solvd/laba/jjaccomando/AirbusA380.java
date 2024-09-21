package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.exceptions.DuplicateBookingException;
import com.solvd.laba.jjaccomando.enums.*;

public final class AirbusA380 extends AirplaneBase {

    private final int id;
    private static int numA380 = 0;
    private final Seat[] firstClassSeats, businessClassSeats, economyClassSeats;
    private static final PlaneType PLANE_TYPE = PlaneType.A380;
    
    //AirbusA380 Object constructor
    public AirbusA380() {
        ++numA380;
        this.id = getTotalPlanes();
        this.firstClassSeats = new Seat[PLANE_TYPE.seatsInFirst];
        this.businessClassSeats = new Seat[PLANE_TYPE.seatsInBusiness];
        this.economyClassSeats = new Seat[PLANE_TYPE.seatsInEcon];
        this.populateSeats();
        addToMap(this);
    }

    //private method used in constructor that assigns Seat arrays with Seat Objects
    //each Seat array will completely fill their capacity with Seat Objects
    //each Seat Object will contain a Seat number and letter 
    //Seat number is based on rows and Seat letter is based on columns
    //number of rows and columns is based on PlaneType
    private void populateSeats() {
        int totalRowFirst = (PLANE_TYPE.seatsInFirst / PLANE_TYPE.numColumnsFirst);
        int totalRowBus = (PLANE_TYPE.seatsInBusiness / PLANE_TYPE.numColumnsBusiness);
        int totalRowEcon = (PLANE_TYPE.seatsInEcon / PLANE_TYPE.numColumnsEcon);

        populateSeatsHelper(SeatType.FIRST_CLASS, firstClassSeats, totalRowFirst,
                PLANE_TYPE.numColumnsFirst, 1);
        populateSeatsHelper(SeatType.BUSINESS_CLASS, businessClassSeats, totalRowBus,
                PLANE_TYPE.numColumnsBusiness, totalRowFirst + 1);
        populateSeatsHelper(SeatType.ECONOMY_CLASS, economyClassSeats, totalRowEcon,
                PLANE_TYPE.numColumnsEcon, totalRowFirst + totalRowBus + 1);
    }

    //helper method for populateSeats method
    private void populateSeatsHelper(SeatType seatType, Seat[] seatArray, int numRows, int numColumns, int startRow) {
        for (int row = startRow; row <= numRows; row++) {
            char seatLetter = 'A';
            for (int i = 0; i < numColumns; i++) {
                Seat seat = new Seat(row, seatLetter, seatType);
                for (int j = 0; j < seatArray.length; j++) {
                    if (seatArray[j] == null) {
                        seatArray[j] = seat;
                        break;
                    }
                }
                ++seatLetter;
            }
        }
    }

    //returns current number of AirbusA380 Objects instantiated
    public static int getNumA380() {
        return numA380;
    }



    ////////////// AirplaneBase Class Overrides //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //assigns Passenger Object to Seat Object and vice versa
    //Seat Object is pulled from specific Seat array based on SeatType parameter
    //specific Seat Object is pulled based on given index parameter
    //method is called in a Flight Object's method and index parameter is provided in that Flight Object's method
    @Override
    public boolean assignSeat(Passenger person, int index, SeatType seat) {
        switch (seat) {
            case FIRST_CLASS:
                firstClassSeats[index].addPassenger(person);
                try {
                    person.setSeatNum(firstClassSeats[index]);
                } catch (DuplicateBookingException e) {
                    return false;
                }
                break;
            
            case BUSINESS_CLASS:
                businessClassSeats[index].addPassenger(person);
                try {
                    person.setSeatNum(businessClassSeats[index]);
                } catch (DuplicateBookingException e) {
                    return false;
                }
                break;

            case ECONOMY_CLASS:
                economyClassSeats[index].addPassenger(person);
                try {
                    person.setSeatNum(economyClassSeats[index]);
                } catch (DuplicateBookingException e) {
                    return false;
                }
                break;
        
            default:
                return false;
        }
        return true;
    }

    //returns this Object's PlaneType 
    @Override
    public PlaneType getPlaneType() {
        return PLANE_TYPE;
    }



    ////////////// UniqueIdInterface Overrides ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns AirbusA380 Object's id#
    @Override
    public int getId() {
        return id;
    }
 


    ////////////// Object Class Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns a String of an AirbusA380 Object as the Object's "COMPANY", "CLASSIFICATION", and ID#
    @Override
    public String toString() {
        return String.format("%1$s %2$s\nPlane ID#: %3$d", PLANE_TYPE.company, PLANE_TYPE.classification, id);
    }

    //compares 2 AirbusA380 Objects by comparing their Object's hashcode
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof AirbusA380) {
            AirbusA380 cast = (AirbusA380)obj;
            return this.getId() == cast.getId();
        }
        return false;
    }

    //AirbusA380 Object's hashcode is set to its unique ID#
    @Override
    public int hashCode() {
        return this.getId();
    }
    
}
