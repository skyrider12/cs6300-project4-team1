package edu.gatech.cs6300;

import java.util.ArrayList;
import java.util.HashSet;

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

public class GradesGUI {
		
	private static JFrame jFrame;  //  @jve:decl-index=0:visual-constraint="107,6"
	private static JPanel jContentPane;
	private static JComboBox jComboBox;
	private static JTextArea jTextArea;
	private static JButton jButton;
	private ArrayList<Student> studentList;
	
	GradesDB db;
	
	public GradesGUI(GradesDB gDB){
		this.db = gDB;
		this.getJFrame();
		if(this.studentList == null || this.studentList.size() == 0) {
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			jButton.setEnabled(false);
			jTextArea.setEnabled(false);
			studentList= new ArrayList<Student>();
		}
		
		/* When startup GUI, populate combo box */
		if(jComboBox.getItemCount() == 0) {
			this.populateComboStudents(gDB.getStudents());
		} else {
			jComboBox.setSelectedIndex(0);
		}
		
		jFrame.setVisible(true);
	}
	
	
	/**
	 * Given a HashSet<Students> fill in combobox appropriately
	 * Only fill with students names -- will lazy-load info when selected
	 */
	public void populateComboStudents(HashSet<Student> students) {
//		System.out.println("\nLoading Combobox");
		
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
//		System.out.println("setSelected: " + index); 
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
		
		// System.out.println("Got Label Value: " + sValue);
		
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
		
		// System.out.println("Got Attendance Label: " + iAttendance);
		
		return iAttendance;		
	}
	
	private double getGradeLabelValue(String sLabel) {
		String sValue = getLabelValue(sLabel);
		double dValue = 0;
		try {
			dValue= Double.parseDouble(sValue);
		} catch(Exception ex) {
			System.err.println("Error parsing double of: " + sLabel + ", " + sValue);
		}
		return dValue;
	}
	
	public double getProjectTeamGradeLabel(int iProjectNumber) {
		return getGradeLabelValue("Project " + iProjectNumber + " team grade: ");
	}
	
	public double getProjectContributionLabel(int iProjectNumber) {
		return getGradeLabelValue("Project " + iProjectNumber + " Average contribution: ");
	}
	
	public double getProjectAverageGradeLabel(int iProjectNumber) {
		return getGradeLabelValue("Project " + iProjectNumber + " Average grade: ");
	}
	
	public double getAssignmentAvgGradeLabel(int iAssignmentNumber) {
		return getGradeLabelValue("Assignment " + iAssignmentNumber + " Average grade: ");
	}
	
	public double getStudentAssignmentGradeLabel(int iAssignmentNumber) {
		return getGradeLabelValue("Assignment " + iAssignmentNumber + " grade: ");
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
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJComboBox1(), null);
			jContentPane.add(getJTextArea1(), null);
			jContentPane.add(getJButton(), null);
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
			jComboBox.setBounds(new Rectangle(15, 16, 272, 26));
			
			jComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if ((e.getPropertyName().equals("enabled"))) {
//						System.out.println("propertyChange(enabled)"); // TODO Auto-generated property Event stub "enabled" 
					}
				}
			});

			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					System.out.println("actionPerformed()");
					JComboBox cb = (JComboBox)e.getSource();
			        
					/* Get the selected student */
					Student selectedStudent = (Student)cb.getSelectedItem();
	        		
					/* Get student's basic info for display */
					String sBasicInfo = selectedStudent.getBasicInfoForTextarea(db);
					
					/* Fill the textarea with the selected student's info and "Loading..." */
			        jTextArea.setText(sBasicInfo + "\n\nLoading...\n");
					
			        /* Get student's project and assignment info for display */
			        /* This takes a long time ... */
					String sProjectInfo = selectedStudent.getProjectInfoForTextarea(db);
					String sAssignmentInfo = selectedStudent.getAssignmentInfoForTextarea(db);
					
					/* After get back project and assignment info, reset text area content */
					jTextArea.setText(sBasicInfo + sProjectInfo + sAssignmentInfo);
			        
			        /* Enable save button (since diff student) */
			        /* Should this ever really be disabled?? */
	        		jButton.setEnabled(true);
	        		
					jTextArea.setEnabled(true);
					
					/* TODO: Shouldn't have to do this */
					/* setSelectedStudent(cb.getSelectedIndex()); */
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
			jTextArea.setBounds(new Rectangle(301, 22, 286, 387));
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
			jButton.setBounds(new Rectangle(204, 53, 75, 29));
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
							pr.print(jTextArea.getText());
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
	
	public static void main (String[] args){
//		System.out.println("Start");
		Session session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		GradesDB gradesDB = new GradesDB(session);	
		GradesGUI GGUI = new GradesGUI(gradesDB);
		//System.out.println("Number of Students: "+GGUI.getNumStudentsInComboBox());
		
//		System.out.println("\nEnd");
	}
	
//	/**
//	 * The current Student information should be saved
//	 * to a specified file.
//	 */
//	public void testOnSaveButtonClicked() {
//		
//	}

}
