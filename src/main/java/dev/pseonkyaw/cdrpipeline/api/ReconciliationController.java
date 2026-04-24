package dev.pseonkyaw.cdrpipeline.api;

import dev.pseonkyaw.cdrpipeline.rating.RatedCdrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.Instant;

@RestController
@RequestMapping("/api/reconciliation")
@RequiredArgsConstructor
public class ReconciliationController {

    private final RatedCdrRepository ratedCdrRepository;

    @GetMapping("/{mvnoId}")
    public ReconciliationResponse forMvno(
            @PathVariable String mvnoId,
            @RequestParam Instant from,
            @RequestParam Instant to) {

        BigDecimal total = ratedCdrRepository.totalChargesFor(mvnoId, from, to);
        long count = ratedCdrRepository.countByMvnoIdAndOccurredAtBetween(mvnoId, from, to);
        return new ReconciliationResponse(mvnoId, from, to, count, total);
    }
}
