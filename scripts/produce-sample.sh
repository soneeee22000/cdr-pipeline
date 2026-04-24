#!/usr/bin/env bash
set -euo pipefail

KAFKA_CONTAINER="${KAFKA_CONTAINER:-cdr-kafka}"
TOPIC="${TOPIC:-cdr.events}"

read -r -d '' PAYLOAD <<'JSON' || true
{"eventId":"evt-0001","mvnoId":"mvno-acme","subscriberId":"sub-123","mnoId":"orange-fr","countryIso2":"FR","type":"SMS_MO","quantity":1,"occurredAt":"2026-04-20T10:15:00Z"}
JSON

echo "$PAYLOAD" | docker exec -i "$KAFKA_CONTAINER" \
  /opt/kafka/bin/kafka-console-producer.sh \
    --broker-list localhost:9092 \
    --topic "$TOPIC"

echo "Published sample CDR to $TOPIC"
