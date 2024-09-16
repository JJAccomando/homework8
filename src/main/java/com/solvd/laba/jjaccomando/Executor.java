package com.solvd.laba.jjaccomando;

import java.util.Scanner;
import com.solvd.laba.jjaccomando.enums.*;
import com.solvd.laba.jjaccomando.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Executor {

    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        logger.info("Program start successful.");
        Boeing737 plane = new Boeing737();
        Flight flight = new Flight(plane, "LAX", "JFK");
        Passenger person1 = new Passenger("John", "Smith");
        Passenger person2 = new Passenger("George", "Washington");
        SeatType seat = SeatType.ECONOMY_CLASS;

        String firstName = "";
        String lastName = "";
        try (Scanner input = new Scanner(System.in)) { //Creates scanner within try-catch with resources block which will close scanner when finished
            logger.warn("Receiving user input.");
            System.out.print("Enter first name: ");
            firstName = input.nextLine();
            System.out.print("Enter last name: ");
            lastName = input.nextLine();
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            logger.error("User input unsuccessful. Error");
        }
        logger.info("User input successful.");

        Passenger person3 = new Passenger(firstName, lastName);
        PassengerLuggage overweightBag = new PassengerLuggage(55);
        logger.warn("Over weight luggage created.");
        PassengerLuggage[] bags = new PassengerLuggage[15];
        int weight = 45;
        for (int i = 0; i < bags.length; i++) {
            PassengerLuggage bag = new PassengerLuggage(weight);
            bags[i] = bag;
        }

        try {
            person3.addBags(overweightBag); //Try to add overweight PassengerLuggage to Passenger and throws OversizeBagException
        } catch (OversizeBagException obe) {
            System.out.println(obe.getMessage()); 
            logger.error("Failed to add passenger luggage. Error: {OversizeBagException}");
        } catch (OverLimitException ole) {
            System.out.println(ole.getMessage());
            logger.error("Failed to add passenger luggage. Error: {OverLimitException}");
        }

        System.out.println(person3.getNumBags()); //Displays 0 since no PassengerLuggage was added

        try {
            for (PassengerLuggage bag : bags) { //Try to add more than maximum allowed PassengerLuggage to Passenger and throws OverLimitException
                person3.addBags(bag);
            }
        } catch (OversizeBagException obe) {
            System.out.println(obe.getMessage());
            logger.error("Failed to add passenger luggage. Error: {OversizeBagException}");
        } catch (OverLimitException ole) {
            System.out.println(ole.getMessage());
            logger.error("Failed to add passenger luggage. Error: {OverLimitException}");
        }

        System.out.println(person3.getNumBags()); //Displays 10 because Passenger has maximum number of PassengerLuggage
        try {
            System.out.println(person3.getLuggage().toString());
        } catch (EmptyBagException nbe) {
            System.out.println(nbe.getMessage());
            logger.error("Failed to retrieve passenger luggage information. Error: {EmptyBagException}");
        }

        try {
            Passenger[] passengers = flight.getPassengers(); //Try to return Passenger array and throws EmptyPassengerException because no Passengers were added to Flight yet
            for (Passenger passenger : passengers) { 
                System.out.println(passenger); //Try to print each Passenger but will not execute because exception was thrown
            }
        } catch (EmptyPassengerException npe) {
            System.out.println(npe.getMessage()); 
            logger.error("Failed to retrieve passengers information. Error: {EmptyPassengerException}");
        }

        try {
            flight.bookSeat(person1, seat); //Books Passenger on Flight
            flight.bookSeat(person1, seat); //Try to book same Passenger on same Flight and throws DuplicateBookingException
        } catch (DuplicateBookingException dbe) {
            System.out.println(dbe.getMessage()); 
            logger.error("Failed to add passenger to flight. Error: {DuplicateBookingException}");
        }

        System.out.println(flight.getNumPassengers()); //Prints out 1 because only one Passenger was added since second Passenger was a duplicate

        try {
            System.out.println(person1.getSeat()); //Prints person1's seat number
            System.out.println(person2.getSeat()); //Try to print person2's seat number and throws EmptySeatException because person2 was never assigned a Seat
        } catch (EmptySeatException nse) {
            System.out.println(nse.getMessage()); 
            logger.error("Failed to retrieve seat information. Error: {EmptySeatException}");
        }

        logger.info("Program success. Shutting down...");
    }  
    
}
