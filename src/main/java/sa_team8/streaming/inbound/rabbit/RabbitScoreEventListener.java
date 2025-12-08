package sa_team8.streaming.inbound.rabbit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import sa_team8.streaming.inbound.ScoreEventListener;
import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent;
import sa_team8.streaming.inbound.event.ScoreUpdateEvent;
import sa_team8.streaming.pipeline.competition_data_change.CompetitionDataChangeEventPipeline;
import sa_team8.streaming.pipeline.score_update.ScoreUpdateEventPipeline;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitScoreEventListener implements ScoreEventListener {

  private final CompetitionDataChangeEventPipeline competitionDataChangeEventPipeline;
  private final ScoreUpdateEventPipeline scoreUpdateEventPipeline;

  // 1) 대회 메타데이터/팀 전체 변경 이벤트
  @RabbitListener(queues = "scoreboard.queue.competition-data-change")
  public void onCompetitionDataChange(CompetitionDataChangeEvent event) {
    competitionDataChangeEventPipeline.process(event);
  }

  // 2) 점수만 변경되는 이벤트
  @RabbitListener(queues = "scoreboard.queue.score-update")
  public void onScoreUpdate(ScoreUpdateEvent event) {
    scoreUpdateEventPipeline.process(event);
  }
}
