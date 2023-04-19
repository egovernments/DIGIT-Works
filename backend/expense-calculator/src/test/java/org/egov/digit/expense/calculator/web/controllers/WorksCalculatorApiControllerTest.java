package org.egov.digit.expense.calculator.web.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.egov.digit.expense.calculator.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
* API tests for WorksCalculatorApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(WorksCalculatorApiController.class)
@Import(TestConfiguration.class)
public class WorksCalculatorApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void worksCalculatorV1CalculatePostSuccess() throws Exception {
        mockMvc.perform(post("/works-calculator/v1/_calculate").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void worksCalculatorV1CalculatePostFailure() throws Exception {
        mockMvc.perform(post("/works-calculator/v1/_calculate").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void worksCalculatorV1EstimatePostSuccess() throws Exception {
        mockMvc.perform(post("/works-calculator/v1/_estimate").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void worksCalculatorV1EstimatePostFailure() throws Exception {
        mockMvc.perform(post("/works-calculator/v1/_estimate").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}
