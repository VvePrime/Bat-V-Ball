package com.vivek.batvball.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.vivek.batvball.entities.DayData;
import com.vivek.batvball.entities.Player;

@Service
public class PlayerDAO {
	
	public Player getPlayerDetailsById(Integer id){
		Configuration con = new Configuration().addAnnotatedClass(Player.class).addAnnotatedClass(DayData.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		Player player = ss.find(Player.class, id);		
		ss.close();
		return player;
	}
	
	public Boolean isPresent(Integer id){
		Configuration con = new Configuration().addAnnotatedClass(Player.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		Player player = ss.find(Player.class, id);		
		if(player != null)
			return true;
		return false;
	}
	
	public Player getLeadingRunScorer() {
		Configuration con = new Configuration().addAnnotatedClass(Player.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		List < Player > players = ss.createNamedQuery("highestRuns", Player.class).getResultList();
		ss.close();
		return players.get(0);
	}
	
	public Player getSixHitter() {
		Configuration con = new Configuration().addAnnotatedClass(Player.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		Query query =ss.createQuery("SELECT p FROM Player p WHERE p.sixes = (SELECT MAX(p2.sixes) FROM Player p2)");
		List<Player> players = query.getResultList();
		ss.close();
		return players.get(0);
	}
	
	public Player getHighAveragePlayer() {
		Configuration con = new Configuration().addAnnotatedClass(Player.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		List < Player > players = ss.createNamedQuery("bestAverage", Player.class).getResultList();
		ss.close();
		return players.get(0);
	}
//	
//	public Long getRuns(Integer id) {
//		Configuration con = new Configuration().addAnnotatedClass(Player.class);
//		SessionFactory sf = con.buildSessionFactory();
//		Session ss = sf.openSession();
//		Query q= ss.createQuery("select p.runs from Player p where p.id= :id");
//		q.setInteger("id", id);
//		List<Long> runs = q.getResultList();
//		return runs.get(0);
//	}
	
	public void saveScoreCard(Player player, Boolean isCardPresent) {
		Configuration con = new Configuration().addAnnotatedClass(Player.class).addAnnotatedClass(DayData.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		Transaction tr = ss.beginTransaction();
		ss.update(player);
		List <DayData> dayData = player.getDayData();
		if(!isCardPresent)
			ss.save(dayData.get(0));
		else {
			ss.update(dayData.get(0));
		}
		tr.commit();
		ss.close();
	}

}
