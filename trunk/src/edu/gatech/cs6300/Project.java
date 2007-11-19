package edu.gatech.cs6300;

import java.util.HashSet;

public class Project implements ProjectInterface{
	HashSet<Team> Teams;
	String Name;
	String Description;
	
	private int iProjectNumber = -1;
	
	public Project(){
		Teams = new HashSet<Team> ();
	}

	public void addTeam(Team team) {
		this.Teams.add(team);
	}

	public int getAverageScore() {
		int sum = 0;
		for (Team team: Teams){
			sum = sum+team.score;
		}
		return sum/Teams.size();
	}

	public String getProjectDescription() {
		return this.Description;
	}

	public String getProjectName() {
		return this.Name;
	}

	public Team getTeamByName(String teamName) {
		for (Team team: Teams){
			if (teamName.equals(team.TeamName)){
				return team;
			}
		}
		return null;
	}

	public HashSet<Team> getTeams() {
		return this.Teams;
	}

	public void setProjectDescription(String projectDescription) {
		this.Description = projectDescription;
	}

	public void setProjectName(String projectName) {
		this.Name = projectName;
	}

	public void setTeams(HashSet<Team> teams) {
		this.Teams = teams;	
	}
	
	public void setProjectNumber(int iPrjNum) {
		this.iProjectNumber = iPrjNum;
	}
	
	public int getProjectNumber() {
		return this.iProjectNumber;
	}
	
	public Team getStudentTeam(Student student) {
		Team studentTeam = null;
		for (Team t : this.Teams) {
			if (t.isMember(student)) {
				studentTeam = t;
			}
		}
		return studentTeam;
	}
	
	public String getInfoForTextarea(Student student) {
		StringBuilder info = new StringBuilder();
		
		Team studentTeam = getStudentTeam(student);
		
		info.append("\nProject ");
		info.append(this.getProjectNumber());
		info.append(" team: ");
		info.append(studentTeam.getName());
		info.append("\nProject ");
		info.append(this.getProjectNumber());
		info.append(" Average grade: ");
		info.append(this.getAverageScore());
		info.append("\nProject ");
		info.append(this.getProjectNumber());
		info.append(" team grade: ");
		info.append(studentTeam.getTeamScore());
		info.append("\nProject ");
		info.append(this.getProjectNumber());
		info.append(" Average contribution: ");
		info.append(studentTeam.getRatingForStudent(student));
		
		return info.toString();
	}

}
