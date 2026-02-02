package io.github.plaguv.contract.envelope.metadata;

public record EventVersion(
        int major,
        int minor,
        int patch
) {
    public EventVersion {
        if (major < 0) {
            throw new IllegalArgumentException("EventVersion attribute 'major' cannot be negative, was '%d'".formatted(major));
        }
        if (minor < 0) {
            throw new IllegalArgumentException("EventVersion attribute 'minor' cannot be negative, was '%d'".formatted(minor));
        }
        if (patch < 0) {
            throw new IllegalArgumentException("EventVersion attribute 'patch' cannot be negative, was '%d'".formatted(patch));
        }
    }

    public EventVersion(int major) {
        this(major, 0, 0);
    }

    public EventVersion(int major, int minor) {
        this(major, minor, 0);
    }

    @Override
    public String toString() {
        return "%d.%d.%d".formatted(major, minor, patch);
    }
}