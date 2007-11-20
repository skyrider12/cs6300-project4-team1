package edu.gatech.cs6300;

import java.util.HashSet;
import java.util.ArrayList;

public class GradesDBApp {
	
	private GradesGUI gradesGUI;
	private HashSet<Student> students = null;
	private ArrayList<Project> projects = null;
	private ArrayList<Assignment> assignments = null;
	
	public static void main (String[] args){
		GradesDBApp gradesDBApp = new GradesDBApp();
	}
	
	public GradesDBApp() {		
		Session session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		
		GradesDB gradesDB = new GradesDB(session);
		
		/* Get students from google docs through GradesDB */
		this.students = gradesDB.getStudents();

		/* Get all projects from GradesDB */
		this.projects = gradesDB.getProjects();
		
		/* For each project, look in respective worksheet...*/		
		for(Project p : projects) {
			/* find teams (e.g., worksheet "P4 Teams") and members */
			HashSet<Team> teams = gradesDB.getTeamsForProject(p.getProjectNumber());
			for (Team t : teams){
				t.project = p;
			}
			p.setTeams(teams);	
		}
		
		/* Get all Assignments from GradesDB */
		this.assignments = gradesDB.getAssignments();
		
		//gradesDB.g
		
		/* Create the instance of GradesGUI */
		gradesGUI = new GradesGUI();
		
		/* Fill combo-box on GUI with our students HashSet */
		gradesGUI.populateComboStudents(students);
	}
	
	public GradesGUI getGradesGUI() {
		return this.gradesGUI;
	}
	
	public ArrayList<Assignment> getAssignments() {
		return this.assignments;
	}
	
	public ArrayList<Project> getProjects() {
		return this.projects;
	}
	
	public HashSet<Student> getStudents() {
		return this.students;
	}
		
}
