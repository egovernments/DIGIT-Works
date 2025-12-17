package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.tracer.model.CustomException;
import org.egov.works.web.models.Rates;
import org.egov.works.web.models.SorComposition;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.egov.works.config.ErrorConfiguration.SOR_COMPOSITION_NOT_FOUND_TIME_KEY;
import static org.egov.works.config.ErrorConfiguration.SOR_COMPOSITION_NOT_FOUND_TIME_MSG;

@Component
@Slf4j
public class CommonUtil {

    public Rates getApplicatbleRate(List<Rates> ratesList, Long givenTime) {
        Comparator<Rates> comparator = (o1, o2) -> {
            String validFrom1 = o1.getValidFrom();
            String validFrom2 = o2.getValidFrom();
            return validFrom1.compareTo(validFrom2);
        };

        Collections.sort(ratesList, comparator.reversed());
        for (Rates rate : ratesList) {
            long validFrom = parseTime(rate.getValidFrom());
            long validTo = parseTime(rate.getValidTo(), Long.MAX_VALUE); // Default to Long.MAX_VALUE if not valid

            if (givenTime >= validFrom && givenTime <= validTo) {
                return rate;
            }
        }
        return null; // Or throw an exception if no valid rate is found

    }

    private static long parseTime(String timeStr) {
        return parseTime(timeStr, Long.MIN_VALUE); // Default to Long.MIN_VALUE if not valid
    }

    private static long parseTime(String timeStr, long defaultValue) {
        try {
            return Long.parseLong(timeStr);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public SorComposition getApplicableSorComposition(List<SorComposition> sorCompositions, Long time) {
        Comparator<SorComposition> comparator = (o1, o2) -> {
            String validFrom1 = o1.getEffectiveFrom();
            String validFrom2 = o2.getEffectiveFrom();
            return validFrom1.compareTo(validFrom2);
        } ;
        Collections.sort(sorCompositions, comparator.reversed());
        for (SorComposition sorComposition : sorCompositions) {
            long validFrom = parseTime(sorComposition.getEffectiveFrom());
            long validTo = parseTime(sorComposition.getEffectiveTo(), Long.MAX_VALUE); // Default to Long.MAX_VALUE if not valid
            if (time >= validFrom && time <= validTo) {
                return sorComposition;
            }
        }

        throw new CustomException(SOR_COMPOSITION_NOT_FOUND_TIME_KEY, SOR_COMPOSITION_NOT_FOUND_TIME_MSG + sorCompositions.get(0).getSorId());
    }

}