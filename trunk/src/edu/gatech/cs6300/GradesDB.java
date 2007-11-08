package src.edu.gatech.cs6300;

import java.net.URL;
import java.util.List;
import java.util.HashSet;

import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.client.spreadsheet.*;

public class GradesDB {

	Session session;
	
    SpreadsheetEntry spreadsheet;
    
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
    	
    	WorksheetEntry worksheet = getWorksheet(spreadsheet, "Data");
    
    	ListFeed feed = getFeed(session.service, worksheet);
    	int assignment_num = getNum(feed) ;
        return assignment_num;
    }
    
    public int getNumProjects() {

        return getNumAssignments();
    }
    
    public HashSet<Student> getStudents() { 
        System.out.println("Supposed to return students!");
        return new HashSet<Student>();
    }
    
    public Student getStudentByName(String sName) {
        return new Student();
    }
    
    public Student getStudentByID(String sGTID) {
        return new Student();
    }
    
    public List getSpreadsheets(SpreadsheetService service) {
        URL metafeedUrl=null;
        SpreadsheetFeed feed=null;
        
        try {
            metafeedUrl = new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/full");
            feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
        }
        catch(Exception ex) {
            /* exception ! */
        }
        
        List spreadsheets = feed.getEntries();
        
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
    
    public ListFeed getColumn(SpreadsheetService service, WorksheetEntry worksheet, String sColumnTitle) {
    	System.out.println("getColumn");
    	URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed feed = null;

        try {
            feed = service.getFeed(listFeedUrl, ListFeed.class);    
        } catch (Exception e) {
            /* exception ! */
        }
        
        if (feed != null) {
        	System.out.println(feed.getEntries().get(0).getCustomElements().getTags());
        	
//        	for (int i=0; i<feed.getEntries().get(0).getCustomElements().getTags().size(); i++){
//        		if (feed.getEntries().get(0).getCustomElements().getTags().toArray()[i].equals(sColumnTitle)){
//        			System.out.println(feed.getEntries().get(0).getCustomElements().getValue(arg0));
//        		}
//        	}
//        	
            for (ListEntry Lentry : feed.getEntries()) {
                
                //System.out.println(Lentry.getTitle().getPlainText());
                
                for (String tag : Lentry.getCustomElements().getTags()) {
                	if (tag.equals(sColumnTitle)){
                		System.out.println("  " + Lentry.getCustomElements().getValue(tag) + "");
                	}
                }
            }
        }
        
        return feed;
    }
    
    public ListFeed getRow(SpreadsheetService service, WorksheetEntry worksheet, String sRowTitle) {
    	System.out.println("getRow");
        URL listFeedUrl = worksheet.getListFeedUrl();
        ListFeed feed = null;

        try {
            feed = service.getFeed(listFeedUrl, ListFeed.class);    
        } catch (Exception e) {
            /* exception ! */
        }
        
        if (feed != null) {     	    	
            for (ListEntry Lentry : feed.getEntries()) {
                if (Lentry.getTitle().getPlainText().equals(sRowTitle)){
                	for (String tag : Lentry.getCustomElements().getTags()) {
                		System.out.print("  " + Lentry.getCustomElements().getValue(tag) + " ");
                	}
                }
            }
        }
        
        return feed;
    }
    
    public int getNum(ListFeed feed) {
        return feed.getEntries().size();
    }
    
}
