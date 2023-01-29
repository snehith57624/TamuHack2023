package com.example.springboot.model;

import java.util.HashSet;
import java.util.Set;

public class Team implements Comparable<Team>{
    public int strength, teamId, remainingStrength;
    public Set<Integer> prefferedTeamIds;
    public Set<Integer> toleratedTeamIds;
    public Set<Integer> noWayTeamIds;

    public Team(){
        prefferedTeamIds = new HashSet<Integer>();
        toleratedTeamIds = new HashSet<Integer>();
        noWayTeamIds = new HashSet<Integer>();
    }

    public Team(int n){
        this();
        this.strength = n;
    }

    public void addToPrefferedTeamIds(int teamId){
        prefferedTeamIds.add(teamId);
    }

    public void addSetToPrefferedTeamIds(Set<Integer> teamIds){
        prefferedTeamIds.addAll(teamIds);
    }

    public void addToToleratedTeamIds(int teamId){
        toleratedTeamIds.add(teamId);
    }

    public void addSetToToleratedTeamIds(Set<Integer> teamIds){
        toleratedTeamIds.addAll(teamIds);
    }

    public void addToNoWayTeamIds(int teamId){
        noWayTeamIds.add(teamId);
    }

    public void addSetToNoWayTeamIds(Set<Integer> teamIds){
        noWayTeamIds.addAll(teamIds);
    }

    public Set<Integer> getPrefferedTeamIds() {
        return prefferedTeamIds;
    }

    public Set<Integer> getToleratedTeamIds() {
        return toleratedTeamIds;
    }

    public Set<Integer> getNoWayTeamIds() {
        return noWayTeamIds;
    }

    public boolean elligibleToConsider(int teamId){
        return !noWayTeamIds.contains(teamId);
    }

    public void updateNoWayInBoth(Team[] teams){
        for(Integer id: noWayTeamIds){
            teams[id].addToNoWayTeamIds(this.teamId);
        }
    }

    public void updateSetOfIdsWithZeroStart(Team[] teams){
        for (Team team: teams) {
            Set<Integer> set = new HashSet<Integer>();
            set.addAll(team.prefferedTeamIds);
            team.prefferedTeamIds = new HashSet<Integer>();
            for (Integer id: set) {
                team.prefferedTeamIds.add(id-1);
            }

            set = new HashSet<Integer>();
            set.addAll(team.toleratedTeamIds);
            team.toleratedTeamIds = new HashSet<Integer>();
            for (Integer id: set) {
                team.toleratedTeamIds.add(id-1);
            }

            set = new HashSet<Integer>();
            set.addAll(team.noWayTeamIds);
            team.noWayTeamIds = new HashSet<Integer>();
            for (Integer id: set) {
                team.noWayTeamIds.add(id-1);
            }
        }

    }

    @Override
    public int compareTo(Team team) {
        // Descending Order
        return (int)(team.strength-this.strength);
    }
}
