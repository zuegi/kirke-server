package ch.wesr.kirke.server;

import ch.wesr.kirke.server.repository.EventRepositoryImpl;
import com.solacesystems.jcsmp.impl.TopicImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@Configuration
public class KirkeConfiguration {

    @Autowired
    private EventRepositoryImpl eventRepository;

    @Bean
    Consumer<Message<String>> myConsumer() {

        return msg -> {


            log.info("Received: " + msg.getPayload());
            log.info("Destination: " + msg.getHeaders().get("solace_destination"));
            log.info(msg.getHeaders().toString());
            log.info("TTL: " + msg.getHeaders().get("solace_timeToLive"));
            // FIXME Ermittlung der TopicImpl in Klasse oder Methode
            TopicImpl solaceDestination = (TopicImpl) msg.getHeaders().get("solace_destination");
            assert solaceDestination != null;
            log.info("solace_destination: {}", solaceDestination.getName());

            // FIXME Ermittlung der UUID in Klasse oder Methode
            String[] split = solaceDestination.getName().split("/");
            assert split.length == 3;
            UUID uuid = UUID.fromString(split[2]);
            eventRepository.save(uuid, msg.getPayload());
        };
    }
}
