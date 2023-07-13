package alexgit95.batch.monnaiefilegenerator.services;

import alexgit95.batch.monnaiefilegenerator.model.Medaille;
import alexgit95.batch.monnaiefilegenerator.model.MedalsCollection;

import java.io.File;
import java.util.List;

public interface ExportToFilesServices {

    void exportToMarkDownFile(File srcFile, String content);

    void exportToJSONFile(File srcFile, MedalsCollection collection);
}
