package src.edu.gatech.cs6300.testcases;

import src.edu.gatech.cs6300.Constants;
import src.edu.gatech.cs6300.GradesDB;
import src.edu.gatech.cs6300.Project;
import src.edu.gatech.cs6300.Session;
import src.edu.gatech.cs6300.Student;
import junit.framework.TestCase;

public class ProjectTest extends TestCase{

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
	
	public void testGetContribution() {
		Project project = db.getProjectByName("P1");
		Student student1 = db.getStudentByName("Josepha Jube");
		Student student2 = db.getStudentByName("Shevon Wise");
		Student student3 = db.getStudentByName("Caileigh Raybould");
		
		assertEquals(project.getContribution(student1), 9.50);
		assertEquals(project.getContribution(student2), 9.25);
		assertEquals(project.getContribution(student3), 7.67);
	}
	
	public void testGetTeamName() {
		Project project = db.getProjectByName("P1");
		Student student1 = db.getStudentByName("Josepha Jube");
		Student student2 = db.getStudentByName("Shevon Wise");
		Student student3 = db.getStudentByName("Caileigh Raybould");
		
		assertTrue(project.getTeamName(student1).equals("Team 2"));
		assertTrue(project.getTeamName(student2).equals("Team 1"));
		assertTrue(project.getTeamName(student3).equals("Team 3"));
	}
	
	public void testGetGrades() {
		Project project = db.getProjectByName("P1");
		Student student1 = db.getStudentByName("Josepha Jube");
		Student student2 = db.getStudentByName("Shevon Wise");
		Student student3 = db.getStudentByName("Caileigh Raybould");
		
		String team1 = project.getTeamName(student2);
		String team2 = project.getTeamName(student1);
		String team3 = project.getTeamName(student3);
		
		assertEquals(project.getGrade(team1), 93);
		assertEquals(project.getGrade(team2), 96);
		assertEquals(project.getGrade(team3), 90);
		
		assertEquals(project.getAverageGrade(), 93);
	}

}
