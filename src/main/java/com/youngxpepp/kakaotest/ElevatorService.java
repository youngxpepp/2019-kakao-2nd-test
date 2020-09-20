package com.youngxpepp.kakaotest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ElevatorService {

	private static final String ROOT_URI = "http://localhost:8000";

	private final RestTemplate restTemplate;

	public ElevatorService() {
		this.restTemplate = new RestTemplateBuilder()
			.rootUri(ROOT_URI)
			.build();
	}

	public Dto.StartResponseDto start(String userKey, Integer problemId, Integer numberOfElevators) {
		ResponseEntity<Dto.StartResponseDto> response = restTemplate
			.exchange("/start/{user_key}/{problem_id}/{number_of_elevators}",
				HttpMethod.POST,
				null,
				Dto.StartResponseDto.class,
				userKey,
				problemId,
				numberOfElevators);

		return response.getBody();
	}

	public Dto.OnCallResponseDto getCalls(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Auth-Token", token);
		HttpEntity request = new HttpEntity(headers);

		ResponseEntity<Dto.OnCallResponseDto> response =
			restTemplate.exchange("/oncalls", HttpMethod.GET, request, Dto.OnCallResponseDto.class);

		return response.getBody();
	}
}
