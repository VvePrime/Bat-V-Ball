package com.vivek.batvball.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries({
		@NamedQuery(name = "bestAverage", query = "SELECT p FROM Player p WHERE p.average = (SELECT MAX(p2.average) FROM Player p2)"),
		@NamedQuery(name = "highestRuns", query = "SELECT p FROM Player p WHERE p.runs = (SELECT MAX(p2.runs) FROM Player p2)") })
@Entity
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="runs")
	private Integer runs;
	
	@Column(name="dots")
	private Integer dots;
	
	@Column(name="singles")
	private Integer singles;
	
	@Column(name="doubles")
	private Integer doubles;
	
	@Column(name="threes")
	private Integer threes;
	
	@Column(name="fours")
	private Integer fours;
	
	@Column(name="sixes")
	private Integer sixes;
	
	@Column(name="dismissed")
	private Integer dismissed;
	
	@Column(name="average")
	private Float average;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DayData> dayDataList = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRuns() {
		return runs;
	}

	public void setRuns(Integer runs) {
		this.runs = runs;
	}

	public Integer getDots() {
		return dots;
	}

	public void setDots(Integer dots) {
		this.dots = dots;
	}

	public Integer getSingles() {
		return singles;
	}

	public void setSingles(Integer singles) {
		this.singles = singles;
	}

	public Integer getDoubles() {
		return doubles;
	}

	public void setDoubles(Integer doubles) {
		this.doubles = doubles;
	}

	public Integer getThrees() {
		return threes;
	}

	public void setThrees(Integer threes) {
		this.threes = threes;
	}

	public Integer getFours() {
		return fours;
	}

	public void setFours(Integer fours) {
		this.fours = fours;
	}

	public Integer getSixes() {
		return sixes;
	}

	public void setSixes(Integer sixes) {
		this.sixes = sixes;
	}

	public Integer getDismissed() {
		return dismissed;
	}

	public void setDismissed(Integer dismissed) {
		this.dismissed = dismissed;
	}

	public Float getAverage() {
		return average;
	}

	public void setAverage(Float average) {
		this.average = average;
	}

	public List<DayData> getDayDataList() {
		return dayDataList;
	}

	public void setDayDataList(List<DayData> dayDataList) {
		this.dayDataList = dayDataList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(average, dayDataList, dismissed, dots, doubles, fours, id, name, runs, singles, sixes,
				threes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(average, other.average) && Objects.equals(dayDataList, other.dayDataList)
				&& Objects.equals(dismissed, other.dismissed) && Objects.equals(dots, other.dots)
				&& Objects.equals(doubles, other.doubles) && Objects.equals(fours, other.fours)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name) && Objects.equals(runs, other.runs)
				&& Objects.equals(singles, other.singles) && Objects.equals(sixes, other.sixes)
				&& Objects.equals(threes, other.threes);
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", runs=" + runs + ", dots=" + dots + ", singles=" + singles
				+ ", doubles=" + doubles + ", threes=" + threes + ", fours=" + fours + ", sixes=" + sixes
				+ ", dismissed=" + dismissed + ", average=" + average + ", dayDataList=" + dayDataList + "]";
	}

}
