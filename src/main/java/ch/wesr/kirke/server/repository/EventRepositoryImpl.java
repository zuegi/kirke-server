package ch.wesr.kirke.server.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
public class EventRepositoryImpl implements EventRepository {

//    Map<UUID, Map<Class<?>, String>> eventMap;
    private final Map<UUID, Map<LocalDateTime, String>> eventMap;

    public EventRepositoryImpl() {
//        objectMapper = new ObjectMapper();
        // ist ein Hack aber funktioniert
        // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.Instant` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310"
//        objectMapper.registerModule(new JavaTimeModule());
        eventMap = new LinkedHashMap<>();
    }


    @Override
    public void save(UUID targetId, String event) {
        if (eventMap.containsKey(targetId)) {
            Map<LocalDateTime, String> localDateListMap = eventMap.get(targetId);
            localDateListMap.put(LocalDateTime.now(), event);
        } else {
            Map<LocalDateTime, String> localDateListMap = new TreeMap<>();
            localDateListMap.put(LocalDateTime.now(), event);
            eventMap.put(targetId, localDateListMap);
        }
    }

    @Override
    public Map<LocalDateTime, String> findByTargetId(UUID targetId) {

        if (eventMap.containsKey(targetId)) {
           return eventMap.get(targetId);
        }
        //FIXME Exception Handling für TargetId nicht gefunden
        throw new RuntimeException("TargetId [" + targetId + "] not found");
    }
}
