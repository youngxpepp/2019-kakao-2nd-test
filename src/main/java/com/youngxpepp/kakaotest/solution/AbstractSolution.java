package com.youngxpepp.kakaotest.solution;

import com.youngxpepp.kakaotest.ElevatorTemplate;

public abstract class AbstractSolution {

	protected ElevatorTemplate elevatorTemplate;

	public AbstractSolution(ElevatorTemplate elevatorTemplate) {
		this.elevatorTemplate = elevatorTemplate;
	}

	public abstract void solve(String userKey,
		int problemId, int numberOfElevators, int elevatorMaxCapacity, int buildingMaxFloor, int callSize);
}
