package edu.gatech.cs6300;

import java.util.HashSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import javax.swing.JButton;

public class GradesGUI {
	
	private JFrame jFrame;  //  @jve:decl-index=0:visual-constraint="107,6"
	private JPanel jContentPane;
	private JComboBox jComboBox;
	private JTextArea jTextArea;
	private JButton jButton;
	
	GradesDB gDB;
	
	public GradesGUI(GradesDB gDB){
		this.gDB = gDB;
		this.getJFrame();
		this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.jButton.setEnabled(false);
		this.jTextArea.setEnabled(false);
		
		this.populateComboStudents(gDB.getStudents());
//		this.jComboBox.addItem("item1");
//		this.jComboBox.addItem("item2");
//		this.jComboBox.addItem("item3");
		
		jFrame.setVisible(true);
	}
	
	
	/**
	 * Given a HashSet<Students> fill in combobox appropriately
	 */
	public void populateComboStudents(HashSet<Student> students) {
		for (Student student : students){
			this.jComboBox.addItem(student.getName());
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
		
	}
	
	/**
	 * @return the Student currently selected in the combobox
	 */
	public Student getSelectedStudent() {
		return new Student();
	}
	
	/**
	 * @return the value in the StudentName Label
	 */
	public String getStudentNameLabel() {
		return "";
	}
	
	/**
	 * @return the value in the StudentGTID Label
	 */
	public String getStudentGTIDLabel() {
		return "";
	}
	
	/**
	 * @return the value in the StudentEmail Label
	 */
	public String getStudentEmailLabel() {
		return "";
	}
	
	/**
	 * @return the value in the StudentAttendance Label, cast to Integer
	 */
	public String getStudentAttendanceLabel() {
		return "";
	}
	
	public int getProjectTeamGradeLabel(int iProjectNumber) {
		return -1;
	}
	
	public int getProjectContributionLabel(int iProjectNumber) {
		return -1;
	}
	
	public int getProjectAverageGradeLabel(int iProjectNumber) {
		return -1;
	}
	
	public int getAssignmentAvgGradeLabel(int iAssignmentNumber) {
		return -1;
	}
	
	public int getStudentAssignmentGradeLabel(int iAssignmentNumber) {
		return -1;
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
						System.out.println("propertyChange(enabled)"); // TODO Auto-generated property Event stub "enabled" 
					}
				}
			});

			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()");
					JComboBox cb = (JComboBox)e.getSource();
			        Student student = (Student)cb.getSelectedItem();
			        
			        jTextArea.setText(student.getName());
			        jTextArea.append("\n"+student.getGtid());
			        jTextArea.append("\n"+student.getEmail());
			        
			        jButton.setEnabled(true);
					jTextArea.setEnabled(true);
					
					setSelectedStudent(cb.getSelectedIndex());
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
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					jButton.setEnabled(false);
				}
			});
		}
		return jButton;
	}
	
	public static void main (String[] args){
		System.out.println("Start");
		Session session = new Session();
		GradesDB gradesDB = new GradesDB(session);	
		GradesGUI GGUI = new GradesGUI(gradesDB);
		
		System.out.println("End");
	}

}