ALTER TABLE eg_mb_measurement_measures
DROP COLUMN currentvalue;

ALTER TABLE eg_mb_measurement_details
ADD COLUMN cumulative NUMERIC;