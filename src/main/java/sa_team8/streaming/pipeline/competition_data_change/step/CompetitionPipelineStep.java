package sa_team8.streaming.pipeline.competition_data_change.step;

import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent;

public interface CompetitionPipelineStep {
  void process(CompetitionDataChangeEvent event);
}
