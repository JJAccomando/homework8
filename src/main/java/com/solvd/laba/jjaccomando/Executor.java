package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.enums.Airport;
import com.solvd.laba.jjaccomando.enums.SeatType;
import com.solvd.laba.jjaccomando.enums.luggagecategories.SpecialItemsLuggage;
import com.solvd.laba.jjaccomando.exceptions.*;

import java.lang.reflect.*;
import java.util.*;

import java.util.stream.Collectors;

import static java.lang.System.out;

public class Executor {

    public static void main(String[] args) throws OversizeBagException, OverLimitException, DuplicateBookingException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        //Reflection: Gather class information

        Class<?> c = Class.forName("com.solvd.laba.jjaccomando.Passenger");
        Field[] fields = c.getDeclaredFields();
        Method[] methods = c.getDeclaredMethods();
        Constructor<?>[] constructors = c.getConstructors();
        out.println("Class Fields:");
        for (Field f : fields) {
            out.print(Modifier.toString(f.getModifiers()) + " ");
            out.println(f.getType() + " " + f.getName());
        }
        out.println();

        out.println("Class Constructors");
        for (Constructor<?> cons : constructors) {
            out.print(cons.getName() + " ");
            out.println(Arrays.toString(cons.getParameterTypes()));
        }
        out.println();

        out.println("Class Methods");
        for (Method m : methods) {
            out.print(Modifier.toString(m.getModifiers()) + " ");
            out.print(m.getReturnType() + " ");
            out.print(m.getName() + " ");
            out.println(Arrays.toString(m.getParameterTypes()));
        }


        Constructor<?> constructor = c.getConstructor(String.class, String.class, int.class);
        Object instance = constructor.newInstance("JJ", "A", 55);
        Method method = c.getMethod("getAge");

        out.println("\nMethod call using reflection: " + method.invoke(instance));
        out.println();


        //Object creation and collection stream methods

        Boeing737 plane1 = new Boeing737();
        AirbusA320 plane2 = new AirbusA320();
        Boeing737 plane3 = new Boeing737();
        AirbusA380 plane4 = new AirbusA380();
        AirbusA380 plane5 = new AirbusA380();
        AirbusA380 plane6 = new AirbusA380();
        AirbusA380 plane7 = new AirbusA380();
        AirbusA380 plane8 = new AirbusA380();
        AirbusA380 plane9 = new AirbusA380();

        Flight flight1 = new Flight(plane1, Airport.LAX, Airport.HNL);
        Flight flight2 = new Flight(plane2, Airport.JFK, Airport.LHR);
        Flight flight3 = new Flight(plane3, Airport.DFW, Airport.CDG);
        Flight flight4 = new Flight(plane4, Airport.HND, Airport.DXB);
        Flight flight5 = new Flight(plane5, Airport.LAX, Airport.ZRH);
        Flight flight6 = new Flight(plane6, Airport.LAX, Airport.DXB);
        Flight flight7 = new Flight(plane7, Airport.LAX, Airport.CDG);
        Flight flight8 = new Flight(plane8, Airport.LAX, Airport.LHR);
        Flight flight9 = new Flight(plane9, Airport.LAX, Airport.FRA);

        double flightsTotalDistance = Double.parseDouble(String.format("%.2f", Flight.mapFlights.values().stream()
                .mapToDouble(Flight::getFlightDistance)
                .reduce(0,
                        (a, b) -> a + b)));

        System.out.println(flightsTotalDistance + " Miles\n");

        List<Flight> filteredFlights = Flight.filteredFlights(flight -> flight.getDepartureLocation() == Airport.LAX);
        filteredFlights.stream()
                .forEach(flight -> System.out.println(flight.flightInfo() + "\n"));

        Passenger person1 = new Passenger("Julius", "Caesar", 55);
        Passenger person2 = new Passenger("John", "Smith", 43);
        Passenger person3 = new Passenger("Jane", "Doe", 35);
        Passenger person4 = new Passenger("Spongebob", "Squarepants", 33);
        flight1.bookSeat(person1, SeatType.FIRST_CLASS);
        flight2.bookSeat(person2, SeatType.ECONOMY_CLASS);
        flight3.bookSeat(person3, SeatType.BUSINESS_CLASS);
        flight1.bookSeat(person4, SeatType.BUSINESS_CLASS);

        PassengerLuggage bag = new PassengerLuggage(40, SpecialItemsLuggage.HUNTING_EQUIPMENT);
        PassengerLuggage bag2 = new PassengerLuggage(25);
        PassengerLuggage bag3 = new PassengerLuggage(55);
        person1.addBags(bag);

        Map<Class<? extends AirplaneBase>, List<AirplaneBase>> mapByPlaneType1 =
                AirplaneBase.airplaneSet
                        .stream()
                        .collect(
                        Collectors.groupingBy(AirplaneBase::getClass));
        mapByPlaneType1.get(AirbusA380.class)
                .stream()
                .forEach(plane -> System.out.println(plane.toString()));

        System.out.println();

        Map<Class<? extends AirplaneBase>, Set<AirplaneBase>> mapByPlaneType2 =
                AirplaneBase.airplaneSet
                        .stream()
                        .collect(
                        Collectors.groupingBy(AirplaneBase::getClass, Collectors.toSet()));

        Set<AirplaneBase> airbusA380Set = AirplaneBase.getFilteredAirplaneSet(p -> p instanceof AirbusA380);
        airbusA380Set.stream()
                .forEach(plane -> System.out.println(plane.toString()));
    }
}
