package src.edu.gatech.cs6300;

import com.google.gdata.client.spreadsheet.*;

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
		GradesDB gDB = new GradesDB(s);

		System.out.println(s.login(Constants.USERNAME, Constants.PASSWORD));
		gDB.getColumn(s.service, gDB.getWorksheet(gDB.getSpreadsheet(gDB.getSpreadsheets(s.service), Constants.GRADES_DB),"Details"),"gtid");
		gDB.getRow(s.service, gDB.getWorksheet(gDB.getSpreadsheet(gDB.getSpreadsheets(s.service), Constants.GRADES_DB),"Details"),"Wilfrid Eastwood");
		gDB.getNum(gDB.getFeed(s.service, gDB.getWorksheet(gDB.getSpreadsheet(gDB.getSpreadsheets(s.service), Constants.GRADES_DB),"Details")));
		System.out.println("Num of Students: "+gDB.getNumStudents());
		System.out.println("Num of Assignments: "+gDB.getNumAssignments());
    }
    
}
