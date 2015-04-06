# Task Cards for the Story Cards given. #

1). Develop a class (Project) that gets the information for a project, such as average grade and contribution grades.

2).  Develop a class (Assignment) that gets the information for an assignment, such as average grade and individual grades.

4). Add a function to the **GradesDB** class 'HashSet

&lt;Project&gt;

 getProjects()' which gets the a HashSet of projects available from the online spreadsheet.

5). Add a function to the **GradesDB** class 'HashSet

&lt;Assignment&gt;

 getAssignments()' which gets a HashSet of assignments available from the online spreadsheet.

6). Add a function to the **GradesDB** class 'Project getProjectByName(String name)' which gets a Project based on the name passed in.

7). Add a function to the **GradesDB** class 'Assignment getAssignmentByName(String name)' which gets an Assignment based on the name passed in.

8). Add a function to the **GradesDB** class 'void saveStudentInfo(Student student)' which saves all the data of the student in a file mentioned by the complete file path specified.

9). Develop a new class **'GradesGUI'**(or any other name you like) which provides all the functionality of the User interacting with the application. It should contain the minimum member variables and items as as a Combo box, a TextArea displaying the student's information, and a Save button.

10). Add a function in the **GradesGUI** class - 'void populateComboStudents()' which brings the name of all the students.

11). Add a function in the **GradesGUI** class - 'void onComboIndexChanged()' which displays the complete data of the selected Student when a new student is selected on the Graphical interface - using the above written Modular functions and existing methods like but not limiting to 'getNumProjects()' and 'getNumAssignments'.

12). Add a function in the **GradesGUI** class - 'void onSaveButtonClicked()' which asks the User for the desired file name and the Path where the file needs to be saved and then call the function saveStudentInfo()