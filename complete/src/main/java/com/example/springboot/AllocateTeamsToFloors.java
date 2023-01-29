package com.example.springboot;

import com.example.springboot.model.Floor;
import com.example.springboot.model.FloorTeamOutput;
import com.example.springboot.model.Team;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AllocateTeamsToFloors {
    public static FloorTeamOutput allocateTeamsToFloors(Floor[] floors, Team[] teams){
        Arrays.sort(floors);
        Arrays.sort(teams);
        Set<Integer> available = new HashSet<Integer>();
        Set<Integer> assigned = new HashSet<Integer>();
        for (int i=0;i< teams.length; i++){
            available.add(i);
        }
//        System.out.println("Check 1");
        int teamId = 0;
        for (Floor floor : floors) {
            while ((teamId<teams.length) &&(floor.remainingCapacity < teams[teamId].strength)) {
                teamId++;
            }
            if(teamId>=teams.length){
                break;
            }
            boolean res = floor.addAssignedTeamIds(teams[teamId]);
            if (res){
                available.remove(teamId);
                assigned.add(teamId);
            }
            teamId++;
        }
        boolean flag = true;
        while(flag){
            for(Integer availableTeamId : available){

                for (int i=0;i< floors.length;i++){
                    boolean res = floors[i].addAssignedTeamIds(teams[availableTeamId]);
                    if(res){
                        assigned.add(availableTeamId);
                        break;
                    }
                }
            }
            if(assigned.size()+available.size()==teams.length){
                flag = false;
            }
            available.removeAll(assigned);
        }
//        System.out.println("Check 5");

        // partial filling
        for(Integer availableTeamId: available){
            for (int i=0;i< floors.length;i++){
                int partialCapacity = floors[i].remainingCapacity;
                boolean res = floors[i].addPartialAssignedTeamIds(teams[availableTeamId]);
                if(res){
                    teams[availableTeamId].remainingStrength = teams[availableTeamId].strength - partialCapacity;
                    assigned.add(availableTeamId);
                    break;
                }
            }
        }
        for(int i=0;i<teams.length;i++){
            if (teams[i].remainingStrength == 0){
                available.remove(i);
            }
        }
        boolean checkFillFlag = checkFloorCapacity(floors);
        if (!checkFillFlag){
            balanceFloorsStrength(floors, teams);
        }
        int totalCapacity = 0;
        int remaining = 0;
        for (Floor floor: floors){
            totalCapacity += floor.capacity;
            remaining += floor.remainingCapacity;
//            System.out.println("Floor "+floor.floorId+" left with "+floor.remainingCapacity+" of "+floor.capacity);
//            System.out.println("Assigned Ids:");
//            for (int id: floor.assignedTeamIds){
//                System.out.println(id);
//            }
        }
        int plannedSeating = totalCapacity-remaining;
        FloorTeamOutput floorTeamOutput = new FloorTeamOutput();
        floorTeamOutput.setFloors(floors);
        floorTeamOutput.setActualSeating(totalCapacity);
        floorTeamOutput.setPlannedSeating(plannedSeating);
        floorTeamOutput.setTotalTeams(assigned.size());

        return floorTeamOutput;
    }

    private static void balanceFloorsStrength(Floor[] floors, Team[] teams) {
        Set<Integer> underFilled = new HashSet<Integer>();

        // Overfilled => if occupied > 0.25 of capacity
        Set<Integer> overFilled = new HashSet<Integer>();
        for(int i=0;i< floors.length;i++){
            if((floors[i].remainingCapacity*1.0/floors[i].capacity)<0.25){
                underFilled.add(i);
            } else{
                overFilled.add(i);
            }
        }

        for(Integer overFilledFloorId: overFilled ){
            for (Integer underFilledFloorId: underFilled){
                if(floors[underFilledFloorId].noWayList.containsAll(floors[overFilledFloorId].assignedTeamIds)){
                    continue;
                }
                int expectedFill = (int)Math.ceil(floors[underFilledFloorId].capacity*0.25);
                int needed = expectedFill-(floors[underFilledFloorId].capacity-floors[underFilledFloorId].remainingCapacity);
                for (Integer id: floors[overFilledFloorId].assignedTeamIds) {
                    if(!floors[underFilledFloorId].noWayList.contains(id)){
                        int temp = floors[overFilledFloorId].filledCap.get(id);
                        if (((floors[overFilledFloorId].remainingCapacity+temp)*1.0/floors[overFilledFloorId].capacity)<=0.75){
//                            if(needed<=temp){
//
////                                floors[underFilledFloorId].filledCap
//                            }
                            if(temp > floors[underFilledFloorId].remainingCapacity){
                                floors[overFilledFloorId].filledCap.put(id, floors[overFilledFloorId].filledCap.get(id)-floors[underFilledFloorId].remainingCapacity);
                            } else{
                                floors[overFilledFloorId].filledCap.remove(id);
                                floors[overFilledFloorId].remainingCapacity += temp;
                                floors[overFilledFloorId].assignedTeamIds.remove(id);
                            }
                            floors[underFilledFloorId].remainingCapacity = Math.max(floors[underFilledFloorId].remainingCapacity- temp, 0);
                            floors[underFilledFloorId].filledCap.put(id, Math.min(floors[underFilledFloorId].remainingCapacity, temp));
                            floors[underFilledFloorId].assignedTeamIds.add(id);
                            needed = Math.max(needed-temp, 0);
                        }
                    }
                }

            }
        }
    }

    private static boolean checkFloorCapacity(Floor[] floors) {
        for (Floor floor: floors) {
            if((floor.remainingCapacity*1.0/floor.capacity)<0.25){
                return false;
            }
        }
        return true;
    }
}
