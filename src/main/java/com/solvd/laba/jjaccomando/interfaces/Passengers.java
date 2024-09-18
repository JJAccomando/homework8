package com.solvd.laba.jjaccomando.interfaces;

import com.solvd.laba.jjaccomando.CustomLinkedList;
import com.solvd.laba.jjaccomando.PassengerLuggage;
import com.solvd.laba.jjaccomando.Seat;
import com.solvd.laba.jjaccomando.exceptions.*;

public interface Passengers {

    int MAX_LUGGAGE = 10;

    String getFirstName();
    String getLastName();
    void addBags(PassengerLuggage bag) throws OversizeBagException, OverLimitException;
    int getNumBags();
    void setSeatNum(Seat seat) throws DuplicateBookingException;
    Seat getSeat() throws EmptySeatException;
    CustomLinkedList<PassengerLuggage> getLuggage() throws EmptyBagException;
    
}
