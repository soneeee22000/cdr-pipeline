package dev.pseonkyaw.cdrpipeline.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(
        name = "rated_cdr",
        indexes = {
                @Index(name = "idx_rated_mvno_occurred", columnList = "mvno_id, occurred_at"),
                @Index(name = "idx_rated_subscriber", columnList = "subscriber_id")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatedCdr {

    @Id
    @Column(name = "event_id", length = 64)
    private String eventId;

    @Column(name = "mvno_id", nullable = false, length = 64)
    private String mvnoId;

    @Column(name = "subscriber_id", nullable = false, length = 64)
    private String subscriberId;

    @Column(name = "mno_id", nullable = false, length = 64)
    private String mnoId;

    @Column(name = "country_iso2", nullable = false, length = 2)
    private String countryIso2;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 16)
    private CdrType type;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "charge_amount", nullable = false, precision = 18, scale = 6)
    private BigDecimal chargeAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "rated_at", nullable = false)
    private Instant ratedAt;
}
