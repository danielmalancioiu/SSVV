package com.ssvv;

import com.ssvv.repository.*;
import com.ssvv.domain.*;
import com.ssvv.service.Service;
import com.ssvv.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class THIntegrationTest {
	@Mock
	private StudentValidator studentValidator;

	@Mock
	private TemaValidator temaValidator;

	@Mock
	private StudentXMLRepo studentXMLRepository;

	@Mock
	private TemaXMLRepo temaXMLRepository;

	@Mock
	private NotaValidator notaValidator;

	@Mock
	private NotaXMLRepo notaXMLRepository;

	private Service service;

	@BeforeEach
	public void setup() {

		studentValidator = mock(StudentValidator.class);
		temaValidator = mock(TemaValidator.class);
		notaValidator = mock(NotaValidator.class);
		temaXMLRepository = mock(TemaXMLRepo.class);
		studentXMLRepository = mock(StudentXMLRepo.class);
		notaXMLRepository = mock(NotaXMLRepo.class);

		service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
	}


	@Test
	public void testAddStudent(){

		System.out.println("Test student - add");

		Student s1 = new Student("", "test", 934, "test@gmail.com");

		try{
			doThrow(new ValidationException("Nume incorect!")).when(studentValidator).validate(s1);
		}
		catch (ValidationException e){
			e.printStackTrace();
		}

		try{
			Assertions.assertThrows(ValidationException.class, () -> service.addStudent(s1));
		}
		catch (ValidationException e){
			e.printStackTrace();
		}
	}



	@Test
	public void testAddStudentAndAssignment() {

		System.out.println("Test student and tema - add");

		Student s1 = new Student("222", "test", 934, "test@gmail.com");
		Tema tema1 = new Tema("223", "", 1, 1);

		try{
			doNothing().when(studentValidator).validate(s1);
			when(studentXMLRepository.save(s1)).thenReturn(null);
			doThrow(new ValidationException("Descriere invalida!")).when(temaValidator).validate(tema1);
		}
		catch (ValidationException e){
			e.printStackTrace();
		}

		try{
			Student s1_test = service.addStudent(s1);
			Assertions.assertNull(s1_test);
			Assertions.assertThrows(ValidationException.class, () -> service.addTema(tema1));
		}
		catch (ValidationException e){
			e.printStackTrace();
		}
	}


	@Test
	public void testAddStudentAndAssignmentAndGrade(){

		System.out.println("Test student and tema and nota - add");

		Student s1 = new Student("222", "test", 934, "test@gmail.com");
		Tema tema1 = new Tema("222", "a", 1, 2);
		Nota nota1 = new Nota("222", "222", "222", 10, LocalDate.now());


		try{
			doNothing().when(studentValidator).validate(s1);
			when(studentXMLRepository.save(s1)).thenReturn(null);

			doNothing().when(temaValidator).validate(tema1);
			when(temaXMLRepository.save(tema1)).thenReturn(null);

			when(studentXMLRepository.findOne(nota1.getIdStudent())).thenReturn(s1);
			when(temaXMLRepository.findOne(nota1.getIdTema())).thenReturn(tema1);
		}
		catch (ValidationException e){
			e.printStackTrace();
		}

		try{
			Student s1_test = service.addStudent(s1);
			Assertions.assertNull(s1_test);
			Tema tema1_test = service.addTema(tema1);
			Assertions.assertNull(tema1_test);

			Assertions.assertThrows(ValidationException.class, () -> service.addNota(nota1, "ok"));
			Assertions.assertEquals(10.0, nota1.getNota());


		}
		catch (ValidationException e){
			e.printStackTrace();
		}
	}

}
