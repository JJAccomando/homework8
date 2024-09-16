package com.solvd.laba.jjaccomando.interfaces;

import com.solvd.laba.jjaccomando.Passenger;
import com.solvd.laba.jjaccomando.exceptions.EmptyPassengerException;

public interface Seats {

    Passenger getPassenger() throws EmptyPassengerException;
    boolean addPassenger(Passenger person);
    boolean isAvailable();
    int getSeatNum();
    char getSeatLetter();

}
