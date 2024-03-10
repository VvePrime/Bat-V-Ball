package com.vivek.batvball.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivek.batvball.dao.PlayerDAO;
import com.vivek.batvball.dto.ScoreCardInputDTO;
import com.vivek.batvball.entities.DayData;
import com.vivek.batvball.entities.DayDataId;
import com.vivek.batvball.entities.Player;

@Service
public class PlayerService {
	
	@Autowired
	PlayerDAO playerDAO;
	
	public void processFile() {
		
	}
	
	public void processInput(ScoreCardInputDTO scoreCardDTO) {
		Player player = playerDAO.getPlayerDetailsById(scoreCardDTO.getPlayerId());
		if(player!=null && validateInputDateString(scoreCardDTO)) {
			insertScoreCard(scoreCardDTO,player);
		}
		else
			System.out.println("Invalid Player Id - player not present");
	}
	
	private boolean validateInputDateString(ScoreCardInputDTO scoreCardDTO) {
		return true;		
	}

	private void insertScoreCard(ScoreCardInputDTO scoreCardDTO, Player player) {
		Boolean isScoreCardPresent = calculateScore(scoreCardDTO,player);
		playerDAO.saveScoreCard(player,isScoreCardPresent);
	}
	
	private Boolean calculateScore(ScoreCardInputDTO scoreCardDTO, Player player) {
		List<Character> chars = scoreCardDTO.getScoreCard().chars().mapToObj(e->(char)e).collect(Collectors.toList());
		Integer dots,singles,doubles,threes,fours,sixes,dismissed,runs;
		dots = singles = doubles = threes = fours = sixes = dismissed = runs = 0;
		for(Character c : chars) {
			switch(c) {
				case '.':
					dots += 1;
					break;
				case '1':
					singles += 1;
					break;
				case '2':
					doubles += 1;
					break;
				case '3':
					threes += 1;
					break;
				case '4':
					fours += 1;
					break;
				case '6':
					sixes += 1;
					break;
				case 'w':
					dismissed += 1;
					break;
			}
		}
		runs = 1*singles + 2*doubles + 3*threes + 4*fours + 6*sixes;
		player.setDismissed(player.getDismissed()+dismissed);
		player.setDots(player.getDots()+dots);
		player.setSingles(player.getSingles()+singles);
		player.setDoubles(player.getDoubles()+doubles);
		player.setThrees(player.getThrees()+threes);
		player.setFours(player.getFours()+fours);
		player.setSixes(player.getSixes()+sixes);
		player.setRuns(player.getRuns()+runs);
		Float average;
		if(dismissed + player.getDismissed() > 0) average = (float) (player.getRuns()/player.getDismissed());
		else average = (float)player.getRuns();
		player.setAverage( average );
		
		
		//convert to map to get the value by date str and change and put and then finally convert to list and put it in player obj
		List<DayData> dayDataList = player.getDayData();
		List<DayData> dayDataFilteredList = null;
		
		if(!dayDataList.isEmpty())
			dayDataFilteredList = dayDataList.stream()
					.filter(s-> s.getDayDataId().getDate().equals(scoreCardDTO.getDateString()) && s.getDayDataId().getPlayer().getId()== scoreCardDTO.getPlayerId())
					.collect(Collectors.toList());
		if(dayDataFilteredList == null || dayDataFilteredList.isEmpty()) {
			DayData dayData = new DayData();
			dayData.setScorecard(scoreCardDTO.getScoreCard());
			DayDataId dayDataId = new DayDataId();
			dayDataId.setDate(scoreCardDTO.getDateString());
			dayDataId.setPlayer(player);
			dayData.setUpdatedOn(new Date());
			dayData.setDayDataId(dayDataId);
			player.setDayData(List.of(dayData));
			return false;
		}
		else {
			DayData dayDataCurr = dayDataFilteredList.get(0);
			dayDataCurr.setScorecard(dayDataCurr.getScorecard().concat(scoreCardDTO.getScoreCard()));
			dayDataCurr.setUpdatedOn(new Date());
			player.setDayData(List.of(dayDataCurr));
			return true;
		}		
	}

}
