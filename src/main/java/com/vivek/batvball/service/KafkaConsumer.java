package com.vivek.batvball.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "bat-v-ball", groupId = "umpires")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Batsman with id : " + record.key() + " has scored "+ record.value() +" on the current ball");
    }
}
