package tn.esprit.spring;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	@Autowired
	IEntrepriseService es;
	@Autowired
	EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;

	@Test
	public void testAddEntreprise() throws ParseException {

		Entreprise e = new Entreprise("Teamwill", "sssss");
		Entreprise entrepriseAdded = es.ajouterEntreprise(e);
		assertEquals(e.getName(), entrepriseAdded.getName());
	}

	@Test
	public void testAddDepartement() throws ParseException {

		Departement dep = new Departement("yasminet");
		Departement departementAdded = es.ajouterDepartement(dep);
		assertEquals(dep.getName(), departementAdded.getName());
	}

	@Test
	public void testgetAllDepartementsNamesByEntreprise() {

		List<String> listDepartments = es.getAllDepartementsNamesByEntreprise(3);
		// if there are 5 departements in DB :
		assertEquals(1, listDepartments.size());
	}

	@Test
	public void testRetrieveAllEntreprises() {
		List<Entreprise> listEntreprises = es.retrieveAllEntreprises();
		// if there are 5 users in DB :
		assertEquals(2, listEntreprises.size());
	}

	@Test
	public void testgetEntrepriseById() {
		Entreprise entrepriseRetrieved = es.getEntrepriseById(3);
		assertEquals(3, entrepriseRetrieved.getId());
	}

	@Test
	public void testdeleteEntrepriseById() {

		Entreprise e = entrepriseRepoistory.findById(10).get();

		es.deleteEntrepriseById(10);
		assertEquals(10, e.getId());
	}

	@Test
	public void testdeleteDepartementById() {

		Departement d = deptRepoistory.findById(2229).get();

		es.deleteDepartementById(2229);
		;
		assertEquals(2229, d.getId());
	}

	@Test
	public void testaffecterDepartementAEntreprise() {
		Entreprise e = entrepriseRepoistory.findById(7).get();

		// Departement d=deptRepoistory.findById(2227).get();

		es.affecterDepartementAEntreprise(2227, 7);
		assertEquals(1, e.getDepartements().size());
	}

}
