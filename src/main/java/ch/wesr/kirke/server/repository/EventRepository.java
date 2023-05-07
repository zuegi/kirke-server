package ch.wesr.kirke.server.repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public interface EventRepository {
    void save(UUID targetId, String event);

    Map<UUID, Map<LocalDateTime, String>> findByTargetId(UUID targetId);
}
