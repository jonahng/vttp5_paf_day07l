package com.jonah.vttp5_paf_day07l;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jonah.vttp5_paf_day07l.repos.TaskRepository;

@SpringBootApplication
public class Vttp5PafDay07lApplication implements CommandLineRunner{
	@Autowired
	private TaskRepository taskRepo;

	public static void main(String[] args) {
		SpringApplication.run(Vttp5PafDay07lApplication.class, args);
	}

	@Override
	public void run(String... args){
		//taskRepo.insertTask();
		//taskRepo.update();
		taskRepo.searchComments("extremely", "okay");
	}

}
