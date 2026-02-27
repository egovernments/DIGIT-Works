package org.egov.digit.expense.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.response.ResponseInfo;
import org.egov.digit.expense.web.models.Error;
import org.egov.digit.expense.web.models.ErrorRes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DataAccessExceptionHandler {

    private static final String ERROR_CODE = "QUERY_EXECUTION_ERROR";

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorRes> handleDataAccessException(DataAccessException ex) {
        log.error("DataAccessException caught at controller level", ex);

        Throwable rootCause = ex.getMostSpecificCause();
        String errorMessage = "Database query failed: "
                + (rootCause != null ? rootCause.getMessage() : ex.getMessage());

        Error error = Error.builder()
                .code(ERROR_CODE)
                .message(errorMessage)
                .description(errorMessage)
                .build();

        ErrorRes errorRes = ErrorRes.builder()
                .responseInfo(ResponseInfo.builder()
                        .status("failed")
                        .build())
                .errors(Collections.singletonList(error))
                .build();

        return new ResponseEntity<>(errorRes, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
