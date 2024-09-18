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
import java.lang.Math;


public final class Flight implements UniqueIdInterface, Flights {

    public static LinkedList<Flight> flightList = new LinkedList<>();
    public Map<Seat, Passenger> mapSeatKey = new HashMap<>();
    public Map<Passenger, Seat> mapPassengerKey = new HashMap<>();
    private final int id;
    public static int numFlights = 0;
    private final String flightNumber;
    private final PlaneType planeType;
    private final Airport arrivalLocation, departureLocation;
    private final AirplaneBase plane;
    private final Passenger[] passengers;
    private final double flightDistance;
    private int seatsAvailable, firstClassSeatsCount = 0, businessClassSeatsCount = 0, economyClassSeatsCount = 0, numPassengers = 0;

    //Flight Object constructor 
    public Flight(AirplaneBase myPlane, Airport departureLocation, Airport arrivalLocation) {
        flightList.add(this);
        this.plane = myPlane;
        this.planeType = myPlane.getPlaneType();
        this.seatsAvailable = planeType.totalSeats;
        this.flightNumber = planeType.classification + plane.getId();
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.passengers = new Passenger[planeType.totalSeats];
        this.id = ++numFlights;
        this.flightDistance = getDistance(departureLocation.latitude, departureLocation.longitude, arrivalLocation.latitude, arrivalLocation.longitude);
    }

    //returns distance from departureLocation to arrivalLocation
    private static double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        double radiusEarthMiles = 3958.8;

        double latitude1ToRadians = Math.toRadians(latitude1);
        double latitude2ToRadians = Math.toRadians(latitude2);
        double latitudeDifferenceToRadians = Math.toRadians(latitude2 - latitude1);
        double longitudeDifferenceToRadians = Math.toRadians(longitude2 - longitude1);

        double haversineFormula = Math.pow(Math.sin(latitudeDifferenceToRadians / 2), 2) +
                                  Math.pow(Math.sin(longitudeDifferenceToRadians / 2), 2) *
                                  Math.cos(latitude1ToRadians) *
                                  Math.cos(latitude2ToRadians);

        double inverseHaversineFormula = 2 * Math.asin(Math.sqrt(haversineFormula));

        return radiusEarthMiles * inverseHaversineFormula;
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
                if (firstClassSeatsCount < planeType.seatsInFirst) {
                    plane.assignSeat(person, firstClassSeatsCount++, seatType);
                    return true;
                }
                break;

            case BUSINESS_CLASS:
                if (businessClassSeatsCount < planeType.seatsInBusiness) {
                    plane.assignSeat(person, businessClassSeatsCount++, seatType);
                    return true;
                }
                break;

            case ECONOMY_CLASS:
                if (economyClassSeatsCount < planeType.seatsInEcon) {
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
                return firstClassSeatsCount < planeType.seatsInFirst;
            case BUSINESS_CLASS:
                return businessClassSeatsCount < planeType.seatsInBusiness;
            case ECONOMY_CLASS:
                return economyClassSeatsCount < planeType.seatsInEcon;
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
        return String.format(
                "Flight#: %s\nDeparting from: %s\nArriving to: %s\nNumber of passengers: %d\nPlane: %s %s\nFlight distance: %.2f mi",
                flightNumber, departureLocation, arrivalLocation, numPassengers, planeType.company, planeType.classification, flightDistance
        );
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
