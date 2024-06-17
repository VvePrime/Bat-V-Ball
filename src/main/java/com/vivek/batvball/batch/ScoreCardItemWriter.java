package com.vivek.batvball.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vivek.batvball.dao.PlayerDAO;
import com.vivek.batvball.entities.Player;

@Component 
public class ScoreCardItemWriter implements ItemWriter<Player>{
	
	@Autowired
	PlayerDAO playerDAO;
	
	@Override
	public void write(Chunk<? extends Player> chunk) throws Exception {
		for(Player player: chunk.getItems())
			playerDAO.saveScoreCard(player);		
	}

}
