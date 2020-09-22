package com.youngxpepp.kakaotest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElevatorRepository {

	private final ElevatorTemplate elevatorTemplate;
	private List<Dto.CommandDto> commands = new ArrayList<>();

	public ElevatorRepository(ElevatorTemplate elevatorTemplate) {
		this.elevatorTemplate = elevatorTemplate;
	}

	public void open(int elevatorId) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.OPEN)
			.callIds(Collections.EMPTY_LIST)
			.build();
		commands.add(command);
	}

	public void close(int elevatorId) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.CLOSE)
			.callIds(Collections.EMPTY_LIST)
			.build();
		commands.add(command);
	}

	public void stop(int elevatorId) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.STOP)
			.callIds(Collections.EMPTY_LIST)
			.build();
		commands.add(command);
	}

	public void up(int elevatorId) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.UP)
			.callIds(Collections.EMPTY_LIST)
			.build();
		commands.add(command);
	}

	public void down(int elevatorId) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.DOWN)
			.callIds(Collections.EMPTY_LIST)
			.build();
		commands.add(command);
	}

	public void enter(int elevatorId, List<Integer> callIds) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.ENTER)
			.callIds(callIds)
			.build();
		commands.add(command);
	}

	public void exit(int elevatorId, List<Integer> callIds) {
		Dto.CommandDto command = Dto.CommandDto.builder()
			.elevatorId(elevatorId)
			.command(ElevatorCommand.EXIT)
			.callIds(callIds)
			.build();
		commands.add(command);
	}

	public void flush(String token) {
		elevatorTemplate.action(token, commands);
		clear();
	}

	public void clear() {
		commands.clear();
	}
}
