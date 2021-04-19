package coreClasses;

public class Pirates {
    // pirates doesnt need to actually have an object, thus all its methods can be static
    private String description;
    private int goodDemand;

    public static void getDescription(){
        // something like "Pirates, roll the die to play your chances!"
    }

    public static void attackShip(Ship ship) {
        // attacks ship based on the roll of the die
        // ship upgrades increase chances agaisnt pirates
        // except we need a way to measure the defense capabilities of a ship
    }

    private void takeGoods(Ship ship){
        // takes goods from a ships cargo hol
        // demand for goods is decided by a random number generator
        // creates a random goods demand
        // int goodDemand = randNumber... idk how to do this, do it later
        if (goodDemand > ship.getOccupiedCargoCapacity()){
            // ships crew have to walk the plank.
            // GAMEOVER
        }
        else{
            // how to we implement pirates taking items.
            // do we take items from teh itemArrayList until the demand is satisfied.
            // in this case you take more than demanded.
        }
    }
}
