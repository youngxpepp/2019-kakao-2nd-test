package com.youngxpepp.kakaotest.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.youngxpepp.kakaotest.Dto;
import com.youngxpepp.kakaotest.ElevatorRepository;
import com.youngxpepp.kakaotest.ElevatorStatus;
import com.youngxpepp.kakaotest.ElevatorTemplate;

public class DivisionSolution extends AbstractSolution {

	public DivisionSolution(ElevatorTemplate elevatorTemplate) {
		super(elevatorTemplate);
	}

	@Override
	public void solve(String userKey, int problemId, int numberOfElevators, int elevatorMaxCapacity,
		int buildingMaxFloor, int callSize) {

		ElevatorRepository repository = new ElevatorRepository(elevatorTemplate);
		Dto.StartResponseDto startResponseDto = elevatorTemplate.start(userKey, problemId, numberOfElevators);
		String token = startResponseDto.getToken();
		Dto.OnCallResponseDto onCallResponseDto = elevatorTemplate.getCalls(token);

		List<ElevatorStatus> prevDirections = new ArrayList<>();

		for(int i = 0; i < numberOfElevators; i++) {
			prevDirections.add(ElevatorStatus.UPWARD);
		}

		while (!onCallResponseDto.getIsEnd()) {
			List<Dto.CallDto> calls = onCallResponseDto.getCalls();
			List<Dto.ElevatorDto> elevators = onCallResponseDto.getElevators();
			Collections.sort(elevators);

			for (int i = 0; i < elevators.size(); i++) {
				Dto.ElevatorDto elevator = elevators.get(i);
				int firstFloor = 1 + i * (buildingMaxFloor / numberOfElevators);
				int lastFloor = firstFloor + (buildingMaxFloor / numberOfElevators) - 1;

				if (i == elevators.size() - 1) {
					lastFloor = buildingMaxFloor;
				}

				int elevatorCapacity = elevatorMaxCapacity - elevator.getPassengers().size();

				List<Dto.CallDto> rangedCalls = CallUtil.getRangedCalls(firstFloor, lastFloor, calls);
				List<Dto.CallDto> higherStartCalls = CallUtil.getHigherStartCalls(elevator.getFloor(), rangedCalls);
				List<Dto.CallDto> higherEndCalls = CallUtil.getHigherEndCalls(elevator.getFloor(),
					elevator.getPassengers());
				List<Dto.CallDto> lowerStartCalls = CallUtil.getLowerStartCalls(elevator.getFloor(), rangedCalls);
				List<Dto.CallDto> lowerEndCalls = CallUtil.getLowerEndCalls(elevator.getFloor(),
					elevator.getPassengers());
				List<Dto.CallDto> waitingCalls = CallUtil.getWaitingCalls(elevator.getFloor(), rangedCalls);
				List<Dto.CallDto> arrivedCalls = CallUtil.getArrivedCalls(elevator.getFloor(),
					elevator.getPassengers());

				if (elevator.getStatus().equals(ElevatorStatus.OPENED)) {
					// 엘레베이터 문이 열린 경우
					// 1. 승객이 내린다.
					// 2. 승객이 탄다.
					// 3. 문을 닫는다.

					if (!arrivedCalls.isEmpty()) {
						repository.exit(elevator.getId(), CallUtil.getIdListFromCalls(arrivedCalls));
					} else if (!waitingCalls.isEmpty() && elevatorCapacity > 0) {
						int enteredSize = Integer.min(waitingCalls.size(), elevatorCapacity);
						List<Dto.CallDto> enteredCalls = new ArrayList<>();

						for(int j = 0; j < enteredSize; j++) {
							enteredCalls.add(waitingCalls.get(j));
						}

						repository.enter(elevator.getId(), CallUtil.getIdListFromCalls(enteredCalls));
					} else {
						repository.close(elevator.getId());
					}
				} else if (elevator.getStatus().equals(ElevatorStatus.STOPPED)) {
					// 엘레베이터가 멈춘 경우
					// 1. 내릴 사람이 있는 경우
					// 2. 탈 사람이 있는 경우
					// 3. 방향 UPWARD
					// 3-1. 위에 내릴 사람이나 탈 사람이 있는 경우
					// 3-2. 아래에 내릴 사람이나 탈 사람이 있는 경우
					// 3-3. STOP
					// 4. 방향 DOWNWARD
					// 4-1. 아래에 내릴 사람이나 탈 사람이 있는 경우
					// 4-2. 위에 내릴 사람이나 탈 사람이 있는 경우
					// 4-3. STOP

					if (!arrivedCalls.isEmpty()) {
						repository.open(elevator.getId());
					} else if (!waitingCalls.isEmpty() && elevatorCapacity > 0) {
						repository.open(elevator.getId());
					} else if (prevDirections.get(i).equals(ElevatorStatus.UPWARD)) {
						if (!higherStartCalls.isEmpty() || !higherEndCalls.isEmpty()) {
							repository.up(elevator.getId());
						} else if (!lowerStartCalls.isEmpty() || !lowerEndCalls.isEmpty()) {
							repository.down(elevator.getId());
						} else {
							repository.stop(elevator.getId());
						}
					} else if (prevDirections.get(i).equals(ElevatorStatus.DOWNWARD)) {
						if (!lowerStartCalls.isEmpty() || !lowerEndCalls.isEmpty()) {
							repository.down(elevator.getId());
						} else if (!higherStartCalls.isEmpty() || !higherEndCalls.isEmpty()) {
							repository.up(elevator.getId());
						} else {
							repository.stop(elevator.getId());
						}
					}
				} else if (elevator.getStatus().equals(ElevatorStatus.UPWARD)) {
					if (!arrivedCalls.isEmpty() || !waitingCalls.isEmpty()) {
						repository.stop(elevator.getId());
					} else if (!higherStartCalls.isEmpty() || !higherEndCalls.isEmpty()) {
						repository.up(elevator.getId());
					} else {
						repository.stop(elevator.getId());
					}
					prevDirections.set(i, ElevatorStatus.UPWARD);
				} else if (elevator.getStatus().equals(ElevatorStatus.DOWNWARD)) {
					if (!arrivedCalls.isEmpty() || !waitingCalls.isEmpty()) {
						repository.stop(elevator.getId());
					} else if (!lowerStartCalls.isEmpty() || !lowerEndCalls.isEmpty()) {
						repository.down(elevator.getId());
					} else {
						repository.stop(elevator.getId());
					}
					prevDirections.set(i, ElevatorStatus.DOWNWARD);
				}
			}

			repository.flush(token);
			onCallResponseDto = elevatorTemplate.getCalls(token);
		}
	}
}
