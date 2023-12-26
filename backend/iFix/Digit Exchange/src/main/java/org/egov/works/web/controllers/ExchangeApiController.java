package org.egov.works.web.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@javax.annotation.Generated(value = "org.egov.codegen.SpringBootCodegen", date = "2023-12-26T11:42:32.468+05:30[Asia/Kolkata]")
@Controller
@RequestMapping("")
public class ExchangeApiController {

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    public ExchangeApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/exchange/v1/allocation", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1AllocationPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/bill", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1BillPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/demand", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1DemandPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/disbuse", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1DisbusePost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-allocation", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnAllocationPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-bill", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnBillPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-demand", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnDemandPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-disbuse", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnDisbusePost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-program", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnProgramPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-receipt", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnReceiptPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/on-sanction", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1OnSanctionPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/program", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1ProgramPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/receipt", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1ReceiptPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/exchange/v1/sanction", method = RequestMethod.POST)
    public ResponseEntity<Object> exchangeV1SanctionPost(@Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Object>(objectMapper.readValue("{  \"message\" : {    \"correlation_id\" : \"{}\",    \"error\" : {      \"code\" : \"err.request.bad\",      \"message\" : \"message\"    },    \"ack_status\" : \"ACK\",    \"timestamp\" : \"2000-01-23T04:56:07.000+00:00\"  }}", Object.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

}
