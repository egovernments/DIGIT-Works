package org.egov.works.util;

import org.egov.works.config.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Slf4j
@Component
public class UrlShortenerUtil {

//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Autowired
//    private ContractServiceConfiguration configs;
//
//
//    public String getShortenedUrl(String url) {
//
//        HashMap<String, String> body = new HashMap<>();
//        body.put("url", url);
//        StringBuilder builder = new StringBuilder(configs.getUrlShortnerHost());
//        builder.append(configs.getUrlShortnerEndpoint());
//        String res = restTemplate.postForObject(builder.toString(), body, String.class);
//
//        if (StringUtils.isEmpty(res)) {
//            log.error("URL_SHORTENING_ERROR", "Unable to shorten url: " + url);
//            ;
//            return url;
//        } else return res;
//    }
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ContractServiceConfiguration configs;


    public String getShortenedUrl(String url) {

        HashMap<String, String> body = new HashMap<>();
        body.put("url", url);
        StringBuilder builder = new StringBuilder(configs.getUrlShortnerHost());
        builder.append(configs.getUrlShortnerEndpoint());
        String res = restTemplate.postForObject(builder.toString(), body, String.class);

        if (StringUtils.isEmpty(res)) {
            log.error("URL_SHORTENING_ERROR", "Unable to shorten url: " + url);
            ;
            return url;
        } else return res;
    }


}