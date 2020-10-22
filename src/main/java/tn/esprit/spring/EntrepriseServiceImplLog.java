package tn.esprit.spring;

import org.apache.log4j.Logger;

public class EntrepriseServiceImplLog {
	private static final Logger l= Logger.getLogger(EntrepriseServiceImplLog.class);

	public static void main(String[] args) {
		EntrepriseServiceImplLog entrepriseservice= new EntrepriseServiceImplLog();
		entrepriseservice.getAllPrducts();}
	public void getAllPrducts() {
		try{
		l.info("In getAllPrducts() : ");
		l.debug("Je vais lancer la divsion.");
		int i= 1/0;
		l.debug("Je viens de lancer la divsion. "+ i);
		l.debug("Je viens de finir l'opération X.");
		l.info("Out getAllPrducts() without errors.");
		}
		// TODO Auto-generated method stub

		catch(Exception e) { l.error("Erreur dans getAllPrducts() : "+ e); }
		}
		

}
