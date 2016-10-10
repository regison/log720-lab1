
public class DossierImpl extends DossierPOA{

	private string nom;
	private String prenom;
	private String permisId;
	private String plaqueId;
	private int levelId;
	
	public DossierImpl (String nom, String prenom, String plaque, String permis)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.permisId = permis;
		this.plaqueId = plaque;
	}
}
