package com.adaptionsoft.games.uglytrivia;

public class Player {
    private final String name;
    private Place place;
    private int purse;
    private boolean isInPenaltyBox;

    public Player(String name) {
        this.name = name;
        this.place = new Place(0);
        this.purse = 0;
        this.isInPenaltyBox = false;
    }

    public String getName() {
        return name;
    }

    public Place getPlace() {
        return place;
    }

    public int getPurse() {
        return purse;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setPurse(int purse) {
        this.purse = purse;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        isInPenaltyBox = inPenaltyBox;
    }
}
