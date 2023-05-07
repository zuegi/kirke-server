package ch.wesr.kirke.server.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

class EventRepositoryTest {

    EventRepositoryImpl eventRepository;

    @BeforeEach
    void setup() {
        eventRepository = new EventRepositoryImpl();
    }
    @Test
    void testemich() {

        UUID foodCartId = UUID.randomUUID();

        FoodCartEventTestHelper.FoodCartCreatedEvent foodCartCreatedEvent = FoodCartEventTestHelper.createFoodCartCreatedEvent(foodCartId);
        eventRepository.save(foodCartId, foodCartCreatedEvent.toString());
        UUID productId = UUID.randomUUID();
        FoodCartEventTestHelper.ProductSelectedEvent productSelectedEvent = FoodCartEventTestHelper.createProductSelectedEvent(foodCartId, productId, 2);
        eventRepository.save(foodCartId, productSelectedEvent.toString());

        UUID productTwoId = UUID.randomUUID();
        FoodCartEventTestHelper.ProductSelectedEvent productTwoSelectedEvent = FoodCartEventTestHelper.createProductSelectedEvent(foodCartId, productTwoId, 2);
        eventRepository.save(foodCartId, productTwoSelectedEvent.toString());

        Map<UUID, Map<LocalDateTime, String>> byTargetId = eventRepository.findByTargetId(foodCartId);

        Assertions.assertThat(byTargetId).isNotEmpty();
        // FIXME hier die Tests erweitern und klären, dass die Events in der korrekten Reihenfolg ausgegeben werden.

    }

}
