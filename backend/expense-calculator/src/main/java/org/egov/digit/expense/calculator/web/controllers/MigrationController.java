package org.egov.digit.expense.calculator.web.controllers;

import org.egov.digit.expense.calculator.util.SorMigrationUtil;
import org.egov.digit.expense.calculator.web.models.RequestInfoWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("")
public class MigrationController {
    private SorMigrationUtil sorMigrationUtil;

    public MigrationController(SorMigrationUtil sorMigrationUtil) {
        this.sorMigrationUtil = sorMigrationUtil;
    }
    @RequestMapping(value = "/_migrate/{key}", method = RequestMethod.POST)
    public ResponseEntity<String> migrateSor(@RequestBody RequestInfoWrapper requestInfoWrapper, @PathVariable String key) {


        return new ResponseEntity<>(sorMigrationUtil.migrateSor(requestInfoWrapper.getRequestInfo(),
                key), HttpStatus.OK);


    }
}
