package edu.gatech.cs6300.testcases;

import edu.gatech.cs6300.Assignment;
import edu.gatech.cs6300.Constants;
import edu.gatech.cs6300.GradesDB;
import edu.gatech.cs6300.GradesGUI;
import edu.gatech.cs6300.Project;
import edu.gatech.cs6300.Session;
import edu.gatech.cs6300.Student;

import junit.framework.TestCase;

public class GradesGUITest extends TestCase {
	private Session session = null;
	private GradesDB db = null;
	private GradesGUI gradesGUI = null;
	
	protected void setUp() throws Exception {
		session = new Session();
		
		/* log into google docs with constant username/password */
		session.login(Constants.USERNAME, Constants.PASSWORD);
		
		/* log into our GradesDB */
		db = session.getDBByName(Constants.GRADES_DB);
		
		/* create instance of our GUI */
		gradesGUI = new GradesGUI();
		
		/* fill the combo-box with students */
		gradesGUI.populateComboStudents(db.getStudents());
		
		super.setUp();
	}

	protected void tearDown() throws Exception {
		session.logout();
		super.tearDown();
	}
	
	/**
	 * Should load the combo-box with the name of
	 * all the students in the database.
	 */
	public void testPopulateComboStudents() {
		try {		
			/* check to make sure there are correct number of students */
			assertEquals(db.getNumStudents(), gradesGUI.getNumStudentsInComboBox());
			
			/* check to make sure that the students are in correct order ?? */
			
			/* check to see if a student is a correct index ?? */
			
		} catch (Exception e) {
			fail("Exception while populating combo students");
		}		
	}
	
	public void testSetSelectedStudent() {
		try {
			/* Select a student */
			gradesGUI.setSelectedStudent(3);
			assertTrue(gradesGUI.getSelectedStudent().getName().compareTo("SomeName") == 0);
		} catch (Exception e) {
			fail("Exception while setting selected student");
		}		
	}
		
	/**
	 * After selecting a different student from the combo-box,
	 * the selected student's personal information is correct
	 */
	public void testOnComboIndexChangedPersonalInfo() {
		/* Select a student */
		gradesGUI.setSelectedStudent(4);

		/* Get currently selected student */
		Student selectedStudent = gradesGUI.getSelectedStudent();
		
		/* Select student 4 (index 3) and make sure info propogates to new boxes */
		try {			
			/* Check to make sure the student's info is in the labels */
			assertTrue(gradesGUI.getStudentNameLabel().compareTo(selectedStudent.getName()) == 0);
			assertTrue(gradesGUI.getStudentGTIDLabel().compareTo(selectedStudent.getGtid()) == 0);
			assertTrue(gradesGUI.getStudentEmailLabel().compareTo(selectedStudent.getEmail()) == 0);
			assertEquals(gradesGUI.getStudentAttendanceLabel(), selectedStudent.getAttendance());
		} catch (Exception e) {
			fail("Exception while trying to populate selected student's personal info");
		}		
	}
	
	/**
	 * After selecting a different student from combo-box
	 * make sure that correct number of projects are in list
	 * and that one of the projcts has right info for selected student
	 */
	public void testOnComboIndexChangedProjectInfo() {
		int iProjectNumber = 3;
		
		/* Select a student */
		gradesGUI.setSelectedStudent(5);
				
		Project project = db.getProjectByName("P" + iProjectNumber);
				
		/* Get currently selected student */
		Student selectedStudent = gradesGUI.getSelectedStudent();
		
		int iTeamNumber = selectedStudent.getProjectTeam(iProjectNumber);
		
		/* Test student's project info against labels */
		try {
			/* student’s team's grades */
			assertEquals(gradesGUI.getProjectTeamGradeLabel(iProjectNumber), project.getTeamGrade(iTeamNumber));
			/* average grade across teams */
			assertEquals(gradesGUI.getProjectAverageGradeLabel(iProjectNumber), project.getAverageTeamGrade());
			/* average contribution received by the student from his/her team members */
			assertEquals(gradesGUI.getProjectContributionLabel(iProjectNumber), project.getAverageContributionByTeamForStudent(iTeamNumber, selectedStudent));		
		} catch (Exception e) {
			fail("Exception while checking combo-box project info");
		}
	}
	
	/**
	 * After selecting a different student from combo-box
	 * make sure that the correct number of assignments are in the list
	 * and that one of the assignments has right info for selected student
	 */
	public void testOnComboIndexChangedAssignmentInfo() {
		/* Select a student */
		gradesGUI.setSelectedStudent(6);
		
		Assignment assignment2 = db.getAssignmentByName("Assignment 2");
				
		/* Get currently selected student */
		Student selectedStudent = gradesGUI.getSelectedStudent();
		
		try {
			assertEquals(gradesGUI.getAssignmentAvgGradeLabel(2), assignment2.getAverageGrade());
			assertEquals(gradesGUI.getStudentAssignmentGradeLabel(2), assignment2.getStudentGrade(selectedStudent));			
		} catch (Exception e) {
			fail("Exception while checking combo-box assignment info");
		}		
	}
	
	/**
	 * The current Student information should be saved
	 * to a specified file.
	 */
	public void testOnSaveButtonClicked() {
		
	}
	
}
