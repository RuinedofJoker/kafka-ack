package org.joker.kafka_ack.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MsgListener {

    @KafkaListener(id = "listener1", groupId = "listenerGroup1", topics = "topic1")
    public void listen(ConsumerRecord<?, ?> msg, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        boolean handleSuccess = false;
        try {
            Optional.ofNullable(msg.value()).ifPresent(value -> {
                log.info("接收到消息: {}", value);
            });
            handleSuccess = true;
        } catch (Exception e) {
            log.error("处理消息出现异常");
            handleSuccess = false;
        }
        if (handleSuccess) {
            ack.acknowledge();
        } else {
            // 拒绝消息，10秒后会再次接收到此消息
            ack.nack(10000);
        }
    }
}
