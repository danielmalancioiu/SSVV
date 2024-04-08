package com.ssvv;

import com.ssvv.domain.Nota;
import com.ssvv.domain.Student;
import com.ssvv.domain.Tema;
import com.ssvv.repository.NotaXMLRepo;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.NotaValidator;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.TemaValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntegrationTest {
	private Service service;

	@BeforeAll
	static void createXML() {
		File xml = new File("fisiere/studentiTest.xml");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"<inbox>\n" +
					"\n" +
					"</inbox>");
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		File xml2 = new File("fisiere/temeTest.xml");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml2))) {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"<inbox>\n" +
					"\n" +
					"</inbox>");
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		File xml3 = new File("fisiere/noteTest.xml");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml3))) {
			writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
					"<inbox>\n" +
					"\n" +
					"</inbox>");
			writer.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	public void setup() {
		StudentValidator studentValidator = new StudentValidator();
		TemaValidator temaValidator = new TemaValidator();

		StudentXMLRepo studentXMLRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
		TemaXMLRepo temaXMLRepository = new TemaXMLRepo("fisiere/temeTest.xml");
		NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
		NotaXMLRepo notaXMLRepository = new NotaXMLRepo("fisiere/noteTest.xml");
		service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
	}

	@AfterAll
	public static void teardown() {

		new File("fisiere/studentiTest.xml").delete();
		new File("fisiere/temeTest.xml").delete();
		new File("fisiere/noteTest.xml").delete();
	}


	@Test
	public void integrationTestAddStudent() {
		Student student = new Student("s1", "Student", 931, "s1@gmail.com");
		assertNull(service.addStudent(student));
	}

	@Test
	public void integrationTestAddTema() {
		Tema tema = new Tema("t1", "Tema", 6, 6);
		assertNull(service.addTema(tema));
	}

	@Test
	public void integrationTestAddGrade() {

		Nota nota = new Nota("g1", "s1", "t1", 10, LocalDate.now());
		assertEquals(service.addNota(nota, "bine"), 7.5);

		service.deleteNota("g1");
		service.deleteStudent("s1");
		service.deleteTema("t1");
	}

	@Test
	public void integrationTestAddStudentTemaGrade() {

		Student student = new Student("s2", "Student", 931, "s2@gmail.com");
		Tema tema = new Tema("t2", "Tema", 6, 6);
		Nota nota = new Nota("g2", "s2", "t2", 10, LocalDate.now());

		assertNull(service.addStudent(student));
		assertNull(service.addTema(tema));
		assertEquals(service.addNota(nota, "bine"), 7.5);

		service.deleteNota("g2");
		service.deleteStudent("s2");
		service.deleteTema("t2");
	}

}
