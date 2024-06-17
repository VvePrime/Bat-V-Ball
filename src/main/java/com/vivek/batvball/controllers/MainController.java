package com.vivek.batvball.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.batvball.dao.PlayerDAO;
import com.vivek.batvball.dto.ScoreCardInputDTO;
import com.vivek.batvball.entities.Player;
import com.vivek.batvball.service.PlayerService;

@RestController
@RequestMapping(value="/api/bat-v-ball")
public class MainController {
	
	@Autowired
	PlayerDAO dao;
	
	@Autowired
	PlayerService playerService;
	
	@GetMapping(value="/is-player-present")
	public ResponseEntity<Boolean> getRunsById(@RequestParam("id") Integer id) {
		return new ResponseEntity<Boolean>(dao.isPlayerPresent(id), HttpStatus.OK);
	}
	
	@GetMapping(value="/get-six-hitter")
	public ResponseEntity<String> getSixHitter() {
		Player player = dao.getSixHitter();
		System.out.println(player.getName());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value="/get-best-avg-player")
	public ResponseEntity<String> getBestAvg() {
		Player player = dao.getHighAveragePlayer();
		System.out.println(player.getName());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping(value="/get-leading-run-scorer")
	public ResponseEntity<String> getHighScorer() {
		Player player = dao.getLeadingRunScorer();
		System.out.println(player.getName());
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/process-scorecard")
	public ResponseEntity<String> getRunsById(@RequestBody ScoreCardInputDTO scoreCardDTO) {
		playerService.saveScoreCard(scoreCardDTO);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@PostMapping(value="/process-file")
	public ResponseEntity<String> processFile(){
		playerService.processFile();
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
}
