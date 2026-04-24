# Changelog

All notable changes to this project follow [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and [Semantic Versioning](https://semver.org/).

## [Unreleased]

### Added

- Portfolio-grade README with mermaid architecture, sequence, and tech-stack diagrams
- MIT license
- Design-decisions table explaining the dual-database split, idempotency key choice, micros arithmetic, and error-handling deserializer

## [0.1.0] — 2026-04-24

### Added

- Initial Spring Boot 3.5 scaffold (Java 21)
- `domain` package: `Cdr` record, `CdrType` enum, `TariffPlan` JPA entity with composite key, `RatedCdr` JPA entity
- `ingestion` package: `CdrKafkaConsumer`, `RawCdrDocument` MongoDB document, `RawCdrRepository`
- `rating` package: `RatingService` (idempotent on `event_id`), `TariffRepository`, `RatedCdrRepository` with `COALESCE(SUM)` reconciliation query
- `api` package: `ReconciliationController` (`GET /api/reconciliation/{mvnoId}`)
- `config` package: Kafka consumer factory with `ErrorHandlingDeserializer` + `JsonDeserializer`, topic bean for `cdr.events`
- Docker Compose with Kafka 3.8 (KRaft), MySQL 8.4, MongoDB 7
- Seed data for `tariff_plan` (7 rows covering FR + DE for `mvno-acme` and `mvno-tesla`)
- `scripts/produce-sample.sh` helper to publish a test CDR
