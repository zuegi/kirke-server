package ch.wesr.kirke.eventsourcing;

import ch.wesr.kirke.eventsourcing.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.StaticMessageHeaderAccessor;
import org.springframework.integration.acks.AcknowledgmentCallback;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class KirkeConfiguration {

    @Autowired
    private EventRepository eventRepository;

    @Bean
    Consumer<Message<String>> myConsumer() {

        return msg -> {

            log.info("Received: " + msg.getPayload());
            log.info("Destination: " + msg.getHeaders().get("solace_destination"));
            log.info(msg.getHeaders().toString());
            log.info("TTL: " + msg.getHeaders().get("solace_timeToLive"));
            eventRepository.save(msg);
        };
    }
}
