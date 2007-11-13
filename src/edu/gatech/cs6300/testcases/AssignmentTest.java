package src.edu.gatech.cs6300.testcases;

import src.edu.gatech.cs6300.Assignment;
import src.edu.gatech.cs6300.Constants;
import src.edu.gatech.cs6300.GradesDB;
import src.edu.gatech.cs6300.Session;
import src.edu.gatech.cs6300.Student;
import junit.framework.TestCase;

public class AssignmentTest extends TestCase {

	private Session session = null;
	GradesDB db = null;

	protected void setUp() throws Exception {
		session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		db = session.getDBByName(Constants.GRADES_DB);
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		session.logout();
		super.tearDown();
	}
	
	public void testGetAverageGrade() {
		Assignment assignment = db.getAssignmentByName("Assignment 1");
		assertEquals(assignment.getAverageGrade(), 99.28);	
	}
	
	public void testGetStudentGrade() {
		Assignment assignment = db.getAssignmentByName("Assignment 1");
		Student student1 = db.getStudentByName("Josepha Jube");
		Student student2 = db.getStudentByName("Christine Schaeffer");
		
		assertEquals(assignment.getStudentGrade(student1), 100);
		assertEquals(assignment.getStudentGrade(student2), 90);
	}
}
