package edu.gatech.cs6300;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.client.spreadsheet.*;

public class GradesDB {

    Session session;
    
    SpreadsheetEntry spreadsheet;
    
    DecimalFormat formatter = new DecimalFormat("#0.0#");
    
    public GradesDB(Session session) {
        this.session = session;
        
        List sheets = getSpreadsheets(session.service);
        spreadsheet = getSpreadsheet(sheets, Constants.GRADES_DB);
    }
    
    public int getNumStudents() {
        
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Details");
    
        ListFeed feed = getFeed(session.service, worksheet);
        int student_num = getNum(feed) ;
        return student_num;
    }
    
    public int getNumAssignments() {
        
        return getAssignments().size();
    }
    
    public int getNumProjects() {

        return getProjects().size();
    }
    
    public HashSet<Student> getStudents() { 
        
        HashSet<Student> students = new HashSet<Student>();
        
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Details");
        ListFeed feed = getFeed(session.service, worksheet);
        
        for (ListEntry entry : feed.getEntries()) {
            Student student = new Student();
            student.setName(entry.getCustomElements().getValue("name"));
            student.setGTID(entry.getCustomElements().getValue("gtid"));
            students.add(student);
        }
        
        WorksheetEntry attendanceworksheet = getWorksheet(spreadsheet, "Attendance");
        ListFeed attendancefeed = getFeed(session.service, attendanceworksheet);
        
        for (ListEntry entry : attendancefeed.getEntries()) {   
            Student student = getStudentByName(entry.getCustomElements().getValue("studentname"), students);
            if (student != null) {
                String attendance = entry.getCustomElements().getValue("total").replace("%", "");
                
                if (attendance.indexOf(".") != -1) {
                    attendance = attendance.substring(0, attendance.indexOf("."));
                }
                student.setAttendance(Integer.parseInt(attendance));
            }
          
        }
        
        return students;
    }
    
    
    public Student getStudentByName(String sName) {
        return getStudentByName(sName, getStudents());
    }
    
    public Student getStudentByName(String sName, HashSet<Student> students) {
        
        for (Student student : students) {
            if (student.getName().equals(sName)) {
                return student;
            }
        }
        return null;
    }
    
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
     *
     */
    public ArrayList<String> getProjects() {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Data");
        ArrayList<String> projects = getColumn(session.service, worksheet, "projects");

        return projects;
    }

    /**
     * 
     * @return
     */
    public ArrayList<String> getAssignments() {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Data");
        ArrayList<String> assignments = getColumn(session.service, worksheet, "assignments");

        return assignments;
    }
    
    public double getAverageAssignmentGrade(String assignmentp) {
        //String newAssignment = assignmentp.toLowerCase().replaceAll(" ", "");  
        String newAssignment = assignmentp;
        
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Grades");
        ArrayList<String> grades = getColumn(session.service, worksheet, newAssignment);
        
        double sum = 0;
        for (String strGrade : grades) {
            double grade = Double.parseDouble(strGrade);
            sum += grade;
        }
        
        if (grades.size() <= 0) {
            return 0;
        }
        
        return Double.parseDouble(formatter.format(sum/grades.size()));
    }
    
    public double getStudentGrade(String assignmentp, Student student) {
        
        //String newAssignment = assignmentp.toLowerCase().replaceAll(" ", ""); 
        String newAssignment = assignmentp;
        
        WorksheetEntry worksheet = getWorksheet(spreadsheet, "Grades");
        ListFeed feed = getFeed(session.service, worksheet);
        
        double grade = 0;
        for (ListEntry entry : feed.getEntries()) {   
            String name = entry.getCustomElements().getValue("name");
            if (name.equals(student.getName())) {
                grade = Double.parseDouble(entry.getCustomElements().getValue(newAssignment));
            }
        }
        
        return Double.parseDouble(formatter.format(grade));
    }
    

    public String getTeamName(Student student, String project) {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Teams");
        ListFeed feed = getFeed(session.service, worksheet);
        
        for (ListEntry entry : feed.getEntries()) {
            for(String tag : entry.getCustomElements().getTags()) {
                String val = entry.getCustomElements().getValue(tag);
                if (val.equals(student.getName())) {
                    return entry.getCustomElements().getValue("team");
                }
            }
        }
        return "";
    }
    
    public double getContribution(Student student, String project) {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Contri");
        ListFeed feed = getFeed(session.service, worksheet);

        double val = 0;
        for (ListEntry entry : feed.getEntries()) {
          String studentName = entry.getCustomElements().getValue("students"); 
          if (studentName.equals(student.getName())) {
              return Double.parseDouble(entry.getCustomElements().getValue("average")); 
          }
          
        }
        return Double.parseDouble(formatter.format(val));
    }
    
 
    
    public double getTeamGrade(String teamName, String project) {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Grades");
        //String realTeamName = teamName.toLowerCase().replaceAll(" ", "");
        
        ArrayList<String> column = getColumn(session.service, worksheet, teamName);
        
        return Double.parseDouble(formatter.format(Double.parseDouble((String) column.toArray()[column.size() - 1])));
    }
    
    public int getAverageProjectGrade(String project) {
        WorksheetEntry worksheet = getWorksheet(spreadsheet, project + " Grades");
        ListFeed feed = getFeed(session.service, worksheet);
        
        List<ListEntry> entries = feed.getEntries();
        ListEntry entry = entries.get(entries.size() - 1);
        
        int sum = 0;
        int count = 0;
        for (String tag : entry.getCustomElements().getTags()) {
            //System.out.println("Tag in avgProject: " + tag);
            if (!tag.equals("criteria") && !tag.equals("maxpoints")) {
               sum += Float.parseFloat(entry.getCustomElements().getValue(tag));
               count++;
            }
        }
  
        if (count == 0) {
            return 0;
        }
        return (int)sum/count;
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

    public int getNum(ListFeed feed) {
        return feed.getEntries().size();
    }
  
}
