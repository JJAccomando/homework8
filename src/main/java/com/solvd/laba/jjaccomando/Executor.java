package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.enums.Airport;
import com.solvd.laba.jjaccomando.enums.luggagecategories.SpecialItemsLuggage;
import com.solvd.laba.jjaccomando.exceptions.EmptyBagException;
import com.solvd.laba.jjaccomando.exceptions.OverLimitException;
import com.solvd.laba.jjaccomando.exceptions.OversizeBagException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Executor {

    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) throws EmptyBagException, OversizeBagException, OverLimitException {
        Boeing737 plane1 = new Boeing737();
        Flight flight1 = new Flight(plane1, Airport.LAX, Airport.HNL);
        Passenger person1 = new Passenger("JJ", "Accomando");
        PassengerLuggage bag = new PassengerLuggage(40, SpecialItemsLuggage.HUNTING_EQUIPMENT);
        person1.addBags(bag);
        System.out.println(Objects.requireNonNull(person1.getLuggage().find(bag.getId())).getLuggageCategory().getCategory());
        System.out.println(Objects.requireNonNull(person1.getLuggage().find(bag.getId())).getLuggageCategory().getCategoryDescription());
        System.out.println(flight1.flightInfo());
    }
}
