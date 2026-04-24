package dev.pseonkyaw.cdrpipeline.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.Instant;

public record Cdr(
        @NotBlank String eventId,
        @NotBlank String mvnoId,
        @NotBlank String subscriberId,
        @NotBlank String mnoId,
        @NotBlank String countryIso2,
        @NotNull CdrType type,
        @PositiveOrZero long quantity,
        @NotNull Instant occurredAt
) {
    @JsonCreator
    public Cdr(
            @JsonProperty("eventId") String eventId,
            @JsonProperty("mvnoId") String mvnoId,
            @JsonProperty("subscriberId") String subscriberId,
            @JsonProperty("mnoId") String mnoId,
            @JsonProperty("countryIso2") String countryIso2,
            @JsonProperty("type") CdrType type,
            @JsonProperty("quantity") long quantity,
            @JsonProperty("occurredAt") Instant occurredAt
    ) {
        this.eventId = eventId;
        this.mvnoId = mvnoId;
        this.subscriberId = subscriberId;
        this.mnoId = mnoId;
        this.countryIso2 = countryIso2;
        this.type = type;
        this.quantity = quantity;
        this.occurredAt = occurredAt;
    }
}
