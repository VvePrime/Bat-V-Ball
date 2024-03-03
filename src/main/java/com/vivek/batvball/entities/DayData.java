package com.vivek.batvball.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="day_data")
public class DayData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DayDataId dayDataId;
	
	@Column(name="scorecard")
	private String scorecard;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name="updated_on")
	private Date updatedOn;

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public DayDataId getDayDataId() {
		return dayDataId;
	}

	public void setDayDataId(DayDataId dayDataId) {
		this.dayDataId = dayDataId;
	}

	public String getScorecard() {
		return scorecard;
	}

	public void setScorecard(String scorecard) {
		this.scorecard = scorecard;
	}

	@Override
	public String toString() {
		return "DayData [dayDataId=" + dayDataId + ", scorecard=" + scorecard + ", updatedOn=" + updatedOn + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dayDataId, scorecard, updatedOn);
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
		return Objects.equals(dayDataId, other.dayDataId) && Objects.equals(scorecard, other.scorecard)
				&& Objects.equals(updatedOn, other.updatedOn);
	}

}
