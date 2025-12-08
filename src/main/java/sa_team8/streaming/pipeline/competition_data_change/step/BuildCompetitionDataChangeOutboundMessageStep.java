package sa_team8.streaming.pipeline.competition_data_change.step;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent;
import sa_team8.streaming.outbound.message.impl.CompetitionDataChangeOutboundMessage;
import sa_team8.streaming.pipeline.FinalOutboundStep;

@Component
@RequiredArgsConstructor
public class BuildCompetitionDataChangeOutboundMessageStep implements CompetitionPipelineStep {

  private final FinalOutboundStep finalOutboundStep;

  @Override
  public void process(CompetitionDataChangeEvent event) {
    finalOutboundStep.handle(new CompetitionDataChangeOutboundMessage(event));
  }
}
