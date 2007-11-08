package edu.gatech.cs6300;

import java.net.URL;
import java.util.List;
import java.util.HashSet;

import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.client.spreadsheet.*;

public class GradesDB {

    public int getNumStudents() {
        return 14;
    }
    
    public int getNumAssignments() {
        return 0;
    }
    
    public int getNumProjects() {
        return 0;
    }
    
    public HashSet<Student> getStudents() {        
        return new HashSet<Student>();
    }
    
    public Student getStudentByName(String sName) {
        return new Student();
    }
    
    public Student getStudentByID(String sGTID) {
        return new Student();
    }
    
    private List getSpreadsheet(SpreadsheetService service) {
    	URL metafeedUrl=null;
    	SpreadsheetFeed feed=null;
    	try{
    		
    		metafeedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
    		feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
    	}
    	catch(Exception ex)
    	{
    		
    	}
        List spreadsheets = feed.getEntries();
        for (int i = 0; i < spreadsheets.size(); i++) {
          SpreadsheetEntry entry = (SpreadsheetEntry)spreadsheets.get(i);
          System.out.println("\t" + entry.getTitle().getPlainText());
        }
        return spreadsheets;
    }
    
}
