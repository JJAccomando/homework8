package com.solvd.laba.jjaccomando.interfaces;

import com.solvd.laba.jjaccomando.Passenger;
import com.solvd.laba.jjaccomando.exceptions.DuplicateBookingException;
import com.solvd.laba.jjaccomando.exceptions.EmptyPassengerException;
import com.solvd.laba.jjaccomando.enums.SeatType;

public interface Flights {

    boolean getSeatsAvailable();
    boolean getSeatsAvailable(SeatType seatType);
    boolean bookSeat(Passenger person, SeatType seatType) throws DuplicateBookingException;
    int getNumPassengers();
    Passenger[] getPassengers() throws EmptyPassengerException;
    String flightInfo();

}
