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
		File xml = new File("fisiere/TemeTest.xml");
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
		this.temaFileRepository = new TemaXMLRepo("fisiere/TemeTest.xml");
		this.temaValidator = new TemaValidator();
		this.service = new Service(null, null, this.temaFileRepository, this.temaValidator, null, null);
	}

	@AfterAll
	static void removeXML() {
		new File("fisiere/TemeTest.xml").delete();
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

}