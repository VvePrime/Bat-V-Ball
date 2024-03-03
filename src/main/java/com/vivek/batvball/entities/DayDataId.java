package com.vivek.batvball.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DayDataId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="date")
	private String date;
	
	@Column(name="player_id")
	private Integer playerId;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	@Override
	public String toString() {
		return "DayDataId [date=" + date + ", playerId=" + playerId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, playerId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DayDataId other = (DayDataId) obj;
		return Objects.equals(date, other.date) && Objects.equals(playerId, other.playerId);
	}
	
	
}
