package org.egov.works.web.controllers;


import org.egov.works.web.models.AnalysisRequest;
import org.egov.works.web.models.ErrorRes;
import org.egov.works.web.models.JobSchedulerRequest;
import org.egov.works.web.models.JobSchedulerResponse;
import org.egov.works.web.models.JobSchedulerSearchCriteria;
import org.egov.works.web.models.RateAnalysisResponse;
import org.egov.works.web.models.RatesResponse;
    import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;
import java.util.*;

    import javax.validation.constraints.*;
    import javax.validation.Valid;
    import javax.servlet.http.HttpServletRequest;
        import java.util.Optional;
@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2024-05-22T17:59:35.524035+05:30[Asia/Kolkata]")
@Controller
    @RequestMapping("")
    public class RateAnalysisApiController{

        private final ObjectMapper objectMapper;

        private final HttpServletRequest request;

        @Autowired
        public RateAnalysisApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
        }

                @RequestMapping(value="/rate-analysis/v1/_calculate", method = RequestMethod.POST)
                public ResponseEntity<RateAnalysisResponse> rateAnalysisV1CalculatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody AnalysisRequest body) {
                        String accept = request.getHeader("Accept");
                            if (accept != null && accept.contains("application/json")) {
                            try {
                            return new ResponseEntity<RateAnalysisResponse>(objectMapper.readValue("{  \"rateAnalysis\" : [ {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"lineItems\" : [ {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    }, {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"analysisQuantity\" : 100,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\",    \"status\" : \"ACTIVE\"  }, {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"lineItems\" : [ {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    }, {      \"additionalDetials\" : { },      \"targetId\" : \"SOR00102\",      \"id\" : \"8d7d883e-c840-4d6a-9938-44760759f1ac\",      \"type\" : \"SOR\",      \"amountDetails\" : [ {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      }, {        \"amount\" : 98765,        \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",        \"heads\" : \"CA.9\",        \"type\" : \"FIXED\"      } ]    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"analysisQuantity\" : 100,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\",    \"status\" : \"ACTIVE\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", RateAnalysisResponse.class), HttpStatus.NOT_IMPLEMENTED);
                            } catch (IOException e) {
                            return new ResponseEntity<RateAnalysisResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            }

                        return new ResponseEntity<RateAnalysisResponse>(HttpStatus.NOT_IMPLEMENTED);
                }

                @RequestMapping(value="/rate-analysis/v1/_create", method = RequestMethod.POST)
                public ResponseEntity<RatesResponse> rateAnalysisV1CreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody AnalysisRequest body) {
                        String accept = request.getHeader("Accept");
                            if (accept != null && accept.contains("application/json")) {
                            try {
                            return new ResponseEntity<RatesResponse>(objectMapper.readValue("{  \"rates\" : {    \"isBasicVariant\" : true,    \"quantity\" : 1,    \"description\" : \"description\",    \"sorVariant\" : \"NA\",    \"sorSubType\" : \"Earth Works\",    \"amountDetails\" : [ {      \"amount\" : 98765,      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"heads\" : \"CA.9\",      \"type\" : \"FIXED\"    }, {      \"amount\" : 98765,      \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",      \"heads\" : \"CA.9\",      \"type\" : \"FIXED\"    } ],    \"uom\" : \"Cubic Meter\",    \"sorType\" : \"Works\",    \"rate\" : 650,    \"tenantId\" : \"pb.jalandhar OR dwss\",    \"sorId\" : \"123432-234jsd-23823d-12bdqq\",    \"id\" : \"251c51eb-e970-4e01-a99a-70136c47a934\",    \"effectiveFrom\" : \"effectiveFrom\",    \"sorCode\" : \"SOR00101\"  },  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", RatesResponse.class), HttpStatus.NOT_IMPLEMENTED);
                            } catch (IOException e) {
                            return new ResponseEntity<RatesResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            }

                        return new ResponseEntity<RatesResponse>(HttpStatus.NOT_IMPLEMENTED);
                }

                @RequestMapping(value="/rate-analysis/v1/scheduler/_create", method = RequestMethod.POST)
                public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerCreatePost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody JobSchedulerRequest body) {
                        String accept = request.getHeader("Accept");
                            if (accept != null && accept.contains("application/json")) {
                            try {
                            return new ResponseEntity<JobSchedulerResponse>(objectMapper.readValue("{  \"scheduledJobs\" : [ {    \"jobId\" : \"JOB-2223-000051\",    \"scheduledOn\" : \"1712686291000\",    \"rateEffectiveFrom\" : \"1715278291000\",    \"noOfSorScheduled\" : 2,    \"auditDetails\" : {      \"lastModifiedTime\" : 6,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 0    },    \"tenantId\" : \"tenantId\",    \"id\" : \"18222916-e29d-4e83-a15c-a5575059811b\",    \"sorDetails\" : [ {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    }, {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    } ],    \"status\" : \"IN PROGRESS\"  }, {    \"jobId\" : \"JOB-2223-000051\",    \"scheduledOn\" : \"1712686291000\",    \"rateEffectiveFrom\" : \"1715278291000\",    \"noOfSorScheduled\" : 2,    \"auditDetails\" : {      \"lastModifiedTime\" : 6,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 0    },    \"tenantId\" : \"tenantId\",    \"id\" : \"18222916-e29d-4e83-a15c-a5575059811b\",    \"sorDetails\" : [ {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    }, {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    } ],    \"status\" : \"IN PROGRESS\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", JobSchedulerResponse.class), HttpStatus.NOT_IMPLEMENTED);
                            } catch (IOException e) {
                            return new ResponseEntity<JobSchedulerResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            }

                        return new ResponseEntity<JobSchedulerResponse>(HttpStatus.NOT_IMPLEMENTED);
                }

                @RequestMapping(value="/rate-analysis/v1/scheduler/_search", method = RequestMethod.POST)
                public ResponseEntity<JobSchedulerResponse> rateAnalysisV1SchedulerSearchPost(@Parameter(in = ParameterIn.DEFAULT, description = "", schema=@Schema()) @Valid @RequestBody JobSchedulerSearchCriteria body) {
                        String accept = request.getHeader("Accept");
                            if (accept != null && accept.contains("application/json")) {
                            try {
                            return new ResponseEntity<JobSchedulerResponse>(objectMapper.readValue("{  \"scheduledJobs\" : [ {    \"jobId\" : \"JOB-2223-000051\",    \"scheduledOn\" : \"1712686291000\",    \"rateEffectiveFrom\" : \"1715278291000\",    \"noOfSorScheduled\" : 2,    \"auditDetails\" : {      \"lastModifiedTime\" : 6,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 0    },    \"tenantId\" : \"tenantId\",    \"id\" : \"18222916-e29d-4e83-a15c-a5575059811b\",    \"sorDetails\" : [ {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    }, {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    } ],    \"status\" : \"IN PROGRESS\"  }, {    \"jobId\" : \"JOB-2223-000051\",    \"scheduledOn\" : \"1712686291000\",    \"rateEffectiveFrom\" : \"1715278291000\",    \"noOfSorScheduled\" : 2,    \"auditDetails\" : {      \"lastModifiedTime\" : 6,      \"createdBy\" : \"createdBy\",      \"lastModifiedBy\" : \"lastModifiedBy\",      \"createdTime\" : 0    },    \"tenantId\" : \"tenantId\",    \"id\" : \"18222916-e29d-4e83-a15c-a5575059811b\",    \"sorDetails\" : [ {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    }, {      \"failureReason\" : \"failureReason\",      \"sorId\" : \"f8b0f90b-0f09-4512-a0a7-21aaa47600f6\",      \"additionalDetails\" : { },      \"sorCode\" : \"SOR-00123\",      \"status\" : \"status\"    } ],    \"status\" : \"IN PROGRESS\"  } ],  \"responseInfo\" : {    \"ver\" : \"ver\",    \"resMsgId\" : \"resMsgId\",    \"msgId\" : \"msgId\",    \"apiId\" : \"apiId\",    \"ts\" : 0,    \"status\" : \"SUCCESSFUL\"  }}", JobSchedulerResponse.class), HttpStatus.NOT_IMPLEMENTED);
                            } catch (IOException e) {
                            return new ResponseEntity<JobSchedulerResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
                            }
                            }

                        return new ResponseEntity<JobSchedulerResponse>(HttpStatus.NOT_IMPLEMENTED);
                }

        }
