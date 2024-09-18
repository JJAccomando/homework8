package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.enums.Airport;
import com.solvd.laba.jjaccomando.enums.SeatType;
import com.solvd.laba.jjaccomando.enums.luggagecategories.SpecialItemsLuggage;
import com.solvd.laba.jjaccomando.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.solvd.laba.jjaccomando.interfaces.PrintInfo;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

public class Executor {

    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws EmptyBagException, OversizeBagException, OverLimitException, DuplicateBookingException {
        Boeing737 plane1 = new Boeing737();
        AirbusA320 plane2 = new AirbusA320();
        Boeing737 plane3 = new Boeing737();
        Flight flight1 = new Flight(plane1, Airport.LAX, Airport.HNL);
        Passenger person1 = new Passenger("JJ", "Accomando");
        Passenger person2 = new Passenger("John", "Smith");
        Passenger person3 = new Passenger("Jane", "Doe");
        Passenger person4 = new Passenger("Spongebob", "Squarepants");
        PassengerLuggage bag = new PassengerLuggage(40, SpecialItemsLuggage.HUNTING_EQUIPMENT);
        PassengerLuggage bag2 = new PassengerLuggage(25);
        PassengerLuggage bag3 = new PassengerLuggage(55);
        person1.addBags(bag);
        PrintInfo<Passenger> passengerName = (p) -> p.getFirstName() + " " + p.getLastName();
        PrintInfo<PassengerLuggage> luggageInfo = (luggageItem) -> {
            Function<PassengerLuggage, String> specialItemCheck = (luggage) -> {
                if (luggage.isSpecial()) {
                    return luggage.toString() + "\n" +
                            luggage.getLuggageCategory() + "\n" +
                            luggage.getLuggageCategory().getCategory() + "\n" +
                            luggage.getLuggageCategory().getCategoryDescription();
                }
                else
                    return luggage.toString();
            };
            return specialItemCheck.apply(luggageItem);
        };
        System.out.println(luggageInfo.getInfo(bag));
        System.out.println();

        System.out.println(luggageInfo.getInfo(bag2));
        System.out.println();

        System.out.println(luggageInfo.getInfo(bag3));
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
        System.out.println();

        BiPredicate<Flight, Passenger> hasPassenger = (f, p) -> {
            try {
                for (Passenger person : f.getPassengers()) {
                    if (person.equals(p)) {
                        return true;
                    }
                }
                return false;
            } catch (EmptyPassengerException empty) {
                return false;
            }
        };

        if(hasPassenger.test(flight1, person1)) {
            System.out.println(passengerName.getInfo(person1));
        } else
            System.out.println(passengerName.getInfo(person1) + " is not on this flight!");
        System.out.println();

        BiFunction<Set<AirplaneBase>, Class<? extends AirplaneBase>, Map<Integer, AirplaneBase>> mapByPlaneType = (s, a) -> {
            Map<Integer, AirplaneBase> planeMap = new HashMap<>();
            for (AirplaneBase plane : s) {
                if (a.isInstance(plane)) {
                    planeMap.put(plane.getId(), plane);
                }
            }
            return planeMap;
        };

        Map<Integer, AirplaneBase> boeing737Map = mapByPlaneType.apply(AirplaneBase.airplaneSet, Boeing737.class);
        Map<Integer, AirplaneBase> airbusA320Map = mapByPlaneType.apply(AirplaneBase.airplaneSet, AirbusA320.class);

        Consumer<Map<Integer, AirplaneBase>> printMap = (m) -> {
            for (AirplaneBase plane : m.values()) {
                System.out.println(plane);
            }
        };

        printMap.accept(boeing737Map);
        System.out.println();

        printMap.accept(airbusA320Map);
    }

}
