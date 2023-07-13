package alexgit95.batch.monnaiefilegenerator;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import alexgit95.batch.monnaiefilegenerator.services.OrchestratorServices;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import alexgit95.batch.monnaiefilegenerator.model.Medaille;

@SpringBootApplication
public class MonnaiefilegeneratorApplication {
	

	@Autowired
	private OrchestratorServices orchestratorServices;

	public static void main(String[] args) {
		SpringApplication.run(MonnaiefilegeneratorApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			orchestratorServices.execute();

		};
	}






}
