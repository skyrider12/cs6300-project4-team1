package edu.gatech.cs6300;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Team implements TeamInterface {
	int score;
	String TeamName;
	Project project;
	HashSet<Student> Members;
	Map<Student, Float> Ratings;
	
	public Team(){
		Members = new HashSet<Student>();
		Ratings = new HashMap<Student, Float>();
		project = new Project();
	}
	
	public Map<Student, Float> getRatings(){
		return this.Ratings;	
	}
	
	public void setRatings(Map<Student, Float> ratings){
		this.Ratings = ratings;
	}
	
	public Float getRatingForStudent(Student s){
		return this.Ratings.get(s);	
	}
	
	public void setRatingForStudent(Student s, Float score){
		this.Ratings.put(s, score);
	}
	
	public HashSet<Student> getMembers(){
		return this.Members;
	}
	
	public void setMembers(HashSet<Student> members){
		this.Members = members;
	}
	
	public boolean isMember(Student student) {
		return this.Members.contains(student);
	}
	
	public void addStudent(Student student){
		this.Members.add(student);
	}
	
	public String getName(){
		return this.TeamName;
	}
	
	public void setName(String name){
		this.TeamName = name;
	}
	
	public int getSize(){
		return this.Members.size();	
	}
	
	public int getTeamScore(){
		return this.score;	
	}
	
	public void setTeamScore(int teamScore){
		this.score = teamScore;
	}
	
	public void setProject(Project p) {
		this.project = p;
	}
	
	public Project getProject() {
		return this.project;
	}

}
