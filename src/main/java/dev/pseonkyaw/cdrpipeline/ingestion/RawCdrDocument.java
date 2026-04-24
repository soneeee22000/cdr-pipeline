package dev.pseonkyaw.cdrpipeline.ingestion;

import dev.pseonkyaw.cdrpipeline.domain.Cdr;
import dev.pseonkyaw.cdrpipeline.domain.CdrType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "raw_cdr")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RawCdrDocument {

    @Id
    private String eventId;

    @Indexed
    private String mvnoId;

    private String subscriberId;
    private String mnoId;
    private String countryIso2;
    private CdrType type;
    private long quantity;
    private Instant occurredAt;
    private Instant receivedAt;

    public static RawCdrDocument from(Cdr cdr) {
        return RawCdrDocument.builder()
                .eventId(cdr.eventId())
                .mvnoId(cdr.mvnoId())
                .subscriberId(cdr.subscriberId())
                .mnoId(cdr.mnoId())
                .countryIso2(cdr.countryIso2())
                .type(cdr.type())
                .quantity(cdr.quantity())
                .occurredAt(cdr.occurredAt())
                .receivedAt(Instant.now())
                .build();
    }
}
