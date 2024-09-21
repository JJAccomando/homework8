package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.exceptions.*;
import com.solvd.laba.jjaccomando.interfaces.Passengers;
import com.solvd.laba.jjaccomando.interfaces.UniqueIdInterface;

public final class Passenger implements UniqueIdInterface, Passengers {

    private final int id, age;
    private static int numPassengers = 0;
    private final String firstName, lastName;
    private int countBags = 0;
    private final CustomLinkedList<PassengerLuggage> myList = new CustomLinkedList<>();
    public Seat seat;

    //Passenger Object constructor 
    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = ++numPassengers;
    }

    public int getAge() {
        return age;
    }

    //returns current number of Passenger Objects instantiated
    public static int getNumPassengers() {
        return numPassengers;
    }



    ////////////// Passengers Overrides //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns Passenger Object's first name
    @Override
    public final String getFirstName() {
        return firstName;
    }

    //returns Passenger Object's last name
    @Override
    public final String getLastName() {
        return lastName;
    }

    //adds PassengerLuggage Object to Passenger Object's array of PassengerLuggage and returns true
    //does not add PassengerLuggage Object if PassengerLuggage weight is over 50lbs or Passenger PassengerLuggage array is full
    @Override
    public final void addBags(PassengerLuggage bag) throws OversizeBagException, OverLimitException {
        if (countBags >= MAX_LUGGAGE) 
            throw new OverLimitException("Passenger has maximum number of luggage!");
        if (bag.isOverweight())
            throw new OversizeBagException("luggage cannot exceed maximum weight of 50lbs!");
        myList.add(bag);
        countBags++;
    }

    //returns total number of PassengerLuggage Object's Passenger has
    @Override
    public final int getNumBags() {
        return countBags;
    }

    //assigns Passenger Object with a Seat unless Passenger's current Seat is the same as the new Seat
    @Override
    public final void setSeatNum(Seat seat) throws DuplicateBookingException {
        if (this.seat != null && this.seat.equals(seat)) {
            throw new DuplicateBookingException("Passenger has already been assigned this seat!");
        }
        this.seat = seat;
    }

    //returns Passenger Object's assigned Seat if Passenger has assigned Seat
    @Override
    public final Seat getSeat() throws EmptySeatException {
        if (seat == null)
            throw new EmptySeatException("Passenger does not have assigned seat!");
        return seat;
    }

    //returns array containing Passenger Object's PassengerLuggage Objects if Passenger has at least 1 PassengerLuggage
    @Override
    public final CustomLinkedList<PassengerLuggage> getLuggage() throws EmptyBagException {
        if (countBags == 0)
            throw new EmptyBagException("Passenger does not have any luggage!");
        return myList;
    }



    ////////////// UniqueIdInterface Overrides ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns Passenger Object's ID#
    @Override
    public final int getId() {
        return id;
    }



    ////////////// Object Class Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns a String of a Passenger Object as that Object's "firstName", "lastName",  and ID#
    @Override
    public final String toString() {
        return String.format("Passenger: %1$s %2$s Passenger ID#: %3$d", firstName, lastName, id);
    }

    //compares 2 Passenger Objects by comparing their Object's hashcode
    @Override
    public final boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Passenger) {
            Passenger cast = (Passenger)obj;
            return this.getId() == cast.getId();
        }
        return false;
    }

    //Passenger Object's hashcode is set to its unique ID#
    @Override
    public final int hashCode() {
        return this.getId();
    }
    
}
