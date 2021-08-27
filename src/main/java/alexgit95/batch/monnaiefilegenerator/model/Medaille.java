package alexgit95.batch.monnaiefilegenerator.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
public class Medaille {

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public int getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(int codePostal) {
		this.codePostal = codePostal;
	}
	public int getDepartement() {
		return departement;
	}
	public void setDepartement(int departement) {
		this.departement = departement;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	private String nom;
	private String ville;
	private int codePostal;
	private int departement;
	private double value;
	
	
	
	
	
}
