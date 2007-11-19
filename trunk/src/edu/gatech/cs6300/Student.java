package edu.gatech.cs6300;

import java.lang.StringBuilder;

public class Student {
    private String name;
    private String gtid;
    private int attendance;
    private String email;
    
    public Student() {

    	this.name = "";
    	this.gtid = "";
    	this.attendance = 0;
        this.email = "";

    }

    public String getName(){
        return this.name;
    }
    
    public String getGtid(){        
        return this.gtid;
    }
    
    public int getAttendance(){ 
        return this.attendance;
    }
    
    /**
     * @return the email address for the student
     */
    public String getEmail() {
    	return this.email;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setGTID(String gtid) {
        this.gtid = gtid;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
    
    public int getProjectTeam(int iProjectNumber) {
    	return -1;
    }
    
	public String getInfoForTextarea() {
		StringBuilder info = new StringBuilder();
		
		/* append already-loaded info */
		info.append("Name: ");
		info.append(this.getName());
		info.append("\nGTID: ");
		info.append(this.getGtid());
		info.append("\nEmail: ");
		info.append(this.getEmail());
		info.append("\nAttendance: ");
		info.append(this.getAttendance());
		info.append("%");
		
		return info.toString();
	}
	
	public String getProjectInfoForTextarea(GradesDB db) {
		StringBuilder info = new StringBuilder();
		
//		System.out.println("Getting project info for " + this);
		/* Get project info from DB */
		int iNumProjects = db.getNumProjects();
		for (int i=1; i <= iNumProjects; i++){
			info.append("\nProject ");
			info.append(i);
			info.append(" team: ");
			info.append(db.getTeamName(this, "P"+i));
			info.append("\nProject ");
			info.append(i);
			info.append(" Average grade: ");
			info.append(db.getAverageProjectGrade("P"+i));
			info.append("\nProject ");
			info.append(i);
			info.append(" team grade: ");
			info.append(db.getTeamGrade(db.getTeamName(this, "P"+i), "P"+i));
			info.append("\nProject ");
			info.append(i);
			info.append(" Average contribution: ");
			info.append(db.getContribution(this, "P"+i));
		}
		
		return info.toString();
	}
	
	public String getAssignmentInfoForTextarea(GradesDB db) {
		StringBuilder info = new StringBuilder();
		
//		System.out.println("Getting assignment info for " + this);
		int iNumAssignments = db.getNumAssignments();
		for (int i=1; i <= iNumAssignments; i++) {
			info.append("\nAssignment ");
			info.append(i);
			info.append(" grade: ");
			info.append(db.getStudentGrade("assignment "+i, this));
			info.append("\nAssignment ");
			info.append(i);
			info.append(" Average grade: ");
			info.append(db.getAverageAssignmentGrade("assignment "+i));
		} 

		return info.toString();
	}

    public String toString() {
    	return this.name;
    }

}
