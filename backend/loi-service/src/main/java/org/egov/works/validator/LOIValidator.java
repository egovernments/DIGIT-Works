package org.egov.works.validator;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.util.MDMSUtils;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LOIValidator {

    @Autowired
    private MDMSUtils mdmsUtils;

    public void validateCreateLOI(LetterOfIndentRequest request) {
//       TODO:  1. Format Validations
//       TODO:  2. Master Data Validation
//       TODO:  3. Check workPackageNumber
//       TODO:  4. Check contractorId
//       TODO:  5. (If present) validate oicId
//       TODO:  6. Throw custom error map
    }

}
