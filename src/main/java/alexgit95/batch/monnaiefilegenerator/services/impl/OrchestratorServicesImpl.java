package alexgit95.batch.monnaiefilegenerator.services.impl;

import alexgit95.batch.monnaiefilegenerator.MonnaiefilegeneratorApplication;
import alexgit95.batch.monnaiefilegenerator.model.Medaille;
import alexgit95.batch.monnaiefilegenerator.model.MedalsCollection;
import alexgit95.batch.monnaiefilegenerator.services.ExportToFilesServices;
import alexgit95.batch.monnaiefilegenerator.services.OrchestratorServices;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
@Component
public class OrchestratorServicesImpl implements OrchestratorServices {

    private Logger logger = LoggerFactory.getLogger(OrchestratorServicesImpl.class);

    @Value("${separator}")
    private  String separator;

    @Value("${sourceFilePath}")
    private String  pathname;
    @Value("${generateJSONfile}")
    private boolean generateJson;
    @Value("${generateMarkdownfile}")
    private boolean generateMarkdown;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Autowired
    private ExportToFilesServices exportToFilesServices;
    @Override
    public void execute() throws IOException {


        File inputCsvFile= new File(pathname);

        if(!inputCsvFile.exists()){
            logger.error("Le fichier {} n'existe pas", pathname);
            return;
        }

        List<String> readLines = FileUtils.readLines(inputCsvFile);
        logger.info("NB Lignes : {}", readLines.size()-2);
        Map<Integer,List<Medaille>> all= new TreeMap<Integer,List<Medaille>>();
        List<Medaille> allMedals = new ArrayList<>();

        //On passe les 2 premieres lignes qui sont des entetes
        for(int i=2;i<readLines.size();i++) {

            Medaille parseLine = parseLine(readLines.get(i));
            allMedals.add(parseLine);

            groupByDepartment(all, parseLine);
        }

        logger.info("Nombre de medaille recuperee : {}", allMedals.size());

        String output = buildMardownStringByDepartment(all);

        logger.debug(output);

        logger.info("Generation des fichiers sorties");
        if(generateJson){
            logger.info("Generation du json...");
            exportToFilesServices.exportToJSONFile(inputCsvFile, buildCollection(allMedals));
            logger.info("Fin Generation du json...");
        }

        if(generateMarkdown){
            logger.info("Generation du markdown...");
            exportToFilesServices.exportToMarkDownFile(inputCsvFile, output);
            logger.info("Fin Generation du markdown...");
        }
    }

    private String buildMardownStringByDepartment(Map<Integer, List<Medaille>> all) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<Integer,List<Medaille>> entry : all.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            sb.append(generateMardownForDep(entry.getKey(), entry.getValue()));

        }
        sb.append("\nContenu genere le "+sdf.format(new Date())+"\n");
        String output = sb.toString();
        return output;
    }

    private void groupByDepartment(Map<Integer, List<Medaille>> all, Medaille parseLine) {
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

    private MedalsCollection buildCollection(List<Medaille> allMedals){
        MedalsCollection collection = new MedalsCollection();
        collection.setNbMedals(allMedals.size());
        collection.setAllMedals(allMedals);
        collection.setExportDate(new Date());
        double somme=0;
        for(Medaille temp : allMedals){
            somme+=temp.getValue();
        }
        collection.setCollectionValue(somme);

        return collection;
    }


}
