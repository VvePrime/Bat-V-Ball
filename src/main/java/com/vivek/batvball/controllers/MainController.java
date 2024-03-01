package com.vivek.batvball.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vivek.batvball.dao.PlayerDAO;

@RestController
@RequestMapping(value="/api")
public class MainController {
	
	@Autowired
	PlayerDAO dao;
	
	@GetMapping(value="/get-runs")
	public ResponseEntity<String> getRunsById(@RequestParam("id") Integer id) {
		dao.getRuns(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
