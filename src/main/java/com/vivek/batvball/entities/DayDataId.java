package com.vivek.batvball.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class DayDataId implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name="date")
	private String date;
	
//	@Column(name="player_id")
//	private Integer playerId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="player_id")
	private Player player;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


	@Override
	public String toString() {
		return "DayDataId [date=" + date + ", player=" + player + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, player);
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
		return Objects.equals(date, other.date) && Objects.equals(player, other.player);
	}
	
	
}
