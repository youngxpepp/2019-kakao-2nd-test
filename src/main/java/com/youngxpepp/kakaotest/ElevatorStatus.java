package com.youngxpepp.kakaotest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum ElevatorStatus {
	UPWARD("UPWARD"),
	DOWNWARD("DOWNWARD"),
	OPENED("OPENED"),
	STOPPED("STOPPED");

	@JsonValue
	private String status;

	ElevatorStatus(String status) {
		this.status = status;
	}

	@JsonCreator
	public static ElevatorStatus fromStatusString(String status) {
		return ElevatorStatus.valueOf(status);
	}
}
