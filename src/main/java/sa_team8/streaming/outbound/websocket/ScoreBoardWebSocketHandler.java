package sa_team8.streaming.outbound.websocket;

import java.util.Map;
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

  // publicId → sessions
  private final Map<String, Set<WebSocketSession>> sessions =
      new ConcurrentHashMap<>();

  @Override
  public Mono<Void> handle(WebSocketSession session) {
    String publicId = extractPublicId(session);

    sessions
        .computeIfAbsent(publicId, k -> ConcurrentHashMap.newKeySet())
        .add(session);

    log.info("[WS] Client connected. publicId={}, session={}",
        publicId, session.getId());

    return session.receive()
        .doFinally(signal -> {
          Set<WebSocketSession> set = sessions.get(publicId);
          if (set != null) {
            set.remove(session);
            if (set.isEmpty()) {
              sessions.remove(publicId);
            }
          }

          log.info("[WS] Client disconnected. publicId={}, session={}, signal={}",
              publicId, session.getId(), signal);
        })
        .then();
  }

  public void push(String publicId, String json) {
    Set<WebSocketSession> targetSessions = sessions.get(publicId);
    if (targetSessions == null || targetSessions.isEmpty()) {
      return;
    }

    targetSessions.removeIf(s -> !s.isOpen());

    targetSessions.forEach(session ->
        session.send(Mono.just(session.textMessage(json)))
            .doOnError(e ->
                log.warn("[WS] Send failed. publicId={}, session={}, error={}",
                    publicId, session.getId(), e.getMessage()))
            .subscribe()
    );
  }

  private String extractPublicId(WebSocketSession session) {
    String path = session.getHandshakeInfo().getUri().getPath();
    return path.substring(path.lastIndexOf('/') + 1);
  }
}
