package edu.gatech.cs6300;

import java.util.HashSet;

import javax.swing.JFrame;


public class GradesGUI extends JFrame {

    public GradesGUI() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void populateComboStudents(HashSet<Student> students) {
        
    }
    
    public int getNumStudentsInComboBox() {
        return 0;
    }
    
    public void setSelectedStudent(int student) {
        
    }
    
    public Student getSelectedStudent() {
        return new Student();
    }

    public String getStudentNameLabel() {
        return "";
    }
    
    public String getStudentGTIDLabel() {
        return "";
    }
    public String getStudentEmailLabel() {
        return "";
    }
    public String getStudentAttendanceLabel() {
        return "";
    }
    
    public String getAssignmentAvgGradeLabel(int val) {
        return "";
    }
    
    public String getStudentAssignmentGradeLabel(int val) {
        return "";
    }
    
    public String getProjectTeamGradeLabel(int val) {
        return "";
    }
    
    public String getProjectAverageGradeLabel(int val) {
        return "";
    }
    
    public String getProjectContributionLabel(int val) {
        return "";
    }
} 
