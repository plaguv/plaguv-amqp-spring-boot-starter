package io.github.plaguv.contract.utility;

import io.github.plaguv.contract.envelope.payload.EventDomain;
import io.github.plaguv.contract.envelope.routing.EventScope;

public final class WildcardStringBuilder {

    private WildcardStringBuilder() {}

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private String wildcard;

        private Builder() {}

        public Builder withSeparator(char separator) {
            return this;
        }

        public Builder withDomain(EventDomain domain) {
            return this;
        }

        public Builder withScope(EventScope eventScope) {
            return this;
        }

        public Builder withTarget(String target) {
            return this;
        }

        public String build() {
            return this.wildcard;
        }
    }
}