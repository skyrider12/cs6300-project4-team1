package edu.gatech.cs6300;

import java.util.Map;

public class Assignment implements AssignmentInterface{
	String Description;
	int number;
	Map<Student, Integer> Scores;
	GradesDB db;

	public String getAssignmentDescription() {
		return this.Description;
	}

	public int getAssignmentNumber() {
		return this.number;
	}

	public Map<Student, Integer> getScores() {
		return this.Scores;
	}
	
	public int getScoreForStudent(Student student) {
		return this.Scores.get(student);
	}
	
	public int getAverageScore() {
		int sum = 0;
		for (Integer score: this.Scores.values()) {
			sum = sum + score;
		}
		return sum/Scores.size();
	}

	public void setAssignmentDescription(String assignmentDescription) {
		this.Description = assignmentDescription;
	}

	public void setAssignmentNumber(int assignmentNumber) {
		this.number = assignmentNumber;
	}

	public void setScores(Map<Student, Integer> scores) {
		this.Scores = scores;
	}
			
	public String getInfoForTextarea(Student student) {
		StringBuilder info = new StringBuilder();
		
		info.append("\nAssignment ");
		info.append(this.getAssignmentNumber());
		info.append(" grade: ");
		info.append(this.getScoreForStudent(student));
		info.append("\nAssignment ");
		info.append(this.getAssignmentNumber());
		info.append(" Average grade: ");
		info.append(this.getAverageScore());
		
		return info.toString();
	}


}
