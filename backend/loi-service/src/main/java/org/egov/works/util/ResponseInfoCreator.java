package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.common.contract.request.RequestInfo;
import org.egov.common.contract.response.ResponseInfo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseInfoCreator {

    public ResponseInfo createResponseInfoFromRequestInfo(final RequestInfo requestHeader, final Boolean success) {

        final String correlationId = requestHeader != null ? requestHeader.getCorrelationId() : "";
        Long ts = null;
        if (requestHeader != null)
            ts = requestHeader.getTs();
        final String msgId = requestHeader != null ? requestHeader.getMsgId() : "";
        return ResponseInfo.builder().ts(ts).msgId(msgId).status("COMPLETED").build();
    }
}
