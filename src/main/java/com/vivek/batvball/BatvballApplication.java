package com.vivek.batvball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vivek.dao.PlayerDAO;

@SpringBootApplication
public class BatvballApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatvballApplication.class, args);
		fetch();
	}
	
	public static void fetch() {
		PlayerDAO dao = new PlayerDAO();
		System.out.println("Sachin scored a total of "+dao.getRuns()+" runs in international cricket.");
	}

}
