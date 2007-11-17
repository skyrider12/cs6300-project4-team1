package edu.gatech.cs6300;

import java.util.Map;

public interface OverallGradeCalculator {
	public void setFormula(String formula);
	public String getFormula();
	public double getStudentGrade(Student student);
	public Map<Student, Double> getAllGrades();
	public double getAverageGrade();
	public double getMedianGrade();
}
