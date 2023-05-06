package ch.wesr.kirke.eventsourcing.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
public class EventRepository {

    Map<UUID, Map<Class<?>, String>> eventMap;
    ObjectMapper objectMapper;

/*    public EventRepository() {
        objectMapper = new ObjectMapper();
        // ist ein Hack aber funktioniert
        // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.Instant` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
        objectMapper.registerModule(new JavaTimeModule());
        eventMap = new LinkedHashMap<>();
    }*/


    public Consumer<Message<String>> save(Message<String> msg) {
        String solaceDestination = (String) msg.getHeaders().get("solace_destination");
        assert solaceDestination != null;
        String[] split = solaceDestination.split("/");
        assert split.length == 3;
        UUID uuid = UUID.fromString(split[2]);
        log.info("save: {}, {}", uuid, msg.getPayload());
/*
        if (eventMap.containsKey(objectId)) {
            Map<Class<?>, String> classStringMap = eventMap.get(objectId);
            classStringMap.put(event.getClass(), eventAsString);
        } else {
            Map<Class<?>, String> classStringMap = new LinkedHashMap<>();
            classStringMap.put(event.getClass(), eventAsString);
            eventMap.put((UUID) objectId, classStringMap);

*/

        // FIXME
        return null;
    }
}
