package com.vivek.batvball.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import com.vivek.batvball.entities.Player;

@Service
public class PlayerDAO {
	
	public Player getPlayerDetailsById(){
		Configuration con = new Configuration().addAnnotatedClass(Player.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		Player player = ss.find(Player.class, 10);		
		return player;
	}
	
	public Long getRuns(Integer id) {
		Configuration con = new Configuration().addAnnotatedClass(Player.class);
		SessionFactory sf = con.buildSessionFactory();
		Session ss = sf.openSession();
		Query q= ss.createQuery("select p.runs from Player p where p.id= :id");
		q.setInteger("id", id);
		List<Long> runs = q.getResultList();
		return runs.get(0);
	}

}
