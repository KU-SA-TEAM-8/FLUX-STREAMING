package sa_team8.streaming.outbound.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sa_team8.streaming.domain.ScoreboardSink;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreBoardWebSocketHandler implements WebSocketHandler {

  private final ScoreboardSink scoreboardSink;

  @Override
  public Mono<Void> handle(WebSocketSession session) {

    Flux<String> outboundFlux = scoreboardSink.getSink()
        .asFlux()
        .doOnSubscribe(s -> log.info("Client connected: {}", session.getId()))
        .doFinally(s -> log.info("Client disconnected: {}", session.getId()));

    return session.send(
        outboundFlux.map(session::textMessage)
    );
  }
}
