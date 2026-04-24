package dev.pseonkyaw.cdrpipeline.rating;

import dev.pseonkyaw.cdrpipeline.domain.Cdr;
import dev.pseonkyaw.cdrpipeline.domain.RatedCdr;
import dev.pseonkyaw.cdrpipeline.domain.TariffPlan;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {

    private final TariffRepository tariffRepository;
    private final RatedCdrRepository ratedCdrRepository;

    @Transactional
    public RatedCdr rateAndStore(Cdr cdr) {
        if (ratedCdrRepository.existsById(cdr.eventId())) {
            log.debug("skipping duplicate cdr eventId={}", cdr.eventId());
            return ratedCdrRepository.findById(cdr.eventId()).orElseThrow();
        }

        TariffPlan.Key key = new TariffPlan.Key(cdr.mvnoId(), cdr.type(), cdr.countryIso2());
        Optional<TariffPlan> plan = tariffRepository.findById(key);
        TariffPlan tariff = plan.orElseThrow(() ->
                new RateCardNotFoundException(cdr.mvnoId(), cdr.type(), cdr.countryIso2()));

        RatedCdr rated = RatedCdr.builder()
                .eventId(cdr.eventId())
                .mvnoId(cdr.mvnoId())
                .subscriberId(cdr.subscriberId())
                .mnoId(cdr.mnoId())
                .countryIso2(cdr.countryIso2())
                .type(cdr.type())
                .quantity(cdr.quantity())
                .chargeAmount(tariff.rate(cdr.quantity()))
                .currency(tariff.getCurrency())
                .occurredAt(cdr.occurredAt())
                .ratedAt(Instant.now())
                .build();

        return ratedCdrRepository.save(rated);
    }
}
