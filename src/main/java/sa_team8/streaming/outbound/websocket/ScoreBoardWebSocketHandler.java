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
    // 수신(클라이언트 -> 서버)은 지금 필요 없으니 ignore
    var input = session.receive()
        .doOnNext(msg -> log.debug("Received from client: {}", msg.getPayloadAsText()))
        .then(); // ignore

    // sink에서 나오는 JSON 문자열을 WebSocket TextMessage로 변환
    Flux<String> outboundFlux = scoreboardSink.getSink()
        .asFlux()
        .doOnSubscribe(s -> log.info("Client connected: {}", session.getId()))
        .doFinally(s -> log.info("Client disconnected: {}", session.getId()));

    var output = session.send(
        outboundFlux.map(session::textMessage)
    );

    return Mono.zip(input, output).then();
  }
}
