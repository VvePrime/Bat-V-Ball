package com.vivek.batvball.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.vivek.batvball.dao.PlayerDAO;
import com.vivek.batvball.dto.ScoreCardInputDTO;
import com.vivek.batvball.entities.DayData;
import com.vivek.batvball.entities.DayDataId;
import com.vivek.batvball.entities.Player;

@Service
public class PlayerService {

	@Autowired
	PlayerDAO playerDAO;
	
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public void processFile() {
		List<ScoreCardInputDTO> scoreCardList = readFile("src/main/resources/input-file.txt");
		for (ScoreCardInputDTO card : scoreCardList)
			processInput(card);
	}

	private List<ScoreCardInputDTO> readFile(String filePath) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(filePath);
		} catch (FileNotFoundException e) {
			logger.info("An exception occured while trying to read file.");
		}
		if (fileReader != null)
			return parseCSV(fileReader);
		else
			return null;
	}

	private static List<ScoreCardInputDTO> parseCSV(FileReader reader) {
		CsvToBean<ScoreCardInputDTO> csvToBean = new CsvToBeanBuilder<ScoreCardInputDTO>(reader)
				.withType(ScoreCardInputDTO.class).withIgnoreLeadingWhiteSpace(true).build();
		return csvToBean.parse();
	}

	public void processInput(ScoreCardInputDTO scoreCardDTO) {
		Player player = playerDAO.getPlayerDetailsById(scoreCardDTO.getPlayerId());
		if (null != player && validateInput(scoreCardDTO) && !isCardAlreadyPresent(scoreCardDTO, player)) {
			insertScoreCard(scoreCardDTO, player);
		}
	}

	private boolean validateInput(ScoreCardInputDTO scoreCardDTO) {
		//Boolean isDateValid = false;
		Boolean isCardValid = false;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        LocalDate date = LocalDate.parse(scoreCardDTO.getDateString(), formatter);
	        //isDateValid = date.isBefore(LocalDate.now());
		}
		catch(DateTimeParseException ex) {
			logger.error("Date format is invalid. required format is : dd-mm-yyyy");
		}
		String scoreCard = scoreCardDTO.getScoreCard();
		if(!scoreCard.contains("w") || (scoreCard.contains("w") && (scoreCard.indexOf("w") == scoreCard.length()-1)))
			isCardValid = true;
		if(!isCardValid)
			logger.info("Score Card is invalid. Runs shouldnt be present after wicket.");
//		if(!isDateValid)
//			logger.info("Date is invalid.");
		return  isCardValid; //isDateValid &&
	}

	private void insertScoreCard(ScoreCardInputDTO scoreCardDTO, Player player) {
		calculateScore(scoreCardDTO, player);
		playerDAO.saveScoreCard(player);
		logger.info("Score Card successfully saved.");
	}
	
	private Boolean isCardAlreadyPresent(ScoreCardInputDTO scoreCardDTO, Player player) {
		List<DayData> dayDataList = player.getDayDataList();
		List<DayData> dayDataFilteredList = null;
		if (!dayDataList.isEmpty())
			dayDataFilteredList = dayDataList.stream()
					.filter(s -> s.getDayDataId().getDate().equals(scoreCardDTO.getDateString())
							&& s.getDayDataId().getPlayerId() == scoreCardDTO.getPlayerId())
					.collect(Collectors.toList());
		if (null != dayDataFilteredList && !dayDataFilteredList.isEmpty()) {
			logger.info("Score card already present for this date.");
			return true;
		}
		return false;
	}

	private void calculateScore(ScoreCardInputDTO scoreCardDTO, Player player) {
		List<Character> chars = scoreCardDTO.getScoreCard().chars().mapToObj(e -> (char) e)
				.collect(Collectors.toList());
		Integer dots, singles, doubles, threes, fours, sixes, dismissed, runs;
		dots = singles = doubles = threes = fours = sixes = dismissed = runs = 0;
		for (Character c : chars) {
			switch (c) {
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
		runs = 1 * singles + 2 * doubles + 3 * threes + 4 * fours + 6 * sixes;
		player.setDismissed(player.getDismissed() + dismissed);
		player.setDots(player.getDots() + dots);
		player.setSingles(player.getSingles() + singles);
		player.setDoubles(player.getDoubles() + doubles);
		player.setThrees(player.getThrees() + threes);
		player.setFours(player.getFours() + fours);
		player.setSixes(player.getSixes() + sixes);
		player.setRuns(player.getRuns() + runs);
		Float average;
		if (dismissed + player.getDismissed() > 0)
			average = (float) ((float)player.getRuns() / (float)player.getDismissed());
		else
			average = (float) player.getRuns();
		player.setAverage(average);

			DayData dayData = new DayData();
			dayData.setScorecard(scoreCardDTO.getScoreCard());
			DayDataId dayDataId = new DayDataId();
			dayDataId.setDate(scoreCardDTO.getDateString());
			dayDataId.setPlayerId(player.getId());
//			dayData.setPlayer(player);
			dayData.setUpdatedOn(new Date());
			dayData.setDayDataId(dayDataId);
			player.setDayDataList(List.of(dayData));
	}

}
