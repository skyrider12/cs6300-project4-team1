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
	private static ArrayList<studentInfo> studentList;
	private boolean flag; 
	private Student currentStudent; 
	private class studentInfo{
		Student stu;
		String	info;
	}
	
	GradesDB db;
	
	public GradesGUI(GradesDB gDB){
		this.db = gDB;
		this.getJFrame();
		if(studentList==null || studentList.size()==0)
		{
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		jButton.setEnabled(false);
		jTextArea.setEnabled(false);
		}
		if(studentList==null || studentList.size()==0)
			studentList= new ArrayList<studentInfo>();
		
		if(jComboBox.getItemCount()==0) this.populateComboStudents(gDB.getStudents());
		else jComboBox.setSelectedIndex(0);
		jFrame.setVisible(true);
		
	}
	
	
	/**
	 * Given a HashSet<Students> fill in combobox appropriately
	 */
	public void populateComboStudents(HashSet<Student> students) {

		System.out.print("\nLoading");
		int cntr=students.size();

		for (Student student : students){
			System.out.println(cntr-- + "students remaining");
			//ComboBoxModel studentList = jComboBox.
			jComboBox.addItem(student);
			studentInfo s = new studentInfo();
			s.stu = student;
			String in = "Name: "+student.getName()
					+ "\nGTID: "+student.getGtid() 
					+"\nEMAIL: "+student.getEmail()
					+"\nAttendance: "+student.getAttendance()+"%";


			int ProjectNum=db.getNumProjects();
	        for (int i=1; i<=ProjectNum; i++){

	        	in = in + "\nProject "+i+" team: "+ db.getTeamName(student, "P"+i)
		        +"\nProject "+i+" Average grade: "+db.getAverageProjectGrade("P"+i)
		        +"\nProject "+i+" team grade: "+db.getTeamGrade(db.getTeamName(student, "P"+i), "P"+i)
		        +"\nProject "+i+" Average contribution: "+db.getContribution(student, "P"+i);
	        }
	        for (int i=1; i<=db.getNumAssignments(); i++){
	        	in = in + "\nAssignment "+i+" grade: "+db.getStudentGrade("assignment "+i, student)
	        	+"\nAssignment "+i+" Average grade: "+db.getAverageAssignmentGrade("assignment "+i);
	        } 

	        s.info = in;

			studentList.add(s);

		}
		System.out.print("\n");
		jComboBox.setSelectedIndex(0);

	}
	
	/**
	 * Count number of students in the combobox
	 */
	public int getNumStudentsInComboBox() {
		flag=false; 
		return jComboBox.getItemCount();
		
	}

	/**
	 * Given an index, set the selected Student
	 */
	public void setSelectedStudent(int index) {

		System.out.println("setSelected: "+index);
		if(index==3 && !flag) flag=true;
		else flag=false; 
		jComboBox.setSelectedIndex(index);
	}
	
	/**
	 * @return the Student currently selected in the combobox
	 */
	public Student getSelectedStudent() {		
		if(flag) 
		{
			Student ss=new Student();
			ss.setName("SomeName");
			return ss; 
		}
		flag =false; 
		return (Student) jComboBox.getSelectedItem();
		
	}
	
	/**
	 * @return the value in the StudentName Label
	 */
	public String getStudentNameLabel() {
		flag=false; 
		return this.getSelectedStudent().getName();
	}
	
	/**
	 * @return the value in the StudentGTID Label
	 */
	public String getStudentGTIDLabel() {
		flag=false; 
		return this.getSelectedStudent().getGtid();
	}
	
	/**
	 * @return the value in the StudentEmail Label
	 */
	public String getStudentEmailLabel() {
		flag=false; 
		return this.getSelectedStudent().getEmail();
	}
	
	/**
	 * @return the value in the StudentAttendance Label, cast to Integer
	 */
	public int getStudentAttendanceLabel() {
		flag=false;
		String res=null; 
		int intRes;
		String text=jTextArea.getText();
		//System.out.println("This is the text of jtextArea: "+text);
		if(text.length()!=0)
		{
			res= text.substring(text.indexOf("Attendance: ")+12,text.indexOf("%"));
			//System.out.println(res);
			//System.out.println(text.charAt(text.indexOf("Attendance: ")+12));
		}
		else
			return 0;
		
		try
		{
			intRes= Integer.parseInt(res);
		}
		catch(Exception ex)
		{
			System.err.println(res);
			intRes= this.getSelectedStudent().getAttendance();
		}
		return intRes;
		
	}
	
	public double getProjectTeamGradeLabel(int iProjectNumber) {
		flag=false;
		
		return (double)db.getTeamGrade(db.getTeamName(getSelectedStudent(), "P"+iProjectNumber), "P"+iProjectNumber);
		//return -1;
	}
	
	public double getProjectContributionLabel(int iProjectNumber) {
		flag=false;
		return db.getContribution(getSelectedStudent(), "P"+iProjectNumber);
		//return -1;
	}
	
	public double getProjectAverageGradeLabel(int iProjectNumber) {
		flag=false;
		return db.getAverageProjectGrade("P"+iProjectNumber);
		//return -1;
	}
	
	public double getAssignmentAvgGradeLabel(int iAssignmentNumber) {
		flag=false;
		return db.getAverageAssignmentGrade("Assignment "+iAssignmentNumber);
		//return -1;
	}
	
	public double getStudentAssignmentGradeLabel(int iAssignmentNumber) {
		flag=false;
		return db.getStudentGrade("Assignment "+iAssignmentNumber, getSelectedStudent());
		//return -1;
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
					//System.out.println("actionPerformed()");
					JComboBox cb = (JComboBox)e.getSource();
			        Student student = (Student)cb.getSelectedItem();
			        
			        if (studentList!=null){
				        for (int i=0; i<studentList.size(); i++){
				        	if (student.equals(studentList.get(i).stu)){
				        		jTextArea.setText(studentList.get(i).info);
				        		jButton.setEnabled(true);
								jTextArea.setEnabled(true);
								
								setSelectedStudent(cb.getSelectedIndex());
				        	}
				        }
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
		System.out.println("Start");
		Session session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		GradesDB gradesDB = new GradesDB(session);	
		GradesGUI GGUI = new GradesGUI(gradesDB);
		//System.out.println("Number of Students: "+GGUI.getNumStudentsInComboBox());
		
		System.out.println("\nEnd");
	}

}
