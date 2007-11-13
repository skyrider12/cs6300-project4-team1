package edu.gatech.cs6300;

import java.util.HashSet;

public class GradesGUI {

	/**
	 * Given a HashSet<Students> fill in combobox appropriately
	 */
	public void populateComboStudents(HashSet<Student> students) {

	}
	
	/**
	 * Count number of students in the combobox
	 */
	public int getNumStudentsInComboBox() {
		return -1;
	}

	/**
	 * Given an index, set the selected Student
	 */
	public void setSelectedStudent(int index) {
		
	}
	
	/**
	 * @return the Student currently selected in the combobox
	 */
	public Student getSelectedStudent() {
		return new Student();
	}
	
	/**
	 * @return the value in the StudentName Label
	 */
	public String getStudentNameLabel() {
		return "";
	}
	
	/**
	 * @return the value in the StudentGTID Label
	 */
	public String getStudentGTIDLabel() {
		return "";
	}
	
	/**
	 * @return the value in the StudentEmail Label
	 */
	public String getStudentEmailLabel() {
		return "";
	}
	
	/**
	 * @return the value in the StudentAttendance Label, cast to Integer
	 */
	public String getStudentAttendanceLabel() {
		return "";
	}
	
	public int getProjectTeamGradeLabel(int iProjectNumber) {
		return -1;
	}
	
	public int getProjectContributionLabel(int iProjectNumber) {
		return -1;
	}
	
	public int getProjectAverageGradeLabel(int iProjectNumber) {
		return -1;
	}
	
	public int getAssignmentAvgGradeLabel(int iAssignmentNumber) {
		return -1;
	}
	
	public int getStudentAssignmentGradeLabel(int iAssignmentNumber) {
		return -1;
	}
	
}
