package edu.gatech.cs6300.testcases;

import java.util.HashSet;

import junit.framework.TestCase;
import edu.gatech.cs6300.Utils;
import edu.gatech.cs6300.Constants;
import edu.gatech.cs6300.GradesDB;
import edu.gatech.cs6300.Session;
import edu.gatech.cs6300.Student;

public class GradesDBTest extends TestCase {
	private Session session = null;
	GradesDB db = null;
	Utils u = null;
	
	protected void setUp() throws Exception {
		session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		db = session.getDBByName(Constants.GRADES_DB);
		u = new Utils();
		u.ungo();
		super.setUp();
	}

	protected void tearDown() throws Exception {
		session.logout();
		u.ungo();
		super.tearDown();
	}

	public void testGetNumStudents() {
		try {
			int numStudents = db.getNumStudents();
			assertEquals(14, numStudents);
		} catch (Exception e) {
			fail("Exception");
		}
	}

	public void testGetNumAssignments() {
		try {
			int numAssignments = db.getNumAssignments();
			assertEquals(3, numAssignments);
		} catch (Exception e) {
			fail("Exception");
		}
	}

	public void testGetNumProjects() {
		int numProjects;
		try {
			numProjects = db.getNumProjects();
			assertEquals(3, numProjects);
		} catch (Exception e) {
			fail("Exception");
		}
	}
	
	public void testGetStudents1() {
		HashSet<Student> students = null;
		try {
			students = db.getStudents();
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(14, students.size());
	}

	public void testGetStudents2() {
		HashSet<Student> students = null;
		try {
			students = db.getStudents();
		} catch (Exception e) {
			fail("Exception");
		}
		boolean found = true;
		for (Student s : students) {
			if ((s.getName().compareTo("Cynthia Faast") == 0) && (s.getGtid().compareTo("901234514") == 0)) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}
	
	public void testGetStudentsByName1(){
		Student student = null;
		try {
			student = db.getStudentByName("Rastus Kight");
		} catch (Exception e) {
			fail("Exception");
		}
		assertTrue(student.getGtid().compareTo("901234512") == 0);
	}

	public void testGetStudentsByName2(){
		Student student = null;
		try {
			student = db.getStudentByName("Grier Nehling");
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(96, student.getAttendance());
	}

	public void testGetStudentsByName3() throws InterruptedException {
		Student student = null;
		Utils u = new Utils();
		try {
			student = db.getStudentByName("Grier Nehling");
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(96, student.getAttendance());
		u.go();
		Thread.sleep(10000);
		try {
			student = db.getStudentByName("Grier Nehling");
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(60, student.getAttendance());
	}

	public void testGetStudentsByID(){
		try {
			Student student = db.getStudentByID("901234504");
			assertTrue(student.getName().compareTo("Shevon Wise") == 0);
		} catch (Exception e) {
			fail("Exception");
		}
	}
}
