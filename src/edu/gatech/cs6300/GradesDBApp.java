package edu.gatech.cs6300;

import java.util.HashSet;

public class GradesDBApp {

	private static GradesGUI gradesGUI;
	
	public static void main (String[] args){
		Session session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		
		GradesDB gradesDB = new GradesDB(session);
		
		HashSet<Student> students = null;
		
		/* Create the GradesGUI */
		gradesGUI = new GradesGUI();
		
		/* Get students */
		students = gradesDB.getStudents();
		
		/* Fill combo-box on GUI */
		gradesGUI.populateComboStudents(students);
	
	}
	
}
