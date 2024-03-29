package alexgit95.batch.monnaiefilegenerator.services.impl;

import alexgit95.batch.monnaiefilegenerator.model.Medaille;
import alexgit95.batch.monnaiefilegenerator.model.MedalsCollection;
import alexgit95.batch.monnaiefilegenerator.services.ExportToFilesServices;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
@Component
public class ExportToFilesServicesImpl implements ExportToFilesServices {


    private Logger logger = LoggerFactory.getLogger(ExportToFilesServicesImpl.class);

    @Override
    public void exportToMarkDownFile(File srcFile, String content){
        File destFile =new File(srcFile.getParentFile(), "medals.md");
        try {
            FileUtils.write(destFile, content, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Erreur lors de l'ecriture du fichier markdown", e);
        }
    }

    @Override
    public void exportToJSONFile(File srcFile, MedalsCollection collection){
        File destFile =new File(srcFile.getParentFile(), "medals.js");
        File destFileWeb =new File(srcFile.getParentFile(), "medals-web.js");
        Gson gson = new Gson();
        String json = gson.toJson(collection);
        try {
            FileUtils.write(destFile, json, Charset.forName("UTF-8"));
            FileUtils.write(destFileWeb, "const data="+json, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Erreur lors de l'ecriture du fichier json", e);
        }

    }
}
