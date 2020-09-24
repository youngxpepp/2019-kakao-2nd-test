package com.youngxpepp.kakaotest;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.NONE)
public class Dto {

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class StartResponseDto {

		private String token;
		private Integer timestamp;
		private List<ElevatorDto> elevators;

		@JsonProperty("is_end")
		private Boolean isEnd;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, setterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class OnCallResponseDto {

		private String token;
		private Integer timestamp;
		private List<ElevatorDto> elevators;
		private List<CallDto> calls;

		@JsonProperty("is_end")
		private Boolean isEnd;
	}

	@AllArgsConstructor
	@Builder
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class ActionRequestDto {

		private List<CommandDto> commands;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class ActionResponseDto {

		private String token;
		private Integer timestamp;
		private List<ElevatorDto> elevators;

		@JsonProperty("is_end")
		private Boolean isEnd;
	}

	@AllArgsConstructor
	@Builder
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class CommandDto {

		@JsonProperty("elevator_id")
		private Integer elevatorId;

		private ElevatorCommand command;

		@JsonProperty("call_ids")
		private List<Integer> callIds;
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class ElevatorDto implements Comparable<ElevatorDto> {

		private Integer id;
		private Integer floor;
		private List<CallDto> passengers;
		private ElevatorStatus status;

		@Override
		public int compareTo(ElevatorDto o) {
			return this.id.compareTo(o.getId());
		}
	}

	@NoArgsConstructor
	@Getter
	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
	public static class CallDto implements Comparable<CallDto> {

		private Integer id;
		private Integer timestamp;
		private Integer start;
		private Integer end;

		@Override
		public int compareTo(CallDto o) {
			return end.compareTo(o.end);
		}
	}
}
