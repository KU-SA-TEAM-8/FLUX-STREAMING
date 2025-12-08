package sa_team8.streaming.pipeline;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sa_team8.streaming.domain.ScoreboardSink;
import sa_team8.streaming.outbound.message.ScoreBoardOutboundMessage;

@Component
@Slf4j
@RequiredArgsConstructor
public class FinalOutboundStep {

  private final ScoreboardSink scoreboardSink;
  private final ObjectMapper objectMapper;

  public void handle(ScoreBoardOutboundMessage msg) {
    try {
      String json = objectMapper.writeValueAsString(msg);
      scoreboardSink.getSink().tryEmitNext(json);
    } catch (JsonProcessingException e) {
      log.error("Failed to serialize CompetitionDataChangeEvent", e);
    }
  }

}
