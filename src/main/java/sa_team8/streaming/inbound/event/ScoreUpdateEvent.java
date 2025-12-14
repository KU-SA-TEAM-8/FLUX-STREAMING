package sa_team8.streaming.inbound.event;

import java.util.List;
import java.util.UUID;
import lombok.Getter;
import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent.ScoreHistoryRes;

@Getter
public class ScoreUpdateEvent extends BaseEvent {
  private String publicId;
  private List<ScoreUpdateEventTeamRowRes> teams;
  private List<ScoreHistoryRes> scoreHistories;

  public record ScoreUpdateEventTeamRowRes(
      UUID teamId,
      String name,
      int score
  ) {

  }
}
