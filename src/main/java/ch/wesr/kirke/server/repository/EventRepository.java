package ch.wesr.kirke.server.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EventRepository {
    void save(UUID targetId, String event);

    Map<LocalDateTime, String> findByTargetId(UUID targetId);
}
