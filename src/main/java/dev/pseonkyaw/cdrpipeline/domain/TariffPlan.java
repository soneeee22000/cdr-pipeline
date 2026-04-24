package dev.pseonkyaw.cdrpipeline.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tariff_plan")
@IdClass(TariffPlan.Key.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffPlan {

    @Id
    @Column(name = "mvno_id", nullable = false, length = 64)
    private String mvnoId;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 16)
    private CdrType type;

    @Id
    @Column(name = "country_iso2", nullable = false, length = 2)
    private String countryIso2;

    @Column(name = "unit_price_micros", nullable = false)
    private long unitPriceMicros;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    public BigDecimal rate(long quantity) {
        return BigDecimal.valueOf(unitPriceMicros)
                .multiply(BigDecimal.valueOf(quantity))
                .movePointLeft(6);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {
        private String mvnoId;
        private CdrType type;
        private String countryIso2;
    }
}
