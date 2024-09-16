package com.solvd.laba.jjaccomando;

import com.solvd.laba.jjaccomando.interfaces.Luggage;
import com.solvd.laba.jjaccomando.interfaces.UniqueIdInterface;

public final class PassengerLuggage implements UniqueIdInterface, Luggage {

    private final int id;
    private int weight;
    private static int numLuggage = 0;
    private boolean isOverweight = false;

    //PassengerLuggage Object constructor
    public PassengerLuggage(int weight) {
        this.id = ++numLuggage;
        this.weight = weight;
        this.isOverweight = weight > 50;
    }

    //returns total number of PassengerLuggage Object's instantiated
    public static int getNumLuggage() {
        return numLuggage;
    }



    ////////////// PassengerLuggage Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //resets PassengerLuggage Object's weight to new weight value
    @Override
    public final void setWeight(int weight) {
        this.weight = weight;
    }

    //returns PassengerLuggage Object's weight
    @Override
    public final int getWeight() {
        return weight;
    }

    //returns true if PassengerLuggage Object's weight is over 50lbs
    @Override
    public final boolean isOverweight() {
        return isOverweight;
    }



    ////////////// UniqueIdInterface Overrides ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns PassengerLuggage Object's id#
    @Override
    public final int getId() {
        return id;
    }



    ////////////// Object Class Overrides ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //returns a String of a PassengerLuggage Object as the Object's id# and "weight"
    @Override
    public final String toString() {
        return String.format("PassengerLuggage ID#: %1$d\nPassengerLuggage weight: %2$d", id, weight);
    }

    //compares 2 PassengerLuggage Objects by comparing their Object's hashcode
    @Override
    public final boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof PassengerLuggage) {
            PassengerLuggage cast = (PassengerLuggage)obj;
            return this.getId() == cast.getId();
        }
        return false;
    }

    //PassengerLuggage Object's hashcode is set to its unique ID#
    @Override
    public final int hashCode() {
        return this.getId();
    }
    
}
