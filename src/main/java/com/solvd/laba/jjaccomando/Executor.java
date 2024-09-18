package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.enums.Airport;
import com.solvd.laba.jjaccomando.enums.SeatType;
import com.solvd.laba.jjaccomando.enums.luggagecategories.SpecialItemsLuggage;
import com.solvd.laba.jjaccomando.exceptions.DuplicateBookingException;
import com.solvd.laba.jjaccomando.exceptions.EmptyBagException;
import com.solvd.laba.jjaccomando.exceptions.OverLimitException;
import com.solvd.laba.jjaccomando.exceptions.OversizeBagException;
import com.solvd.laba.jjaccomando.interfaces.ApplyFilter;
import com.solvd.laba.jjaccomando.interfaces.PrintInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Executor {

    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws EmptyBagException, OversizeBagException, OverLimitException, DuplicateBookingException {
        Boeing737 plane1 = new Boeing737();
        Flight flight1 = new Flight(plane1, Airport.LAX, Airport.HNL);
        Passenger person1 = new Passenger("JJ", "Accomando");
        Passenger person2 = new Passenger("John", "Smith");
        Passenger person3 = new Passenger("Jane", "Doe");
        Passenger person4 = new Passenger("Spongebob", "Squarepants");
        PassengerLuggage bag = new PassengerLuggage(40, SpecialItemsLuggage.HUNTING_EQUIPMENT);
        person1.addBags(bag);
        PrintInfo<PassengerLuggage> luggageInfo = (luggage -> {
            return luggage.getLuggageCategory() + "\n" +
                    luggage.getLuggageCategory().getCategory() + "\n" +
                    luggage.getLuggageCategory().getCategoryDescription();
        });
        System.out.println(luggageInfo.getInfo(bag));
        System.out.println();

        System.out.println(flight1.flightInfo());
        System.out.println();

        flight1.bookSeat(person1, SeatType.FIRST_CLASS);
        flight1.bookSeat(person2, SeatType.FIRST_CLASS);
        flight1.bookSeat(person3, SeatType.ECONOMY_CLASS);
        flight1.bookSeat(person4, SeatType.BUSINESS_CLASS);

        Passenger[] firstClassPassengers = flight1.filterPassengers(p -> {
            Seat seat = flight1.mapPassengerKey.get(p);
            return seat != null && seat.getSeatType() == SeatType.FIRST_CLASS;
        });

        Passenger[] economyClassPassengers = flight1.filterPassengers(p -> {
            Seat seat = flight1.mapPassengerKey.get(p);
            return seat != null && seat.getSeatType() == SeatType.ECONOMY_CLASS;
        });

        for (Passenger passenger : firstClassPassengers) {
            System.out.println(passenger.getFirstName() + " " + passenger.getLastName());
        }
        System.out.println();
        for (Passenger passenger : economyClassPassengers) {
            System.out.println(passenger.getFirstName() + " " + passenger.getLastName());
        }
        System.out.println();

        PrintInfo<Flight> flightInfo = (Flight::flightInfo);
        System.out.println(flightInfo.getInfo(flight1));


    }
}
