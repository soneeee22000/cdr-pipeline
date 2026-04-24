package dev.pseonkyaw.cdrpipeline.ingestion;

import dev.pseonkyaw.cdrpipeline.domain.Cdr;
import dev.pseonkyaw.cdrpipeline.rating.RateCardNotFoundException;
import dev.pseonkyaw.cdrpipeline.rating.RatingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CdrKafkaConsumer {

    private final RawCdrRepository rawCdrRepository;
    private final RatingService ratingService;

    @KafkaListener(
            topics = "${cdr.topics.events:cdr.events}",
            groupId = "${spring.kafka.consumer.group-id:cdr-pipeline}",
            containerFactory = "cdrKafkaListenerContainerFactory"
    )
    public void onCdr(Cdr cdr) {
        log.info("received cdr eventId={} mvno={} type={}", cdr.eventId(), cdr.mvnoId(), cdr.type());
        rawCdrRepository.save(RawCdrDocument.from(cdr));
        try {
            ratingService.rateAndStore(cdr);
        } catch (RateCardNotFoundException ex) {
            log.warn("rating skipped - {}", ex.getMessage());
        }
    }
}
