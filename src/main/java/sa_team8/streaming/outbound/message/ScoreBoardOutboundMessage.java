package sa_team8.streaming.outbound.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sa_team8.streaming.inbound.event.BaseEvent;
@Getter
@AllArgsConstructor
public abstract class ScoreBoardOutboundMessage {
  private MessageType type; // "COMPETITION_DATA_CHANGE" or "SCORE_UPDATE"
  private BaseEvent payload;
}