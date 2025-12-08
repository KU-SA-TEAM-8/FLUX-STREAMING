package sa_team8.streaming.pipeline.score_update.step;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sa_team8.streaming.inbound.event.ScoreUpdateEvent;
import sa_team8.streaming.outbound.message.impl.ScoreUpdateOutboundMessage;
import sa_team8.streaming.pipeline.FinalOutboundStep;

@Component
@RequiredArgsConstructor
public class BuildScoreBoardUpdateOutboundMessage implements ScorePipelineStep {

  private final FinalOutboundStep finalOutboundStep;

  @Override
  public void process(ScoreUpdateEvent event) {
    finalOutboundStep.handle(new ScoreUpdateOutboundMessage(event));
  }
}
