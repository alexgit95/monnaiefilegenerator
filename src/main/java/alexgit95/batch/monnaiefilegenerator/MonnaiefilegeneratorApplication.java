package alexgit95.batch.monnaiefilegenerator;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import alexgit95.batch.monnaiefilegenerator.model.Medaille;

@SpringBootApplication
public class MonnaiefilegeneratorApplication {
	
	private Logger logger = LoggerFactory.getLogger(MonnaiefilegeneratorApplication.class);
	
	private final String separator=";";
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public static void main(String[] args) {
		SpringApplication.run(MonnaiefilegeneratorApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String pathname = "monnaie.csv";
			//logger.info("Chemin du fichier:"+pathname);
			File inputCsvFile= new File(pathname);
			List<String> readLines = FileUtils.readLines(inputCsvFile);
			logger.info("NB Lignes:"+readLines.size());
			Map<Integer,List<Medaille>> all= new TreeMap<Integer,List<Medaille>>();
			for(int i=2;i<readLines.size();i++) {
				Medaille parseLine = parseLine(readLines.get(i));
				if(all.containsKey(parseLine.getDepartement())) {
					List<Medaille> list = all.get(parseLine.getDepartement());
					list.add(parseLine);
					all.put(parseLine.getDepartement(), list);
				}else {
					List<Medaille> list = new ArrayList<>();
					list.add(parseLine);
					all.put(parseLine.getDepartement(), list);
				}
			}
			StringBuilder sb = new StringBuilder();
			
			for (Map.Entry<Integer,List<Medaille>> entry : all.entrySet()) {
			    //System.out.println(entry.getKey() + "/" + entry.getValue());
			    sb.append(generateMardownForDep(entry.getKey(), entry.getValue()));
			    
			}
			sb.append("\nContenu genere le "+sdf.format(new Date())+"\n");
			String output = sb.toString();
			logger.info(output);
			
		};
	}
	
	
	private Medaille parseLine(String line) {
		//logger.info(line);
		String[] split = line.split(separator);
		Medaille output = new Medaille();
		output.setVille(split[4]);
		output.setNom(split[5]);
		//on eneleve le prefix monaco
		String departement = split[3].replaceAll("MC-", "");
		output.setCodePostal(Integer.valueOf(departement));
		output.setDepartement(output.getCodePostal()/1000);
		output.setValue(Double.parseDouble(split[8].replaceAll("€", "")));
		return output;
	}
	
	private String generateMardownForDep(int departement, List<Medaille> medailles) {
		StringBuilder sb  = new StringBuilder();
		sb.append("\n## "+departement+"\n\n");
		sb.append("| Nom | Ville| Valeur |\n");
		sb.append("| --- | --- | --- |\n");
		
		for (Medaille medaille : medailles) {
			sb.append("| "+medaille.getNom()+" | "+medaille.getVille()+" | "+medaille.getValue() +" | \n");
		}
		return sb.toString();
	}

}
