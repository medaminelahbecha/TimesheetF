package tn.esprit.spring.services;

import java.util.ArrayList;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {
	private static final Logger l= Logger.getLogger(EntrepriseServiceImpl.class);


	@Autowired
	EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	//cv
	public Entreprise ajouterEntreprise(Entreprise entreprise) {


		l.info("In addEntrprise : " + entreprise); 

		Entreprise e =entrepriseRepoistory.save(entreprise);
		l.info("Out of addEntreprise. " ); 

		return e;
	}
	//cv
	public Departement ajouterDepartement(Departement dep) {
		l.info("In  addDepartement : " + dep); 

		Departement deprt =deptRepoistory.save(dep);
		l.info("Out of addDepartement. " ); 

		return deprt;
	}

	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		l.info("In  affecterDepartementAEntreprise : departementId, entrepriseId"+ depId  + entrepriseId); 

		//Le bout Master de cette relation N:1 est departement  
		//donc il faut rajouter l'entreprise a departement 
		// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
		//Rappel : la classe qui contient mappedBy represente le bout Slave
		//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		l.info(" entrepriseId" + entrepriseId); 

		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		l.info(" departementId "+ depId  ); 

		depManagedEntity.setEntreprise(entrepriseManagedEntity);
		deptRepoistory.save(depManagedEntity);
		l.info("departement  affecté"); 

		l.info("Out of affecterDepartementAEntreprise. " ); 


	}

	public List<Entreprise> retrieveAllEntreprises() {
		l.info("In  retrieveAllEntreprises : "); 
		List<Entreprise> entreprises =  (List<Entreprise>) entrepriseRepoistory.findAll();  
		for (Entreprise entreprise : entreprises) {
			l.debug("entreprise +++ : " + entreprise);
		}
		l.info("Out of retrieveAllEntreprises."); 
		return entreprises;
	}

	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		l.info("In  getAllDepartementByEntreprise : entrepriseId" + entrepriseId); 

		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		l.info("entreprise  : " + entrepriseManagedEntity);

		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			l.info("departements of entreprise : " + entrepriseManagedEntity);

			depNames.add(dep.getName());


		}
		l.info("Out of getAllDepartementByEntreprise. " ); 

		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		l.info("in  deleteEntreprise id = " + entrepriseId);
		Entreprise e =entrepriseRepoistory.findById(entrepriseId).get();	
		entrepriseRepoistory.delete(e);
		l.info("entreprise deleted." +e.getName() );
		l.info("out of  deleteentreprise");


	}

	@Transactional
	//delete byId
	public void deleteDepartementById(int depId) {
		l.info("in  deleteDepartement id = " + depId);

		Departement d=deptRepoistory.findById(depId).get();
		deptRepoistory.delete(d);

		l.info("departement deleted." +d.getName() );
		l.info("out of  deleteDepartement");
	}
	//cv
	public Entreprise getEntrepriseById(int entrepriseId) {
		l.info("in  getEntreprise id = " + entrepriseId);

		Entreprise e=entrepriseRepoistory.findById(entrepriseId).get();
		l.info("entreprise returned : " + e);
		return e;
	}

}
