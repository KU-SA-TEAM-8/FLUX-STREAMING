package sa_team8.streaming.pipeline.score_update;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sa_team8.streaming.inbound.event.ScoreUpdateEvent;
import sa_team8.streaming.pipeline.EventPipeline;
import sa_team8.streaming.pipeline.score_update.step.ScorePipelineStep;

@Component
@RequiredArgsConstructor
public class ScoreUpdateEventPipeline implements EventPipeline {

  private final List<ScorePipelineStep> steps;

  @Override
  public void process(Object event) {
    ScoreUpdateEvent e = (ScoreUpdateEvent) event;

    for (ScorePipelineStep step : steps) {
      step.process(e);
    }
  }
}
