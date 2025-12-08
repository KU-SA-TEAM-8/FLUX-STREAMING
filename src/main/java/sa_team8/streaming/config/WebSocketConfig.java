package sa_team8.streaming.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import sa_team8.streaming.outbound.websocket.ScoreBoardWebSocketHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

  private final ScoreBoardWebSocketHandler scoreboardWebSocketHandler;

  @Bean
  public HandlerMapping webSocketMapping() {
    // /ws/scoreboard 로 연결하면 이벤트 스트림 받음
    Map<String, WebSocketHandler> map = Map.of(
        "/ws/scoreboard", scoreboardWebSocketHandler
    );
    log.info(">>> WebSocket mapping loaded: {}", map.keySet());

    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setUrlMap(map);
    mapping.setOrder(-9999); // WebFlux 기본 라우팅보다 우선
    return mapping;
  }

  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter();
  }
}
