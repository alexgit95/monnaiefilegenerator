package alexgit95.batch.monnaiefilegenerator.services;

import alexgit95.batch.monnaiefilegenerator.model.Medaille;

import java.io.File;
import java.util.List;

public interface ExportToFilesServices {

    void exportToMarkDownFile(File srcFile, String content);

    void exportToJSONFile(File srcFile, List<Medaille> allMedals);
}
