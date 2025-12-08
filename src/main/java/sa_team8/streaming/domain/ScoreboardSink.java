package sa_team8.streaming.domain;

import lombok.Getter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class ScoreboardSink {
  // 여러 클라이언트에게 multicast 하되, backpressure는 버림
  @Getter
  private final Sinks.Many<String> sink =
      Sinks.many().multicast().onBackpressureBuffer();
}
