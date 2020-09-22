package com.youngxpepp.kakaotest.solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.youngxpepp.kakaotest.Dto;
import com.youngxpepp.kakaotest.ElevatorRepository;
import com.youngxpepp.kakaotest.ElevatorStatus;
import com.youngxpepp.kakaotest.ElevatorTemplate;

public class CollectiveSolution extends AbstractSolution {

	public CollectiveSolution(ElevatorTemplate elevatorTemplate) {
		super(elevatorTemplate);
	}

	@Override
	public void solve(String userKey, int problemId, int numberOfElevators, int elevatorMaxCapacity,
		int buildingMaxFloor, int callSize) {

		ElevatorRepository elevatorRepository = new ElevatorRepository(elevatorTemplate);
		Dto.StartResponseDto startResponse = elevatorTemplate.start(userKey, problemId, 1);
		String token = startResponse.getToken();
		Dto.OnCallResponseDto onCallResponse = elevatorTemplate.getCalls(token);

		ElevatorStatus directionalElevatorStatus = ElevatorStatus.UPWARD;

		while(!onCallResponse.getIsEnd()) {
			Dto.ElevatorDto elevator = onCallResponse.getElevators().get(0);
			int elevatorCapacity = elevatorMaxCapacity - elevator.getPassengers().size();

			List<Dto.CallDto> calls = onCallResponse.getCalls();
			List<Dto.CallDto> arrivedCalls = getArrivedCalls(elevator.getFloor(), elevator.getPassengers());
			List<Dto.CallDto> waitingCalls = getWaitingCalls(elevator.getFloor(), calls);
			List<Dto.CallDto> higherStartCalls = getHigherStartCalls(elevator.getFloor(), calls);
			List<Dto.CallDto> higherEndCalls = getHigherEndCalls(elevator.getFloor(), elevator.getPassengers());
			List<Dto.CallDto> lowerStartCalls = getLowerStartCalls(elevator.getFloor(), calls);
			List<Dto.CallDto> lowerEndCalls = getLowerEndCalls(elevator.getFloor(), elevator.getPassengers());

			if(directionalElevatorStatus.equals(ElevatorStatus.UPWARD)) {
				Collections.sort(waitingCalls, Comparator.reverseOrder());
			} else {
				Collections.sort(waitingCalls);
			}

			if(elevator.getStatus().equals(ElevatorStatus.OPENED)) {
				// 엘레베이터 문이 열렸을 때
				// 1. 내릴 사람이 있다면 내린다.
				// 2. 여유 공간이 있고, 태울 사람이 있다면 태운다.
				// 3. 문을 닫는다.

				if(!arrivedCalls.isEmpty()) {
					elevatorRepository.exit(elevator.getId(), getIdsFromCalls(arrivedCalls));
				}
				else if(elevatorCapacity > 0 && !waitingCalls.isEmpty()) {
					int minSize = Integer.min(elevatorCapacity, waitingCalls.size());
					List<Dto.CallDto> enteringCalls = new ArrayList<>();
					for(int i = 0; i < minSize; i++) {
						enteringCalls.add(waitingCalls.get(i));
					}
					elevatorRepository.enter(elevator.getId(), getIdsFromCalls(enteringCalls));
				}
				else {
					elevatorRepository.close(elevator.getId());
				}
			}
			else if(elevator.getStatus().equals(ElevatorStatus.STOPPED)) {
				// 엘레베이터가 정지해있을 때, UPWARD, DOWNWARD, OPENED, STOPPED로 바뀔 수 있다.
				// 1. 내릴 사람과 탈 사람이 있는지 확인하고 OPENED 전환
				// 2. 내릴 사람과 탈 사람이 없다면,
				// 2-1. 최근 방향 UPWARD이고, 높은 곳에 도착하거나 호출이 있는 경우, UPWARD 전환
				// 2-2. 최근 방향 UPWARD이고, 높은 곳에 도착하거나 호출이 없는 경우, 낮은 곳에 도착하거나 호출이 있는 경우, DOWNWARD 전환
				// 2-3. 최근 방향 DOWNWARD이고, 낮은 곳에 도착하거나 호출이 있는 경우, DOWNWARD 전환
				// 2-4. 최근 방향 DOWNWARD이고, 낮은 곳에 도착하거나 호출이 없는 경우, 높은 곳에 도착하거나 호출이 있는 경우, UPWARD 전환
				// 2-5. 위 조건이 만족하지 않는 경우 STOPPED 유지

				if(!arrivedCalls.isEmpty()) {
					elevatorRepository.open(elevator.getId());
				}
				else if(!waitingCalls.isEmpty() && elevatorCapacity > 0) {
					elevatorRepository.open(elevator.getId());
				}
				else {
					if(directionalElevatorStatus.equals(ElevatorStatus.UPWARD) &&
						(!higherStartCalls.isEmpty() ||
						!higherEndCalls.isEmpty())) {

						elevatorRepository.up(elevator.getId());
					}
					else if(directionalElevatorStatus.equals(ElevatorStatus.UPWARD) &&
						(!lowerStartCalls.isEmpty() ||
						!lowerEndCalls.isEmpty())) {

						elevatorRepository.down(elevator.getId());
					}
					else if(directionalElevatorStatus.equals(ElevatorStatus.DOWNWARD) &&
						(!lowerStartCalls.isEmpty() ||
						!lowerEndCalls.isEmpty())) {

						elevatorRepository.down(elevator.getId());
					}
					else if(directionalElevatorStatus.equals(ElevatorStatus.DOWNWARD) &&
						(!higherStartCalls.isEmpty() ||
						!higherEndCalls.isEmpty())) {

						elevatorRepository.up(elevator.getId());
					}
					else {
						elevatorRepository.stop(elevator.getId());
					}
				}
			}
			else if(elevator.getStatus().equals(ElevatorStatus.UPWARD)) {
				// 엘레베이터가 올라가고 있는 상태
				// 1. 내릴 사람 있는 경우
				// 2. 탈 사람 있는 경우
				// 3. 높은 곳에 도착하거나 호출이 있는 경우
				// 4. STOPPED
				directionalElevatorStatus = ElevatorStatus.UPWARD;

				if(!arrivedCalls.isEmpty()) {
					elevatorRepository.stop(elevator.getId());
				}
				else if(!waitingCalls.isEmpty() && elevatorCapacity > 0) {
					elevatorRepository.stop(elevator.getId());
				}
				else if(!higherStartCalls.isEmpty() || !higherEndCalls.isEmpty()) {
					elevatorRepository.up(elevator.getId());
				}
				else {
					elevatorRepository.stop(elevator.getId());
				}
			}
			else if(elevator.getStatus().equals(ElevatorStatus.DOWNWARD)) {
				// 엘레베이터가 내려가고 있는 상태
				// 1. 내릴 사람 있는 경우
				// 2. 탈 사람 있는 경우
				// 3. 낮은 곳에 도착하거나 호출이 있는 경우
				// 4. STOPPED
				directionalElevatorStatus = ElevatorStatus.DOWNWARD;

				if(!arrivedCalls.isEmpty()) {
					elevatorRepository.stop(elevator.getId());
				}
				else if(!waitingCalls.isEmpty() && elevatorCapacity > 0) {
					elevatorRepository.stop(elevator.getId());
				}
				else if(!lowerStartCalls.isEmpty() || !lowerEndCalls.isEmpty()) {
					elevatorRepository.down(elevator.getId());
				}
				else {
					elevatorRepository.stop(elevator.getId());
				}
			}

			elevatorRepository.flush(token);
			onCallResponse = elevatorTemplate.getCalls(token);
		}
	}

	private List<Dto.CallDto> getArrivedCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getEnd() == floor)
			.collect(Collectors.toList());
	}

	private List<Dto.CallDto> getWaitingCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() == floor)
			.collect(Collectors.toList());
	}

	private List<Dto.CallDto> getHigherStartCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() > floor)
			.collect(Collectors.toList());
	}

	private List<Dto.CallDto> getHigherEndCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getEnd() > floor)
			.collect(Collectors.toList());
	}

	private List<Dto.CallDto> getLowerStartCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() < floor)
			.collect(Collectors.toList());
	}

	private List<Dto.CallDto> getLowerEndCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getEnd() < floor)
			.collect(Collectors.toList());
	}

	private List<Integer> getIdsFromCalls(List<Dto.CallDto> calls) {
		return calls.stream()
			.map(call -> call.getId())
			.collect(Collectors.toList());
	}
}
