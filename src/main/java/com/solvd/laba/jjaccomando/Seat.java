package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.enums.SeatType;
import com.solvd.laba.jjaccomando.exceptions.EmptyPassengerException;
import com.solvd.laba.jjaccomando.interfaces.Seats;
import com.solvd.laba.jjaccomando.interfaces.UniqueIdInterface;

public final class Seat implements UniqueIdInterface, Seats {

    private final int SEAT_NUM;
    private final char SEAT_LETTER;
    private final int id;
    private static int numTotalSeats = 0;
    private Passenger passenger;
    private boolean available = true;
    private final SeatType seatType;

    //Seat Object constructor
    public Seat(int seatNum, char seatLetter, SeatType seatType) {
        SEAT_NUM = seatNum;
        SEAT_LETTER = seatLetter;
        this.seatType = seatType;
        id = ++numTotalSeats;
    }

    //returns total number of Seat Objects instantiated in program
    public final int getNumTotalSeats() {
        return numTotalSeats;
    }

    public final SeatType getSeatType() {
        return seatType;
    }



    ////////////// Seats Overrides /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns Passenger Object assigned to this Seat Object
    //throws exception if no Passenger was assigned
    @Override
    public final Passenger getPassenger() throws EmptyPassengerException {
        if (!available)
            throw new EmptyPassengerException("Seat has no assigned passenger!");
        return passenger;
    }

    //returns true if Seat Object does not have assigned Passenger
    @Override
    public final boolean isAvailable() {
        return available;
    }

    //assigns Passenger to this Seat object and changes Seat availability to false 
    @Override
    public final boolean addPassenger(Passenger passenger) {
        this.passenger = passenger;
        return available = false;
    }

    //returns Seat row number
    @Override
    public final int getSeatNum() {
        return SEAT_NUM;
    }

    //returns Seat column letter
    @Override
    public final char getSeatLetter() {
        return SEAT_LETTER;
    }
    


    ////////////// UniqueIdInterface Overrides ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns Seat Object's id#
    @Override
    public final int getId() {
        return id;
    }



    ////////////// Object Class Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns a String of a Seat Object as that Object's Seat "SEAT_NUM", and "SEAT_LETTER"
    @Override
    public final String toString() {
        return "Seat#: " + String.valueOf(SEAT_NUM) + SEAT_LETTER;
    }

    //compares 2 Seat Objects by comparing their Object's hashcode
    @Override
    public final boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Seat) {
            Seat cast = (Seat)obj;
            return this.getId() == cast.getId();
        }
        return false;
    }

    //Seat Object's hashcode is set to its unique ID#
    @Override
    public final int hashCode() {
        return this.getId();
    }

}
