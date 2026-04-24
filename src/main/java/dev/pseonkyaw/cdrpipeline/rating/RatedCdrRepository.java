package dev.pseonkyaw.cdrpipeline.rating;

import dev.pseonkyaw.cdrpipeline.domain.RatedCdr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;

public interface RatedCdrRepository extends JpaRepository<RatedCdr, String> {

    @Query("""
           SELECT COALESCE(SUM(r.chargeAmount), 0)
             FROM RatedCdr r
            WHERE r.mvnoId = :mvnoId
              AND r.occurredAt >= :from
              AND r.occurredAt <  :to
           """)
    BigDecimal totalChargesFor(@Param("mvnoId") String mvnoId,
                               @Param("from") Instant from,
                               @Param("to") Instant to);

    long countByMvnoIdAndOccurredAtBetween(String mvnoId, Instant from, Instant to);
}
