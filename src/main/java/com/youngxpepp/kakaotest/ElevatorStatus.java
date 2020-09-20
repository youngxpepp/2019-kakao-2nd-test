package com.youngxpepp.kakaotest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

@Getter
public enum ElevatorStatus {
	UPWARD("UPWARD"),
	DOWNWARD("DOWNWARD"),
	OPENED("OPENED"),
	STOPPED("STOPPED");

	private String status;

	ElevatorStatus(String status) {
		this.status = status;
	}

	@JsonCreator
	public static ElevatorStatus fromStatusString(String status) {
		return ElevatorStatus.valueOf(status);
	}
}
