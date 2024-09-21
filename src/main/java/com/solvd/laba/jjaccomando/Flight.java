package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.exceptions.DuplicateBookingException;
import com.solvd.laba.jjaccomando.exceptions.EmptySeatException;
import com.solvd.laba.jjaccomando.enums.*;
import com.solvd.laba.jjaccomando.exceptions.EmptyPassengerException;
import com.solvd.laba.jjaccomando.interfaces.Flights;
import com.solvd.laba.jjaccomando.interfaces.UniqueIdInterface;
import java.util.*;
import java.lang.Math;

import com.solvd.laba.jjaccomando.interfaces.DuplicateChecker;
import com.solvd.laba.jjaccomando.interfaces.ApplyFilter;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Flight implements UniqueIdInterface, Flights {

    public Map<Seat, Passenger> mapSeatKey = new HashMap<>();
    public Map<Passenger, Seat> mapPassengerKey = new HashMap<>();
    public static Map<String, Flight> mapFlights = new HashMap<>();
    private final int id;
    public static int numFlights = 0;
    private final String flightNumber;
    private final PlaneType planeType;
    private final Airport arrivalLocation, departureLocation;
    private final AirplaneBase plane;
    public final Passenger[] passengers;
    private final double flightDistance;
    private int seatsAvailable, firstClassSeatsCount = 0, businessClassSeatsCount = 0, economyClassSeatsCount = 0, numPassengers = 0;

    //Flight Object constructor 
    public Flight(AirplaneBase myPlane, Airport departureLocation, Airport arrivalLocation) {
        this.plane = myPlane;
        this.planeType = myPlane.getPlaneType();
        this.seatsAvailable = planeType.totalSeats;
        Supplier<Integer> randomNum = () -> Math.abs(new Random().nextInt(1000));
        this.flightNumber = planeType.abbreviation + plane.getId() + randomNum.get();
        mapFlights.put(this.flightNumber, this);
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

        double unformatted = radiusEarthMiles * inverseHaversineFormula;

        return Double.parseDouble(String.format("%.2f", unformatted));
    }

    //returns current number of Flight Objects instantiated
    public static int getNumFlights() {
        return numFlights;
    }

    public static Map<Flight, List<String>> getPassengerNamesByFlight() {
        Map<Flight, List<Passenger>> PassengerListByFlight = mapFlights.values().stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        flight -> flight.filterPassengers(
                                        passenger -> passenger != null)
                        ));
        return PassengerListByFlight.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(passenger -> passenger.toString() +
                                        " " + passenger.seat.toString())
                                .collect(Collectors.toList())
                ));
    }

    //adds Passenger to Flight Object by storing the Passenger in the Flight Object's Passenger array
    //this method is called inside bookSeat method which already checks for Seat availability
    //bookSeat method will only call this method if a Seat is available so a Passenger will always be able to be added to the array when this method is called
    private void addPassenger(Passenger person) {
        passengers[numPassengers++] = person;
    }

    //helper method for bookSeat method
    private boolean handleSeatAssignment(Passenger person, SeatType seatType) {
        switch (seatType) {
            case FIRST_CLASS:
                if (firstClassSeatsCount < planeType.seatsInFirst) {
                    return plane.assignSeat(person, firstClassSeatsCount++, seatType);
                }
                break;

            case BUSINESS_CLASS:
                if (businessClassSeatsCount < planeType.seatsInBusiness) {
                    return plane.assignSeat(person, businessClassSeatsCount++, seatType);
                }
                break;

            case ECONOMY_CLASS:
                if (economyClassSeatsCount < planeType.seatsInEcon) {
                    return plane.assignSeat(person, economyClassSeatsCount++, seatType);
                }
                break;

            default:
                return false;
        }
        return false;
    }

    public List<Passenger> filterPassengers(ApplyFilter<Passenger> filter) {
        return Arrays.stream(passengers)
                .filter(filter::filter)
                .collect(Collectors.toList());
    }

    public double getFlightDistance() {
        return flightDistance;
    }

    public Airport getDepartureLocation() {
        return departureLocation;
    }

    public Airport getArrivalLocation() {
        return arrivalLocation;
    }

    public static List<Flight> filteredFlights(ApplyFilter<Flight> filter) {
        return mapFlights.values().stream()
                .filter(filter::filter)
                .collect(Collectors.toList());
    }



    ////////////// Flights Overrides /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns true if Flight Object has any availability regardless of SeatType
    @Override
    public boolean getSeatsAvailable() {
        return seatsAvailable > 0;
    }

    //returns true if there are available Seats for given SeatType
    @Override
    public boolean getSeatsAvailable(SeatType seatType) {
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
    public boolean bookSeat(Passenger person, SeatType seatType) throws DuplicateBookingException {
        DuplicateChecker<Passenger> checker = (p, pa) -> {
            for (Passenger passenger : pa) {
                if (p.equals(passenger)) {
                    return true;
                }
            }
            return false;
        };

        if (checker.check(person, passengers)) {
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
    public int getNumPassengers() {
        return numPassengers;
    }

    //returns Passenger array from Flight Object if array is not empty
    @Override
    public Passenger[] getPassengers() throws EmptyPassengerException {
        if (numPassengers == 0)
            throw new EmptyPassengerException("Flight does not contain any passengers!");
        return passengers;
    }

    //returns Flight Object general information as a String
    @Override
    public String flightInfo() {
        return String.format(
                "Flight#: %s\nDeparting from: %s\nArriving to: %s\nNumber of passengers: %d\nPlane: %s %s\nFlight distance: %.2f mi",
                flightNumber, departureLocation, arrivalLocation, numPassengers, planeType.company, planeType.classification, flightDistance
        );
    }



    ////////////// UniqueIdInterface Overrides ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //returns Flight Object's ID#
    @Override
    public int getId() {
        return id;
    }



    ////////////// Object Class Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns a String of a Flight Object as the Object's "flightNum"
    @Override
    public String toString() {
        return "Flight#: " + flightNumber;
    }

    //compares 2 Flight Object's by comparing their Object's hashcode
    @Override
    public boolean equals(Object obj) {
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
    public int hashCode() {
        return this.getId();
    }
    
}
