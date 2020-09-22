package com.youngxpepp.kakaotest;

import com.youngxpepp.kakaotest.solution.AbstractSolution;
import com.youngxpepp.kakaotest.solution.CollectiveSolution;

public class Application {

	public static void main(String[] args) {
		ElevatorTemplate elevatorTemplate = new ElevatorTemplate();
		AbstractSolution solution = new CollectiveSolution(elevatorTemplate);
		solution.solve("leegeonhong3", 1, 1, 8, 25, 200);
	}
}
