package com.vivek.batvball.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="day_data")
public class DayData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    private DayDataId dayDataId;

    @ManyToOne
    @MapsId("playerId")
    private Player player;
	
	@Column(name="scorecard")
	private String scorecard;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	public DayDataId getDayDataId() {
		return dayDataId;
	}

	public void setDayDataId(DayDataId dayDataId) {
		this.dayDataId = dayDataId;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getScorecard() {
		return scorecard;
	}

	public void setScorecard(String scorecard) {
		this.scorecard = scorecard;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dayDataId, player, scorecard, updatedOn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DayData other = (DayData) obj;
		return Objects.equals(dayDataId, other.dayDataId) && Objects.equals(player, other.player)
				&& Objects.equals(scorecard, other.scorecard) && Objects.equals(updatedOn, other.updatedOn);
	}

	@Override
	public String toString() {
		return "DayData [dayDataId=" + dayDataId + ", player=" + player + ", scorecard=" + scorecard + ", updatedOn="
				+ updatedOn + "]";
	}


}
