package com.vivek.batvball.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vivek.batvball.dto.ScoreCardInputDTO;

@Service
public class KafkaConsumer {

	@Autowired
	PlayerService playerService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @KafkaListener(topics = "bat-v-ball", groupId = "umpires")
    public void listen(ConsumerRecord<String, String> record) {
    	ScoreCardInputDTO dto = new ScoreCardInputDTO();
    	dto.setPlayerId(Integer.parseInt(record.key()));
    	dto.setScoreCard(record.value());
    	dto.setDateString(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
    	playerService.processInput(dto);
       logger.info( record.value() +" is todays Score Card of the Batsman with id : " + record.key() +"date: "+ dto.getDateString());
    }
}
