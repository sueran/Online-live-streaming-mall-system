package ltd.newbee.mall.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Slf4j
@Configuration
@EnableWebSocket
public class WebsocketConfig {
  @Bean
    public ServerEndpointExporter serverEndpointExporter(){
      return  new ServerEndpointExporter();
  }
}
