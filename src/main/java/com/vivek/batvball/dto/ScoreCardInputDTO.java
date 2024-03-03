package com.vivek.batvball.dto;

public class ScoreCardInputDTO {

	private String dateString;
	private Integer playerId;
	private String scoreCard;
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public Integer getPlayerId() {
		return playerId;
	}
	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}
	public String getScoreCard() {
		return scoreCard;
	}
	public void setScoreCard(String scoreCard) {
		this.scoreCard = scoreCard;
	}
	
}
