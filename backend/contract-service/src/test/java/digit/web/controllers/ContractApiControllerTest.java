package digit.web.controllers;

import digit.web.models.ContractCriteria;
import digit.web.models.ContractRequest;
import digit.web.models.ContractResponse;
import digit.web.models.ErrorRes;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import digit.TestConfiguration;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
* API tests for ContractApiController
*/
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(ContractApiController.class)
@Import(TestConfiguration.class)
public class ContractApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contractV1CreatePostSuccess() throws Exception {
        mockMvc.perform(post("/contract/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void contractV1CreatePostFailure() throws Exception {
        mockMvc.perform(post("/contract/v1/_create").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1SearchPostSuccess() throws Exception {
        mockMvc.perform(post("/contract/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void contractV1SearchPostFailure() throws Exception {
        mockMvc.perform(post("/contract/v1/_search").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void contractV1UpdatePostSuccess() throws Exception {
        mockMvc.perform(post("/contract/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
    }

    @Test
    public void contractV1UpdatePostFailure() throws Exception {
        mockMvc.perform(post("/contract/v1/_update").contentType(MediaType
        .APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
    }

}
