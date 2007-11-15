package edu.gatech.cs6300.testcases;

import java.util.ArrayList;
import java.util.HashSet;

import junit.framework.TestCase;
import edu.gatech.cs6300.Constants;
import edu.gatech.cs6300.GradesDB;
import edu.gatech.cs6300.Session;
import edu.gatech.cs6300.Student;

public class GradesDBTest extends TestCase {
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

    public void testGetStudentsByID(){
        try {
            Student student = db.getStudentByID("901234504");
            assertTrue(student.getName().compareTo("Shevon Wise") == 0);
        } catch (Exception e) {
            fail("Exception");
        }
    }

    public void testGetProjects1() {
        ArrayList<String> projects = null;
        try {
            projects = db.getProjects();
        } catch (Exception e) {
            fail("Exception");
        }
        assertEquals(3, projects.size());
    }
    
    public void testGetProjects2() {
        ArrayList<String> projects = null;
        try {
            projects = db.getProjects();
        } catch (Exception e) {
            fail("Exception");
        }
        boolean found = false;
        for (String p : projects) {
            if ((p.equals("P1")) && (db.getAverageProjectGrade(p) == 93)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
    
    public void testGetAssignments1() {
        ArrayList<String> assignments = null;
        try {
            assignments = db.getAssignments();
        } catch (Exception e) {
            fail("Exception");
        }
        assertEquals(3, assignments.size());
    }
    
    public void testGetAssignments2() {
        ArrayList<String> assignments = null;
        try {
            assignments = db.getAssignments();
        } catch (Exception e) {
            fail("Exception");
        }
        boolean found = false;
        for (String s : assignments) {
            if ((s.equals("Assignment 1")) && (db.getAverageAssignmentGrade("Assignment 1") == 99.29)) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }
    
    public void testGetAverageGrade() {
        assertEquals(db.getAverageAssignmentGrade("Assignment 1"), 99.29);  
    }
    
    public void testGetStudentGrade() {
        Student student1 = db.getStudentByName("Josepha Jube");
        Student student2 = db.getStudentByName("Christine Schaeffer");
        
        assertEquals(db.getStudentGrade("Assignment 1", student1), 100.0);
        assertEquals(db.getStudentGrade("Assignment 1", student2), 90.0);
    }
    
    public void testGetContribution() {
        Student student1 = db.getStudentByName("Josepha Jube");
        Student student2 = db.getStudentByName("Shevon Wise");
        Student student3 = db.getStudentByName("Caileigh Raybould");
        
        assertEquals(db.getContribution(student1, "P1"), 9.5);
        assertEquals(db.getContribution(student2, "P1"), 9.25);
        assertEquals(db.getContribution(student3, "P1"), 7.67);
    }
    
    public void testGetTeamName() {
        
        Student student1 = db.getStudentByName("Josepha Jube");
        Student student2 = db.getStudentByName("Shevon Wise");
        Student student3 = db.getStudentByName("Caileigh Raybould");
        
        assertTrue(db.getTeamName(student1, "P1").equals("Team 2"));
        assertTrue(db.getTeamName(student2, "P1").equals("Team 1"));
        assertTrue(db.getTeamName(student3, "P1").equals("Team 3"));
    }
    
    public void testGetProjectGrades() {
 
        Student student1 = db.getStudentByName("Josepha Jube");
        Student student2 = db.getStudentByName("Shevon Wise");
        Student student3 = db.getStudentByName("Caileigh Raybould");
        
        String team1 = db.getTeamName(student2, "P1");
        String team2 = db.getTeamName(student1, "P1");
        String team3 = db.getTeamName(student3, "P1");
        
        assertEquals(db.getTeamGrade(team1, "P1"), 93.0);
        assertEquals(db.getTeamGrade(team2, "P1"), 96.0);
        assertEquals(db.getTeamGrade(team3, "P1"), 90.0);
        
        assertEquals(db.getAverageProjectGrade("P1"), 93.0);
    }
    
    public void testGetAverageProjectGrade() {
        assertEquals(db.getAverageProjectGrade("P3"), 99.33);        
    }
    
    public void testGetAverageContribution() {
        Student student = db.getStudentByName("Sheree Gadow");
        
        assertEquals(db.getContribution(student, "P1"), 7.00);
    }
    
}
