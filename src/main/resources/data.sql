INSERT INTO
  tariff_plan (
    mvno_id,
    type,
    country_iso2,
    unit_price_micros,
    currency
  )
VALUES
  ('mvno-acme', 'SMS_MO', 'FR', 50000, 'EUR'),
  ('mvno-acme', 'SMS_MT', 'FR', 0, 'EUR'),
  ('mvno-acme', 'VOICE_MO', 'FR', 20000, 'EUR'),
  ('mvno-acme', 'VOICE_MT', 'FR', 0, 'EUR'),
  ('mvno-acme', 'DATA', 'FR', 100, 'EUR'),
  ('mvno-tesla', 'SMS_MO', 'DE', 70000, 'EUR'),
  ('mvno-tesla', 'DATA', 'DE', 80, 'EUR') ON DUPLICATE KEY
UPDATE unit_price_micros =
VALUES
  (unit_price_micros);
