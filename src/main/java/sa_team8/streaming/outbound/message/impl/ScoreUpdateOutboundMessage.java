package sa_team8.streaming.outbound.message.impl;

import lombok.Getter;
import sa_team8.streaming.inbound.event.ScoreUpdateEvent;
import sa_team8.streaming.outbound.message.MessageType;
import sa_team8.streaming.outbound.message.ScoreBoardOutboundMessage;

@Getter
public class ScoreUpdateOutboundMessage extends ScoreBoardOutboundMessage {
  public ScoreUpdateOutboundMessage(ScoreUpdateEvent event) {
    super(event.getPublicId(), MessageType.SCORE_UPDATE, event);
  }
}
