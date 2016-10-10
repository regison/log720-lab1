import java.util.ArrayList;

public class DossierImpl {
	private String nom;
	private String prenom;
	private String permisId;
	private String plaqueId;
	private int levelId;
	
	
	private ArrayList<Integer> infractionsArray ;
	private ArrayList<Integer> reactionsArray ;
	
	public DossierImpl (String nom, String prenom, String plaque, String permis)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.permisId = permis;
		this.plaqueId = plaque;
		
		infractionsArray = new ArrayList<Integer>();
		reactionsArray = new ArrayList<Integer>();
	}
	
	public void ajouterReactionAuDossierSelectionne(int idReaction) {
		this.reactionsArray.add(idReaction);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPermisId() {
		return permisId;
	}

	public void setPermisId(String permisId) {
		this.permisId = permisId;
	}

	public String getPlaqueId() {
		return plaqueId;
	}

	public void setPlaqueId(String plaqueId) {
		this.plaqueId = plaqueId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}



	public ArrayList<Integer> getInfractionsArray() {
		return infractionsArray;
	}


	public ArrayList<Integer> getReactionsArray() {
		return reactionsArray;
	}

}
