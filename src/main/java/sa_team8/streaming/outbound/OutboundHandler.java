package sa_team8.streaming.outbound;

public interface OutboundHandler {
  void push(String publicId, String json);
}
