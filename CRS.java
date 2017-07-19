
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class CRS implements Serializable {
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	ArrayList<Course> courses = new ArrayList<Course>();
	ArrayList<Student> students = new ArrayList<Student>();
	ArrayList<Admin> admins = new ArrayList<Admin>();
	
	public CRS() {
		
	}
	
	
	//exported course search. Search will loop until correct class found. exported to make code cleaner. 
	public Course findCourse(Scanner scanner) { //TESTED
		
		//scanner.nextLine();
		
		System.out.println("Enter course name or course ID:");
		
		try {
			
			String searchterm = scanner.nextLine();
		
			for (Course crs : courses) {
				
				if (crs.courseID.equalsIgnoreCase(searchterm) || crs.name.equalsIgnoreCase(searchterm)) { //using equals to compare actual value, not reference
					System.out.println("Course found: "+crs.name+" - "+crs.courseID);
					System.out.println("");
					System.out.println("Is this the right course?"); //verify if correct
					System.out.println("");
					System.out.printf("%s\n%s\n\n", "1 - Yes", "2 - No"); //print input options 
					
					int s2 = Integer.parseInt(scanner.nextLine());
					
					if (s2 == 1) { //if right course, return
						return crs;
					}
					
					else { //if wrong course or wrong code 
						return null; 
					}	
				}
				else {
					continue;
				}
			}
		}
		catch (NoSuchElementException err) {
			return null;
		}
		return null;
	}
	
	//exported student search. Search will loop until correct student found. Exported to make code cleaner. 
	public Student findStudent(Scanner scanner) {
		
		System.out.print("Enter student name or student ID: (ex. John Chwe)");
		
		try { //accommodates NoSuchElementException
		
			String searchterm = scanner.nextLine();
			
			for (Student stdnt : students) {
				String wname = stdnt.fname + " " + stdnt.lname;
				
				if (wname.equalsIgnoreCase(searchterm) || stdnt.ID.equalsIgnoreCase(searchterm)) {
					System.out.println("Student found: " + wname + " - "+ stdnt.ID);
					System.out.println("Is this the right student?");
					System.out.printf("%s\n%s\n", "1 - Yes", "2 - No");
					
					int s2 = Integer.parseInt(scanner.nextLine());
					
					if (s2 == 1) { //if right course, return
						return stdnt;
					}
					
					else {
						System.out.println("Student not found or invalid selection code. Please try again.");
						return null; 
					}	
				}
				else {
					continue;
				}
			}
		}
		
		catch (NoSuchElementException err) {//message if not found customized to each method
			return null;
		}
		return null;
	}
	
	//imports class data from csv. stores info in static arraylist in CRS
	public void getCourses() throws FileNotFoundException {
		
		File file = new File("MyUniversityCourses.csv");
		if (!file.exists()) {
			System.err.println("File does not exist.");
			return;
		}
		
		Scanner scan = new Scanner(file);
		
			
		while (scan.hasNextLine()) { //while file hasnt been exhausted
				
			String temp=scan.nextLine(); //read into a string
				
			String[] temphold=temp.split(","); //separate string by commas
				
			Course a=new Course(); //declare and instantiate new course, use temphold to fill course data fields
				
			a.name=temphold[0];
			a.courseID=temphold[1];
			a.maxStudents=Integer.parseInt(temphold[2]);
			a.currentStudents=Integer.parseInt(temphold[3]);
			a.instuctor=temphold[5];
			a.section=Integer.parseInt(temphold[6]);
			a.location=temphold[7];
				
			courses.add(a);
				
		}
			
		scan.close();		
	}	
	
	//reads student info from csv. Students used to test program. 
	public void getStudents() throws FileNotFoundException {
		
		File file = new File("samplestudents.csv");
		if (!file.exists()) {
			System.err.println("File does not exist.");
			System.exit(0);
		}
		
		Scanner scan = new Scanner(file);
		
			
		while (scan.hasNextLine()) { //while file hasnt been exhausted
				
			String temp=scan.nextLine(); //read into a string
				
			String[] temphold=temp.split(","); //separate string by commas
				
			Student std=new Student(); //declare and instantiate new course, use temphold to fill course data fields
				
			std.fname = temphold[0];
			std.lname = temphold[1];
			std.username = temphold[2];
			std.password = temphold[3];
			std.ID = temphold[4];
				
			students.add(std);
		
		}
		scan.close();
	}
}
