package tn.esprit.spring;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployerTest {

	@Autowired
	IEmployeService employeService;
	@Autowired
	EmployeRepository employeRepository;

	/*
	 * @Test public void testAddEmployer() { Employe employer = new Employe("amine",
	 * "lahbecha", "amine@gmail.com", true, Role.INGENIEUR) ; int employerAdd =
	 * employeService.addOrUpdateEmploye(employer); assertEquals(employer.getId(),
	 * employerAdd); }
	 * 
	 * 
	 * @Test public void testUpdateEmploye() { Employe employer = new Employe(1,
	 * "mohamed amine", "lahbecha", "amine@gmail.com", "12345", true,
	 * Role.INGENIEUR); int employerAdd =
	 * employeService.addOrUpdateEmploye(employer); assertEquals(employer.getId(),
	 * employerAdd); }
	 */
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
}