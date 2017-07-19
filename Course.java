import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	String courseID;
	int maxStudents;
	
	String instuctor; 
	int section;
	String location; 
	
	ArrayList<Student> enrolled = new ArrayList<Student>(); //all classes start with 0 students
	
	int currentStudents = 0; 
	
	//int currentStudents = enrolled.size(); was going to use this but it doesnt update in real time. 
	

}
