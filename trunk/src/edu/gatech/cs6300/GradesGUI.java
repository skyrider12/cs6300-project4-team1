package edu.gatech.cs6300;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class GradesGUI {
	
	private static JFrame jFrame;  //  @jve:decl-index=0:visual-constraint="107,6"
	private static JPanel jContentPane;
	private static JComboBox jComboBox;
	private static JTextArea jTextArea;
	private static JButton jButton;
	private ArrayList<Student> studentList;
	private JEditorPane jEditorPane = null;
	private JButton jButton1 = null;
	private JLabel jLabel = null;
	private JLabel StudentList = null;
	private JLabel studentGrade = null;
	private JTextField studentScore = null;
	public formulae f;
	static DecimalFormat formatter = new DecimalFormat("#0.00#");
	
    class formulae {
    	double AS; /*average grade in the assignments*/
    	int AT; /*attendance*/
    	ArrayList<Integer> PR; /*Team grade in Proejct n*/
    	float IC; /*Average individual contribution in Project n*/
    }
    
	public GradesGUI(){
		this.getJFrame();
		if(this.studentList == null || this.studentList.size() == 0) {
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			jButton.setEnabled(false);
			jTextArea.setEnabled(false);
			jButton1.setEnabled(false);
			studentScore.setEnabled(false);
			studentList= new ArrayList<Student>();
		}
		f = new formulae();
		f.PR = new ArrayList<Integer>();
		
		jFrame.setVisible(true);
	}	
	
	/**
	 * Given a HashSet<Students> fill in combobox appropriately
	 * Only fill with students names -- will lazy-load info when selected
	 */
	public void populateComboStudents(HashSet<Student> students) {	
		for (Student student : students){
			jComboBox.addItem(student);		
		}
	}
		
	/**
	 * Count number of students in the combobox
	 */
	public int getNumStudentsInComboBox() {
		return jComboBox.getItemCount();
		
	}

	/**
	 * Given an index, set the selected Student
	 */
	public void setSelectedStudent(int index) {
		jComboBox.setSelectedIndex(index);
	}
	
	/**
	 * @return the Student currently selected in the combobox
	 */
	public Student getSelectedStudent() {		
		return (Student) jComboBox.getSelectedItem();		
	}
	
	/**
	 * @return the value in the StudentName Label
	 */
	public String getStudentNameLabel() {
		return getLabelValue("Name: ");
	}
	
	/**
	 * @return the value in the StudentGTID Label
	 */
	public String getStudentGTIDLabel() {
		return getLabelValue("GTID: ");
	}
	
	/**
	 * @return the value in the StudentEmail Label
	 */
	public String getStudentEmailLabel() {
		return getLabelValue("Email: ");
	}
	
	private String getLabelValue(String sLabel) {
		String sValue = null;
		int iLabelLength = sLabel.length();
		
		String text = jTextArea.getText();
				
		if (text.length() > 0) {
			int iLabelIndex = text.indexOf(sLabel);
			if (iLabelIndex >= 0) {
				sValue = text.substring(iLabelIndex + iLabelLength, text.indexOf("\n", iLabelIndex));
			}
		}
		return sValue;
	}
	
	/**
	 * @return the value in the StudentAttendance Label, cast to Integer
	 */
	public int getStudentAttendanceLabel() {
		String sAttendance = null; 
		int iAttendance = 0;
		String text = jTextArea.getText();
		
		if(text.length() > 0) {
			sAttendance = text.substring(text.indexOf("Attendance: ")+12,text.indexOf("%"));
			
			try {
				iAttendance= Integer.parseInt(sAttendance);
			} catch(Exception ex) {
				System.err.println(sAttendance);
			}
		}
		return iAttendance;		
	}
	
	private int getGradeLabelValueInt(String sLabel) {
		String sValue = getLabelValue(sLabel);
		int iValue = 0;
		try {
			iValue= Integer.parseInt(sValue);
		} catch(Exception ex) {
			System.err.println("Error parsing integer of: " + sLabel + ", " + sValue);
		}
		return iValue;
	}
	
	private Float getGradeLabelValueFloat(String sLabel) {
		String sValue = getLabelValue(sLabel);
		Float fValue = null;
		try {
			fValue= Float.parseFloat(sValue);
		} catch(Exception ex) {
			System.err.println("Error parsing integer of: " + sLabel + ", " + sValue);
		}
		return fValue;
	}
	
	public int getProjectTeamGradeLabel(int iProjectNumber) {
		return getGradeLabelValueInt("Project " + iProjectNumber + " team grade: ");
	}
	
	public Float getProjectContributionLabel(int iProjectNumber) {
		return getGradeLabelValueFloat("Project " + iProjectNumber + " Average contribution: ");
	}
	
	public int getProjectAverageGradeLabel(int iProjectNumber) {
		return getGradeLabelValueInt("Project " + iProjectNumber + " Average grade: ");
	}
	
	public int getAssignmentAvgGradeLabel(int iAssignmentNumber) {
		return getGradeLabelValueInt("Assignment " + iAssignmentNumber + " Average grade: ");
	}
	
	public int getStudentAssignmentGradeLabel(int iAssignmentNumber) {
		return getGradeLabelValueInt("Assignment " + iAssignmentNumber + " grade: ");
	}

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(600, 450));
			jFrame.setContentPane(getJContentPane1());
			jFrame.setTitle("cs6300 GRADING TOOL");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane1() {
		if (jContentPane == null) {
			studentGrade = new JLabel();
			studentGrade.setBounds(new Rectangle(20, 203, 174, 21));
			studentGrade.setText("Calculated student Grade:");
			StudentList = new JLabel();
			StudentList.setBounds(new Rectangle(16, 55, 105, 18));
			StudentList.setText("Student:");
			jLabel = new JLabel();
			jLabel.setBounds(new Rectangle(16, 12, 82, 18));
			jLabel.setText("Formula:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJComboBox1(), null);
			jContentPane.add(getJTextArea1(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getJEditorPane(), null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(jLabel, null);
			jContentPane.add(StudentList, null);
			jContentPane.add(studentGrade, null);
			jContentPane.add(getStudentScore(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1(){
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(13, 78, 272, 26));
			
			jComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if ((e.getPropertyName().equals("enabled"))) {
					}
				}
			});

			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (!f.equals(null)){
						f.AT =0;
						f.AS=0;
						f.IC=0;
						f.PR.clear();
					}
					
					JComboBox cb = (JComboBox)e.getSource();
					Student selectedStudent = (Student)cb.getSelectedItem();
					f.AT = selectedStudent.getAttendance();
					
					String sInfo = selectedStudent.getBasicInfoForTextarea();
					
					Map<Integer, Team> studentTeams = selectedStudent.getTeams();

					for (int i=1; i<=studentTeams.keySet().size(); i++) {
						sInfo += studentTeams.get(i).project.getInfoForTextarea(selectedStudent);
						Team team = studentTeams.get(i);
						f.PR.add(team.getTeamScore());
						f.IC+=team.getRatingForStudent(selectedStudent);
					}
					
					Map<Integer, Assignment> assignments = selectedStudent.getAssignments();
					
					for (int j=1; j<= assignments.keySet().size(); j++){

						Assignment assignment = assignments.get(j);
						f.AS += assignment.getScoreForStudent(selectedStudent);
						sInfo += assignments.get(j).getInfoForTextarea(selectedStudent);						
					}
					 
					f.AS = f.AS/assignments.keySet().size();
					f.IC = f.IC/studentTeams.keySet().size();
					
					/* Using Student, display basic, project, and assignment info */
					jTextArea.setText(sInfo);
			        
			        /* Enable save button (since diff student) */
			        /* Should this ever really be disabled?? */
	        		jButton.setEnabled(true);
	        		
					jTextArea.setEnabled(true);
					
					String equation = setFormula();
					equation = equation.replaceFirst("= ", "");
					
					if (!equation.equals("")){
						
						equation = equation.replaceAll("AS", Double.toString(f.AS));
						equation = equation.replaceAll("AT", Integer.toString(f.AT));
						equation = equation.replaceAll("IC", Float.toString(f.IC));

						for (int i=1; i<=f.PR.size(); i++){
							equation = equation.replaceAll("PR"+i, Integer.toString(f.PR.get(i-1)));
						}

						Double result = FormulaParser.processEquation(equation);
						studentScore.setText(Double.toString(Double.parseDouble(formatter.format(result))));
						studentScore.setEnabled(true);
						
					}
				}
			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(300, 50, 287, 359));
			jTextArea.setLineWrap(true);
		}
		return jTextArea;
	}
	
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(205, 116, 75, 29));
			jButton.setText("Save");

			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String filename = ((Student) jComboBox.getSelectedItem()).getName() + ".txt";
					
				    try {
				        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
				        out.write(jTextArea.getText());
				        out.close();
				        jButton.setEnabled(false);
				    } catch (IOException ioe) {
				    	ioe.printStackTrace();
				    }
					
					final JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(null);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						try {
							PrintWriter pr = new PrintWriter(new FileWriter(file));
							String allinfo = jTextArea.getText() + "\n" +
											"Formula" +setFormula() + "\n" +
											"Total Score: "+studentScore.getText();
							pr.print(allinfo);
							pr.close();
							jButton.setEnabled(false);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jEditorPane	
	 * 	
	 * @return javax.swing.JEditorPane	
	 */
	private JEditorPane getJEditorPane() {
		if (jEditorPane == null) {
			jEditorPane = new JEditorPane();
			jEditorPane.setBounds(new Rectangle(122, 9, 361, 22));
			jEditorPane.setText("= ");
			jEditorPane.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					jButton1.setEnabled(true); 
				}
			});
		}
		return jEditorPane;
	}

	/**
	 * This method initializes jButton1	; button for Formula saving
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(489, 3, 100, 29));
			jButton1.setText("Calculate");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String equation = setFormula();
					equation = equation.replaceFirst("=", "");

					equation = equation.replaceAll("AS", Double.toString(f.AS));
					equation = equation.replaceAll("AT", Integer.toString(f.AT));
					equation = equation.replaceAll("IC", Float.toString(f.IC));
					
					for (int i=1; i<=f.PR.size(); i++){
						equation = equation.replaceAll("PR"+i, Integer.toString(f.PR.get(i-1)));
					}
									
					Double result = FormulaParser.processEquation(equation);
					studentScore.setText(Double.toString(Double.parseDouble(formatter.format(result))));
					studentScore.setEnabled(true);
					jButton1.setEnabled(false);
				}
			});
		}
		return jButton1;
	}
	
	public String setFormula(){
		return jEditorPane.getText();
	}

	/**
	 * This method initializes studentScore	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getStudentScore() {
		if (studentScore == null) {
			studentScore = new JTextField();
			studentScore.setBounds(new Rectangle(199, 204, 52, 19));
		}
		return studentScore;
	}
}