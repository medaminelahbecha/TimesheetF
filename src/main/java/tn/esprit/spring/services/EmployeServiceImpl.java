package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import org.apache.log4j.Logger;


@Service
public class EmployeServiceImpl implements IEmployeService {
	private static final Logger logger = Logger.getLogger(EmployeServiceImpl.class);


	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	@Override
	public Employe authenticate(String login, String password) {
		logger.info("Authentification");
		return employeRepository.getEmployeByEmailAndPassword(login, password);
		
	}

	@Override
	public int addOrUpdateEmploye(Employe employe) {
		logger.info("add or update employes");
		employeRepository.save(employe);
		logger.info("operation termine avec succe");
		return employe.getId();
		
	}

	@Override
	public Employe mettreAjourEmailByEmployeId(String email, int employeId) {
		logger.info("update EmailEmploye by employeID");
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);
		logger.info("update avec succé");
		return employe;
	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {
		logger.info("Affecter employer a un departement");
		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		if (depManagedEntity.getEmployes() == null) {

			List<Employe> employes = new ArrayList<>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		} else {

			depManagedEntity.getEmployes().add(employeManagedEntity);
		}
		logger.info("Au cour de l'affecctation");

		// à ajouter?
		deptRepoistory.save(depManagedEntity);
		logger.info("Affectation terminé");

	}

	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		logger.info("DesAffecter un employer du departement");
		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for (int index = 0; index < employeNb; index++) {
			if (dep.getEmployes().get(index).getId() == employeId) {
				dep.getEmployes().remove(index);
				break;// a revoir
			}
		}
		logger.info("Desaffectation terminé");
	}

	// Tablesapce (espace disque)

	public int ajouterContrat(Contrat contrat) {
		logger.info("Ajouter Un contrat");
		contratRepoistory.save(contrat);
		logger.info("contrat ajouter");
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		logger.info("Affecter contrat a un employer");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		logger.info("l'employer "+employeManagedEntity.getNom() +"a un contrat");

	}

	public String getEmployePrenomById(int employeId) {
		logger.info("Get le prenom de l'employe Par l id ");
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		logger.info("voici l'employe "+employeManagedEntity.getNom() );
		return employeManagedEntity.getPrenom();
	}

	public void deleteEmployeById(int employeId) {
		logger.info("effacer un employer");
		Employe employe = employeRepository.findById(employeId).get();

		// Desaffecter l'employe de tous les departements
		// c'est le bout master qui permet de mettre a jour
		// la table d'association
		for (Departement dep : employe.getDepartements()) {
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
		logger.info("employe " +employe.getNom() +" effacer avec succé");
	}

	public void deleteContratById(int contratId) {
		logger.info("effacer un contrat");
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		contratRepoistory.delete(contratManagedEntity);
		logger.info("contrat "+ contratManagedEntity.getReference() +" effacer");

	}

	public int getNombreEmployeJPQL() {
		logger.info("afficher le nombre des employes");
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		logger.info("afficher les noms des employes");
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		logger.info("afficher les employe par entreprise");
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		logger.info("mise a jour de l'email d'un employe");
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	public void deleteAllContratJPQL() {
		logger.info("effacer un contrat");
		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
		logger.info("afficher le salaire d'un employe");
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		logger.info("afficher le salaire d'un employe dans un departement");
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		logger.info("afficher timeSheets par mission et date ");
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
		logger.info("T");
		return (List<Employe>) employeRepository.findAll();
	}

}