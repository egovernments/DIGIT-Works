package org.egov.ifms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
public class IfmsRestTemplateInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {

        log.info("Interceptor");

//        String reqBody = new String(body);
//        byte[] encryptedRequestBody = service.doEncryption(reqBody);
//        ClientHttpResponse response = execution.execute(request, encryptedRequestBody);
//        String responseBody = new String(response.getBody().readAllBytes());
//        byte[] decryptedResponseBodyBytes = service.doDecryption(responseBody);
//        String decryptedResponseBody = new String(decryptedResponseBodyBytes);
//
//        // prepare modified response
//        ClientHttpResponse decryptedRes = new ClientHttpResponse() {
//            @Override
//            public HttpHeaders getHeaders() {
//                return response.getHeaders();
//            }
//
//            @Override
//            public InputStream getBody() throws IOException {
//                // The expected modified response body to be populated here
//                return new ByteArrayInputStream(decryptedResponseBody.getBytes());
//            }
//
//            @Override
//            public HttpStatus getStatusCode() throws IOException {
//                return response.getStatusCode();
//            }
//
//            @Override
//            public int getRawStatusCode() throws IOException {
//                return response.getRawStatusCode();
//            }
//
//            @Override
//            public String getStatusText() throws IOException {
//                return response.getStatusText();
//            }
//
//            @Override
//            public void close() {
//
//            }
//        };
//        return decryptedRes;

        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
