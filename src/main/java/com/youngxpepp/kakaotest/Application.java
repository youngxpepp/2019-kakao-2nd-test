package com.youngxpepp.kakaotest;

import com.youngxpepp.kakaotest.solution.AbstractSolution;
import com.youngxpepp.kakaotest.solution.CollectiveSolution;
import com.youngxpepp.kakaotest.solution.DivisionSolution;

public class Application {

	public static void main(String[] args) {
		ElevatorTemplate elevatorTemplate = new ElevatorTemplate();
		AbstractSolution solution = new DivisionSolution(elevatorTemplate);
		solution.solve("leegeonhong", 2, 4, 8, 25, 500);
	}
}
