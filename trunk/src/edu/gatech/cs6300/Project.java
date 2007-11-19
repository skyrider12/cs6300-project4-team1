package edu.gatech.cs6300;

import java.util.HashSet;

public class Project implements ProjectInterface{
	HashSet<Team> Teams;
	String Name;
	String Description;
	GradesDB db;
	
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

}
