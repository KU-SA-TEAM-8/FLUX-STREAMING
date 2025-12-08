package sa_team8.streaming.pipeline.score_update.step;

import sa_team8.streaming.inbound.event.ScoreUpdateEvent;

public interface ScorePipelineStep {
  void process(ScoreUpdateEvent event);
}
