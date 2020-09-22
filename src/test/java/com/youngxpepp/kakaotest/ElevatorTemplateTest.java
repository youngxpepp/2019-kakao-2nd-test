package com.youngxpepp.kakaotest;

import org.junit.jupiter.api.Test;

public class ElevatorTemplateTest {

	private static ElevatorTemplate elevatorTemplate = new ElevatorTemplate();

	@Test
	public void testStart() {
		Dto.StartResponseDto startResponse = elevatorTemplate.start("test", 0, 1);
	}

	@Test
	public void testGetCalls() {
		Dto.StartResponseDto startResponse = elevatorTemplate.start("test", 0, 1);
		Dto.OnCallResponseDto onCallResponseDto = elevatorTemplate.getCalls(startResponse.getToken());
	}

	@Test
	public void testAction() {
		Dto.StartResponseDto startResponse = elevatorTemplate.start("test", 0, 1);
		String token = startResponse.getToken();
		// elevatorService.action(token, );
	}
}
