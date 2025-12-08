package sa_team8.streaming.inbound.event;

import java.util.List;
import java.util.UUID;
import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent.ScoreHistoryRes;

public class ScoreUpdateEvent extends BaseEvent {
  private List<ScoreUpdateEventTeamRowRes> teams;
  private List<ScoreHistoryRes> scoreHistories;

  public record ScoreUpdateEventTeamRowRes(
      UUID teamId,
      String name,
      int score
  ) {

  }
}
