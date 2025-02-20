package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private static class Marche{
		private Etal[] etals;
		
		public Marche(int nbEtals) {
			etals= new Etal[nbEtals];
			for(int i = 0; i<nbEtals; i++) {
				etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		public int trouverEtalLibre() {
			int i = 0;
			for(Etal e : this.etals) {
				if(!e.isEtalOccupe()) {
					return i;
				}
				i++;
			}
			return -1;
		}
		
		public Etal[] trouverEtals(String produit){
			int nbTrouves = 0;
			for(Etal e : this.etals) {
				if(e.contientProduit(produit)) {
					nbTrouves++;
				}
			}
			int i = 0;
			Etal[] etalsTrouves = new Etal[nbTrouves];
			for(Etal et : this.etals) {
				if(et.contientProduit(produit)) {
					etalsTrouves[i]=et;
					i++;
				}
			}
			return etalsTrouves;
			
		}
		
		public Etal trouverVendeur(Gaulois gaulois){
			for(Etal e : this.etals) {
				if(e.getVendeur()==gaulois) {
					return e;
				}
			}
			return null;
		}
		
		public String afficherMarche() {
			StringBuilder string = new StringBuilder();
			int i = 0;
			for(Etal e : this.etals) {
					string.append(e.afficherEtal()+"\n");
					i++;
			}
			if(i!=etals.length-1) {
				string.append("Il reste " + (etals.length-i) + " étals non utilisés dans le marché.\n");
			}
			return string.toString();
		}
	}
	

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int indiceEtal = marche.trouverEtalLibre();
		if(indiceEtal!=-1) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtal+1) + ".\n");
		}
		else {
			chaine.append("Il n'y a plus d'etal libre !\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsTrouves = marche.trouverEtals(produit);
		if(etalsTrouves.length==0) {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit +" au marché.\n");
		}
		else if(etalsTrouves.length==1) {
			chaine.append("Seul le vendeur " + etalsTrouves[0].getVendeur().getNom() + " propose des " + produit + " au marché.\n");
		}
		else {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for(Etal e : etalsTrouves) {
				chaine.append("- " + e.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
//	public Etal rechercherEtal(Gaulois vendeur) {
//		
//	}
	
	
//	public String afficherMarche() {
//		
//	}
	
}