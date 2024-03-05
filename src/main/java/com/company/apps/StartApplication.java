package com.company.apps;

import com.company.apps.model.entity.Player;
import com.company.apps.repository.CSVRepository;
import com.company.apps.repository.MyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;

import java.util.List;

@Log4j2
@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    @Autowired
    private MyRepo myRepo;

    @Autowired
    ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        myRepo.save(Player.builder().playerID("123").nameFirst("Vlad").nameLast("Gon").build());
        List<Player> all = myRepo.findAll();


    }
}