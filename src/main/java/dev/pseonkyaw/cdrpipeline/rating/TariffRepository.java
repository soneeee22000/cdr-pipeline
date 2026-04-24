package dev.pseonkyaw.cdrpipeline.rating;

import dev.pseonkyaw.cdrpipeline.domain.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TariffRepository extends JpaRepository<TariffPlan, TariffPlan.Key> {
}
