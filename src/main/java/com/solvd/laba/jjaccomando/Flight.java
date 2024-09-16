package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.exceptions.DuplicateBookingException;
import com.solvd.laba.jjaccomando.exceptions.EmptySeatException;
import com.solvd.laba.jjaccomando.enums.*;
import com.solvd.laba.jjaccomando.exceptions.EmptyPassengerException;
import com.solvd.laba.jjaccomando.interfaces.Flights;
import com.solvd.laba.jjaccomando.interfaces.UniqueIdInterface;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;


public final class Flight implements UniqueIdInterface, Flights {

    public static LinkedList<Flight> flightList = new LinkedList<>();
    public Map<Seat, Passenger> mapSeatKey = new HashMap<>();
    public Map<Passenger, Seat> mapPassengerKey = new HashMap<>();
    private final int id;
    public static int numFlights = 0;
    private final String flightNumber, departureLocation, arrivalLocation;
    private final PlaneType planeType;
    private final AirplaneBase plane;
    private final Passenger[] passengers;
    private int seatsAvailable, firstClassSeatsCount = 0, businessClassSeatsCount = 0, economyClassSeatsCount = 0, numPassengers = 0;

    //Flight Object constructor 
    public Flight(AirplaneBase myPlane, String departureLocation, String arrivalLocation) {
        flightList.add(this);
        this.plane = myPlane;
        this.planeType = myPlane.getPlaneType();
        this.seatsAvailable = planeType.TOTAL_SEATS;
        this.flightNumber = planeType.CLASSIFICATION + plane.getId();
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.passengers = new Passenger[planeType.TOTAL_SEATS];
        this.id = ++numFlights;
    }

    //returns current number of Flight Objects instantiated
    public static int getNumFlights() {
        return numFlights;
    }

    //adds Passenger to Flight Object by storing the Passenger in the Flight Object's Passenger array
    //this method is called inside bookSeat method which already checks for Seat availability
    //bookSeat method will only call this method if a Seat is available so a Passenger will always be able to be added to the array when this method is called
    private final void addPassenger(Passenger person) {
        passengers[numPassengers++] = person;
    }

    //helper method for bookSeat method
    private final boolean handleSeatAssignment(Passenger person, SeatType seatType) {
        switch (seatType) {
            case FIRST_CLASS:
                if (firstClassSeatsCount < planeType.SEATS_IN_FIRST) {
                    plane.assignSeat(person, firstClassSeatsCount++, seatType);
                    return true;
                }
                break;

            case BUSINESS_CLASS:
                if (businessClassSeatsCount < planeType.SEATS_IN_BUSINESS) {
                    plane.assignSeat(person, businessClassSeatsCount++, seatType);
                    return true;
                }
                break;

            case ECONOMY_CLASS:
                if (economyClassSeatsCount < planeType.SEATS_IN_ECON) {
                    plane.assignSeat(person, economyClassSeatsCount++, seatType);
                    return true;
                }
                break;

            default:
                return false;
        }
        return false;
    }



    ////////////// Flights Overrides /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns true if Flight Object has any availability regardless of SeatType
    @Override
    public final boolean getSeatsAvailable() {
        return seatsAvailable > 0;
    }

    //returns true if there are available Seats for given SeatType
    @Override
    public final boolean getSeatsAvailable(SeatType seatType) {
        switch (seatType) {
            case FIRST_CLASS:
                return firstClassSeatsCount < planeType.SEATS_IN_FIRST;
            case BUSINESS_CLASS:
                return businessClassSeatsCount < planeType.SEATS_IN_BUSINESS;
            case ECONOMY_CLASS:
                return economyClassSeatsCount < planeType.SEATS_IN_ECON;
            default:
                break;
        }
        return false;
    }

    //adds a Passenger to the Flight Object Passenger array and assigns Passenger a Seat based on SeatType
    @Override
    public final boolean bookSeat(Passenger person, SeatType seatType) throws DuplicateBookingException {
        for (Passenger passenger : passengers) {
            if (person.equals(passenger))
                throw new DuplicateBookingException("Passenger has already booked a seat!");
        }
        if (handleSeatAssignment(person, seatType)) {
            try {
                person.getSeat();
                mapPassengerKey.put(person, person.getSeat());
                mapSeatKey.put(person.getSeat(), person);
                addPassenger(person);
            } catch (EmptySeatException ignored) {}
            seatsAvailable--;
            return true;
        }
        return false;
    }

    //returns Flight Object's number of Passengers
    @Override
    public final int getNumPassengers() {
        return numPassengers;
    }

    //returns Passenger array from Flight Object if array is not empty
    @Override
    public final Passenger[] getPassengers() throws EmptyPassengerException {
        if (numPassengers == 0)
            throw new EmptyPassengerException("Flight does not contain any passengers!");
        return passengers;
    }

    //returns Flight Object general information as a String
    @Override
    public final String flightInfo() {
        String flightNumber = "Flight#: " + this.flightNumber;
        String departingFrom = "Departing from: " + departureLocation;
        String arrivingTo = "Arriving to: " + arrivalLocation;
        String passengers = "Number of passengers: " + numPassengers;
        String planeInfo = String.format("Plane: %1$s %2$s", planeType.COMPANY, planeType.CLASSIFICATION);
        return String.format("%1$s\n%2$s\n%3$s\n%4$s\n%5$s", flightNumber, departingFrom, arrivingTo, passengers, planeInfo);
    }



    ////////////// UniqueIdInterface Overrides ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //returns Flight Object's ID#
    @Override
    public final int getId() {
        return id;
    }



    ////////////// Object Class Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns a String of a Flight Object as the Object's "flightNum"
    @Override
    public final String toString() {
        return "Flight#: " + flightNumber;
    }

    //compares 2 Flight Object's by comparing their Object's hashcode
    @Override
    public final boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Flight) {
            Flight cast = (Flight)obj;
            return this.getId() == cast.getId();
        }
        return false;
    }

    //Flight Object's hashcode is set to its ID#
    @Override
    public final int hashCode() {
        return this.getId();
    }
    
}
