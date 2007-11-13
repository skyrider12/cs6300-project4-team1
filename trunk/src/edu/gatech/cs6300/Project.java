package edu.gatech.cs6300;

public class Project {

	//eg- Project 1, Project 2, etc.
	private String projectName;
	
	public Project(String projectName) {
		this.projectName = projectName;
	}
	
	public float getContribution(Student student) {
		return 0;
	}
	
	public String getTeamName(Student student) {
		return "";
	}
	
	public float getGrade(String teamName) {
		return 0;
	}
	
	public float getAverageGrade() {
		return 0;
	}
	
	public String getName() {
		return this.projectName;
	}
	
}
