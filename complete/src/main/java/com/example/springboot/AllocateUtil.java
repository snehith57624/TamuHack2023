package com.example.springboot;

import com.example.springboot.model.Floor;
import com.example.springboot.model.FloorTeamOutput;
import com.example.springboot.model.Team;

public class AllocateUtil {
    public static FloorTeamOutput calculate(Floor[] floorsInput, Team[] teamsInput) {
        if(teamsInput.length==0){
            return null;
        }
        teamsInput[0].updateSetOfIdsWithZeroStart(teamsInput);
        for (int i=0;i<teamsInput.length; i++){
            teamsInput[i].updateNoWayInBoth(teamsInput);
        }
        FloorTeamOutput floorTeamOutput = new FloorTeamOutput();
        try {
                floorTeamOutput = AllocateTeamsToFloors.allocateTeamsToFloors(floorsInput, teamsInput);
        } catch (Exception e){
                System.out.println("Exception "+e);
        }
        return floorTeamOutput;
    }
}