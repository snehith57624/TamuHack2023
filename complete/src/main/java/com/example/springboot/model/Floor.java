package com.example.springboot.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Floor implements Comparable<Floor>{
    public int capacity, remainingCapacity, floorId;
    public Set<Integer> noWayList;
    public Set<Integer> assignedTeamIds;
    public Map<Integer, Integer> filledCap;
    public String floorName;


    public Floor(int n){
        this();
        this.capacity = n;
        this.remainingCapacity = n;
    }

    public Floor(){
        noWayList = new HashSet<Integer>();
        assignedTeamIds = new HashSet<Integer>();
        filledCap = new HashMap<Integer, Integer>();
    }

    public void addToNoWayList(int teamId){
        noWayList.add(teamId);
    }

    public void addSetToNoWayList(Set<Integer> teamIds){
        noWayList.addAll(teamIds);
    }

    public boolean notElligibleToConsiderInFloor(int teamId){
        return noWayList.contains(teamId);
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public boolean addAssignedTeamIds(Team team){
        if ( notElligibleToConsiderInFloor(team.teamId) || (remainingCapacity<team.strength)){
            return false;
        }
        assignedTeamIds.add(team.teamId);
        remainingCapacity -= team.strength;
        filledCap.put(team.teamId, team.strength);
        addSetToNoWayList(team.noWayTeamIds);
        return true;
    }

    public boolean addPartialAssignedTeamIds(Team team){
        if ( notElligibleToConsiderInFloor(team.teamId) || remainingCapacity == 0){
            return false;
        }
        assignedTeamIds.add(team.teamId);
        filledCap.put(team.teamId,Math.min(remainingCapacity, team.strength));
        remainingCapacity = Math.max(remainingCapacity - team.strength, 0);
        addSetToNoWayList(team.noWayTeamIds);
        return true;
    }

    @Override
    public int compareTo(Floor floor) {
        // Descending Order
        return (int)(floor.capacity-this.capacity);
    }
}
