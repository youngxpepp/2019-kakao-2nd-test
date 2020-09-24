package com.youngxpepp.kakaotest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ElevatorCommand {
	STOP("STOP"),
	UP("UP"),
	DOWN("DOWN"),
	OPEN("OPEN"),
	CLOSE("CLOSE"),
	ENTER("ENTER"),
	EXIT("EXIT");

	@JsonValue
	private String command;

	ElevatorCommand(String command) {
		this.command = command;
	}

	@JsonCreator
	public static ElevatorCommand fromCommandString(String command) {
		return ElevatorCommand.valueOf(command);
	}
}
