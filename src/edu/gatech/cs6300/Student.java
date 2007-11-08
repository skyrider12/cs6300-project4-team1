package src.edu.gatech.cs6300;

public class Student {
    public String name;
    public String Gtid;
    public int Attendance;
    
    public Student() {
        name = "";
        Gtid = "";
        Attendance = 0;
        // TODO Auto-generated constructor stub
    }

    public String getName(){
        return this.name;
    }
    
    public String getGtid(){        
        return this.Gtid;
    }
    
    public int getAttendance(){ 
        return this.Attendance;
    }
    
}
