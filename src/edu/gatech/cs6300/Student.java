package edu.gatech.cs6300;

import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.Map;

public class Student {
    private String name;
    private String gtid;
    private int attendance;
    private String email;
    private Map<Integer, Team> teams;
    private Map<Integer, Assignment> assignments;
    
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
    	return getBasicInfoForTextarea();
    }
    
	public String getBasicInfoForTextarea() {
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
	
    public String toString() {
    	return this.name;
    }

}
