package com.vivek.batvball.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vivek.batvball.dto.ScoreCardInputDTO;
import com.vivek.batvball.entities.Player;
import com.vivek.batvball.service.PlayerService;

@Component
public class ScoreCardItemProcessor implements ItemProcessor<ScoreCardInputDTO, Player>{
	
	@Autowired
	PlayerService playerService;

	@Override
	public Player process(ScoreCardInputDTO item) throws Exception {
		return playerService.processInput(item);
	}

}
