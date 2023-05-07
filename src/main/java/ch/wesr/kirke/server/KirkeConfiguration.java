package ch.wesr.kirke.server;

import ch.wesr.kirke.server.repository.EventRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solacesystems.jcsmp.impl.TopicImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.BinderHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Configuration
public class KirkeConfiguration {

    @Autowired
    private EventRepositoryImpl eventRepository;

    private static String REPLYTO_DESTINATION_KEY = "solace_replyTo";
    private static String CORRELATION_ID_KEY = "solace_correlationId";

    @Bean
    Consumer<Message<String>> myConsumer() {

        return msg -> {
            log.debug("Received: " + msg.getPayload());
            log.debug("Destination: " + msg.getHeaders().get("solace_destination"));
            log.debug(msg.getHeaders().toString());
            log.debug("TTL: " + msg.getHeaders().get("solace_timeToLive"));
            // extract topic
            TopicImpl solaceDestination = determineTopic(msg);
            // extract uuid
            UUID uuid = determineUUID(solaceDestination);
            eventRepository.save(uuid, msg.getPayload());
        };
    }

    @Bean
    Function<Message<String>, Message<String>> targetId() {
        return msg -> {
            TopicImpl solaceDestination = determineTopic(msg);
            // extract uuid
            UUID uuid = determineUUID(solaceDestination);

            Map<UUID, Map<LocalDateTime, String>> byTargetId = eventRepository.findByTargetId(uuid);
            String mapAsJson = convertMapToJson(byTargetId);

            // Get the Topic to replyTo and correlation ID
            String replyToTopic = msg.getHeaders().getOrDefault(REPLYTO_DESTINATION_KEY, "").toString();
            String cid = msg.getHeaders().getOrDefault(CORRELATION_ID_KEY, "").toString();

            log.info("Processing request with cid of: " + cid);
            log.info("ReplyTo Topic: " + replyToTopic);

            // Return Response Message w/ target destination set only if one provided, otherwise use binding config
            if (replyToTopic.isEmpty()) {
                return MessageBuilder.withPayload(mapAsJson)
                        .setHeader(CORRELATION_ID_KEY, cid)
                        .build();
            }
            else {
                return MessageBuilder.withPayload(mapAsJson)
                        .setHeader(CORRELATION_ID_KEY, cid)
                        .setHeader(BinderHeaders.TARGET_DESTINATION, replyToTopic)
                        .build();
            }

        };
    }


    public String convertMapToJson(Map<UUID, Map<LocalDateTime, String>> map) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(map);
            log.info(json);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // FIXME null or empty String?
        return null;
    }

    @NotNull
    private static UUID determineUUID(TopicImpl solaceDestination) {
        String[] split = solaceDestination.getName().split("/");
        assert split.length == 3;
        UUID uuid = UUID.fromString(split[2]);
        return uuid;
    }

    @NotNull
    private static TopicImpl determineTopic(Message<String> msg) {
        TopicImpl solaceDestination = (TopicImpl) msg.getHeaders().get("solace_destination");
        assert solaceDestination != null;
        log.info("solace_destination: {}", solaceDestination.getName());
        return solaceDestination;
    }
}
