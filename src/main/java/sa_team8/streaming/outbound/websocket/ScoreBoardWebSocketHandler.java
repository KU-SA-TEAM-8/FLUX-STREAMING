package sa_team8.streaming.outbound.websocket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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
public class ScoreBoardWebSocketHandler implements WebSocketHandler {

  // 현재 연결된 모든 세션
  private final Set<WebSocketSession> sessions =
      ConcurrentHashMap.newKeySet();

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    sessions.add(session);
    log.info("[WS] Client connected: {}", session.getId());

    // 클라이언트로부터 메시지는 사용 안 함 (연결 유지용)
    return session.receive()
        .doOnError(e ->
            log.warn("[WS] Receive error. session={}, error={}",
                session.getId(), e.getMessage()))
        .doFinally(signal -> {
          sessions.remove(session);
          log.info("[WS] Client disconnected: {}, signal={}",
              session.getId(), signal);
        })
        .then();
  }

  /**
   * 서버 → 클라이언트 이벤트 push
   */
  public void push(String json) {
    if (sessions.isEmpty()) {
      return;
    }

    sessions.removeIf(session -> !session.isOpen());

    sessions.forEach(session -> {
      session.send(Mono.just(session.textMessage(json)))
          .doOnError(e ->
              log.warn("[WS] Send failed. session={}, error={}",
                  session.getId(), e.getMessage()))
          .subscribe();
    });
  }
}
