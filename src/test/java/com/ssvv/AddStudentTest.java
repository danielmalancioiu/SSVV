package com.ssvv;

import com.ssvv.domain.Student;
import com.ssvv.repository.StudentXMLRepo;
import com.ssvv.service.Service;
import com.ssvv.validation.StudentValidator;
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

public class AddStudentTest {
    private StudentXMLRepo studentFileRepository;
    private StudentValidator studentValidator;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setup() {
        this.studentFileRepository = new StudentXMLRepo("fisiere/studentiTest.xml");
        this.studentValidator = new StudentValidator();
        this.service = new Service(this.studentFileRepository, this.studentValidator, null, null, null, null);
    }

    @AfterAll
    static void removeXML() {
        new File("fisiere/studentiTest.xml").delete();
    }

    @Test
    void testAddStudentOnGroup() {
        Student newStudent1 = new Student("1", "A", 931, "test@gmail.com");
        Student newStudent2 = new Student("2", "B", -934, "test@gmail.com");
        Student newStudent3 = new Student("3", "C", 0, "test@gmail.com");
        this.service.addStudent(newStudent1);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        this.service.addStudent(newStudent3);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        assertEquals(students.next(), newStudent1);
        assertEquals(students.next(), newStudent3);

        this.service.deleteStudent("1");
        this.service.deleteStudent("3");
    }

    @Test
    void testAddStudentOnName() {
        Student newStudent1 = new Student("1", "Ana", 100, "s1@email.com");
        Student newStudent2 = new Student("2", "", 100, "s2@email.com");
        Student newStudent3 = new Student("3", "Andrei", -3, "s3@email.com");
        this.service.addStudent(newStudent1);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        assertEquals(students.next(), newStudent1);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent3));

        this.service.deleteStudent("1");
    }

    @Test
    void testAddStudentOnEmail() {
        Student newStudent1 = new Student("1111", "Ana", 333, "lupu@gmail.com");
        Student newStudent2 = new Student("1111211", "", 100, "");
        this.service.addStudent(newStudent1);
        java.util.Iterator<Student> students = this.service.getAllStudenti().iterator();
        assertEquals(students.next(), newStudent1);
        assertThrows(ValidationException.class, () -> this.service.addStudent(newStudent2));

        this.service.deleteStudent("1111");
    }

    @Test
    void testValidStudent() {
        final Student student = new Student("1", "Daniel", 934, "stud@example.com");
        this.service.addStudent(student);
        this.service.deleteStudent("1");
    }

    @Test
    void testInvalidStudent() {
        final Student student = new Student("", "Daniel", 934, "stud@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testValidId() {
        final Student student = new Student("1", "Daniel", 934, "stud@example.com");
        service.addStudent(student);
        service.deleteStudent("1");
    }

    @Test
    void testNullId() {
        final Student student = new Student("null", "Daniel", 934, "stud@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testInvalidNameCharacters() {
        final Student student = new Student("1", "Test123+-", 934, "stud@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testEmptyName() {
        final Student student = new Student("1", "", 934, "stud@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testValidGroup() {
        final Student student = new Student("1", "Daniel", 934, "stud@example.com");
        this.service.addStudent(student);
        this.service.deleteStudent("1");
    }

    @Test
    void testInvalidGroupLess() {
        final Student student = new Student("1", "Daniel", -934, "stud@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testInvalidGroupGreater() {
        final Student student = new Student("1", "Daniel", Integer.MAX_VALUE + 1, "stud@example.com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }

    @Test
    void testValidEmail() {
        final Student student = new Student("1", "Daniel", 934, "stud@example.com");
        this.service.addStudent(student);
        this.service.deleteStudent("1");
    }

    @Test
    void testInvalidEmail() {
        final Student student = new Student("1", "Daniel", 934, "com");
        assertThrows(ValidationException.class, () -> this.service.addStudent(student));
    }
}
