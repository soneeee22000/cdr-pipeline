package dev.pseonkyaw.cdrpipeline.rating;

import dev.pseonkyaw.cdrpipeline.domain.CdrType;

public class RateCardNotFoundException extends RuntimeException {
    public RateCardNotFoundException(String mvnoId, CdrType type, String countryIso2) {
        super("No tariff plan for mvno=" + mvnoId + " type=" + type + " country=" + countryIso2);
    }
}
