package org.digit.exchange.utils;

import org.springframework.stereotype.Service;
import org.digit.exchange.models.fiscal.FiscalMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import com.jcraft.jsch.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class DispatcherUtil {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final WebClient webClient;
    private final JavaMailSender mailSender;

    private FiscalMessageMapperUtil mapper;

    public DispatcherUtil(@Autowired(required = false)KafkaTemplate<String, String> kafkaTemplate, @Autowired(required = false)JavaMailSender mailSender) {
        this.kafkaTemplate = kafkaTemplate;
        this.webClient = WebClient.create();
        this.mailSender = mailSender;
        this.mapper = new FiscalMessageMapperUtil();
    }

    public String dispatch(String url, FiscalMessage fiscalHeader, String fiscalMessageStr) {
        
        // ProgramRequestMessage programRequestMessage = new ProgramRequestMessage();
        // programRequestMessage.setHeader(fiscalHeader);        
        FiscalMessage fiscalMessage;
        fiscalMessage = this.mapper.formatMessage(fiscalHeader.getType(),fiscalMessageStr);

        if (url.startsWith("http://") || url.startsWith("https://")) {
            // HTTP call using WebClient
            Mono<String> responseMono = webClient.post()
                     .uri(url)
                     .body(Mono.just(fiscalMessage), FiscalMessage.class)
                     .retrieve()
                     .bodyToMono(String.class);
            String result = responseMono.block();
            return result;
        } 
        // else if (url.startsWith("kafka://")) {
        //     // Kafka message
        //     String[] parts = url.substring(8).split("@");
        //     String topic = parts[0];
        //     kafkaTemplate.send(topic, fiscalMessage);
        // } else if (url.startsWith("file://")) {
        //     // Write to file
        //     String filePath = url.substring(7);
        //     filePath = applyDynamicPath(filePath);
        //     try {
        //         Files.write(Paths.get(filePath), fiscalMessage.getBytes(), StandardOpenOption.CREATE);
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     }
        // } else if (url.startsWith("sftp://")) {
        //     // SFTP upload
        //     String[] parts = url.substring(7).split("@");
        //     String[] credentials = parts[0].split(":");
        //     String username = credentials[0];
        //     String password = credentials[1];
        //     String sftpPath = parts[1];
        //     sftpPath = applyDynamicPath(sftpPath);
        //     writeToSftp(sftpPath, username, password, fiscalMessage);
        // } else if (url.startsWith("mailto://")) {
        //     // Send email
        //     String emailAddress = url.substring(9);
        //     sendEmail(emailAddress, "Subject Here", fiscalMessage);
        // }
        // 
        return null;
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        mailSender.send(mailMessage);
    }

    private String applyDynamicPath(String path) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd/HH");
        return path.replace("{dynamic}", now.format(formatter));
    }

    private void writeToSftp(String sftpPath, String username, String password, String message) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            session = jsch.getSession(username, sftpPath, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

            try (InputStream is = new ByteArrayInputStream(message.getBytes())) {
                channelSftp.put(is, sftpPath);
            }

        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null) channelSftp.exit();
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }
}
