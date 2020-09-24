package com.youngxpepp.kakaotest.solution;

import java.util.List;
import java.util.stream.Collectors;

import com.youngxpepp.kakaotest.Dto;

public class CallUtil {

	public static List<Dto.CallDto> getRangedCalls(int firstFloor, int lastFloor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() >= firstFloor && call.getStart() <= lastFloor)
			.collect(Collectors.toList());
	}

	public static List<Dto.CallDto> getArrivedCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getEnd() == floor)
			.collect(Collectors.toList());
	}

	public static List<Dto.CallDto> getWaitingCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() == floor)
			.collect(Collectors.toList());
	}

	public static List<Dto.CallDto> getHigherStartCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() > floor)
			.collect(Collectors.toList());
	}

	public static List<Dto.CallDto> getHigherEndCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getEnd() > floor)
			.collect(Collectors.toList());
	}

	public static List<Dto.CallDto> getLowerStartCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getStart() < floor)
			.collect(Collectors.toList());
	}

	public static List<Dto.CallDto> getLowerEndCalls(int floor, List<Dto.CallDto> calls) {
		return calls.stream()
			.filter(call -> call.getEnd() < floor)
			.collect(Collectors.toList());
	}

	public static List<Integer> getIdListFromCalls(List<Dto.CallDto> calls) {
		return calls.stream()
			.map(call -> call.getId())
			.collect(Collectors.toList());
	}
}
