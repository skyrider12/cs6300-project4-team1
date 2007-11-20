package edu.gatech.cs6300;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Map;

import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.client.spreadsheet.*;

public class GradesDB implements OverallGradeCalculator{

    Session session;    
    SpreadsheetEntry spreadsheet;
    DecimalFormat formatter = new DecimalFormat("#0.0#");
    Map<Student, Double> grade;
    String Formula;
    formulae Form = new formulae();
    
    Map<String, Student> studentMap = new HashMap<String, Student>();
    
    /* Cached worksheets */
    private static WorksheetEntry wsData = null;
    private static WorksheetEntry wsDetails = null;
    private static WorksheetEntry wsAttendance = null;
    private static WorksheetEntry wsGrades = null;
    
    private class formulae {
    	double AS; /*average grade in the assignments*/
    	int AT; /*attendance*/
    	ArrayList<Double> PR; /*Team grade in Proejct n*/
    	ArrayList<Double> IC; /*Average individual contribution in Project n*/
    }
    
    public GradesDB(Session session) {
        this.session = session;
        if(session.service==null) {System.err.println("session.service=null, exiting"); System.exit(1);}
        List sheets = getSpreadsheets(session.service);
        if(sheets==null) {System.err.println("sheets=null, exiting"); System.exit(1);}
        spreadsheet = getSpreadsheet(sheets, Constants.GRADES_DB);
    }
    
    public int getNumStudents() {
        WorksheetEntry wsDetails = this.getDetailsWorksheet();
    
        ListFeed feed = getFeed(session.service, wsDetails);
        int student_num = getNum(feed) ;
        return student_num;
    }

    /**
     * Creates HashSet of Students by connecting to google docs and parsing info
     */
    public HashSet<Student> getStudents() { 
        HashSet<Student> students = new HashSet<Student>();
        
        WorksheetEntry wsDetails = this.getDetailsWorksheet();
        WorksheetEntry wsAttendance = this.getAttendanceWorksheet();
        
        /* Get basic info for each student */
        ListFeed feed = getFeed(session.service, wsDetails);
        for (ListEntry entry : feed.getEntries()) {
            Student student = new Student();
            student.setName(entry.getCustomElements().getValue("name"));
            student.setGTID(entry.getCustomElements().getValue("gtid"));
            student.setEmail(entry.getCustomElements().getValue("email"));

            students.add(student);
            
            studentMap.put(student.getName(), student);
        }
        
        /* Get attendance of each student */
        ListFeed attendancefeed = getFeed(session.service, wsAttendance);
        for (ListEntry entry : attendancefeed.getEntries()) {   
            Student student = getStudentByName(entry.getCustomElements().getValue("studentname"));
            if (student != null) {
                String attendance = entry.getCustomElements().getValue("total").replace("%", "");
                
                if (attendance.indexOf(".") != -1) {
                    attendance = attendance.substring(0, attendance.indexOf("."));
                }
                student.setAttendance(Integer.parseInt(attendance));
                System.out.println(student.getName());
                System.out.println(student.getAttendance());
            }
        }
        
        return students;
    }
    
    
    public Student getStudentByName(String sName) {
        //return getStudentByName(sName, getStudents());
    	return studentMap.get(sName);
    }
    
//    public Student getStudentByName(String sName, HashSet<Student> students) {
//        
//        for (Student student : students) {
//            if (student.getName().equals(sName)) {
//                return student;
//            }
//        }
//        return null;
//    }
    
    public Student getStudentByID(String sGTID) {
        return getStudentByID(sGTID, getStudents());
    }
    
    public Student getStudentByID(String sGTID, HashSet<Student> students) {
        
        for (Student student : students) {
            if (student.getGtid().equals(sGTID)) {
                return student;
            }
        }
        return null;
    }
 
    /**
     * Creates ArrayList of Projects by connecting to google docs and parsing info
     */
    public ArrayList<Project> getProjects() {
    	ArrayList<Project> projects = new ArrayList<Project>();
    	Project p = null;
    	
    	/* Connect to "Data" Worksheet */
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Data");
        
        /* Get two columns from "Data" worksheet */
        ArrayList<String> projectsColumn = getColumn(session.service, worksheet, "projects");
        ArrayList<String> descriptionColumn = getColumn(session.service, worksheet, "description");       
        
        /* Create a new Project instance for each row found, using col info to set proj */
        for (int i=0; i < projectsColumn.size(); i++) {
        	p = new Project();
        	p.setProjectNumber(Integer.parseInt(projectsColumn.get(i).replaceFirst("P", "")));
        	p.setProjectName(projectsColumn.get(i));
        	p.setProjectDescription(descriptionColumn.get(i));
        	//p.
        	projects.add(p);
        }
        
        return projects;
    }

    /**
     * Creates ArrayList of Assignments by connecting to google docs and parsing info
     */
    public ArrayList<Assignment> getAssignments() {
    	ArrayList<Assignment> assignments = new ArrayList<Assignment>();
    	Assignment a = null;
  	        
        /* Get two columns from "Data" worksheet */
        ArrayList<String> assignmentsColumn = getColumn(session.service, this.getDataWorksheet(), "assignments");
        ArrayList<String> descriptionColumn = getColumn(session.service, this.getDataWorksheet(), "description");
        ArrayList<String> namesColumn = getColumn(session.service, this.getGradesWorksheet(), "name");
        
        for (int i=0; i < assignmentsColumn.size(); i++){
        	a = new Assignment();
        	a.setAssignmentNumber(Integer.parseInt(assignmentsColumn.get(i).replaceFirst("Assignment ", "")));
        	a.setAssignmentDescription(descriptionColumn.get(i));
        	
        	/* get all student grades for this assignment */
        	Map<Student, Integer> studentGrades = new HashMap<Student, Integer>();
        	ArrayList<String> gradesColumn = getColumn(session.service, this.getGradesWorksheet(), "assignment" + (i + 1));
        	for (int j = 0; j < gradesColumn.size(); j++) {
        		Student curStudent = studentMap.get(namesColumn.get(j));
        		curStudent.addAssignment(i + 1, a);
        		studentGrades.put(curStudent, new Integer(gradesColumn.get(j)));
        	}
        	
        	a.setScores(studentGrades);
        	//a.Scores
        	assignments.add(a);
        }
        return assignments;
    }
    
    /** TODO: This should not belong in GradesDB, but we can use to refactor out */
    public double getAverageAssignmentGrade(String assignmentp) {
        String newAssignment = assignmentp.toLowerCase().replaceAll(" ", "");  
        //String newAssignment = assignmentp;
        
        ArrayList<String> gradesColumn = getColumn(session.service, this.getGradesWorksheet(), newAssignment);
        
        double sum = 0;
        for (String strGrade : gradesColumn) {
            double grade = Double.parseDouble(strGrade);
            sum += grade;
        }
        
        if (gradesColumn.size() <= 0) {
            return 0;
        }
        
        return Double.parseDouble(formatter.format(sum/gradesColumn.size()));
    }
    
    /** TODO: This should not belong in GradesDB, but we can use to refactor out */
    public double getStudentGrade(String assignmentp, Student student) {
        
        String newAssignment = assignmentp.toLowerCase().replaceAll(" ", ""); 
        //String newAssignment = assignmentp;
        
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Grades");
        if(worksheet==null ) {System.err.println("worksheet null in getStudentGrade - Rerun the program, exiting");System.exit(1);}
        if(session.service==null ) {System.err.println("service null in getStudentGrade - Rerun the program, exiting");System.exit(1);}
        ListFeed feed = getFeed(session.service, worksheet);
        
        double grade = 0;
        for (ListEntry entry : feed.getEntries()) {   
            String name = entry.getCustomElements().getValue("name");
            if (name.equals(student.getName())) {
                grade = Double.parseDouble(entry.getCustomElements().getValue(newAssignment));
                break;
            }
        }
        
        return Double.parseDouble(formatter.format(grade));
    }
    
    /**
     * Find worksheet (e.g., "P4 Teams") and load teams & members
     */
    public HashSet<Team> getTeamsForProject(int iProjNumber) {
    	 WorksheetEntry worksheet = getWorksheet(spreadsheet, "P" + iProjNumber + " Teams");
    	 ListFeed feed = getFeed(session.service, worksheet);
    	 
    	 /* Get all the team grades */
    	 Map<String, Integer> teamGrades = getTeamGrades(iProjNumber);
    	 
    	 /* Get all the student contributions */
    	 Map<String, Float> allContributions = getContibutions(iProjNumber);
    	 
    	 /* The HashSet of Teams to return */
    	 HashSet<Team> teams = new HashSet<Team>();
    	 Team team = null;
    	 
    	 /* Go through each row returned */
    	 for (ListEntry entry : feed.getEntries()) {
    		 team = new Team();
    		 
    		 /* Map of contribution grades but only for this team */
    		 //Map<String, Float> teamContributions = new HashMap<String, Float>();
    		 
    		 /* Set the team name */
    		 team.setName(entry.getCustomElements().getValue("teamname"));
    		 
    		 /* Get the names of its 4-5 members across the row */
    		 HashSet<Student> members = new HashSet<Student>();
    		 Map<String, String> rowFeed = this.getRow(session.service, worksheet, team.getName());
    		 int i = 1;
    		 for (String rowEntry : rowFeed.keySet()) {
    			 //String studentName = rowEntry.getCustomElements().getValue("student" + i);
    			 String studentName = rowFeed.get("student" + i);
    			 
    			 if (studentName != null) {
	    			 Student student = studentMap.get(studentName);
	    			 
	    			 /* set team to students list of teams */
	    			 student.addTeam(iProjNumber, team);
	    			 
	    			 /* add student to team members list */
	    			 members.add(student);
	    			 
	    			 //set contribution
	    			 team.setRatingForStudent(student, allContributions.get(studentName));
	    			 i++;
    			 }
    		 }
    		 team.setMembers(members);
    		 
    		 /* Set the team score */
    		 team.setTeamScore(teamGrades.get(team.getName().toLowerCase().replace(" ", "")).intValue());
 
    		 /* Add to the final HashSet to be returned */
    		 teams.add(team);
    	 }
    	 
    	 return teams;
    }
    
    /** TODO: This should not belong in GradesDB, but we can use to refactor out */    
    public String getTeamName(Student student, String project) {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Teams");
        if(worksheet==null ) {System.err.println("worksheet null in getTeamName,Rerun the program - exiting");System.exit(1);}
        if(session.service==null ) {System.err.println("service null in getTeamName, Rerun the program - exiting");System.exit(1);}
        ListFeed feed = getFeed(session.service, worksheet);
    
        if(feed==null || feed.toString().length()==0) {System.err.println("No feed obtained in getTeamName. Rerun the program or contact your system administrator. Exiting. ");System.exit(1);}
        if (student != null) {
	        for (ListEntry entry : feed.getEntries()) {
	            for(String tag : entry.getCustomElements().getTags()) {
	                String val = entry.getCustomElements().getValue(tag);
	                if (val != null && val.equals(student.getName())) {
	                    return entry.getCustomElements().getValue("teamname");
	                }
	            }
	        }
        }
        return "";
    }
    
    
    /**
     * Get all the contributions for the given project number
     * @param iProject
     * @return Map<String,  Float>
     */
    public Map<String, Float> getContibutions(int iProject) {
    	HashMap<String, Float> contributions = new HashMap<String, Float>();
    	
    	WorksheetEntry worksheet = getWorksheet(spreadsheet, "P" + iProject + " Contri");
        ListFeed feed = getFeed(session.service, worksheet);
        
        /* Get all the students averages from the contributions worksheet and return it as a map */
        for (ListEntry entry : feed.getEntries()) {
            String studentName = entry.getCustomElements().getValue("students"); 
            String strVal = entry.getCustomElements().getValue("average");
            
            if (studentName != null) {
            	double val = Double.parseDouble(strVal);
            	contributions.put(studentName, new Float(val)); 
            }   
        }     
    	return contributions;
    }
    
    public int getContribution(Student student, String project) {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Contri");
        ListFeed feed = getFeed(session.service, worksheet);

        double val = 0;
        for (ListEntry entry : feed.getEntries()) {
        	
          String studentName = entry.getCustomElements().getValue("students"); 
          if (studentName != null && studentName.equals(student.getName())) {
              val = Double.parseDouble(entry.getCustomElements().getValue("average")); 
              break;
          }
          
        }
        return (int)Double.parseDouble(formatter.format(val));
    }
    
 
    public Map<String, Integer> getTeamGrades(int projNumber) {
    	HashMap<String, Integer> teamGrades = new HashMap<String, Integer>();
 
    	WorksheetEntry worksheet = getWorksheet(spreadsheet, "P" + projNumber + " Grades");
    	ListFeed feed = getFeed(session.service, worksheet);
    	List<ListEntry> entries = feed.getEntries();
        ListEntry entry = entries.get(entries.size() - 1);
    	
    	 for (String tag : entry.getCustomElements().getTags()) {
             if (!tag.equals("criteria") && !tag.equals("maxpoints")) {
            	 teamGrades.put(tag, new Integer(entry.getCustomElements().getValue(tag)));
             }       
         }
    	
    	 return teamGrades;
    }
//    public double getTeamGrade(String teamName, String project) {
//        
//        //String realTeamName = teamName.toLowerCase().replaceAll(" ", "");
//        
//        ArrayList<String> column = getColumn(session.service, worksheet, teamName);
//        
//        return Double.parseDouble(formatter.format(Double.parseDouble((String) column.toArray()[column.size() - 1])));
//    }
    
    public int getAverageProjectGrade(String project) {
//        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Grades");
//        ListFeed feed = getFeed(session.service, worksheet);
//        
//        List<ListEntry> entries = feed.getEntries();
//        ListEntry entry = entries.get(entries.size() - 1);
//        
//        double sum = 0;
//        int count = 0;
//        for (String tag : entry.getCustomElements().getTags()) {
//            //System.out.println("Tag in avgProject: " + tag);
//            if (!tag.equals("criteria") && !tag.equals("maxpoints")) {
//               sum += Double.parseDouble(entry.getCustomElements().getValue(tag));
//               count++;
//            }
//        }
//  
//        if (count == 0) {
//            return 0;
//        }
//        return Double.parseDouble(formatter.format(sum/count));
    	
    	Map<String, Integer> grades = getTeamGrades(Integer.parseInt(project.replace("P", "")));
    	int sum = 0; 
    	for (Integer val : grades.values()) {
    		sum += val.intValue();
    	}
    	return sum/grades.size();
    }
    
    /* ************************************************************
     * 
     * Helper functions useed to query the spreadsheet
     * There aren't tested by any test cases
     * 
     *************************************************************/
    public List getSpreadsheets(SpreadsheetService service) {
        URL metafeedUrl=null;
        SpreadsheetFeed feed=null;
        List spreadsheets = null;
        
        try {
            metafeedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
            feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
        }
        catch(Exception ex) {
            /* exception ! */
        }
        
        if (feed != null) {
            spreadsheets = feed.getEntries();
        }
        
        return spreadsheets;
    }
    
    public SpreadsheetEntry getSpreadsheet(List ssheets, String dbName){
        for (int i = 0; i < ssheets.size(); i++) {
            SpreadsheetEntry entry = (SpreadsheetEntry) ssheets.get(i);
            if (entry.getTitle().getPlainText().equals(dbName)){
                return entry;
            }
        }
        return null;
    }
    
    public WorksheetEntry getDataWorksheet() {
    	if (wsData == null) {
    		wsData = getWorksheet(this.spreadsheet, "Data");
    	}
    	return wsData;
    }
    
    public WorksheetEntry getDetailsWorksheet() {
    	if (wsDetails == null) {
    		wsDetails = getWorksheet(spreadsheet, "Details");
    	}
    	return wsDetails;
    }
    
    public WorksheetEntry getAttendanceWorksheet() {
    	if (wsAttendance == null) {
    		wsAttendance = getWorksheet(spreadsheet, "Attendance");
    	}
    	return wsAttendance;
    }
    
    public WorksheetEntry getGradesWorksheet() {
    	if (wsGrades == null) {
    		wsGrades = getWorksheet(spreadsheet, "Grades");
    	}
    	return wsGrades;
    }
    
    public WorksheetEntry getWorksheet(SpreadsheetEntry ss, String sWorksheetName) {
        WorksheetEntry worksheet = null;
        List worksheets = null;
        
        try {
            worksheets = ss.getWorksheets();    
        } catch (Exception e) {
            /* caught */
        }

        if (worksheets != null) {
            for (int j = 0; j < worksheets.size(); j++) {
                worksheet = (WorksheetEntry) worksheets.get(j);
                String title = worksheet.getTitle().getPlainText();
                if (title.equals(sWorksheetName)) {
                    return worksheet;
                }
            }
        }
        
        return null;
    }
    
    public ListFeed getFeed(SpreadsheetService service, WorksheetEntry worksheet){
        if(worksheet==null ) {System.err.println("worksheet null in getFeed, Rerun the program - exiting");}
        if(service==null ) {System.err.println("service null in getFeed, Rerun the program - exiting");}        
    	URL listFeedUrl = worksheet.getListFeedUrl();

        ListFeed feed = null;

        try {
            feed = service.getFeed(listFeedUrl, ListFeed.class);
            return feed;
        } catch (Exception e) {
            /* exception ! */
            return null;
        }
    }
    
    public ArrayList<String> getColumn(SpreadsheetService service, WorksheetEntry worksheet, String sColumnTitle) {
        
        String columnTitle = sColumnTitle.toLowerCase().replaceAll(" ", "");
        ArrayList<String> columns = new ArrayList<String>();
        if(worksheet==null ) {System.err.println("worksheet null in getColumn, Rerun the program - exiting");}
        if(service==null ) {System.err.println("service null in getColumn, Rerun the program - exiting");}
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed feed = null;

        try {
            feed = service.getFeed(listFeedUrl, ListFeed.class);    
        } catch (Exception e) {
            /* exception ! */
        }
        
        if (feed != null) {
                     
            for (ListEntry entry : feed.getEntries()) {
                columns.add(entry.getCustomElements().getValue(columnTitle));
//                for (String tag : entry.getCustomElements().getTags()) {
//                    if (tag.equals(columnTitle)){
//                        columns.add(entry.getCustomElements().getValue(tag));
//                    }
//                }
            }
        }      
        return columns;
    }
    
    public Map<String, String> getRow(SpreadsheetService service, WorksheetEntry worksheet, String sRowTitle) {
    	URL listFeedUrl = worksheet.getListFeedUrl();
    	ListFeed feed = null;

    	Map<String, String> rowList = new HashMap<String, String>();
    	
    	try {
    		feed = service.getFeed(listFeedUrl, ListFeed.class);    
    	} catch (Exception e) {
    		/* exception ! */
    	}

    	if (feed != null) {     	    	
    		for (ListEntry Lentry : feed.getEntries()) {
    			if (Lentry.getTitle().getPlainText().equals(sRowTitle)){
    				for (String tag : Lentry.getCustomElements().getTags()) {
    					rowList.put(tag, Lentry.getCustomElements().getValue(tag));
    				}
    			}
    		}
    	}
    	return rowList;
    }

    public HashSet<Student> getMember() {
    	String worksheetName;
    	/*
    	for (int i=0; i<this.getNumProjects(); i++){
    		worksheetName = "P"+i+" Teams";
    		WorksheetEntry worksheet = getWorksheet(spreadsheet, "P1 Teams");
    	}
    	*/
        
    	/* this.getRow(session.service, worksheet, sRowTitle) */
    	
    	return null;
    }
    
    public int getNum(ListFeed feed) {
        return feed.getEntries().size();
    }

	public Map<Student, Double> getAllGrades() {
		return this.grade;
	}

	public double getAverageGrade() {
		double sum = 0;
		for (Student s: this.getStudents()){
			sum = sum + grade.get(s);
		}
		return sum/this.grade.size();
	}

	public String getFormula() {
		return this.Formula;
	}

	public double getMedianGrade() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getStudentGrade(Student student) {
		return grade.get(student);
	}

	public void setFormula(String formula) {
		this.Formula = formula;
	}
	
	public void parseFormula(){
		String [] sformula = this.Formula.split(" ");
		for (String s : sformula){
		}
		
	}
	
	public static void main(String args[]) {
		Session session = new Session();
		session.login(Constants.USERNAME, Constants.PASSWORD);
		GradesDB gradesDB = new GradesDB(session);
		gradesDB.setFormula("AS * 0.2 + AT * 0.2 + ((PR1 + PR2 + PR3)/3) * 0.6"); 
		gradesDB.parseFormula();
	}
}