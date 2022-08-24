package org.egov.works.service;

import lombok.extern.slf4j.Slf4j;
import org.egov.works.web.models.LetterOfIndentRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LetterOfIndentService {
    public LetterOfIndentRequest createLOI(LetterOfIndentRequest request){
        return request;
    }
}
