package org.joker.kafka_ack.controller;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/msg")
@Slf4j
public class MsgController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @PostMapping
    public String sendMsg(@RequestBody String msg) throws ExecutionException, InterruptedException {
        ListenableFuture sendFuture = kafkaTemplate.send("topic1", msg);
        log.info("SendResult :{}", JSON.toJSONString(sendFuture.get()));
        return true + "";
    }
}
