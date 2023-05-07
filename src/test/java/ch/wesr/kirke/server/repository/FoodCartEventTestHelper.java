package ch.wesr.kirke.server.repository;

import java.util.UUID;

public class FoodCartEventTestHelper {


    public static FoodCartCreatedEvent createFoodCartCreatedEvent(UUID foodCartId) {
        return new FoodCartCreatedEvent(foodCartId);
    }

    public static FoodCartConfirmedEvent createFoodCartConfirmedEvent(UUID foodCartId) {
        return new FoodCartConfirmedEvent(foodCartId);
    }

    public static ProductSelectedEvent createProductSelectedEvent(UUID foodCartId, UUID productId, int quantity) {
        return new ProductSelectedEvent(foodCartId, productId, quantity);
    }

    public static ProductDeselectedEvent createProductDeselectEvent(UUID foodCartId, UUID productId, int quantity) {
        return new ProductDeselectedEvent(foodCartId, productId, quantity);
    }


    public record FoodCartConfirmedEvent(UUID foodCartId) {
    }

    public record FoodCartCreatedEvent(UUID foodCartId) {

    }

    public record ProductDeselectedEvent(UUID foodCartId, UUID productId, int quantity) {
    }

    public record ProductSelectedEvent(UUID foodCartId, UUID productId, int quantity) {
    }
}
