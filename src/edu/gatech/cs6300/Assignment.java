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

	public void setAssignmentDescription(String assignmentDescription) {
		this.Description = assignmentDescription;
	}

	public void setAssignmentNumber(int assignmentNumber) {
		this.number = assignmentNumber;
	}

	public void setScores(Map<Student, Integer> scores) {
		this.Scores = scores;
	}

}
