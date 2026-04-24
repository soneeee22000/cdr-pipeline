package dev.pseonkyaw.cdrpipeline.api;

import java.math.BigDecimal;
import java.time.Instant;

public record ReconciliationResponse(
        String mvnoId,
        Instant from,
        Instant to,
        long eventCount,
        BigDecimal totalCharge
) {
}
