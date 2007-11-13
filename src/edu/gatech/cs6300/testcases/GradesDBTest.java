package edu.gatech.cs6300.testcases;

import java.util.HashSet;

import junit.framework.TestCase;
import edu.gatech.cs6300.Assignment;
import edu.gatech.cs6300.Constants;
import edu.gatech.cs6300.GradesDB;
import edu.gatech.cs6300.Project;
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
	
	public void testGetProjectByName() {
		Project project = null;
		try {
			project = db.getProjectByName("P1");
		} catch (Exception e) {
			fail("Exception");
		}
		assertTrue(project.getAverageGrade() == 93);
	}
	
	public void testGetAssignmentByName() {
		Assignment assignment = null;
		try {
			assignment = db.getAssignmentByName("Assignment 1");
		} catch (Exception e) {
			fail("Exception");
		}
		assertTrue(assignment.getAverageGrade() == 99.28);
	}
	
	public void testGetProjects1() {
		HashSet<Project> projects = null;
		try {
			projects = db.getProjects();
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(3, projects.size());
	}
	
	public void testGetProjects2() {
		HashSet<Project> projects = null;
		try {
			projects = db.getProjects();
		} catch (Exception e) {
			fail("Exception");
		}
		boolean found = false;
		for (Project p : projects) {
			if ((p.getName().equals("P1")) && (p.getAverageGrade() == 93)) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}
	
	public void testGetAssignments1() {
		HashSet<Assignment> assignments = null;
		try {
			assignments = db.getAssignments();
		} catch (Exception e) {
			fail("Exception");
		}
		assertEquals(3, assignments.size());
	}
	
	public void testGetAssignments2() {
		HashSet<Assignment> assignments = null;
		try {
			assignments = db.getAssignments();
		} catch (Exception e) {
			fail("Exception");
		}
		boolean found = false;
		for (Assignment s : assignments) {
			if ((s.getName().equals("Assignment 1")) && (s.getAverageGrade() == 99.28)) {
				found = true;
				break;
			}
		}
		assertTrue(found);
	}
//	public void testGetTeamGrade(){
//		int teamGrade;	
//		try{
//			teamGrade = db.getTeamGrade(2, 3);
//			assertEquals(96, teamGrade);
//		} catch (Exception e){
//			fail("Exception");
//		}
//	}
//	
//	public void testGetAverageTeamGradeForProject(){
//		float avgTeamGrade;
//		try{
//			avgTeamGrade = db.getAverageTeamGradeForProject(1); 
//			assertEquals(93, avgTeamGrade);
//		} catch(Exception e){
//			fail("Exception");
//		}
//	}
//	
//	public void testGetAverageClassGradeForAssignment(){
//		float avgClassGrade;
//		try{
//			avgClassGrade = db.getAverageClassGradeForAssignment(2);
//			assertEquals(100, avgClassGrade);
//		} catch(Exception e){
//			fail("Exception");
//		}
//	}
//	
//	public void testShowStudentNames(){
//		String[] wholeClass;
//		int studentNo = 0;
//		try{
//			wholeClass = db.showStudentNames();
//			for (Student student: db.getStudents()){
//				for (int i=0; i<wholeClass.length; i++){
//					if (wholeClass[i].equals(student.getName())){
//						studentNo ++;
//					}
//				}
//			}
//			assertEquals(14, studentNo);
//		} catch(Exception e){
//			fail("Exception");
//		}
//	}
//	
//	public void testGetStudentTeamGradeForProject(){
//		int teamGrade;
//		try {
//			teamGrade = db.getStudentTeamGradeForProject("Caileigh Raybould", 3);
//			assertEquals(100, teamGrade);
//		} catch(Exception e){
//			fail("Exception");
//		}
//	}
	
	
}
