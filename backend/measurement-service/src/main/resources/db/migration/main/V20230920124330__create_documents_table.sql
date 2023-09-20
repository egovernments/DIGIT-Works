ALTER TABLE eg_mb_measurement_details
DROP COLUMN cumulative;

ALTER TABLE eg_mb_measurement_measures
ADD COLUMN cumulative NUMERIC;