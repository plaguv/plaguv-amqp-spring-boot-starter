package io.github.plaguv.contract.event;

public record StoreClosedEvent(
        Long storeId
) implements EventInstance {
    public StoreClosedEvent {
        if (storeId == null) {
            throw new IllegalArgumentException("StoreClosedEvents storeId cannot be null");
        }
    }
}