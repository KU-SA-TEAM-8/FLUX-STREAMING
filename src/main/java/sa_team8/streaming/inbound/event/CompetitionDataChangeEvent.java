package sa_team8.streaming.inbound.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompetitionDataChangeEvent extends BaseEvent {
  private ScoreBoardRes scoreBoard;
  private List<ScoreHistoryRes> scoreHistories;

  public record ScoreBoardRes(
      String competitionName,
      String announcement,
      String description,
      Instant startTime,
      Integer totalTime,
      CompetitionStateEnum state,
      List<ScoreBoardTeamRow> teams
  ) {

    public record ScoreBoardTeamRow(
        UUID teamId,
        String name,
        int score
    ) {

    }
  }

  public record ScoreHistoryRes(
      String teamName,
      String againstTeamName,
      int delta,
      String reason,
      Instant changedAt
  ) {

  }
}
