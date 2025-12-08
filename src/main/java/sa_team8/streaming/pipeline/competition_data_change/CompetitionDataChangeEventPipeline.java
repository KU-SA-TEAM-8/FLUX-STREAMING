package sa_team8.streaming.pipeline.competition_data_change;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent;
import sa_team8.streaming.pipeline.EventPipeline;
import sa_team8.streaming.pipeline.competition_data_change.step.CompetitionPipelineStep;

@Component
@RequiredArgsConstructor
public class CompetitionDataChangeEventPipeline implements EventPipeline {

  private final List<CompetitionPipelineStep> steps;

  @Override
  public void process(Object event) {
    CompetitionDataChangeEvent e = (CompetitionDataChangeEvent) event;

    for (CompetitionPipelineStep step : steps) {
      step.process(e);
    }
  }
}
