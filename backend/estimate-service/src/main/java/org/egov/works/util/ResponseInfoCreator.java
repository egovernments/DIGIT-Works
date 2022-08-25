package org.egov.works.util;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.APIInfo;
import org.egov.works.web.models.RequestHeader;
import org.egov.works.web.models.ResponseHeader;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResponseInfoCreator {

    public ResponseHeader createResponseInfoFromRequestInfo(final RequestHeader requestHeader, final Boolean success) {

        final String correlationId = requestHeader != null ? requestHeader.getCorrelationId() : "";
        final String ver = requestHeader != null && requestHeader.getApiInfo().getVersion() != null ? requestHeader.getApiInfo().getVersion() : "";
        Long ts = null;
        if (requestHeader != null)
            ts = requestHeader.getTs();
        final String sign = requestHeader != null && requestHeader.getSignature() != null ? requestHeader.getSignature() : "";
        final String msgId = requestHeader != null ? requestHeader.getMsgId() : "";
        final ResponseHeader.StatusEnum responseStatus = success ? ResponseHeader.StatusEnum.COMPLETED : ResponseHeader.StatusEnum.FAILED;

        APIInfo apiInfo = APIInfo.builder().version(ver).build();
        return ResponseHeader.builder().ts(ts).msgId(msgId).signature(sign)
                .status(responseStatus).build();
    }
}
