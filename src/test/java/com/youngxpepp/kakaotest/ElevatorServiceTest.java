package com.youngxpepp.kakaotest;

import static org.assertj.core.api.BDDAssertions.*;

import org.junit.jupiter.api.Test;

public class ElevatorServiceTest {

	private static ElevatorService elevatorService = new ElevatorService();

	@Test
	public void testStart() {
		Dto.StartResponseDto startResponse = elevatorService.start("test", 0, 1);
	}

	@Test
	public void testGetCalls() {
		Dto.StartResponseDto startResponse = elevatorService.start("test", 0, 1);
		Dto.OnCallResponseDto onCallResponseDto = elevatorService.getCalls(startResponse.getToken());
	}
}
