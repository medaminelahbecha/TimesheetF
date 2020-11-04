package tn.esprit.spring;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployerTest {

	@Autowired
	IEmployeService employeService;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;

	@Test
	public void testAddEmployer() {
		Employe employer = new Employe("amine", "lahbecha", "amine@gmail.com", true, Role.INGENIEUR);
		int employerAdd = employeService.addOrUpdateEmploye(employer);
		assertEquals(employer.getId(), employerAdd);
	}

	@Test
	public void testUpdateEmploye() {
		Employe employer = new Employe(1, "mohamed amine", "lahbecha", "amine@gmail.com", "12345", true,
				Role.INGENIEUR);
		int employerAdd = employeService.addOrUpdateEmploye(employer);
		assertEquals(employer.getId(), employerAdd);
	}

	@Test
	public void testRetreveAllEmployes() {
		List<Employe> listEmployes = employeService.getAllEmployes();
		assertEquals(2, listEmployes.size());
	}

	@Test
	public void AuthentificationTest() {
		Employe authenticateEmploye = employeService.authenticate("amine@gmail.com", "12345");
		assertEquals(1, authenticateEmploye.getId());
	}

	@Test
	public void mettreAjourEmailByEmployeIdTest() {
		Employe employe = employeService.mettreAjourEmailByEmployeId("amine@esprit.tn", 2);
		assertEquals("amine@esprit.tn", employe.getEmail());
	}

	@Test
	public void affecterEmployeADepartementTest() {
		employeService.affecterEmployeADepartement(1, 1);
		Departement depManagedEntity = deptRepoistory.findById(1).get();
		int nbrEmployeParDepartement = depManagedEntity.getEmployes().size();
		assertEquals(3, nbrEmployeParDepartement);
	}

	@Test
	public void desaffecterEmployeDuDepartementTest() {
		employeService.desaffecterEmployeDuDepartement(1, 1);
		Departement depManagedEntity = deptRepoistory.findById(1).get();
		int nbrEmployeParDepartement = depManagedEntity.getEmployes().size();
		assertEquals(1, nbrEmployeParDepartement);

	}

	@Test
	public void ajouterContratTest() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date dateDebut = dateFormat.parse("2015-03-23");
		Contrat contrat = new Contrat(dateDebut, "CDI", 1500);
		employeService.ajouterContrat(contrat);
		List<Contrat> contratList = (List<Contrat>) contratRepoistory.findAll();
		assertEquals(1, contratList.size());
	}

	

	@Test
	public void affecterContratAEmployeTest() {
		employeService.affecterContratAEmploye(1, 1);
		Contrat contrat = contratRepoistory.findById(1).get();
		assertEquals(1, contrat.getEmploye().getId());
	}

	@Test
	public void getEmployePrenomById() {
		String employe = employeService.getEmployePrenomById(2);
		assertEquals("lahbecha", employe);

	}

	@Test
	public void deleteEmployeByIdTest() {
		employeService.deleteEmployeById(3);
		List<Employe> empl = (List<Employe>) employeRepository.findAll();
		assertEquals(2, empl.size());

	}

	@Test
	public void deleteContratByIdTest() {
		employeService.deleteContratById(2);
		List<Contrat> contrat = (List<Contrat>) contratRepoistory.findAll();
		assertEquals(1, contrat.size());
	}

	@Test
	public void getNombreEmployeJPQLTest() {
		int nbr = employeService.getNombreEmployeJPQL();
		assertEquals(2, nbr);

	}

	@Test
	public void getAllEmployeNamesJPQLTest() {
		List<String> emp = employeService.getAllEmployeNamesJPQL();
		assertEquals(2, emp);
	}

	@Test
	public void deleteAllContratJPQLTset() {
		employeService.deleteAllContratJPQL();
		List<Contrat> contrat = (List<Contrat>) contratRepoistory.findAll();
		assertEquals(1, contrat.size());

	}

	@Test
	public void getSalaireByEmployeIdJPQLTest() {

		float salaire = employeService.getSalaireByEmployeIdJPQL(1);
		assertEquals(1500, salaire, 0);

	}
}
