package org.digit.exchange.repository;

import org.digit.exchange.web.models.RequestMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestMessageRepository extends JpaRepository<RequestMessage, String> {
    // // Custom query methods
    List<RequestMessage> findByHeaderSenderId(String senderId);
    List<RequestMessage> findByHeaderReceiverId(String receiverId);
}
