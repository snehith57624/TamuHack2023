package com.example.springboot.model;

public class FloorTeamOutput {
    public int plannedSeating, actualSeating, totalTeams;
    Floor[] floors;

    public int getPlannedSeating() {
        return plannedSeating;
    }

    public void setPlannedSeating(int plannedSeating) {
        this.plannedSeating = plannedSeating;
    }

    public int getActualSeating() {
        return actualSeating;
    }

    public void setActualSeating(int actualSeating) {
        this.actualSeating = actualSeating;
    }

    public int getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(int totalTeams) {
        this.totalTeams = totalTeams;
    }

    public Floor[] getFloors() {
        return floors;
    }

    public void setFloors(Floor[] floors) {
        this.floors = floors;
    }
}
