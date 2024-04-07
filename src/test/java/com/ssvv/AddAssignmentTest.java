package com.ssvv;

import com.ssvv.domain.Student;
import com.ssvv.domain.Tema;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.repository.TemaFileRepository;
import com.ssvv.repository.TemaXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.StudentValidator;
import com.ssvv.validation.TemaValidator;
import com.ssvv.validation.ValidationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddAssignmentTest {
	private TemaXMLRepo temaFileRepository;

	private TemaValidator temaValidator;

	private Service service;

	@BeforeAll
	static void createXML() {
		File xml = new File("fisiere/Teme.xml");
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
	}

	@BeforeEach
	void setup() {
		this.temaFileRepository = new TemaXMLRepo("fisiere/Teme.xml");
		this.temaValidator = new TemaValidator();
		this.service = new Service(null, null, this.temaFileRepository, this.temaValidator, null, null);
	}

	@AfterAll
	static void removeXML() {
		new File("fisiere/Teme.xml").delete();
	}

	@Test
	public void testValidAssignment() {
		final Tema newTema = new Tema("1", "descriere", 5, 5);
		this.service.addTema(newTema);
		java.util.Iterator<Tema> teme = this.service.getAllTeme().iterator();
		assertEquals(teme.next(), newTema);
	}

	@Test
	public void testInvalidAssignment() {
		final Tema newTema = new Tema("", "descriere", 5, 5);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentEmptyID() {
		Tema newTema = new Tema("", "tema1", 1, 1);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentNullID() {
		Tema newTema = new Tema(null, "tema1", 1, 1);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentEmptyDescription() {
		Tema newTema = new Tema("2", "", 2, 2);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentDeadlineTooLarge() {
		Tema newTema = new Tema("3", "tema3", 15, 3);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentDeadlineTooSmall() {
		Tema newTema = new Tema("4", "tema4", 0, 4);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentReceivedTooSmall() {
		Tema newTema = new Tema("5", "tema5", 1, 0);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentReceivedTooLarge() {
		Tema newTema = new Tema("6", "tema6", 1, 15);
		assertThrows(ValidationException.class, () -> this.service.addTema(newTema));
	}

	@Test
	void testAddAssignmentValidAssignment() {
		Tema newTema = new Tema("7", "tema7", 1, 1);
		this.service.addTema(newTema);
		assertEquals(this.service.getAllTeme().iterator().next(), newTema);
	}

	@Test
	void testAddAssignmentDuplicateAssignment() {
		Tema newTema = new Tema("8", "tema8", 1, 1);
		this.service.addTema(newTema);

		Tema newTema2 = new Tema("8", "tema8", 1, 1);

		assertEquals(this.service.addTema(newTema2).getID(), newTema.getID());
	}

}