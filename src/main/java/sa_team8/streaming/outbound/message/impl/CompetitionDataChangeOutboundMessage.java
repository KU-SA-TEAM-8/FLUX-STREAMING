package sa_team8.streaming.outbound.message.impl;

import lombok.Getter;
import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent;
import sa_team8.streaming.outbound.message.MessageType;
import sa_team8.streaming.outbound.message.ScoreBoardOutboundMessage;
@Getter
public class CompetitionDataChangeOutboundMessage extends ScoreBoardOutboundMessage {
  public CompetitionDataChangeOutboundMessage(CompetitionDataChangeEvent event) {
    super(MessageType.COMPETITION_DATA_CHANGE, event);
  }
}
