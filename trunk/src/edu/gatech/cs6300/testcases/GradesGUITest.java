package edu.gatech.cs6300.testcases;

import edu.gatech.cs6300.GradesDBApp;
import edu.gatech.cs6300.GradesGUI;
import edu.gatech.cs6300.Project;
import edu.gatech.cs6300.Student;
import edu.gatech.cs6300.Team;
import edu.gatech.cs6300.Assignment;

import junit.framework.TestCase;

public class GradesGUITest extends TestCase {

	private static GradesDBApp gradesDBApp = null;
    private static GradesGUI gradesGUI = null;
    
    protected void setUp() throws Exception {
    	gradesDBApp = new GradesDBApp();
    	gradesGUI = gradesDBApp.getGradesGUI();		
		
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * Should load the combo-box with the name of
     * all the students in the database.
     */
    public void testPopulateComboStudents() {
        try { 
//        	System.out.println(db.getNumStudents());
//        	System.out.println(gradesGUI.getNumStudentsInComboBox());
            
        	/* check to make sure there are correct number of students */
            assertEquals(gradesDBApp.getStudents().size(), gradesGUI.getNumStudentsInComboBox());           
        } catch (Exception e) {
            fail("Exception while populating combo students");
        }       
    }
    
    public void testSetSelectedStudent() {
        try {
            /* Select a student */
            gradesGUI.setSelectedStudent(3);
            assertTrue(gradesGUI.getStudentNameLabel().compareTo(gradesGUI.getSelectedStudent().getName()) == 0);
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
        } catch (Exception e) {
            fail("Exception while checking student's Name Label: " + e.getMessage());
        }
        
        try {
            assertTrue(gradesGUI.getStudentGTIDLabel().compareTo(selectedStudent.getGtid()) == 0);
        } catch (Exception e) {
            fail("Exception while checking student's GTID Label: " + e.getMessage());
        }
        
        try {
        	assertTrue(gradesGUI.getStudentEmailLabel().compareTo(selectedStudent.getEmail()) == 0);
        } catch (Exception e) {
            fail("Exception while checking student's Email Label: " + e.getMessage());
        }
        
        try {
        	assertEquals(gradesGUI.getStudentAttendanceLabel(), selectedStudent.getAttendance());
        } catch (Exception e) {
            fail("Exception while trying checking student's Attendance label: " + e.getMessage());
        }
    }
    
    /**
     * After selecting a different student from combo-box
     * make sure that correct number of projects are in list
     * and that one of the projcts has right info for selected student
     */
    public void testOnComboIndexChangedProjectInfo() {
        int iProjectNumber = 2;
        
        /* Select a student */
        gradesGUI.setSelectedStudent(5);
                
        /* Get currently selected student */
        Student selectedStudent = gradesGUI.getSelectedStudent();
        
        Team team = selectedStudent.getTeams().get(iProjectNumber);
        Project project = team.getProject();
                
        /* Test student's project info against labels */
        try {
            /* student’s team's grades */
        	System.out.println("stu: " + selectedStudent.getName() + "; GUI: " + gradesGUI.getProjectTeamGradeLabel(iProjectNumber) + "; team: " + team.getTeamScore());
            assertEquals(gradesGUI.getProjectTeamGradeLabel(iProjectNumber), team.getTeamScore());
        } catch (Exception e) {
            fail("Exception while checking student's Team's Grade Label");
        }
        
        try {
            /* average grade across teams */
            assertEquals(gradesGUI.getProjectAverageGradeLabel(iProjectNumber), project.getAverageScore());
        } catch (Exception e) {
            fail("Exception while checking project's Average Team Grade Label");
        }
        
        try {
            /* average contribution received by the student from his/her team members */
            assertEquals(gradesGUI.getProjectContributionLabel(iProjectNumber), team.getRatingForStudent(selectedStudent));      
        } catch (Exception e) {
            fail("Exception while checking student's Average Contribution Label");
        }
    }
    
    /**
     * After selecting a different student from combo-box
     * make sure that the correct number of assignments are in the list
     * and that one of the assignments has right info for selected student
     */
    public void testOnComboIndexChangedAssignmentInfo() {
        /* Select a student */
        gradesGUI.setSelectedStudent(7);
                        
        /* Get currently selected student */
        Student selectedStudent = gradesGUI.getSelectedStudent();
        
        /* Get assignment to check (2) */
        Assignment assignment2 = selectedStudent.getAssignments().get(2);
        
        try {
            assertEquals(gradesGUI.getAssignmentAvgGradeLabel(2), assignment2.getAverageScore());
        } catch (Exception e) {
            fail("Exception while checking assignment's Average Grade Label");
        }
        
        try {
        	System.out.println("stu: " + selectedStudent.getName() + "; GUI: " + gradesGUI.getStudentAssignmentGradeLabel(2) + "; stugr: " + assignment2.getScoreForStudent(selectedStudent));
            assertEquals(gradesGUI.getStudentAssignmentGradeLabel(2), assignment2.getScoreForStudent(selectedStudent));         
        } catch (Exception e) {
            fail("Exception while checking student's Assignment Grade Label");
        }       
    }
    
}
