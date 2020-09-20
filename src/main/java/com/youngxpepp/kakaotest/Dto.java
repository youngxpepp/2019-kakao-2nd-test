package com.youngxpepp.kakaotest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Dto {

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	public static class StartResponseDto {

		private String token;
		private Integer timestamp;
		private List<ElevatorDto> elevators;

		@JsonProperty("is_end")
		private Boolean isEnd;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	public static class OnCallResponseDto {

		private String token;
		private Integer timestamp;
		private List<ElevatorDto> elevators;
		private List<CallDto> calls;

		@JsonProperty("is_end")
		private Boolean isEnd;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	public static class ActionRequestDto {

		private List<CommandDto> commands;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	public static class CommandDto {

		@JsonProperty("elevator_id")
		private Integer elevatorId;

		private ElevatorCommand command;

		@JsonProperty("call_ids")
		private List<Integer> callIds;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Setter
	@Getter
	public static class ElevatorDto {

		private Integer id;
		private Integer floor;
		private List<CallDto> passengers;

		@JsonProperty("status")
		private ElevatorStatus status;
	}

	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Getter
	public static class CallDto {

		private Integer id;
		private Integer timestamp;
		private Integer start;
		private Integer end;
	}
}
