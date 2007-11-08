package edu.gatech.cs6300;

import com.google.gdata.client.spreadsheet.*;

public class Session {
    
    private SpreadsheetService service;

    /***
     * Facilitates login to Google Spreadsheet
     * @return 1 if success; 0 if failure
     */
    public int login(String username, String password) {
        int failure= 1;
        
        this.service = new SpreadsheetService(Constants.GRADES_DB);
        
        try {
            this.service.setUserCredentials(Constants.USERNAME, Constants.PASSWORD);
            failure = 0;
        } catch(Exception ae) {
            /* Catching AuthenticationException */
        	failure= 1;
        }              
        
        return failure;
    }
    
    /***
     * Logs out of Google Spreadsheet
     * @return 1 if success; 0 if failure
     */
    public int logout() {
        return 1;
    }
    
    public GradesDB getDBByName(String DBName) {
        return new GradesDB();
    }
    
    public SpreadsheetService getService() {
        return this.service;
    }
    
    
}
