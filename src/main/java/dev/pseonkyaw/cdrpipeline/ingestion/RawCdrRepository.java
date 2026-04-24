package dev.pseonkyaw.cdrpipeline.ingestion;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RawCdrRepository extends MongoRepository<RawCdrDocument, String> {
}
