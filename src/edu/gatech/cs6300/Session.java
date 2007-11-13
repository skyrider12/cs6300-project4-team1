package edu.gatech.cs6300;

import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import java.util.List;

public class Session {
    
    //private static Session instance;
    
    public SpreadsheetService service;
    
    static Session instance;
    
    public Session() {
    	
    }
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session(); 
        } 
        return instance;       
    }

    /***
     * Facilitates login to Google Spreadsheet
     * @return 0 if success; 1 if failure
     */
    public int login(String username, String password) {
        int success = 1;
        
        this.service = new SpreadsheetService(Constants.APPLICATION_NAME);
        
        try {
            this.service.setUserCredentials(username, password);
            success = 0;
        } catch(Exception ae) {
            /* Catching AuthenticationException */
            success = 1;
        }              
        
        return success;
    }
    
    /***
     * Logs out of Google Spreadsheet
     * @return 0 if success; 1 if failure
     */
    public int logout() {
        return 0;
    }
    
    public GradesDB getDBByName(String DBName) {
    	
        return new GradesDB(this);
    }
    
    public SpreadsheetService getService() {
        return this.service;
    }
    
    public static void main(String args[]){
    	Session s = new Session();
		System.out.println(s.login(Constants.USERNAME, Constants.PASSWORD));
		GradesDB gDB = new GradesDB(s);
		List sheets=gDB.getSpreadsheets(s.service);
		SpreadsheetEntry spreadsheet=gDB.getSpreadsheet(sheets,Constants.GRADES_DB);
		WorksheetEntry worksheet= gDB.getWorksheet(spreadsheet,"Details");
		gDB.getColumn(s.service,worksheet , "gtid");
		gDB.getRow(s.service, worksheet ,"Wilfrid Eastwood");
		gDB.getNum(gDB.getFeed(s.service, worksheet));
		System.out.println("Num of Students: "+gDB.getNumStudents());
		System.out.println("Num of Assignments: "+gDB.getNumAssignments());
    }
    
}
