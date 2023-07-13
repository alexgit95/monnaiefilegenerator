package alexgit95.batch.monnaiefilegenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class MedalsCollection {

    private List<Medaille> allMedals;
    private int nbMedals;

    private Date exportDate;
    private double collectionValue;
}
