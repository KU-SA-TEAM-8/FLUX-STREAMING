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
    Map<String, WebSocketHandler> map = Map.of(
        "/ws/scoreboard/**", scoreboardWebSocketHandler
    );

    SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
    mapping.setUrlMap(map);
    mapping.setOrder(-9999);
    return mapping;
  }

  @Bean
  public WebSocketHandlerAdapter handlerAdapter() {
    return new WebSocketHandlerAdapter();
  }
}
