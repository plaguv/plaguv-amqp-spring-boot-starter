package io.github.plaguv.contract.event;

public record StoreOpenedEvent(
        Long storeId
) implements EventInstance {
    public StoreOpenedEvent {
        if (storeId == null) {
            throw new IllegalArgumentException("StoreOpenedEvents storeId cannot be null");
        }
    }
}