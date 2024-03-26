package com.vivek.batvball.dao;

import java.sql.PreparedStatement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivek.batvball.entities.DayData;
import com.vivek.batvball.entities.Player;

@Service
public class PlayerDAO {
	
	@Autowired
	SessionFactory sf;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
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
		Player player = query.getSingleResult();
		ss.close();
		return player;
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
	
	@Transactional
	public void saveScoreCard(Player player) {
		Session ss = sf.openSession();
		Transaction tr = ss.beginTransaction();
		List <DayData> dayData = player.getDayDataList();
		ss.clear();
//		if(!isCardPresent)
		insertDayData(dayData.get(0));
//		else {
//			ss.merge(dayData.get(0));
//		}
		updatePlayer(player);
		tr.commit();
		ss.close();
	}
	
	
	private void insertDayData(DayData dayData) {
		String query = "insert into day_data values(?,?,?,?)";
		jdbcTemplate.update(connection -> {
			 PreparedStatement ps = connection.prepareStatement(query);
	            ps.setInt(2, dayData.getDayDataId().getPlayerId());
	            ps.setString(1, dayData.getDayDataId().getDate());
	            ps.setString(3,dayData.getScorecard());
	            ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
	            return ps;
		});		
	}

	private void updatePlayer(Player player) {
		String query = "update player set runs=?,dots=?,singles=?,doubles=?,threes=?,fours=?,sixes=?,dismissed=?,average=?"
				+ "where id=?";
		jdbcTemplate.update(connection -> {
			 PreparedStatement ps = connection.prepareStatement(query);
	            ps.setInt(1, player.getRuns());
	            ps.setInt(2, player.getDots());
	            ps.setInt(3, player.getSingles());
	            ps.setInt(4, player.getDoubles());
	            ps.setInt(5, player.getThrees());
	            ps.setInt(6, player.getFours());
	            ps.setInt(7, player.getSixes());
	            ps.setInt(8, player.getDismissed());
	            ps.setFloat(9, player.getAverage());
	            ps.setInt(10, player.getId());
	            return ps;
		});
	}


}
