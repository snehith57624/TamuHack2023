package com.example.springboot;

import com.example.springboot.model.FloorTeamInput;
import com.example.springboot.model.FloorTeamOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class HelloController {

	@PostMapping("/generate")
	public FloorTeamOutput index(@RequestBody FloorTeamInput floorTeamInput) {
		System.out.println(Arrays.toString(floorTeamInput.getFloors()));
		System.out.println(Arrays.toString(floorTeamInput.getTeams()));
		return AllocateUtil.calculate(floorTeamInput.getFloors(), floorTeamInput.getTeams());
	}

}
