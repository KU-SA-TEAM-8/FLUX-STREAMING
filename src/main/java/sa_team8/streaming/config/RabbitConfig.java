package sa_team8.streaming.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  @Value("${scoreboard.rabbitmq.exchange}")
  public String EXCHANGE;

  public String SCORE_UPDATED_KEY = "score.updated";
  public String SCOREBOARD_UPDATED_KEY = "scoreboard.updated";

  @Value("${scoreboard.rabbitmq.queue.score}")
  public String SCORE_QUEUE;

  @Value("${scoreboard.rabbitmq.queue.competition}")
  public String SCOREBOARD_QUEUE;

  @Bean
  public TopicExchange scoreboardExchange() {
    return new TopicExchange(EXCHANGE, true, false);
  }

  @Bean
  public Queue scoreQueue() {
    return QueueBuilder.durable(SCORE_QUEUE).build();
  }

  @Bean
  public Queue scoreboardQueue() {
    return QueueBuilder.durable(SCOREBOARD_QUEUE).build();
  }

  @Bean
  public Binding scoreBinding() {
    return BindingBuilder
        .bind(scoreQueue())
        .to(scoreboardExchange())
        .with(SCORE_UPDATED_KEY);
  }

  @Bean
  public Binding scoreboardBinding() {
    return BindingBuilder
        .bind(scoreboardQueue())
        .to(scoreboardExchange())
        .with(SCOREBOARD_UPDATED_KEY);
  }

  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonMessageConverter());
    return template;
  }
}
