package edu.gatech.cs6300.testcases;


import edu.gatech.cs6300.Constants;
import edu.gatech.cs6300.GradesDB;
import edu.gatech.cs6300.GradesGUI;
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
        gradesGUI = new GradesGUI(db);
        
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
        	System.out.println(db.getNumStudents());
        	System.out.println(gradesGUI.getNumStudentsInComboBox());
            
        	/* check to make sure there are correct number of students */
            assertEquals(db.getNumStudents(), gradesGUI.getNumStudentsInComboBox());           
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
        int iProjectNumber = 3;
        
        /* Select a student */
        gradesGUI.setSelectedStudent(5);
                
        String projectName = "P" + iProjectNumber;
        /* Get currently selected student */
        Student selectedStudent = gradesGUI.getSelectedStudent();
        
        //int iTeamNumber = selectedStudent.getProjectTeam(iProjectNumber);
        
        /* Test student's project info against labels */
        try {
            /* student’s team's grades */
            assertEquals(gradesGUI.getProjectTeamGradeLabel(iProjectNumber), db.getTeamGrade(db.getTeamName(selectedStudent, projectName), projectName));
        } catch (Exception e) {
            fail("Exception while checking student's Team Grade Label");
        }
        
        try {
            /* average grade across teams */
            assertEquals(gradesGUI.getProjectAverageGradeLabel(iProjectNumber), db.getAverageProjectGrade(projectName));
        } catch (Exception e) {
            fail("Exception while checking project's Average Team Grade Label");
        }
        
        try {
            /* average contribution received by the student from his/her team members */
            assertEquals(gradesGUI.getProjectContributionLabel(iProjectNumber), db.getContribution(selectedStudent, projectName));      
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
        gradesGUI.setSelectedStudent(6);
        
        //Assignment assignment2 = db.getAssignmentByName("Assignment 2");
                
        /* Get currently selected student */
        Student selectedStudent = gradesGUI.getSelectedStudent();
        
        try {
            assertEquals(gradesGUI.getAssignmentAvgGradeLabel(2), db.getAverageAssignmentGrade("Assignment 2"));
        } catch (Exception e) {
            fail("Exception while checking assignment's Average Grade Label");
        }
        
        try {
            assertEquals(gradesGUI.getStudentAssignmentGradeLabel(2), db.getStudentGrade("Assignment 2", selectedStudent));         
        } catch (Exception e) {
            fail("Exception while checking student's Assignment Grade Label");
        }       
    }
    
    /**
     * The current Student information should be saved
     * to a specified file.
     */
    public void testOnSaveButtonClicked() {
        
    }
    
}
