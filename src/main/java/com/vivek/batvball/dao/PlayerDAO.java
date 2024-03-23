package com.vivek.batvball.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivek.batvball.entities.DayData;
import com.vivek.batvball.entities.Player;

@Service
public class PlayerDAO {
	
	@Autowired
	SessionFactory sf;
	
	public Player getPlayerDetailsById(Integer id){
		Session ss = sf.openSession();
		Player player = ss.find(Player.class, id);		
		ss.close();
		return player;
	}
	
	public Boolean isPresent(Integer id){
		Session ss = sf.openSession();
		Player player = ss.find(Player.class, id);		
		if(player != null)
			return true;
		return false;
	}
	
	public Player getLeadingRunScorer() {
		Session ss = sf.openSession();
		List < Player > players = ss.createNamedQuery("highestRuns", Player.class).getResultList();
		ss.close();
		return players.get(0);
	}
	
	public Player getSixHitter() {
		Session ss = sf.openSession();
		Query<Player> query =ss.createQuery("SELECT p FROM Player p WHERE p.sixes = (SELECT MAX(p2.sixes) FROM Player p2)", Player.class);
		List<Player> players = query.getResultList();
		ss.close();
		return players.get(0);
	}
	
	public Player getHighAveragePlayer() {
		Session ss = sf.openSession();
		List < Player > players = ss.createNamedQuery("bestAverage", Player.class).getResultList();
		ss.close();
		return players.get(0);
	}
	
	public Integer getRuns(Integer id) {
		Session ss = sf.openSession();
		Query<Integer> q= ss.createNativeQuery("select p.runs from Player p where p.id= :id", Integer.class);
		q.setParameter("id", id);
		List<Integer> runs = q.getResultList();
		return runs.get(0);
	}
	
	public void saveScoreCard(Player player, Boolean isCardPresent) {
		Session ss = sf.openSession();
		Transaction tr = ss.beginTransaction();
		List <DayData> dayData = player.getDayDataList();
		ss.clear();
		if(!isCardPresent)
			ss.save(dayData.get(0));
		else {
			ss.update(dayData.get(0));
		}
		ss.update(player);
		tr.commit();
		ss.close();
	}


}
