package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.interfaces.UniqueIdInterface;
import com.solvd.laba.jjaccomando.enums.*;
import java.util.Set;
import java.util.HashSet;

public abstract class AirplaneBase implements UniqueIdInterface {

    private static int numPlanes;
    public static Set<AirplaneBase> airplaneSet = new HashSet<>();

    {
        //increments total number of airplanes each time a new Object that extends AirplaneBase is created
        ++numPlanes;
    }

    abstract public PlaneType getPlaneType();

    abstract public boolean assignSeat(Passenger person, int index, SeatType seat);

    public static void addToMap(AirplaneBase obj) {
        airplaneSet.add(obj);
    }

    //returns current number of AirplaneBase Objects instantiated
    public static int getTotalPlanes() {
        return numPlanes;
    }
    
}
