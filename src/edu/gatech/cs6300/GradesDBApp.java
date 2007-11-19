package edu.gatech.cs6300;

import java.util.HashSet;
import java.util.ArrayList;

public class GradesDBApp {

	private static GradesGUI gradesGUI;
	
	public static HashSet<Student> students = null;
	public static ArrayList<Team> teams = null;
	public static ArrayList<Project> projects = null;
	public static ArrayList<Assignment> assignments = null;
	
	public static void main (String[] args){
		Session session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		
		GradesDB gradesDB = new GradesDB(session);
		
		/* Get students from google docs through GradesDB */
		students = gradesDB.getStudents();
		
		/* Create the instance of GradesGUI */
		gradesGUI = new GradesGUI();
		
		/* Fill combo-box on GUI with our students HashSet */
		gradesGUI.populateComboStudents(students);
		
		/* Get all projects from GradesDB */
		projects = gradesDB.getProjects();
		
		/* For each project, look in respective worksheet...*/		
		for(Project p : projects) {
			/* find teams (e.g., worksheet "P4 Teams") and members */
			HashSet<Team> teams = gradesDB.getTeamsForProject(p.getProjectNumber());
			p.setTeams(teams);
			
			/* find team grades (e.g., worksheet "P2 Grades") */
			
			/* find member contributions (e.g., worksheet "P1 Contri") */
		}
		
		/* Get all Assignments from GradesDB */
		assignments = gradesDB.getAssignments();
	
	}
		
}
