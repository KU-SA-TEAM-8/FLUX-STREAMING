package sa_team8.streaming.inbound.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompetitionStateEnum {
  WAITING, RUNNING, PAUSED, CLOSED;
}